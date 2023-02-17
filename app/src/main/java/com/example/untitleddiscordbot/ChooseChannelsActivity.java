package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.Models.SettingsModel;
import com.example.untitleddiscordbot.adapters.SelectChannelTreeAdapter;
import com.example.untitleddiscordbot.fragments.BottomSheet.ChannelSettingsFragment;
import com.example.untitleddiscordbot.interfaces.OnChannelSettingsClickInterface;
import com.example.untitleddiscordbot.interfaces.OnDialogDismissNewPermissionsListener;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class ChooseChannelsActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private AllDataModel ADL;
    private FrameLayout backButton;
    private RecyclerView RVChannels;
    private ExtendedFloatingActionButton saveFAB;
    private SelectChannelTreeAdapter channelAdapter;
    private List<DetailedChannelItem> channels;
    private List<ChannelPermissionsModel> channelPermissions;

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
        saveFAB = findViewById(R.id.save_fab);

        OnChannelSettingsClickInterface onChannelSettingsClickInterface = new OnChannelSettingsClickInterface() {
            @Override
            public void onChannelSettingsClick(DetailedChannelItem item) {
                openShelf(item);
            }
        };

        channels = ADL.getChannels();
        channelPermissions = ADL.getSettings().getChannelPermissions();

        channelAdapter = new SelectChannelTreeAdapter(this, channels, channelPermissions, onChannelSettingsClickInterface);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RVChannels.setAdapter(channelAdapter);
        RVChannels.setLayoutManager(linearLayoutManager);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        saveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsModel tempSettings = new SettingsModel(ADL.getSettings());
                tempSettings.setChannelPermissions(channelPermissions);
                mainViewModel.saveSettings(tempSettings);

                onBackPressed();
            }
        });

    }


    private void openShelf(DetailedChannelItem item) {
        ChannelSettingsFragment channelSettingsFragment = new ChannelSettingsFragment(
                new OnDialogDismissNewPermissionsListener() {
                    @Override
                    public void onClick(List<ChannelPermissionsModel> permissionsModels) {
                            channelAdapter.notifyDataSetChanged();
                    }
                },
                channelPermissions,
                item,
                mainViewModel
        );
        channelSettingsFragment.show(getSupportFragmentManager(), "channelSettingsFragment");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish affinity and remove task
        finish();
    }
}
