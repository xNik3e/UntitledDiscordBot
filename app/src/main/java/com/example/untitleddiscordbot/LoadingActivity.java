package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBarHorizontal;
    private TextView infoView, infoDetail;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }
}