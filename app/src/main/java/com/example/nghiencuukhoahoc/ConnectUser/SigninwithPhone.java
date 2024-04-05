package com.example.nghiencuukhoahoc.ConnectUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nghiencuukhoahoc.MainActivity;
import com.example.nghiencuukhoahoc.Model.User;
import com.example.nghiencuukhoahoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SigninwithPhone extends AppCompatActivity {

    String VerificationId;

    private FirebaseAuth auth;

    private EditText Phone,OTP;

    private Button signup,btnOTP;
    private String pnub;
    private TextView redirectTextToLogin,redirectTextToEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinwith_phone);
        auth = FirebaseAuth.getInstance();
        Phone = findViewById(R.id.signupPhone);
        OTP = findViewById(R.id.OTP);
        redirectTextToLogin = findViewById(R.id.tv_Login);
        redirectTextToEmail = findViewById(R.id.tv_signupwithemail);
        signup = findViewById(R.id.btn_Signup);
        btnOTP = findViewById(R.id.btn_OTP);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Phone.getText().toString().trim();
                String pass = OTP.getText().toString().trim();
                pnub=user;
                if(user.isEmpty()){
                    Phone.setError("Phone cant be empty");
                }

                else {
                    if(TextUtils.isEmpty(OTP.getText().toString())){
                        OTP.setError("OTP cant be empty");
                    }
                    else verifycode(OTP.getText().toString());
                }
            }
        });
        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendverification(Phone.getText().toString());
            }
        });

        redirectTextToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninwithPhone.this,SignInActivity.class));
            }
        });

        redirectTextToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninwithPhone.this,SignUpActivity.class));
            }
        });
    }


    private void sendverification(String user){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(user)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if(code!=null){
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(SigninwithPhone.this,"Verification Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String id,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(id,token);
            VerificationId = id;
        }
    };

    private void verifycode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId,code);
        signinbyCred(credential);
    }

    private void signinbyCred(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    User user1=new User(pnub);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(user.getUid()).setValue(user1);
                    Toast.makeText(SigninwithPhone.this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SigninwithPhone.this, MainActivity.class));
                }
                else Toast.makeText(SigninwithPhone.this,"OTP Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}