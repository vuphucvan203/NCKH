package com.example.nghiencuukhoahoc.ConnectUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nghiencuukhoahoc.Model.User;
import com.example.nghiencuukhoahoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText Email,Password,PasswordC;
    private Button signup;
    private TextView redirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.signupEmail);
        Password = findViewById(R.id.signupPassword);
        PasswordC = findViewById(R.id.signupPasswordC);
        redirectText = findViewById(R.id.textView2);
        signup = findViewById(R.id.button2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Email.getText().toString().trim();
                String a=user;
                String pass = Password.getText().toString().trim();

                if(user.isEmpty()){
                    Email.setError("Username cant be empty");
                }

                if(pass.isEmpty()){
                    Password.setError("Password cant be empty");
                }
                else {
                    if(Patterns.EMAIL_ADDRESS.matcher(user).matches() && validate()){
                        auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    //
                                    User user1=new User(getUserNameFromEmail(a),generateRandomPhoneNumber(),a,pass);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(user.getUid()).setValue(user1);
                                    //
                                    user.sendEmailVerification();
                                    Toast.makeText(SignUpActivity.this,"Check email for verification",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this,"Sign up failed" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        redirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });
    }

    private String getUserNameFromEmail(String email) {

        String[] parts = email.split("@");
        if (parts.length > 0) {
            String userName = parts[0].replaceAll("[^a-zA-Z0-9_]", "_");
            return userName;
        } else {
            return email;
        }
    }
    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        String[] prefixes = {"03", "05", "07", "08", "09"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        String phoneNumber = prefix;
        for (int i = 0; i < 7; i++) {
            phoneNumber += random.nextInt(10);
        }
        return phoneNumber;
    }
    private boolean validate() {
        boolean temp=true;
        String pass=Password.getText().toString();
        String cpass=PasswordC.getText().toString();
        if(!pass.equals(cpass)){
            Toast.makeText(SignUpActivity.this,"Password Not matching",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }

}