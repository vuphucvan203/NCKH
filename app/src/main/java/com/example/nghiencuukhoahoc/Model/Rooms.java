package com.example.nghiencuukhoahoc.Model;

public class Rooms {
    private String name;
    private float temperature, humadity, gas;
    private int Number_devices;

    public int getFan_state() {
        return fan_state;
    }

    public void setFan_state(int fan_state) {
        this.fan_state = fan_state;
    }

    private int fan_state;

    public int getGas_state() {
        return gas_state;
    }

    public void setGas_state(int gas_state) {
        this.gas_state = gas_state;
    }

    private int gas_state;
    public Rooms(String name, int gas_state,int fan_state) {
        this.name = name;
        this.gas_state=gas_state;
        this.fan_state=fan_state;
    }
    public Rooms(){
        this.name = "Not availble";
    }

    public int getNumber_devices(){
        int count = 0;
        if(temperature != 0){
            count++;
        }if(humadity != 0){
            count++;
        }if(gas_state != -1){
            count++;
        }if(fan_state != -1){
            count++;
        }
        return count;
    }
    public float getHumadity() {
        return humadity;
    }
    public void setHumadity(float humadity) {
        this.humadity = humadity;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getTemperature() {
        return temperature;
    }
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    public float getHumidity() {
        return humadity;
    }
//    public float getGas() {
//        return gas;
//    }
    public void setHumidity(float humidity) {
        this.humadity = humidity;
    }
}
