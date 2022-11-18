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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.example.untitleddiscordbot.Models.AuthResponseModel;
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

        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        musicFragment = new MusicFragment();
        commandsFragment = new CommandsFragment();



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

//        logout.setOnClickListener(v -> {
//            AuthUtil.clearAuthState(this);
//            startActivity(new Intent(this, AuthActivity.class));
//        });

    }



    @Override
    public void onBackPressed() {
        if(bottomBar.getMenu().getSelectedItem().getId() != R.id.homeFragment) {
            bottomBar.getMenu().select(R.id.homeFragment);
        } else {
            //kill app
            finishAndRemoveTask();
        }
    }
}