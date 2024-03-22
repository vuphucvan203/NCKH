package com.example.nghiencuukhoahoc.Model;

import com.google.gson.Gson;

import java.util.List;

public class ProcessJson {
    public static Gson gson = new Gson();

    public static Desired convert_Json_to_object_desired(String json_string){
        com.example.nghiencuukhoahoc.Model.MyData mydata = gson.fromJson(json_string,MyData.class);
        State state = mydata.getState();
        return state.getDesired();
    }
    public static Reported convert_Json_to_object_reported(String json_string){
        com.example.nghiencuukhoahoc.Model.MyData mydata = gson.fromJson(json_string,MyData.class);
        State state = mydata.getState();
        return state.getReported();
    }
    public static String convert_Rooms_to_Json(List<Rooms> lst_rooms){
        if(lst_rooms == null){
            return "";
        }
        Rooms bedRoom= new Rooms(), livingRoom = new Rooms(),kitchen =new Rooms();
        for(int i = 0 ; i < lst_rooms.size();i++){
            if(lst_rooms.get(i).getName().equals("bedRoom")){
                bedRoom = lst_rooms.get(i);
            }
            if(lst_rooms.get(i).getName().equals("livingRoom")){
                livingRoom = lst_rooms.get(i);
            }
            if(lst_rooms.get(i).getName().equals("kitchen")){
                kitchen = lst_rooms.get(i);
            }
        }
        Desired desired = new Desired(livingRoom,bedRoom,kitchen);
        State state = new State(desired);
        MyData mydata = new MyData(state);
        String json_res = gson.toJson(mydata);
        return json_res;
    }
}
