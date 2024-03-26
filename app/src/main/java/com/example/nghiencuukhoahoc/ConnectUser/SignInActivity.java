package com.example.nghiencuukhoahoc.ConnectUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nghiencuukhoahoc.MainActivity;


import com.example.nghiencuukhoahoc.R;


public class SignInActivity extends AppCompatActivity {
//    CallbackManager mCallbackManager;
//    LoginButton loginButton;
//    private FirebaseAuth mAuth;
//    private final static  String TAG="FACEBOOK";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//        //initialize facebook sdk
//        mAuth = FirebaseAuth.getInstance();
//        //initialize facebook sdk
//        FacebookSdk.sdkInitialize(SignInActivity.this);
//        // Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();
//        loginButton = findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//            }
//        });
//// ...
//
//    }
//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(SignInActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//
//    private void updateUI(FirebaseUser user) {
//        if(user!=null)
//        {
//            Intent i=new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(i);
//        }else{
//            Toast.makeText(this,"Please sign in to continue",Toast.LENGTH_SHORT).show();
//        }
//    }
}