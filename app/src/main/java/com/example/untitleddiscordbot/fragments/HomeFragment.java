package com.example.untitleddiscordbot.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.untitleddiscordbot.MainActivity;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.fragments.BottomSheet.ServerSelectionFragment;
import com.example.untitleddiscordbot.interfaces.ServerSelectionInterface;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class HomeFragment extends Fragment {


    private MaterialButton chooseServer;
    private RecyclerView newsRV;
    private MainViewModel viewModel;

    private Context ctx;
    private UserModel userModel;
    private List<UserGuildModelItem> userGuildModel;
    private ServerSelectionInterface anInterface;

    public HomeFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(((ViewModelStoreOwner) ctx),
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);

        chooseServer = view.findViewById(R.id.button_choose);
        newsRV = view.findViewById(R.id.news_rv);

        anInterface = new ServerSelectionInterface() {
            @Override
            public void selectServer(int position) {

            }
        };

        userGuildModel = viewModel.getStoredUserGuildModel();

        chooseServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerSelectionFragment serverSelectionFragment = new ServerSelectionFragment(userGuildModel, anInterface);
                serverSelectionFragment.show(getChildFragmentManager(), "ServerSelectionFragment");
            }
        });


    }
}