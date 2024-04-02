package com.example.nghiencuukhoahoc.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.R;

import java.text.DecimalFormat;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceAdapter.devicesViewHolder>{
    private Rooms rooms ;
    Animation animationRotate;
    private onItemClickListener listener;
    public DeviceAdapter( onItemClickListener listener) {
        this.listener = listener;
    }
    public void SetData(Rooms _rooms){
        this.rooms = _rooms;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public devicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        animationRotate = AnimationUtils.loadAnimation(parent.getContext(),
                R.anim.rotate);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices,parent,false);
        return new devicesViewHolder(view,listener);
    }
    @Override
    public void onBindViewHolder(@NonNull devicesViewHolder holder, int position) {
        //  devices d = list_devices.get(position);
        DecimalFormat df = new DecimalFormat("#.#");

//        if(position == 0){ //temperature
//            holder.img_devices.setImageResource(R.drawable.temperature);
//            holder.tv_index.setText(df.format(rooms.getTemperature()) + "°" );
//            if(rooms.getTemperature() == 0){
//                holder.img_status.setImageResource(R.drawable.red_dot);
//                holder.tv_status.setText("Not available");
//            }
//            else if(rooms.getTemperature() >= 40){
//                holder.img_status.setImageResource(R.drawable.warning);
//                holder.tv_status.setText("Warning !!!");
//            }
//            else {
//                holder.img_status.setImageResource(R.drawable.normal);
//                holder.tv_status.setText("Normal");
//            }
//        }
//        else if(position ==1 ) {// humidity
//            holder.img_devices.setImageResource(R.drawable.humidity);
//            holder.tv_index.setText(df.format(rooms.getHumidity()) + "%" );
//            if(rooms.getTemperature() == 0){
//                holder.img_status.setImageResource(R.drawable.red_dot);
//                holder.tv_status.setText("Not available");
//            }
//            else {
//                holder.img_status.setImageResource(R.drawable.normal);
//                holder.tv_status.setText("Normal");
//            }
//        }
        if(position == 0){ // gas
            holder.img_devices.setImageResource(R.drawable.gasdetector);
            if(rooms.getGas_state() == -1){
                holder.tv_index.setVisibility(View.INVISIBLE);
                holder.img_status.setImageResource(R.drawable.red_dot);
                holder.tv_status.setText("Not available");
            }
            else if(rooms.getGas_state() == 1){
                holder.tv_index.setTextSize(12);
                holder.tv_index.setText("Detected gas!");
                holder.img_status.setImageResource(R.drawable.warning);
                holder.tv_status.setText("Warning !!!");
            }
            else {
                holder.tv_index.setTextSize(12);
                holder.tv_index.setText("Not detect!");
                holder.img_status.setImageResource(R.drawable.normal);
                holder.tv_status.setText("Normal");
            }
        }else if(position == 1){ // Fan
            holder.img_devices.setImageResource(R.drawable.fan);
            if(rooms.getFan_state() == -1){
                holder.tv_index.setVisibility(View.INVISIBLE);
                holder.img_status.setImageResource(R.drawable.red_dot);
                holder.tv_status.setText("Not available !");
            }
            else {
                holder.tv_index.setTextSize(13);
                holder.tv_index.setVisibility(View.VISIBLE);
                if(rooms.getFan_state() == 0){
                    holder.img_devices.clearAnimation();
                    holder.tv_index.setText("OFF");
                }
                else{
                    holder.img_devices.startAnimation(animationRotate);
                    holder.tv_index.setText("ON");
                }
                holder.img_status.setImageResource(R.drawable.normal);
                holder.tv_status.setText("Normal");
            }
        }
    }
    @Override
    public int getItemCount() {
        if(rooms == null){
            return 0;
        }
        return 2;
    }

    public class devicesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private ImageView img_devices,img_status;
        private TextView tv_index,tv_status;
        private onItemClickListener listener;

        public devicesViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            img_status = itemView.findViewById(R.id.img_warning);
            tv_status = itemView.findViewById(R.id.tv_status);
            img_devices = itemView.findViewById(R.id.img_devices);
            tv_index = itemView.findViewById(R.id.tv_index);
        }

        @Override
        public void onClick(View view) {
            Log.i("CheckOnClick", "onClick: "+ getAdapterPosition());
            Log.i("CheckOnClick", "onClick: "+ rooms.getName());
            if(getAdapterPosition() == 0) {
                listener.onItemClick(getAdapterPosition(), rooms.getName(), rooms.getGas_state());
            }if(getAdapterPosition() == 1){
                listener.onItemClick(getAdapterPosition(), rooms.getName(), rooms.getFan_state());
            }

        }
    }
}

