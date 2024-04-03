package com.example.nghiencuukhoahoc.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nghiencuukhoahoc.Fragment.AllRoomFragment;
import com.example.nghiencuukhoahoc.Fragment.BedRoomFragment;
import com.example.nghiencuukhoahoc.Fragment.GarageFragment;
import com.example.nghiencuukhoahoc.Fragment.KitchenFragment;
import com.example.nghiencuukhoahoc.Fragment.LivingRoomFragment;
import com.example.nghiencuukhoahoc.Fragment.RoomFragment;
import com.example.nghiencuukhoahoc.MyViewModel.RoomsViewModel;

public class FragmentAdapter extends FragmentStateAdapter {
    //    private ViewPager2 viewPager2;
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    //    public void setViewPager2(ViewPager2 viewPager2){
//        this.viewPager2 = viewPager2;
//    }
    private RoomsViewModel roomsViewModel;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, RoomsViewModel roomsViewModel) {
        super(fragmentActivity);
        this.roomsViewModel = roomsViewModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        if (position == 0) return new AllRoomFragment(roomsViewModel);
        else return new RoomFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
