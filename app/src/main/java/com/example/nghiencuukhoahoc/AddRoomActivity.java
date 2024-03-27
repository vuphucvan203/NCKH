package com.example.nghiencuukhoahoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class AddRoomActivity extends AppCompatActivity {

    protected TextInputEditText roomName;
    protected Button btn_gasDetectDv, btn_confirm;
    protected ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        Init();
        BackToMainActivity();
    }

    protected void Init()
    {
        roomName = findViewById(R.id.roomNameAdd);
        btn_gasDetectDv = findViewById(R.id.gasDetectionDevice);
        btn_confirm = findViewById(R.id.confirmAdd);
        back = findViewById(R.id.img_back);
    }

    protected void BackToMainActivity()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}