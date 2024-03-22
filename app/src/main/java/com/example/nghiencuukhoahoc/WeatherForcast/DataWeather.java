package com.example.nghiencuukhoahoc.WeatherForcast;

import java.util.List;

public class DataWeather {
    List<Weather> weather;
    Main main;
    Wind wind;

    public List<com.example.nghiencuukhoahoc.WeatherForcast.Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<com.example.nghiencuukhoahoc.WeatherForcast.Weather> weather) {
        this.weather = weather;
    }

    public com.example.nghiencuukhoahoc.WeatherForcast.Main getMain() {
        return main;
    }

    public void setMain(com.example.nghiencuukhoahoc.WeatherForcast.Main main) {
        this.main = main;
    }

    public com.example.nghiencuukhoahoc.WeatherForcast.Wind getWind() {
        return wind;
    }

    public void setWind(com.example.nghiencuukhoahoc.WeatherForcast.Wind wind) {
        this.wind = wind;
    }
}
