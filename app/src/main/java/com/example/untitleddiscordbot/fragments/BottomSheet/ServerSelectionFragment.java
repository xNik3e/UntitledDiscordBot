package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.adapters.ServerSelectionAdapter;
import com.example.untitleddiscordbot.interfaces.ServerSelectionInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServerSelectionFragment extends BottomSheetDialogFragment {


    private Context ctx;
    private ImageView filter;
    private RecyclerView serverRV;
    private List<UserGuildModelItem> guilds;
    private LinearLayout emptyLayout;
    private ServerSelectionInterface anInterface;
    private boolean isSorted = true;

    private ServerSelectionAdapter adapter;

    public ServerSelectionFragment() {
        // Required empty public constructor
    }

    public ServerSelectionFragment(List<UserGuildModelItem> guilds, ServerSelectionInterface selectionInterface) {
        this.guilds = guilds;
        this.anInterface = selectionInterface;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_server_selection, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filter = view.findViewById(R.id.filter_icon);
        serverRV = view.findViewById(R.id.servers_rv);
        emptyLayout = view.findViewById(R.id.empty_layout);

        //sort guilds based on isBot and name
        sortElements();

        adapter = new ServerSelectionAdapter(guilds, ctx, anInterface);
        if(!guilds.isEmpty()){
            emptyLayout.setVisibility(View.GONE);
            serverRV.setVisibility(View.VISIBLE);
            serverRV.setAdapter(adapter);
        }else{
            emptyLayout.setVisibility(View.VISIBLE);
            serverRV.setVisibility(View.GONE);
        }

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortElements();
                adapter.notifyItemRangeChanged(0, guilds.size());
            }
        });
    }


    public void sortElements(){
        isSorted = !isSorted;
        if(isSorted){
            Collections.sort(guilds, (o1, o2) -> {
                if(o1.isBotAdded() && !o2.isBotAdded()){
                    return 1;
                }else if(!o1.isBotAdded() && o2.isBotAdded()){
                    return -1;}
                else{
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }else{
            Collections.sort(guilds, (o1, o2) -> {
                if(o1.isBotAdded() && !o2.isBotAdded()){
                    return -1;
                }else if(!o1.isBotAdded() && o2.isBotAdded()){
                    return 1;}
                else{
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }


}