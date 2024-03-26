package com.example.nghiencuukhoahoc.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    public RoomsViewModel getRoomsViewModel() {
        return roomsViewModel;
    }

    //    ViewPager2 viewPager2;
    public AllRoomFragment(RoomsViewModel roomsViewModel ) {
        this.roomsViewModel = roomsViewModel;
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
        roomsViewModel = new ViewModelProvider(getActivity()).get(RoomsViewModel.class);
        roomsViewModel.getLst_liveData().observe(getViewLifecycleOwner(), new Observer<List<Rooms>>() {
            @Override
            public void onChanged(List<Rooms> rooms) {
                Log.d("Fragment", "onChanged: "+ rooms.get(0).getTemperature());
                roomAdapter.setDataRoom(rooms);
                rcv_room.setAdapter(roomAdapter);
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit)
        {
            Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
            return  true;
        } else if (item.getItemId() == R.id.action_delete) {
            Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
            return  true;
        } else {
            return super.onContextItemSelected(item);
        }

    }

}