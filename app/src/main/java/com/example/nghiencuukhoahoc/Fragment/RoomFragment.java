package com.example.nghiencuukhoahoc.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nghiencuukhoahoc.Adapter.DeviceAdapter;
import com.example.nghiencuukhoahoc.Adapter.onItemClickListener;
import com.example.nghiencuukhoahoc.Devices.FanActivity;
import com.example.nghiencuukhoahoc.Devices.GazActivity;
import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.MyViewModel.RoomsViewModel;
import com.example.nghiencuukhoahoc.R;

import java.util.List;

public class RoomFragment extends Fragment implements onItemClickListener {
    private RecyclerView rcv_devices;
    private DeviceAdapter adapterDevices;
    private RoomsViewModel roomsViewModel ;
    public RoomFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        rcv_devices = view.findViewById(R.id.recyclerview);
        rcv_devices.setHasFixedSize(true);

        adapterDevices = new DeviceAdapter(this);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rcv_devices.setLayoutManager(linearLayoutManager);

        //getData();

        rcv_devices.setAdapter(adapterDevices);
        roomsViewModel = new ViewModelProvider(getActivity()).get(RoomsViewModel.class);
        roomsViewModel.getLst_liveData().observe(getViewLifecycleOwner(),
                new Observer<List<Rooms>>() {
                    @Override
                    public void onChanged(List<Rooms> rooms) {
                        for(int i = 0 ; i < rooms.size(); i++){
                            Log.e("Type room: ", rooms.get(i).getName());
                            if(rooms.get(i).getName().equals("bedroom")){
                                adapterDevices.SetData(rooms.get(i));
                                break;
                            }
                            if(rooms.get(i).getName().equals("kitchen")){
                                adapterDevices.SetData(rooms.get(i));
                                break;
                            }
                            if(rooms.get(i).getName().equals("living room")){
                                adapterDevices.SetData(rooms.get(i));
                                break;
                            }
                            if(rooms.get(i).getName().equals("garage")){
                                adapterDevices.SetData(rooms.get(i));
                                break;
                            }
                        }
                    }
                });
        return view;
    }
    @Override
    public void onItemClick(int position, String name, int value) {
        Log.i("intent", "onItemClick: " + position);
        if(value != -1) {
            if (position == 0) { // Gas
                Intent intent = new Intent(getActivity(), GazActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("value", value);
                startActivity(intent);
            }else if (position == 1) { //Fan
                Intent intent = new Intent(getActivity(), FanActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),"Not Available !!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}