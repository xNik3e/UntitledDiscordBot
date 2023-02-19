package com.example.untitleddiscordbot.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.untitleddiscordbot.MainActivity;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.material.navigation.NavigationView;

public class SettingsFragment extends Fragment {


    private Context ctx;
    private MainViewModel viewModel;
    private NavigationView parentNavView;
    private DrawerLayout parentDrawerLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
        this.parentNavView = MainActivity.drawerNavigationView;
        this.parentDrawerLayout = MainActivity.drawerLayout;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = viewModel = new ViewModelProvider(((ViewModelStoreOwner) ctx),
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);



        NavController navController = Navigation.findNavController(view.findViewById(R.id.container));
        parentNavView.setCheckedItem(R.id.coreSettings);

        parentNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.coreSettings:
                        navController.navigate(R.id.coreSettingsFragment);
                        parentDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.channelPermission:
                        navController.navigate(R.id.channelPermissionFragment);
                        parentDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.bulkRoles:
                        navController.navigate(R.id.bulkRolesFragment);
                        parentDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    default:
                        parentDrawerLayout.closeDrawer(GravityCompat.START);
                        return false;
                }

            }
        });

    }
}