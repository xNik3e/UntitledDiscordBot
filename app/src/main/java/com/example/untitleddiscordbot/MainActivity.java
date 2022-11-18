package com.example.untitleddiscordbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.untitleddiscordbot.Models.AuthResponseModel;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;
import com.example.untitleddiscordbot.fragments.CommandsFragment;
import com.example.untitleddiscordbot.fragments.HomeFragment;
import com.example.untitleddiscordbot.fragments.MusicFragment;
import com.example.untitleddiscordbot.fragments.SettingsFragment;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;

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

    private TextView logout, toolbarTitle;
    private FrameLayout serverContainer, menuContainer;
    private ImageView profilePicture, serverPicture;
    private Space space;
    private View darkenView;

    private ExpandableBottomBar bottomBar;
    private HomeFragment homeFragment;

    private MainViewModel viewModel;

    private SettingsFragment settingsFragment;
    private MusicFragment musicFragment;
    private CommandsFragment commandsFragment;

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

        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        musicFragment = new MusicFragment();
        commandsFragment = new CommandsFragment();

        UserModel userModel = viewModel.getStoredUserModel();

        NavController navController = Navigation.findNavController(this, R.id.container);

        ExpandableBottomBarNavigationUI.setupWithNavController(bottomBar, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.homeFragment) {
                    toolbarTitle.setText("Home");
                    space.setVisibility(View.VISIBLE);
                    menuContainer.setVisibility(View.GONE);
                    serverContainer.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.settingsFragment) {
                    toolbarTitle.setText("Settings");
                    menuContainer.setVisibility(View.VISIBLE);
                    serverContainer.setVisibility(View.VISIBLE);
                    space.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.musicFragment) {
                    toolbarTitle.setText("Music");
                    menuContainer.setVisibility(View.GONE);
                    serverContainer.setVisibility(View.VISIBLE);
                    space.setVisibility(View.VISIBLE);
                } else if (navDestination.getId() == R.id.commandsFragment) {
                    toolbarTitle.setText("Commands");
                    menuContainer.setVisibility(View.GONE);
                    serverContainer.setVisibility(View.VISIBLE);
                    space.setVisibility(View.VISIBLE);
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

    }


    @Override
    public void onBackPressed() {
        if (bottomBar.getMenu().getSelectedItem().getId() != R.id.homeFragment) {
            bottomBar.getMenu().select(R.id.homeFragment);
        } else {
            //kill app
            finishAndRemoveTask();
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
}