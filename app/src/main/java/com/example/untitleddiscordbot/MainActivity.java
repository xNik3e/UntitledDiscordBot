package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.untitleddiscordbot.Models.AuthResponseModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView logout;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(v -> {
            AuthUtil.clearAuthState(this);
            startActivity(new Intent(this,AuthActivity.class));
        });



    }
}