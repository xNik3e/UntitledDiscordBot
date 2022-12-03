package com.example.untitleddiscordbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.untitleddiscordbot.Models.AuthResponseModel;
import com.example.untitleddiscordbot.Models.DetailedGuild.DetailedGuildItem;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;
import com.example.untitleddiscordbot.fragments.CommandsFragment;
import com.example.untitleddiscordbot.fragments.HomeFragment;
import com.example.untitleddiscordbot.fragments.MusicFragment;
import com.example.untitleddiscordbot.fragments.SettingsFragment;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.MenuItem;
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

public class MainActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private FrameLayout serverContainer, menuContainer;
    private ImageView profilePicture, serverPicture;
    private Space space;

    private View darkenView;
    private LinearLayout loadingLayout, errorContainer;
    private ProgressBar loadingBar;
    private TextView retryText;
    private MaterialButton retryButton, exitAppButton;

    private ExpandableBottomBar bottomBar;
    public static DrawerLayout drawerLayout;
    public static NavigationView drawerNavigationView;
    private NavController navController;

    private UserGuildModelItem selectedServer;

    private MainViewModel viewModel;

    private HomeFragment homeFragment;
    private SettingsFragment settingsFragment;
    private MusicFragment musicFragment;
    private CommandsFragment commandsFragment;

    private CountDownTimer timer;

    private static boolean isLoading = false;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider((ViewModelStoreOwner) this,
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);

        //logout = findViewById(R.id.logoutButton);
        toolbarTitle = findViewById(R.id.toolbar_title);
        bottomBar = findViewById(R.id.bottom_navigation);
        serverContainer = findViewById(R.id.icon_server);
        menuContainer = findViewById(R.id.toolbar_icon_container);
        profilePicture = findViewById(R.id.profile_picture);
        serverPicture = findViewById(R.id.server_picture);
        space = findViewById(R.id.spacing);

        darkenView = findViewById(R.id.darken_view);
        loadingLayout = findViewById(R.id.loading_container);
        errorContainer = findViewById(R.id.error_container);
        loadingBar = findViewById(R.id.loading);
        retryText = findViewById(R.id.retry_text);
        retryButton = findViewById(R.id.button_retry);
        exitAppButton = findViewById(R.id.button_exit);

        darkenView.setVisibility(View.GONE);
        darkenView.setAlpha(0f);
        loadingLayout.setVisibility(View.GONE);
        loadingLayout.setAlpha(0f);
        errorContainer.setVisibility(View.GONE);
        errorContainer.setAlpha(0f);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerNavigationView = findViewById(R.id.drawerNavigation);


        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        musicFragment = new MusicFragment();
        commandsFragment = new CommandsFragment();

        UserModel userModel = viewModel.getStoredUserModel();
        selectedServer = viewModel.getSelectedServer();

        navController = Navigation.findNavController(this, R.id.container);

        ExpandableBottomBarNavigationUI.setupWithNavController(bottomBar, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if (navDestination.getId() == R.id.homeFragment) {
                    toolbarTitle.setText("Home");
                    space.setVisibility(View.VISIBLE);
                    menuContainer.setVisibility(View.GONE);
                    serverContainer.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.settingsFragment) {
                    if (selectedServer != null) {
                        toolbarTitle.setText("Settings");
                        menuContainer.setVisibility(View.VISIBLE);
                        serverContainer.setVisibility(View.VISIBLE);
                        space.setVisibility(View.GONE);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    }else{
                        Toast.makeText(MainActivity.this, "You haven't selected a server!", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.homeFragment);
                    }

                } else if (navDestination.getId() == R.id.musicFragment) {
                    if(selectedServer != null){
                        toolbarTitle.setText("Music");
                        menuContainer.setVisibility(View.GONE);
                        serverContainer.setVisibility(View.VISIBLE);
                        space.setVisibility(View.VISIBLE);

                    }else{
                        Toast.makeText(MainActivity.this, "You haven't selected a server!", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.homeFragment);
                    }
                } else if (navDestination.getId() == R.id.commandsFragment) {
                    if(selectedServer != null){
                        toolbarTitle.setText("Commands");
                        menuContainer.setVisibility(View.GONE);
                        serverContainer.setVisibility(View.VISIBLE);
                        space.setVisibility(View.VISIBLE);

                    }else{
                        Toast.makeText(MainActivity.this, "You haven't selected a server!", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.homeFragment);
                    }
                }
            }
        });


        if (userModel.getAvatar() != null) {
            String url = "https://cdn.discordapp.com/avatars/" + userModel.getId() + "/" + userModel.getAvatar() + ".png";
            Glide.with(this).load(url).placeholder(R.drawable.discord_placeholder).into(profilePicture);
        } else {
            Glide.with(this).load(R.drawable.discord_placeholder).into(profilePicture);
        }

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(userModel);
            }
        });

        viewModel.getSelectedServerLiveData().observe(this, new Observer<UserGuildModelItem>() {
            @Override
            public void onChanged(UserGuildModelItem item) {
                if(item != null){
                    //Is selected and is bot added
                    if(item.isBotAdded()){
                        //select server
                        selectedServer = item;
                        updateServerPicture(item);
                    }
                }
            }
        });

        viewModel.getDetailedGuild().observe(this, new Observer<DetailedGuildItem>() {
            @Override
            public void onChanged(DetailedGuildItem detailedGuildItem) {
                if(detailedGuildItem != null){
                    if(detailedGuildItem.getId() == null){
                        //set loading
                        isLoading = true;
                        setLoading();
                    }else if(detailedGuildItem.getId().equals("error")){
                        //loading error - UPDATE UI
                        setError();
                    }else{
                        //loaded update UI
                        setLoaded();
                    }
                }
            }
        });


        menuContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void setError() {
        isLoading = false;
        loadingLayout.setVisibility(View.VISIBLE);
        darkenView.setVisibility(View.VISIBLE);
        loadingLayout.setAlpha(1f);
        darkenView.setAlpha(1f);
        errorContainer.setVisibility(View.VISIBLE);
        errorContainer.setAlpha(0f);

        errorContainer.animate().alpha(1f).setDuration(200).start();
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoading = true;
                timer.cancel();
                errorContainer.animate().alpha(0f).setDuration(200).start();
                viewModel.fetchDetailedGuild(selectedServer.getId());
            }
        });

        exitAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });


        timer = new CountDownTimer(10000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                String value = (int) (millisUntilFinished / 1000) + "";
                retryText.setText(value);
            }

            @Override
            public void onFinish() {
                if(!isLoading){
                    isLoading = true;
                    errorContainer.animate().alpha(0f).setDuration(200).start();
                    viewModel.fetchDetailedGuild(selectedServer.getId());
                }
            }
        }.start();
    }

    private void setLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingLayout.setAlpha(0f);
        darkenView.setVisibility(View.VISIBLE);
        darkenView.setAlpha(0f);
        errorContainer.setVisibility(View.GONE);
        loadingLayout.setFocusable(true);
        loadingLayout.setClickable(false);

        loadingLayout.animate().alpha(1f).setDuration(400).start();
        darkenView.animate().alpha(1f).setDuration(400).start();

    }

    private void setLoaded(){
        isLoading = false;
        ValueAnimator animator = ValueAnimator.ofInt(100, 0);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                loadingLayout.setAlpha(value/100f);
                darkenView.setAlpha(value/100f);
                if(value == 0){
                    loadingLayout.setVisibility(View.GONE);
                    darkenView.setVisibility(View.GONE);
                }
            }
        });
        animator.start();
    }

    private void updateServerPicture(UserGuildModelItem item) {
        if(item.getIcon() != null){
            String url = "https://cdn.discordapp.com/icons/" + item.getId() + "/" + item.getIcon() + ".png";
            Glide.with(this).load(url).placeholder(R.drawable.discord_placeholder).into(serverPicture);
        }else
            Glide.with(this).load(R.drawable.discord_placeholder).into(serverPicture);

    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if (bottomBar.getMenu().getSelectedItem().getId() != R.id.homeFragment) {
                bottomBar.getMenu().select(R.id.homeFragment);
                navController.navigate(R.id.homeFragment);
            } else {
                //kill app
                finishAffinity();
            }
        }
    }

    private void showPopupMenu(UserModel user) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupmenu_custom_layout_logout, null);
        TextView userName = popupView.findViewById(R.id.username);
        TextView userTag = popupView.findViewById(R.id.usertag);
        TextView logout = popupView.findViewById(R.id.logoutButton);

        userName.setText(user.getUsername());
        String tag = "#" + user.getDiscriminator();
        userTag.setText(tag);

        logout.setOnClickListener(v -> {
            AuthUtil.clearAuthState(this);
            viewModel.clearAllData();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAsDropDown(profilePicture, 0, 24);

        darkenView.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                darkenView.setAlpha((int) animation.getAnimatedValue() / 100f);
            }
        });
        valueAnimator.start();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                ValueAnimator valueAnimator = ValueAnimator.ofInt(100, 0);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        darkenView.setAlpha((int) animation.getAnimatedValue() / 100f);
                        if(animation.getAnimatedValue().equals(0)){
                            darkenView.setVisibility(View.GONE);
                        }
                    }
                });
                valueAnimator.start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 645){
            Toast.makeText(this, "Retrieving servers data", Toast.LENGTH_SHORT).show();
            viewModel.clearAllData();
            startActivity(new Intent(this, LoadingActivity.class));
            finish();
        }else if(requestCode == 546){
            //load server data
            System.out.println("TEST");
        }
    }
}