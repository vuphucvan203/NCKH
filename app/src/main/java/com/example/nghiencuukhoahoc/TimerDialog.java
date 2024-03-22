package com.example.nghiencuukhoahoc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class TimerDialog extends Dialog {
    private TimerDialogOnClicked timerDialogOnClicked;
    private EditText edt_hour,edt_minute,edt_second;
    private int hour, minute,second;
    private String Hour,Minute, Second;
    public TimerDialog(@NonNull Context context, TimerDialogOnClicked timerDialogOnClicked) {
        super(context);
        this.timerDialogOnClicked = timerDialogOnClicked;
        setContentView(R.layout.dialog_timer);

        Window window = getWindow();
        if(window ==  null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        show();
        Button btn_turnON = findViewById(R.id.btn_timer_lamp_turnOn);
        Button btn_turnOFF = findViewById(R.id.btn_timer_lamp_turnOff);
        edt_hour = findViewById(R.id.timer_hour);
        edt_minute = findViewById(R.id.timer_minute);
        edt_second = findViewById(R.id.timer_second);

        btn_turnON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hour = edt_hour.getText().toString();
                Minute =edt_minute.getText().toString();
                Second = edt_second.getText().toString();
                if(Hour.equals("")){ hour = 0;}else{
                    hour = Integer.parseInt(Hour);
                }
                if(Minute.equals("")){ minute = 0; }else{
                    minute = Integer.parseInt(Minute);
                }
                if(Second.equals("")){ second = 0;}
                else{
                    second = Integer.parseInt(Second);
                }

                if(timerDialogOnClicked != null){
                    timerDialogOnClicked.onButtonTurnONClicked(hour,minute,second);
                }
                dismiss();
            }
        });
        btn_turnOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hour = edt_hour.getText().toString();
                Minute =edt_minute.getText().toString();
                Second = edt_second.getText().toString();
                if(Hour.equals("")){ hour = 0;}else{
                    hour = Integer.parseInt(Hour);
                }
                if(Minute.equals("")){ minute = 0; }else{
                    minute = Integer.parseInt(Minute);
                }
                if(Second.equals("")){ second = 0;}
                else{
                    second = Integer.parseInt(Second);
                }
                if(timerDialogOnClicked != null){
                    timerDialogOnClicked.onButtonTurnOFFClicked(hour,minute,second);
                }
                dismiss();
            }
        });
    }

}
