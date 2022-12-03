package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ChannelSettingsFragment extends BottomSheetDialogFragment {

    private Context ctx;
    private MainViewModel mainViewModel;


    public ChannelSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}