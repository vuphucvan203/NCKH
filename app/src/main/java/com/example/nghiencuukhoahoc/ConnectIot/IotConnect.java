package com.example.nghiencuukhoahoc.ConnectIot;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.AttachPolicyRequest;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class IotConnect {
    private static IotConnect instance;
    private static String END_POINT = "a2o5nkg8r9u46f-ats.iot.us-east-1.amazonaws.com";
    public AWSIotMqttManager mqttManager;
    private CognitoCredentialsProvider credentialsProvider;
    final String policyName = "MyPolicy" ,
            MyRegion = "us-east-1";
    int STATUS;
    private final String TOPIC_GET = "$aws/things/esp/shadow/name/test/get";
    // Private constructor để ngăn việc tạo đối tượng từ bên ngoài
    //SingleTon
    // Phương thức tĩnh để truy cập vào instance
    private Context mContext;
    public static synchronized  IotConnect getInstance(Context mContext) {
        if (instance == null) {
            // Nếu instance chưa được khởi tạo, tạo mới nó
            instance = new IotConnect(mContext);
        }
        return instance;
    }
    public IotConnect(Context mContext) {
        this.mContext = mContext;
        String clientId = UUID.randomUUID().toString();
        // Initialize the AWS Cognito credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                mContext, // context
                "us-east-1:cb15b6a4-9aca-421e-a62f-f0615fdf5cb4", // Identity Pool ID
                Regions.US_EAST_1
        );
        // Initialize the AWSIotMqttManager with the configuration
        mqttManager = new AWSIotMqttManager(
                clientId,
                END_POINT);
        connect();
    }
    public void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // attach Policy
                AttachPolicyRequest attachPolicyReq = new AttachPolicyRequest();
                attachPolicyReq.setPolicyName(policyName); // name of your IOT AWS policy
                attachPolicyReq.setTarget(AWSMobileClient.getInstance().getIdentityId());
                AWSIotClient mIotAndroidClient = new AWSIotClient(AWSMobileClient.getInstance());
                mIotAndroidClient.setRegion(Region.getRegion(MyRegion)); // name of your IoT Region such as "us-east-1"
                mIotAndroidClient.attachPolicy(attachPolicyReq);
                try {
                    mqttManager.connect( credentialsProvider, new AWSIotMqttClientStatusCallback() {
                        @Override
                        public void onStatusChanged(final AWSIotMqttClientStatus status, final Throwable throwable) {
                            Log.d("CheckConnection", "Connection Status: " + status);
                            if(status.toString().equals("Connected") ){
                                Log.i("CheckStatus", "1");
                                STATUS = 1;
                            }
                        }
                    });
                } catch (final Exception e) {
                    Log.e("CheckConnection", "Connection error: ", e);
                }
            }
        }).start();
    }
    public void Publish(String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isConnected()){
                    Log.i("checkKetNoi", "chua ket noi");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Log.i("CheckPublish", "Publish: success");
                    mqttManager.publishString(message, TOPIC_GET, AWSIotMqttQos.QOS0);
                } catch (Exception e) {
                    Log.e("checkPublish", "Publish error: ", e);
                }
            }
        }).start();

    }
    public void SubcribetoTopic(String topic){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isConnected()){
                    Log.i("checkKetNoi", "chua ket noi");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("checkKetNoi", "da ket noi");
                try {
                    mqttManager.subscribeToTopic(topic, AWSIotMqttQos.QOS0 /* Quality of Service */,
                            new AWSIotMqttNewMessageCallback() {
                                @Override
                                public void onMessageArrived(final String topic, final byte[] data) {
                                    try {
                                        String message = new String(data, "UTF-8");
                                        Log.d("check_message_receive", "Message received: " + message);
                                    } catch (UnsupportedEncodingException e) {
                                        Log.e("check_message_receive", "Message encoding error: ", e);
                                    }
                                }
                            });
                } catch (Exception e) {
                    Log.e("check_message_receive", "Subscription error: ", e);
                }
            }
        }).start();
    }
    public boolean isConnected(){
        return STATUS == 1 ? true : false ;
    }
    public void UnSubcribeTopic(String topic){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (STATUS != 1){
                    Log.i("checkKetNoi", "chua ket noi");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    mqttManager.unsubscribeTopic(topic);
                } catch (Exception e) {
                    Log.e("UnsubcribeTopic", "Unsubscribe error: ", e);
                }

            }
        }).start();
    }
    public void disconnect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mqttManager.disconnect();
                    Log.i("disConnected", "disconnected");
                } catch (Exception e) {
                    Log.e("disConnected", "Disconnect error: ", e);
                }
            }
        });
    }
}

