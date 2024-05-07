package com.example.nghiencuukhoahoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nghiencuukhoahoc.Adapter.FragmentAdapter;
import com.example.nghiencuukhoahoc.ConnectIot.IotConnect;
import com.example.nghiencuukhoahoc.ConnectUser.SignInActivity;
import com.example.nghiencuukhoahoc.Model.Convert_Rooms;
import com.example.nghiencuukhoahoc.Model.ProcessJson;
import com.example.nghiencuukhoahoc.Model.Reported;
import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.Model.User;
import com.example.nghiencuukhoahoc.MyViewModel.RoomsViewModel;
import com.example.nghiencuukhoahoc.Service.ApplicationService;
import com.example.nghiencuukhoahoc.Service.MyFirebaseMessagingService;
import com.example.nghiencuukhoahoc.WeatherForcast.DataWeather;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String CHANEL_ID ="push_notification_id" ;
    final String LOG_TAG = "checkConnect" ,
            TOPIC_GET_SUBCRIBE = "$aws/things/esp/shadow/name/test/get/accepted",
            TOPIC_GET_PUBLISH = "$aws/things/esp/shadow/name/test/get",
            TOPIC_UPDATE_SUBCRIBE = "$aws/things/esp/shadow/name/test/update/accepted";
    private static IotConnect iotConnect;
    private static final String TAG= MainActivity.class.getName();
    private TextView TvWeather, TvTemperature,
            TvHumidityAndWind;
    private TextView username,us2;
    private ImageView imgWeather,avartar;
    private ImageButton img_btn_Logout;
    private FloatingActionButton addbtn;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter fragmentAdapter;
    private FirebaseAuth auth;
    Dialog dialog;
    private Reported reported;
    String weather,temper;

    RequestQueue requestQueue;
    DataWeather dataWeather;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private RoomsViewModel roomsViewModel;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitWidgets();
        getWeatherForcast();
        getTabLayOut();
        iotConnect = IotConnect.getInstance(getApplicationContext());
//        createWeatherNotification();
        roomsViewModel = new ViewModelProvider(this).get(RoomsViewModel.class);
        initEvent();
        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser().getEmail()!=null) username.setText(auth.getCurrentUser().getEmail());
        else if(auth.getCurrentUser().getPhoneNumber()!=null) username.setText(auth.getCurrentUser().getPhoneNumber());
        pasfb();
    }
    private void pasfb()
    {
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users")
                .child(firebaseUser.getUid()).child("room");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot drinkSnapshot:snapshot.getChildren()){
                        Rooms room = drinkSnapshot.getValue(Rooms.class);
                        roomsViewModel.addRoom(room);
                    }
                }
                getTabLayOut();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createWeatherNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel chanel= new NotificationChannel(CHANEL_ID,"Weather Notification"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chanel);
        }
    }
    private void sendNotification(){
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Notification notification=new NotificationCompat.Builder(this, ApplicationService.CHANNEL_ID)
                .setContentTitle("SmartHome Notification")
                .setContentText("Excessive Heat.Becareful!")
                .setSmallIcon(R.drawable.anh1)
                .setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.Instagram3))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null){
            notificationManager.notify(getNotificationID(),notification);
        }
    }

    private int getNotificationID() {
        return (int)new Date().getTime();
    }

    private void initEvent()
    {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        img_btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        String name = bundle.getString("Name");
        int gas = bundle.getInt("Gas");
        int fan = bundle.getInt("Fan");
        Rooms  room = new Rooms(name,gas,fan);
        Log.e("Name:", name);
        Log.d("Gas:", Integer.toString(gas));
        Log.d("Fan:", Integer.toString(fan));
        if(requestCode == 100 && resultCode == 150)
        {
            roomsViewModel.addRoom(room);
            reference.child(name).setValue(room).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void avoid) {
                    Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void InitWidgets() {
        us2=findViewById(R.id.tot);
        username=findViewById(R.id.TvNameClient);
        img_btn_Logout = findViewById(R.id.img_btn_logOut);
        imgWeather = findViewById(R.id.imgforcast);
        TvWeather = findViewById(R.id.TvWeather);
        TvTemperature = findViewById(R.id.TvTemperature);
        TvHumidityAndWind = findViewById(R.id.TvHumidityAndWind);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewPage2);
        avartar = findViewById(R.id.avatar);
        addbtn=findViewById(R.id.fl_btn);

    }
    private void getTabLayOut() {
        tabLayout.addTab(tabLayout.newTab().setText("All room"));
        tabLayout.addTab(tabLayout.newTab().setText("Living room"));
        tabLayout.addTab(tabLayout.newTab().setText("Bedroom"));
        tabLayout.addTab(tabLayout.newTab().setText("Kitchen"));
        tabLayout.addTab(tabLayout.newTab().setText("Garage"));
        roomsViewModel = new ViewModelProvider(this).get(RoomsViewModel.class);
        roomsViewModel.getLst_liveData().observe(this, new Observer<List<Rooms>>() {
            @Override
            public void onChanged(List<Rooms> rooms) {
                updateTabs(rooms);
            }
        });

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                if(tab.getPosition()!=0)
                    addbtn.hide();
                else if(tab.getPosition()==0) addbtn.show();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void updateTabs(List<Rooms> rooms) {
        tabLayout.removeAllTabs(); // Xóa tất cả các tab hiện có
        tabLayout.addTab(tabLayout.newTab().setText("All room"));
        // Thêm tab mới vào TabLayout với tên phòng tương ứng
        for (Rooms room : rooms) {
            tabLayout.addTab(tabLayout.newTab().setText(capitalizeFirstLetter(room.getName())));
            //fragmentAdapter.createFragment(1);
        }
    }

    private void getWeatherForcast() {
        DecimalFormat df = new DecimalFormat("#.#");
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=hanoi&appid=e66fcd1f23309eebcfa9ff608c396567";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý phản hồi từ API ở đây
                        Log.d("API Response", response);
                        weather = response;
                        dataWeather = ProcessJson.gson.fromJson(weather,DataWeather.class);

                        String temp = df.format((float) (dataWeather.getMain().temp - 273.5));
                        temper=temp;
                        String wind = df.format((float) (dataWeather.getWind().speed * 3.6));
                        int humidity = (int) dataWeather.getMain().humidity;
                        String weather_de = capitalizeFirstLetter(dataWeather.getWeather().get(0).description);
                        TvWeather.setText(weather_de);
                        TvTemperature.setText(temp + "°");
                        TvHumidityAndWind.setText("H:"+ humidity+"% W:"+wind);
                        if (Float.parseFloat(temp) > 30) {
                            createWeatherNotification();
                            sendNotification();
                        }
                        //Log.d("wweather", "H:"+ humidity+"% W:"+wind);
                        if(weather_de.contains("sun")){
                            imgWeather.setImageResource(R.drawable.sunny);
                        }
                        else if(weather_de.contains("rain")){
                            imgWeather.setImageResource(R.drawable.rainny);
                        }
                        else if(weather_de.contains("cloud")){
                            imgWeather.setImageResource(R.drawable.clouds);
                        }
                        else {
                            imgWeather.setImageResource(R.drawable.weather_normal);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi nếu có
                        Log.e("API Error", error.toString());
                    }
                });
        requestQueue.add(stringRequest);
    }
    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!iotConnect.isConnected()){
                    Log.i("checkKetNoi", "chua ket noi");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("checkKetNoi", "da ket noi");
                try {
                    iotConnect.mqttManager.subscribeToTopic(TOPIC_GET_SUBCRIBE,
                            AWSIotMqttQos.QOS0 /* Quality of Service */,
                            new AWSIotMqttNewMessageCallback() {
                                @Override
                                public void onMessageArrived(final String topic, final byte[] data) {
                                    try {
                                        String message = new String(data, "UTF-8");
                                        Log.d("check_message_receive", "Message received: " + message);
                                        reported = ProcessJson.convert_Json_to_object_reported(message);
//                                        Log.d("Device", "temperature: " + reported.getBedRoom().getTemperature()
//                                                + " and " + reported.getBedRoom().getHumidity());
                                        List<Rooms> lst_rooms = Convert_Rooms.convertreported(reported);
//                                        Log.d("checkRooms", lst_rooms.get(0).getTemperature() + "-");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                roomsViewModel.setData(lst_rooms);
//                                                dialog.show();
//                                                vibrator.vibrate(2000);
                                            }
                                        });
                                    } catch (UnsupportedEncodingException e) {
                                        Log.e("check_message_receive", "Message encoding error: ", e);
                                    }
                                }
                            });
                } catch (Exception e) {
                    Log.e("check_message_receive", "Subscription error: ", e);
                }
                try {
                    iotConnect.mqttManager.subscribeToTopic(TOPIC_UPDATE_SUBCRIBE,
                            AWSIotMqttQos.QOS0 /* Quality of Service */,
                            new AWSIotMqttNewMessageCallback() {
                                @Override
                                public void onMessageArrived(final String topic, final byte[] data) {
                                    try {
                                        String message = new String(data, "UTF-8");
                                        Log.d("check_message_receive", "Message received: " + message);
                                        reported = ProcessJson.convert_Json_to_object_reported(message);
//                                        Log.d("Device", "temperature: " +
//                                                reported.getBedRoom().getTemperature()
//                                                + " and " + reported.getBedRoom().getHumidity());
                                        List<Rooms> lst_rooms = Convert_Rooms.convertreported(reported);
//                                        Log.d("checkRooms", lst_rooms.get(0).getTemperature() + "-");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                roomsViewModel.setData(lst_rooms);
                                                for(int i =0 ; i< lst_rooms.size() ; i++){
                                                    if(lst_rooms.get(i).getGas_state() == 0){
                                                        dialog.show();
                                                        vibrator.vibrate(2000);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (UnsupportedEncodingException e) {
                                        Log.e("check_message_receive", "Message encoding error: ", e);
                                    }
                                }
                            });
                } catch (Exception e) {
                    Log.e("check_message_receive", "Subscription error: ", e);
                }
                try {
                    Log.i("CheckPublish", "Publish: success");
                    iotConnect.mqttManager.publishString("", TOPIC_GET_PUBLISH, AWSIotMqttQos.QOS0);
                } catch (Exception e) {
                    Log.e("checkPublish", "Publish error: ", e);
                }
            }
        }).start();
    }
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Chuyển chữ cái đầu thành chữ hoa
        String firstLetter = str.substring(0, 1).toUpperCase();
        // Lấy phần còn lại của chuỗi
        String remainingLetters = str.substring(1);

        // Kết hợp chữ cái đầu viết hoa với phần còn lại của chuỗi
        return firstLetter + remainingLetters;
    }

}