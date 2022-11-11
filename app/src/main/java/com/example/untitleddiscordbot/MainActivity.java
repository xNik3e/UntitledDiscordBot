package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.untitleddiscordbot.Models.AuthResponseModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        AuthResponseModel model = AuthUtil.createAuthResponseModel();
        System.out.println(model.toString());

    }
}