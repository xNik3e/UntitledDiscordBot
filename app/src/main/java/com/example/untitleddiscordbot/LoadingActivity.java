package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBarHorizontal;
    private TextView infoView, infoDetail;
    private boolean isLoading = true;
    private MainViewModel mainViewModel;
    private String userHash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBarHorizontal = findViewById(R.id.progress_horizontal);
        progressBarHorizontal.setProgress(0);
        infoView = findViewById(R.id.infoView);
        infoDetail = findViewById(R.id.infoDetail);

        userHash = AuthUtil.getAccessToken();

        mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this,
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);


        mainViewModel.getUserModel().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel != null){
                    infoDetail.setText("Downloading Guilds info");
                    progressBarHorizontal.setProgress(50);
                    mainViewModel.fetchUserGuilds(userHash);
                }
            }
        });

        mainViewModel.getUserGuildModel().observe(this, new Observer<List<UserGuildModelItem>>() {
            @Override
            public void onChanged(List<UserGuildModelItem> userGuildModel) {
                if(userGuildModel != null){
                    infoView.setText("Loading Complete");
                    progressBarHorizontal.setProgress(100);
                    infoDetail.setVisibility(TextView.GONE);
                    infoView.setTextColor(getResources().getColor(R.color.active_color));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                            finish();
                        }
                    },1000);
                }
            }
        });

        tryAgain();
    }

    private void tryAgain(){
        progressBarHorizontal.setProgress(0);
        infoDetail.setText("Downloading User Info");
        isLoading = true;
        mainViewModel.fetchUserModel(userHash);
    }

    //TODO ADD INTERNET CHECK
}