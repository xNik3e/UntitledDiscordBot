package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.untitleddiscordbot.Models.AuthResponseModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;

import net.openid.appauth.AuthState;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthState authState = AuthUtil.readAuthState(this);
        AuthResponseModel model = AuthUtil.createAuthResponseModel();
        boolean hasExpired = model != null && model.getExpiresAt() < System.currentTimeMillis();
        if(authState == null || hasExpired){
            setContentView(R.layout.activity_splash_screen);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent toLoginActivity = new Intent(SplashScreenActivity.this,AuthActivity.class);
                    finish();
                    startActivity(toLoginActivity);
                }
            }, 2600);
        }else{
            startActivity(new Intent(this,LoadingActivity.class));
        }
    }
}