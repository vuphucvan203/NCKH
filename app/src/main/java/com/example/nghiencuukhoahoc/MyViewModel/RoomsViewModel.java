package com.example.nghiencuukhoahoc.MyViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nghiencuukhoahoc.Model.Rooms;

import java.util.ArrayList;
import java.util.List;

public class RoomsViewModel extends ViewModel {
    private MutableLiveData<List<Rooms>> LiveData ;
    private DataSingleton dataSingleton ;
    List<Rooms> lst;

    public RoomsViewModel() {
        LiveData = new MutableLiveData<>();
        dataSingleton = DataSingleton.getInstance();
        initData();
    }

    public MutableLiveData<List<Rooms>> getLst_liveData() {
        return LiveData;
    }
    private void initData() {
        lst  = new ArrayList<>();
        lst.add(new Rooms("living room",0,0));
        LiveData.setValue(lst);
        dataSingleton.setSharedData(lst);
    }

    public void setData(List<Rooms> m_lst){
        LiveData.setValue(m_lst);
        dataSingleton.setSharedData(m_lst);
    }
    public void addRoom(Rooms rooms)
    {
        lst.add(rooms);
        setData(lst);
    }
    public void removeRoom(Rooms room){
        lst.remove(room);
        setData(lst);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
