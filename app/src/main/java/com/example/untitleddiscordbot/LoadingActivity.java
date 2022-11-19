package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBarHorizontal;
    private TextView infoView, infoDetail;
    private boolean isLoading = true;
    private MainViewModel mainViewModel;
    private String userHash;

    private LiveData<Boolean> isUserGuildsUpdated;
    private boolean isUserGuildsUpdatedValue = false;
    private UserGuildModelItem selectedServer = null;


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


        NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(
                this,
                getLifecycle()
        );

        DialogPropertiesSignal propertiesSignal = builder.getDialogProperties();
        propertiesSignal.setConnectionCallback(new ConnectionCallback() {
            @Override
            public void hasActiveConnection(boolean b) {
                if(b)
                    tryAgain();
            }
        });

        propertiesSignal.setCancelable(false);
        propertiesSignal.setNoInternetConnectionTitle("No Internet Connection");
        propertiesSignal.setNoInternetConnectionMessage("Please check your internet connection and try again");
        propertiesSignal.setShowInternetOnButtons(true);
        propertiesSignal.setPleaseTurnOnText("Please turn on");
        propertiesSignal.setWifiOnButtonText("Wi-Fi");
        propertiesSignal.setMobileDataOnButtonText("Mobile Data");

        propertiesSignal.setOnAirplaneModeTitle("Airplane mode"); // Optional
        propertiesSignal.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        propertiesSignal.setPleaseTurnOffText("Please turn off"); // Optional
        propertiesSignal.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        propertiesSignal.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();

        mainViewModel.getSelectedServerLiveData().observe(this, new Observer<UserGuildModelItem>() {
            @Override
            public void onChanged(UserGuildModelItem item) {
                if(item != null){
                    selectedServer = item;
                }
            }
        });

        mainViewModel.isUserGuildsUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isUserGuildsUpdatedValue = aBoolean;
            }
        });

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
                if(userGuildModel != null && isUserGuildsUpdatedValue){
                    if(selectedServer != null){
                        boolean isServerFound = userGuildModel.stream().anyMatch(guild ->{
                            if(guild.getId().equals(selectedServer.getId())){
                                return guild.isBotAdded();
                            }
                            return false;
                        });
                        if(isServerFound){
                            selectedServer.setBotAdded(true);
                            mainViewModel.setSelectedServer(selectedServer);
                        }
                    }
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
                }else if(userGuildModel != null){
                    mainViewModel.updateUserGuilds();
                }
            }
        });


    }

    private void tryAgain(){
        progressBarHorizontal.setProgress(0);
        infoDetail.setText("Downloading User Info");
        isLoading = true;
        mainViewModel.fetchUserModel(userHash);
    }

    //TODO ADD INTERNET CHECK


}