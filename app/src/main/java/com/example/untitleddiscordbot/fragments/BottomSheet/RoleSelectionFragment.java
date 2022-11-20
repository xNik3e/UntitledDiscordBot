package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.Models.SettingsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RoleSelectionFragment extends BottomSheetDialogFragment {

    private Context ctx;
    private RecyclerView selectedRV, availableRV;
    private TextInputLayout search_layout;
    private TextInputEditText search_edit_text;
    private LinearLayout nothing_found_layout, empty_layout, rv_layout;

    private List<RolesItem> includedRoles, otherRoles, roleItems;
    private SettingsModel settingsModel;
    private MainViewModel viewModel;

    public RoleSelectionFragment() {
        // Required empty public constructor
    }

    public RoleSelectionFragment(List<RolesItem> roles, SettingsModel model, MainViewModel viewModel, boolean required){
        this.roleItems = roles;
        this.settingsModel = model;
        this.viewModel = viewModel;

        AllDataModel adl = viewModel.getAllDataModel().getValue();

        includedRoles = new ArrayList<>();
        otherRoles = new ArrayList<>();


        if(required){
            includedRoles = adl.getRolesByIds(model.getRequiredRoleIds());
        }else{
            includedRoles = adl.getRolesByIds(model.getIgnoredRoleIds());
        }

        otherRoles = roles.stream().filter(role -> !includedRoles.contains(role)).collect(Collectors.toList());
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
        return inflater.inflate(R.layout.fragment_role_selection, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedRV = view.findViewById(R.id.rv_selected_roles);
        availableRV = view.findViewById(R.id.rv_available_roles);
        search_layout = view.findViewById(R.id.role_search_input_layout);
        search_edit_text = view.findViewById(R.id.role_search_edit_text);
        nothing_found_layout = view.findViewById(R.id.nothing_found_layout);
        empty_layout = view.findViewById(R.id.no_role_left_layout);
        rv_layout = view.findViewById(R.id.rv_layout);

        updateUI();
    }

    private void updateUI() {

        nothing_found_layout.setVisibility(View.GONE);
        empty_layout.setVisibility(View.GONE);
        rv_layout.setVisibility(View.GONE);

        if(includedRoles.isEmpty()){
            selectedRV.setVisibility(View.GONE);
        }
        if(otherRoles.isEmpty()){
            empty_layout.setVisibility(View.VISIBLE);
        }

    }
}