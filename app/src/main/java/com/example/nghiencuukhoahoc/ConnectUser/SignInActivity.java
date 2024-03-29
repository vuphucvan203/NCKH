package com.example.nghiencuukhoahoc.ConnectUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nghiencuukhoahoc.MainActivity;


import com.example.nghiencuukhoahoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText Email, Password;
    private Button login;
    private TextView redirectText,tvloginphone;
    private TextView resetPasswdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.signupEmail);
        Password = findViewById(R.id.signupPasswordC);
        redirectText = findViewById(R.id.textView2);
        tvloginphone = findViewById(R.id.tv_loginphone);
        login = findViewById(R.id.button2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                if(!user.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    if(!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(user,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                boolean emailVerified = user.isEmailVerified();
                                if(emailVerified){
                                    Toast.makeText(SignInActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
                                    finish();
                                }
                                else  Toast.makeText(SignInActivity.this,"Email not verified",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }   else {
                        Password.setError("Passwd cant be empty");
                    }
                } else if (user.isEmpty()) {
                    Email.setError("Email cant be empty");
                } else {
                    Email.setError("Email not valid");
                }
            }
        });
        redirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        tvloginphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SigninwithPhone.class));
            }
        });

        resetPasswdTxt = findViewById(R.id.tv_resetpass);
        resetPasswdTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Email.getText().toString().trim();

                if(!user.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    auth.sendPasswordResetEmail(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignInActivity.this,"Check your email/spam folder",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SignInActivity.this,"Action failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (user.isEmpty()) {
                    Email.setError("Email cant be empty");
                } else {
                    Email.setError("Email not valid");
                }
            }
        });
    }
}