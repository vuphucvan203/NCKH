package com.example.nghiencuukhoahoc.Model;

import java.util.ArrayList;
import java.util.List;

public class Convert_Rooms {
    public static List<Rooms> convert(Desired desired){
        List<Rooms> lst = new ArrayList<>();
        lst.add(desired.getBedRoom());
        lst.add(desired.getLivingRoom());
        return lst;
    }
    public static List<Rooms> convertreported(Reported reported){
        List<Rooms> lst = new ArrayList<>();
        lst.add(reported.getBedRoom());
        lst.add(reported.getLivingRoom());
        return lst;
    }
}
