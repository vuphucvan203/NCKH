package com.example.nghiencuukhoahoc;

import android.content.Intent;
import android.os.Bundle;
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

public class AddRoomActivity extends AppCompatActivity implements View.OnClickListener{

    protected TextInputEditText roomName;
    protected Button btn_gasDetectDv, btn_confirm;
    protected ImageView back;
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

        rooms=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("User")
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

    @Override
    public void onClick(View v) {

    }
}