package com.example.nghiencuukhoahoc.MyViewModel;

import com.example.nghiencuukhoahoc.Model.Rooms;

import java.util.ArrayList;
import java.util.List;

public class DataSingleton {
    private static DataSingleton instance;
    private List<Rooms> sharedData;

    private DataSingleton() {
        // Khởi tạo dữ liệu
        sharedData = new ArrayList<>();
    }
    public static synchronized DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }
    public List<Rooms> getSharedData() {
        return sharedData;
    }
    public void setSharedData(List<Rooms> data) {
        sharedData = data;
    }

}
