package com.example.untitleddiscordbot.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
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

    private LinearLayout emptyLayout, serverLayout;

    private Context ctx;
    private UserModel userModel;
    private List<UserGuildModelItem> userGuildModel;
    private ServerSelectionInterface anInterface;
    private FrameLayout serverIconFrame;
    private ImageView serverIcon;

    private static UserGuildModelItem selectedServer;
    private static boolean isUIUpdated = false;

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
        emptyLayout = view.findViewById(R.id.empty_layout);
        serverLayout = view.findViewById(R.id.server_layout);
        serverIconFrame = view.findViewById(R.id.server_icon_frame);
        serverIcon = view.findViewById(R.id.server_icon);



        if (!isUIUpdated){
            serverLayout.setVisibility(View.GONE);
            serverLayout.setAlpha(0);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.setAlpha(1);
            serverIconFrame.setVisibility(View.GONE);
        }else{
            String serverName = selectedServer.getName();
            chooseServer.setText(serverName);
            chooseServer.setPadding(chooseServer.getPaddingLeft() + (int )TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()),
                    chooseServer.getPaddingTop(),
                    chooseServer.getPaddingRight(),
                    chooseServer.getPaddingBottom());

            serverIconFrame.setVisibility(View.VISIBLE);
            updateServerPicture(selectedServer.getIcon(), selectedServer.getId());

            serverLayout.setVisibility(View.VISIBLE);
            serverLayout.setAlpha(1);
            emptyLayout.setVisibility(View.GONE);
            emptyLayout.setAlpha(0);
        }



        anInterface = new ServerSelectionInterface() {
            @Override
            public void selectServer(UserGuildModelItem item) {
                    viewModel.setSelectedServer(item);
                    if(!item.isBotAdded()){
                        //open Custom Tab with the invite link and get on activity result
                        String url = "https://discord.com/api/oauth2/authorize?client_id=689502885159108692&permissions=8&scope=bot";
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.intent.setData(Uri.parse(url));

                        selectedServer = null;
                        isUIUpdated = false;

                        ((MainActivity) ctx).startActivityForResult(customTabsIntent.intent, 645);
                    }else{
                        //dismiss ServerSelectionFragment
                        ServerSelectionFragment f = (ServerSelectionFragment) getChildFragmentManager().findFragmentByTag("ServerSelectionFragment");
                        f.dismissNow();
                    }
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


        viewModel.getSelectedServerLiveData().observe(getViewLifecycleOwner(), new Observer<UserGuildModelItem>() {
            @Override
            public void onChanged(UserGuildModelItem item) {
                if(item != null && item.isBotAdded()){
                    selectedServer = item;
                    chooseServer.setText(selectedServer.getName());
                    updateServerPicture(selectedServer.getIcon(), selectedServer.getId());
                    if(!isUIUpdated)
                        updateUI();
                }

            }
        });

    }

    public void updateUI(){
        //update UI
        serverLayout.setVisibility(View.VISIBLE);
        serverLayout.setAlpha(0);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                serverLayout.setAlpha((int) animation.getAnimatedValue() / 100f);
                emptyLayout.setAlpha(1 - (int) animation.getAnimatedValue() / 100f);
                if ((int) animation.getAnimatedValue() == 100) {
                    emptyLayout.setVisibility(View.GONE);
                    isUIUpdated = true;
                }
            }
        });
        animator.start();

        //update button text
        chooseServer.setText(selectedServer.getName());
        chooseServer.setPadding(chooseServer.getPaddingLeft() + (int )TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()),
                chooseServer.getPaddingTop(),
                chooseServer.getPaddingRight(),
                chooseServer.getPaddingBottom());

        serverIconFrame.setVisibility(View.VISIBLE);
        updateServerPicture(selectedServer.getIcon(), selectedServer.getId());

    }


    private void updateServerPicture(String icon, String id) {
        if(icon != null){
            String url = "https://cdn.discordapp.com/icons/" + id + "/" + icon + ".png";
            Glide.with(this).load(url).placeholder(R.drawable.discord_placeholder).into(serverIcon);
        }else
            Glide.with(this).load(R.drawable.discord_placeholder).into(serverIcon);

    }
}