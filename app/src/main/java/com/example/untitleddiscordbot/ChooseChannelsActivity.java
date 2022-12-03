package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.adapters.SelectChannelTreeAdapter;
import com.example.untitleddiscordbot.interfaces.OnChannelSettingsClickInterface;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ChooseChannelsActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private AllDataModel ADL;
    private FrameLayout backButton;
    private RecyclerView RVChannels;
    private ExtendedFloatingActionButton EXTFab;
    private SelectChannelTreeAdapter channelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_channels);

        this.mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this,
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);
        this.ADL = mainViewModel.getAllDataModel().getValue();

        backButton = findViewById(R.id.toolbar_icon_container);
        RVChannels = findViewById(R.id.rv_channels);
        EXTFab = findViewById(R.id.save_fab);

        OnChannelSettingsClickInterface onChannelSettingsClickInterface = new OnChannelSettingsClickInterface() {
            @Override
            public void onChannelSettingsClick(ChannelPermissionsModel channelPermissions) {

            }
        };

        channelAdapter = new SelectChannelTreeAdapter(this, ADL.getChannels(), ADL.getSettings().getChannelPermissions(), onChannelSettingsClickInterface);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RVChannels.setAdapter(channelAdapter);
        RVChannels.setLayoutManager(linearLayoutManager);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}