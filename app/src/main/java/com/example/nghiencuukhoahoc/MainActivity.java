package com.example.nghiencuukhoahoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nghiencuukhoahoc.Adapter.FragmentAdapter;
import com.example.nghiencuukhoahoc.Model.ProcessJson;
import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.MyViewModel.RoomsViewModel;
import com.example.nghiencuukhoahoc.WeatherForcast.DataWeather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView TvWeather, TvTemperature,
            TvHumidityAndWind;
    private ImageView imgWeather,avartar;
    private ImageButton img_btn_Logout;
    private FloatingActionButton addbtn;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter fragmentAdapter;
    Dialog dialog;

    String weather;

    RequestQueue requestQueue;
    DataWeather dataWeather;
    private RoomsViewModel roomsViewModel;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitWidgets();
        getWeatherForcast();
        getTabLayOut();
        roomsViewModel = new ViewModelProvider(this).get(RoomsViewModel.class);
        initEvent();

    }
    private void initEvent()
    {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_room();
            }
        });
    }

    private void add_room() {
        Rooms  a=new Rooms("garage",90,99,-1,-1);
        roomsViewModel.addRoom(a);
    }

    private void InitWidgets() {
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
        tabLayout.addTab(tabLayout.newTab().setText("All Room"));
        tabLayout.addTab(tabLayout.newTab().setText("Bed Room"));
        tabLayout.addTab(tabLayout.newTab().setText("Living Room"));
        tabLayout.addTab(tabLayout.newTab().setText("Kitchen"));
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
                        String wind = df.format((float) (dataWeather.getWind().speed * 3.6));
                        int humidity = (int) dataWeather.getMain().humidity;
                        String weather_de = capitalizeFirstLetter(dataWeather.getWeather().get(0).description);
                        TvWeather.setText(weather_de);
                        TvTemperature.setText(temp + "°");
                        TvHumidityAndWind.setText("H:"+ humidity+"% W:"+wind);
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