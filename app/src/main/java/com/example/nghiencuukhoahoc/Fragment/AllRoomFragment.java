package com.example.nghiencuukhoahoc.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nghiencuukhoahoc.Adapter.RoomAdapter;
import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.MyViewModel.RoomsViewModel;
import com.example.nghiencuukhoahoc.R;

import java.util.List;

public class AllRoomFragment extends Fragment  {
    private RecyclerView rcv_room;
    private RoomAdapter roomAdapter;
    private RoomsViewModel roomsViewModel ;
//    ViewPager2 viewPager2;
    public AllRoomFragment( ) {

        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        //binding listener
        rcv_room = view.findViewById(R.id.recyclerview);
        rcv_room.setHasFixedSize(true);
        roomAdapter = new RoomAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rcv_room.setLayoutManager(linearLayoutManager);
        rcv_room.setAdapter(roomAdapter);
        roomsViewModel = new ViewModelProvider(getActivity()).get(RoomsViewModel.class);
        roomsViewModel.getLst_liveData().observe(getViewLifecycleOwner(), new Observer<List<Rooms>>() {
            @Override
            public void onChanged(List<Rooms> rooms) {
                Log.d("Fragment", "onChanged: "+ rooms.get(0).getTemperature());
                roomAdapter.setDataRoom(rooms);
            }
        });
        return view;
    }


}