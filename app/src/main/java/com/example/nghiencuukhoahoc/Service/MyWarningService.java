package com.example.nghiencuukhoahoc.Service;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.MyViewModel.DataSingleton;
import com.example.nghiencuukhoahoc.R;

import java.util.List;

public class MyWarningService extends Service {
    private Dialog myDialog;
    List<Rooms> lst_rooms;
    @Override
    public void onCreate() {
        super.onCreate();
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.dialog_warning);
        Window window = myDialog.getWindow();
        if(window ==  null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        myDialog.setCancelable(false);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Hiển thị dialog khi Service được khởi chạy
        Log.d("testService", "onStartCommand: ");
        lst_rooms = DataSingleton.getInstance().getSharedData();
        Log.d("testService", "onStartCommand: "+ lst_rooms.get(0).getGas());
        for(int i =0 ; i < lst_rooms.size() ; i++){
            if(lst_rooms.get(i).getGas() == 0){
                showMyDialog();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        // Giải phóng tài nguyên và đóng dialog khi Service bị hủy
        dismissMyDialog();
        super.onDestroy();
    }

    private void showMyDialog() {
        // Hiển thị dialog
        if (myDialog != null && !myDialog.isShowing()) {
            myDialog.show();
        }
    }

    private void dismissMyDialog() {
        // Đóng dialog
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
