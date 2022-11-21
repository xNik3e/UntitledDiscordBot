package com.example.untitleddiscordbot.fragments.setting_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.SettingsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.adapters.TransparentRoleInfoAdapter;
import com.example.untitleddiscordbot.fragments.BottomSheet.RoleSelectionFragment;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;


public class CoreSettingsFragment extends Fragment {

    private Context ctx;
    private static AllDataModel allDataModel;
    private static SettingsModel settingsModel;
    private MainViewModel viewModel;

    private View prefixView, requireRoleView, ignoreRoleView, autoDeleteView;

    //views for prefix
    private TextInputEditText prefixEditText;
    private TextView exampleCommand;

    //views for require role
    private TextView emptyRequire;
    private RecyclerView requireRV;
    private MaterialButton addRequire;
    private TransparentRoleInfoAdapter requireAdapter;
    private FlexboxLayoutManager requireLayoutManager;

    //views for ignore role
    private TextView emptyIgnore;
    private RecyclerView ignoreRV;
    private MaterialButton addIgnore;
    private TransparentRoleInfoAdapter ignoreAdapter;
    private FlexboxLayoutManager ignoreLayoutManager;

    //views for auto delete
    private MaterialSwitch deleteTriggerSwitch, deleteResponseSwitch;
    private TextInputEditText deleteTriggerTime, deleteResponseTime;


    public CoreSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_core_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(((ViewModelStoreOwner) ctx),
                (ViewModelProvider.Factory) new ViewModelFactory())
                .get(MainViewModel.class);


        allDataModel = AllDataModel.getInstance();
        if (allDataModel != null)
            settingsModel = allDataModel.getSettings();

        prefixView = view.findViewById(R.id.prefix_view);
        requireRoleView = view.findViewById(R.id.require_role_view);
        ignoreRoleView = view.findViewById(R.id.ignore_role_view);
        autoDeleteView = view.findViewById(R.id.auto_delete_view);

        //prefix views
        prefixEditText = prefixView.findViewById(R.id.prefix_input_edit_text);
        exampleCommand = prefixView.findViewById(R.id.example_command);

        //require role views
        emptyRequire = requireRoleView.findViewById(R.id.empty_require);
        requireRV = requireRoleView.findViewById(R.id.rv_roles_require);
        addRequire = requireRoleView.findViewById(R.id.choose_role_require);
        requireLayoutManager = new FlexboxLayoutManager(ctx);
        requireLayoutManager.setFlexDirection(FlexDirection.ROW);
        requireLayoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        requireLayoutManager.setAlignItems(AlignItems.FLEX_START);

        //ignore role views
        emptyIgnore = ignoreRoleView.findViewById(R.id.empty_ignore);
        ignoreRV = ignoreRoleView.findViewById(R.id.rv_roles_ignore);
        addIgnore = ignoreRoleView.findViewById(R.id.choose_role_ignore);
        ignoreLayoutManager = new FlexboxLayoutManager(ctx);
        ignoreLayoutManager.setFlexDirection(FlexDirection.ROW);
        ignoreLayoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        ignoreLayoutManager.setAlignItems(AlignItems.FLEX_START);

        //auto delete views
        deleteTriggerSwitch = autoDeleteView.findViewById(R.id.delete_trigger_switch);
        deleteResponseSwitch = autoDeleteView.findViewById(R.id.delete_response_switch);
        deleteTriggerTime = autoDeleteView.findViewById(R.id.delete_trigger_edit_text);
        deleteResponseTime = autoDeleteView.findViewById(R.id.delete_response_edit_text);


        viewModel.getAllDataModel().observe(getViewLifecycleOwner(), new Observer<AllDataModel>() {
            @Override
            public void onChanged(AllDataModel dataModel) {
                if (dataModel != null) {
                    allDataModel = dataModel;
                    settingsModel = allDataModel.getSettings();
                    updateUI();
                }
            }
        });

        prefixEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        deleteTriggerTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        deleteResponseTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private void openShelf(boolean isRequired){
        RoleSelectionFragment roleSelectionFragment = new RoleSelectionFragment(allDataModel.getRoles(),
                settingsModel,
                viewModel,
                isRequired);
        roleSelectionFragment.show(getChildFragmentManager(), "roleSelection");


    }


    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    private void updateUI() {
        if (allDataModel != null) {
            String prefix = settingsModel.getPrefix();
            String command = prefix + "rolemenu create <name> -nodm";
            prefixEditText.setText(prefix);
            exampleCommand.setText(command);

            //require role
            if(settingsModel.getRequiredRoleIds().isEmpty()){
                emptyRequire.setVisibility(View.VISIBLE);
                requireRV.setVisibility(View.GONE);
            }else{
                emptyRequire.setVisibility(View.GONE);
                requireRV.setVisibility(View.VISIBLE);
                requireAdapter = new TransparentRoleInfoAdapter(ctx, settingsModel.getRequiredRoleIds());
                requireRV.setLayoutManager(requireLayoutManager);
                requireRV.setAdapter(requireAdapter);
            }

            addRequire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openShelf(true);
                }
            });

            //ignore role

            if(settingsModel.getIgnoredRoleIds().isEmpty()){
                emptyIgnore.setVisibility(View.VISIBLE);
                ignoreRV.setVisibility(View.GONE);
            }else{
                emptyIgnore.setVisibility(View.GONE);
                ignoreRV.setVisibility(View.VISIBLE);
                ignoreAdapter = new TransparentRoleInfoAdapter(ctx, settingsModel.getIgnoredRoleIds());
                ignoreRV.setLayoutManager(ignoreLayoutManager);
                ignoreRV.setAdapter(ignoreAdapter);
            }

            addIgnore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openShelf(false);
                }
            });

            //auto delete
            deleteTriggerSwitch.setChecked(settingsModel.isAutoDeleteTriggerEnabled());
            deleteResponseSwitch.setChecked(settingsModel.isAutoDeleteResponseEnabled());
            deleteTriggerTime.setText(String.valueOf(settingsModel.getAutoDeleteTrigger()));
            deleteResponseTime.setText(String.valueOf(settingsModel.getAutoDeleteResponse()));


        }
    }


}