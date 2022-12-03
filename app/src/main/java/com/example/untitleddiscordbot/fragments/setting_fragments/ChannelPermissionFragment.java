package com.example.untitleddiscordbot.fragments.setting_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.untitleddiscordbot.ChooseChannelsActivity;
import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.adapters.SelectChannelChipAdapter;
import com.example.untitleddiscordbot.interfaces.OnSelectChannelChipClickInterface;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class ChannelPermissionFragment extends Fragment {


    private Context ctx;
    private MainViewModel mainViewModel;
    private AllDataModel ADL;

    private View infoCard, selectedChannelsCard;

    //InfoCard
    private TextView infoCardTitle, infoCardContent;
    //SelectedChannelsCard
    private RecyclerView RVSelectedChannels;
    private TextView emptySelectedChannels;
    private MaterialButton editChannels;

    private List<ChannelPermissionsModel> channelPermissions = new ArrayList<>();
    private SelectChannelChipAdapter selectChannelChipAdapter;


    public ChannelPermissionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
        this.mainViewModel = new ViewModelProvider((ViewModelStoreOwner) ctx,
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);
        this.ADL = mainViewModel.getAllDataModel().getValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel_permission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoCard = view.findViewById(R.id.info_card_layout);
        selectedChannelsCard = view.findViewById(R.id.selected_channels_card_layout);

        //InfoCard
        infoCardTitle = infoCard.findViewById(R.id.info_card_title);
        infoCardContent = infoCard.findViewById(R.id.info_card_content);

        //SelectedChannelsCard
        RVSelectedChannels = selectedChannelsCard.findViewById(R.id.selected_channels);
        emptySelectedChannels = selectedChannelsCard.findViewById(R.id.empty_require);
        editChannels = selectedChannelsCard.findViewById(R.id.edit_channels_button);

        infoCardTitle.setText("Information");
        infoCardContent.setText("Configure where and by whom UDB could be used. Select specific channels, whole categories and configure individual channel roles. \n" +
                "\n" +
                "If no specific role selected, permission would be granted to users with roles defined in the core settings.");

        OnSelectChannelChipClickInterface onChannelInterface = new OnSelectChannelChipClickInterface() {
            @Override
            public void onCLick(ChannelPermissionsModel model) {

            }
        };

        channelPermissions.addAll(ADL.getSettings().getChannelPermissions());
        selectChannelChipAdapter = new SelectChannelChipAdapter(ctx, channelPermissions, onChannelInterface);
        LinearLayoutManager LLM = new LinearLayoutManager(ctx);
        RVSelectedChannels.setAdapter(selectChannelChipAdapter);
        RVSelectedChannels.setLayoutManager(LLM);

        editChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, ChooseChannelsActivity.class));
            }
        });

        updateUI();
    }

    private void updateUI() {
        if(channelPermissions.size() == 0){
            RVSelectedChannels.setVisibility(View.GONE);
            emptySelectedChannels.setVisibility(View.VISIBLE);
        }else{
            RVSelectedChannels.setVisibility(View.VISIBLE);
            emptySelectedChannels.setVisibility(View.GONE);
        }
    }
}