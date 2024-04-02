package com.example.nghiencuukhoahoc.Devices;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.MyViewModel.DataSingleton;
import com.example.nghiencuukhoahoc.R;
import com.example.nghiencuukhoahoc.Timer.TimerDialog;
import com.example.nghiencuukhoahoc.Timer.TimerDialogOnClicked;

import java.util.List;

public class GasActivity extends AppCompatActivity {
    ImageView imgView ,iconFan,img_back;
    Animation animationRotate1,animationRotate2;
    TextView tv_nameRoom,tv_Remaining;
    Switch aSwitchFan;
    private List<Rooms> lst_rooms ;
    private int id_Room,value ,curr_value;
    String name_room;
    private final String TOPIC = "$aws/things/smart_home/shadow/name/test/update";
    private int timeRemaining;
    private ProgressBar progressBarTimer;
    CountDownTimer countDownTimer;
    ImageButton btn_timer;
    private TimerDialog myDialog;
    private AnimationDrawable rocketAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        InitWidgets();
        Intent it = getIntent();
        name_room = it.getStringExtra("name");
        if(name_room.equals("kitchen")){
            tv_nameRoom.setText("Kitchen");
        }else if(name_room.equals("living room")){
            tv_nameRoom.setText("Living room");
        }else if(name_room.equals("garage")) {
            tv_nameRoom.setText("Garage");
        }else
        {
            tv_nameRoom.setText("Bedroom");
        }
        //get current data
        lst_rooms = DataSingleton.getInstance().getSharedData();
        //  test data
        Log.d("GasActivity", "onCreate: "+lst_rooms.get(0).getGas_state()+ "-" +
                lst_rooms.get(0).getName());
        for(int i =0 ; i < lst_rooms.size() ; i++){
            if(lst_rooms.get(i).getName().equals(name_room)){
                value = lst_rooms.get(i).getGas_state();
                id_Room = i;
                break;
            }
        }
        if(value == 0){
            OffGas();
        }else{
            OnGas();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chán quá mãi không fix được lỗi này", "Hairrrr");
                onBackPressed();
            }
        });
        aSwitchFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    OffGas();
                    lst_rooms.get(id_Room).setGas_state(0);
                }else{
                    OnGas();
                    lst_rooms.get(id_Room).setGas_state(1);
                }
                curr_value = b ?  1 : 0 ;
//                String message = processJson.convert_Rooms_to_Json(lst_rooms);
//                Log.i("CHeckJson", "json : "+ message);
                String message = "{\n" +
                        "  \"state\": {\n" +
                        "    \"desired\": {\n" +
                        "        \"livingRoom\": " + "{\n" +
                        "           \"fan_state\": " + curr_value + ",\n" +
                        "           \"name\": \"livingRoom\"\n" +
                        "         }\n" +
                        "     }\n" +
                        "  }\n" +
                        "}";
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            iotConnect.mqttManager.publishString(message, TOPIC, AWSIotMqttQos.QOS0);
//                            Log.i("CheckPublish", "Publish: success");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(FanActivity.this,"Sucess !",Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        } catch (Exception e) {
//                            Log.e("checkPublish", "Publish error: ", e);
//                        }
//                    }
//                }).start();
            }
        });
        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog = new TimerDialog(GasActivity.this, new TimerDialogOnClicked() {
                    @Override
                    public void onButtonTurnONClicked(int hour,int minute,int second) {
                        timeRemaining = (hour*60*60 + minute*60 + second) * 1000;
                        btn_timer.setVisibility(View.INVISIBLE);
                        progressBarTimer.setVisibility(View.VISIBLE);
                        tv_Remaining.setVisibility(View.VISIBLE);
                        Log.d("Timer", "onButtonTurnONClicked: "+ timeRemaining);
                        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
                            @Override
                            public void onTick(long l) {
                                // Cập nhật giá trị đếm ngược trên TextView
                                int secondsRemaining = (int) l / 1000;
                                tv_Remaining.setText(String.valueOf(secondsRemaining));
                                // Cập nhật giá trị progress của ProgressBar
                                int progress = (int) (l / (float) timeRemaining * 100);
                                progressBarTimer.setProgress(progress);
                            }
                            @Override
                            public void onFinish() {
                                curr_value = 1;
                                lst_rooms.get(id_Room).setGas_state(curr_value);
//                                String message = processJson.convert_Rooms_to_Json(lst_rooms);
//                                Log.i("CHeckJson", "json : "+ message);
                                String message = "{\n" +
                                        "  \"state\": {\n" +
                                        "    \"desired\": {\n" +
                                        "        \"livingRoom\": " + "{\n" +
                                        "           \"fan_state\": " + curr_value + ",\n" +
                                        "           \"name\": \"livingRoom\"\n" +
                                        "         }\n" +
                                        "     }\n" +
                                        "  }\n" +
                                        "}";
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            iotConnect.mqttManager.publishString(message, TOPIC, AWSIotMqttQos.QOS0);
//                                            Log.i("CheckPublish", "Publish: success");
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Toast.makeText(FanActivity.this,"Turn ON !",Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//                                        } catch (Exception e) {
//                                            Log.e("checkPublish", "Publish error: ", e);
//                                        }
//                                    }
//                                }).start();
                                OnGas();
                                btn_timer.setVisibility(View.VISIBLE);
                                progressBarTimer.setVisibility(View.INVISIBLE);
                                tv_Remaining.setVisibility(View.INVISIBLE);
                            }
                        };
                        countDownTimer.start();
                    }
                    @Override
                    public void onButtonTurnOFFClicked(int hour,int minute, int second) {
                        timeRemaining = (hour * 60 * 60 + minute * 60 + second) * 1000;
                        btn_timer.setVisibility(View.INVISIBLE);
                        progressBarTimer.setVisibility(View.VISIBLE);
                        tv_Remaining.setVisibility(View.VISIBLE);
                        Log.d("Timer", "onButtonTurnONClicked: " + timeRemaining);
                        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
                            @Override
                            public void onTick(long l) {
                                // Cập nhật giá trị đếm ngược trên TextView
                                int secondsRemaining = (int) l / 1000;
                                tv_Remaining.setText(String.valueOf(secondsRemaining));
                                // Cập nhật giá trị progress của ProgressBar
                                int progress = (int) (l / (float) timeRemaining * 100);
                                progressBarTimer.setProgress(progress);
                            }

                            @Override
                            public void onFinish() {
                                curr_value = 0;
                                lst_rooms.get(id_Room).setGas_state(curr_value);
//                                String message = processJson.convert_Rooms_to_Json(lst_rooms);
//                                Log.i("CHeckJson", "json : " + message);
                                String message = "{\n" +
                                        "  \"state\": {\n" +
                                        "    \"desired\": {\n" +
                                        "        \"livingRoom\": " + "{\n" +
                                        "           \"fan_state\": " + curr_value + ",\n" +
                                        "           \"name\": \"livingRoom\"\n" +
                                        "         }\n" +
                                        "     }\n" +
                                        "  }\n" +
                                        "}";
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            iotConnect.mqttManager.publishString(message, TOPIC, AWSIotMqttQos.QOS0);
//                                            Log.i("CheckPublish", "Publish: success");
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Toast.makeText(FanActivity.this, "Turn OFF !", Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//                                        } catch (Exception e) {
//                                            Log.e("checkPublish", "Publish error: ", e);
//                                        }
//                                    }
//                                }).start();
                                OffGas();
                                btn_timer.setVisibility(View.VISIBLE);
                                progressBarTimer.setVisibility(View.INVISIBLE);
                                tv_Remaining.setVisibility(View.INVISIBLE);
                            }
                        };
                        countDownTimer.start();
                    }
                });
            }
        });
    }
}
    private void InitWidgets() {
        tv_nameRoom = findViewById(R.id.tv_nameRoom_fan);
        imgView = findViewById(R.id.img_fan);
        iconFan = findViewById(R.id.icon_fan);
        aSwitchFan = findViewById(R.id.switch_fan);
        img_back = findViewById(R.id.img_back_fan);
        imgView.setBackgroundResource(R.drawable.toto);
        rocketAnimation = (AnimationDrawable) imgView.getBackground();
        progressBarTimer = findViewById(R.id.progressBar_timeRemaining_fan);
        tv_Remaining = findViewById(R.id.tv_countdownTime_fan);
        btn_timer = findViewById(R.id.btn_timer_fan);
    }
    private void OnGas(){
        imgView.setBackgroundResource(R.drawable.toto);
        rocketAnimation = (AnimationDrawable) imgView.getBackground();
        rocketAnimation.start();
        aSwitchFan.setChecked(true);
        aSwitchFan.setText("ON");
    }
    private void OffGas(){
        rocketAnimation.stop();
        aSwitchFan.setChecked(false);
        aSwitchFan.setText("OFF");
    }
}