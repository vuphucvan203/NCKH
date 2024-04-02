package com.example.nghiencuukhoahoc.Adapter;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context mContext;
    private List<Rooms> lstRoom;
    private onItemClickListener listener;
    public RoomAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setDataRoom(List<Rooms> l){
        this.lstRoom = l;
        notifyDataSetChanged();
    }
    public Rooms getItem(int position) {
        return lstRoom.get(position);
    }
    private int selectedPosition = -1;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room,parent,false);
        return new RoomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Rooms currRoom = lstRoom.get(position);
        if(currRoom == null) return;

        switch (currRoom.getName()){
            case "bedroom" :
                holder.tvNameRoom.setText("Bedroom");
                holder.imgRoom.setImageResource(R.drawable.bedroom);
                holder.tvDevices.setText(currRoom.getNumber_devices() + " Devices");
                break;
            case "living room" :
                holder.tvNameRoom.setText("Living room");
                holder.imgRoom.setImageResource(R.drawable.livingroom);
                holder.tvDevices.setText(currRoom.getNumber_devices() + " Devices");
                break;
            case "kitchen":
                holder.tvNameRoom.setText("Kitchen");
                holder.imgRoom.setImageResource(R.drawable.kitchen);
                holder.tvDevices.setText(currRoom.getNumber_devices() + " Devices");
                break;
            case "garage":
                holder.tvNameRoom.setText("Garage");
                holder.imgRoom.setImageResource(R.drawable.garage);
                holder.tvDevices.setText(currRoom.getNumber_devices() + " Devices");
                break;

        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setSelectedPosition(holder.getAdapterPosition());
                notifyDataSetChanged(); // Cập nhật lại adapter để cập nhật giao diện người dùng
                return false;
            }
        });

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select Action");
                menu.add(Menu.NONE, R.id.action_edit, Menu.NONE, "Edit");
                menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, "Delete");
            }
        });

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    holder.aSwitch.setText("On");
                }else{
                    holder.aSwitch.setText("Off");
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        if (lstRoom != null) {
            return lstRoom.size();
        } else {
            return 0;
        }
    }
    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgRoom;
        private TextView tvNameRoom,tvDevices;
        private Switch aSwitch;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.image_room);
            tvNameRoom = itemView.findViewById(R.id.room_name);
            tvDevices = itemView.findViewById(R.id.number_devices);
            aSwitch = itemView.findViewById(R.id.switch_room);
        }

    }
}
