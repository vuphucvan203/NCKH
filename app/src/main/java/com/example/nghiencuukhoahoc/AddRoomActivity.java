package com.example.nghiencuukhoahoc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nghiencuukhoahoc.Model.Rooms;
import com.example.nghiencuukhoahoc.MyViewModel.RoomsViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity{

    //protected TextInputEditText roomName;
    protected Button btn_livingRoom, btn_bedRoom, btn_kitchen, btn_garage, btn_gasDetectDv, btn_fan, btn_confirm;
    protected ImageView back;
    protected boolean isLivingRoom, isBedRoom, isKitchen, isGarage, isGas, isFan;
    protected RoomsViewModel roomsViewModel;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    private List<Rooms> rooms;
    DatabaseReference reference;
    String romName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        Init();

        rooms = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users")
                .child(firebaseUser.getUid()).child("room");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot drinkSnapshot:snapshot.getChildren()){
                        Rooms rooms1 = drinkSnapshot.getValue(Rooms.class);
                        rooms.add(rooms1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BackToMainActivity();
        ConfirmData();
    }

    protected void Init()
    {
        //roomName = findViewById(R.id.roomNameAdd);
        btn_livingRoom = findViewById(R.id.living_room);
        btn_bedRoom = findViewById(R.id.bed_room);
        btn_kitchen = findViewById(R.id.kitchen);
        btn_garage = findViewById(R.id.garage);
        btn_gasDetectDv = findViewById(R.id.gasDetectionDevice);
        btn_fan = findViewById(R.id.fan);
        btn_confirm = findViewById(R.id.confirmAdd);
        back = findViewById(R.id.img_back);

        btn_livingRoom.setBackgroundColor(Color.GRAY);
        btn_bedRoom.setBackgroundColor(Color.GRAY);
        btn_kitchen.setBackgroundColor(Color.GRAY);
        btn_garage.setBackgroundColor(Color.GRAY);
        btn_gasDetectDv.setBackgroundColor(Color.GRAY);
        btn_fan.setBackgroundColor(Color.GRAY);
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

    protected boolean validInput(){
        if(!isLivingRoom && !isBedRoom && !isKitchen && !isGarage)
        {
            Toast.makeText(this, "Vui lòng chọn loại phòng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    protected void ConfirmData()
    {
        btn_livingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_livingRoom.setBackgroundColor(Color.GREEN);
                isLivingRoom = true;
            }
        });
        btn_bedRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bedRoom.setBackgroundColor(Color.GREEN);
                isBedRoom = true;
            }
        });
        btn_kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_kitchen.setBackgroundColor(Color.GREEN);
                isKitchen = true;
            }
        });

        btn_garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_garage.setBackgroundColor(Color.GREEN);
                isGarage = true;
            }
        });

        btn_gasDetectDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_gasDetectDv.setBackgroundColor(Color.GREEN);
                isGas = true;
            }
        });

        btn_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_fan.setBackgroundColor(Color.GREEN);
                isFan = true;
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validInput())
                {
                    Loop:
                    btn_confirm.setBackgroundColor(Color.RED);
                    String name = new String();
                    int gasIndex, fanIndex;
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();

                    if(isLivingRoom) name = "livingRoom";
                    if(isBedRoom) name = "bedroom";
                    if(isKitchen) name = "kitchen";
                    if(isGarage) name = "garage";
                    if(isGas) gasIndex = 1;
                    else gasIndex = -1;
                    if(isFan) fanIndex = 1;
                    else fanIndex = -1;

                    bundle.putString("Name", name);
                    bundle.putInt("Gas", gasIndex);
                    bundle.putInt("Fan", fanIndex);
                    intent.putExtras(bundle);
                    setResult(150, intent);
                    finish();
                }
            }
        });
    }
}