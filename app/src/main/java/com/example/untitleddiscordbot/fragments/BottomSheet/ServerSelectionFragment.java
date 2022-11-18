package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.interfaces.ServerSelectionInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;


public class ServerSelectionFragment extends BottomSheetDialogFragment {

    private ImageView filter;
    private RecyclerView serverRV;
    private List<UserGuildModelItem> guilds;
    private LinearLayout emptyLayout;
    private ServerSelectionInterface anInterface;

    public ServerSelectionFragment() {
        // Required empty public constructor
    }

    public ServerSelectionFragment(List<UserGuildModelItem> guilds, ServerSelectionInterface selectionInterface) {
        this.guilds = guilds;
        this.anInterface = selectionInterface;
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
    }
}