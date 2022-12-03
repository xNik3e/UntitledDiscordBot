package com.example.untitleddiscordbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ChooseChannelsActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private AllDataModel ADL;
    private FrameLayout backButton;
    private RecyclerView RVChannels;
    private ExtendedFloatingActionButton EXTFab;

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


    }
}