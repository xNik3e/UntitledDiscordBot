package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.Models.SettingsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.adapters.IncludedRoleAdapter;
import com.example.untitleddiscordbot.adapters.SelectRoleAdapter;
import com.example.untitleddiscordbot.adapters.ServerSelectionAdapter;
import com.example.untitleddiscordbot.interfaces.OnRoleChipClickInterface;
import com.example.untitleddiscordbot.interfaces.OnRoleSelectedInterface;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;


public class RoleSelectionFragment extends BottomSheetDialogFragment {

    private Context ctx;
    private RecyclerView selectedRV, availableRV;
    private TextInputLayout search_layout;
    private TextInputEditText search_edit_text;
    private LinearLayout nothing_found_layout, empty_layout, rv_layout;

    private List<RolesItem> includedRoles, excludedRoles, otherRoles, roleItems, adapterMainList;
    private SettingsModel settingsModel;
    private MainViewModel viewModel;

    private boolean required;

    private IncludedRoleAdapter includedRoleAdapter;
    private SelectRoleAdapter selectRoleAdapter;

    public RoleSelectionFragment() {
        // Required empty public constructor
    }

    public RoleSelectionFragment(List<RolesItem> roles, SettingsModel model, MainViewModel viewModel, boolean required){
        this.roleItems = roles;
        this.settingsModel = model;
        this.viewModel = viewModel;
        this.required = required;

        AllDataModel adl = viewModel.getAllDataModel().getValue();

        includedRoles = new ArrayList<>();
        excludedRoles = new ArrayList<>();
        otherRoles = new ArrayList<>();
        adapterMainList  = new ArrayList<>();


        if(required){
            includedRoles = adl.getRolesByIds(model.getRequiredRoleIds());
            excludedRoles = adl.getRolesByIds(model.getIgnoredRoleIds());
        }else{
            includedRoles = adl.getRolesByIds(model.getIgnoredRoleIds());
            excludedRoles = adl.getRolesByIds(model.getRequiredRoleIds());
        }


        otherRoles = roles.stream().filter(role -> includedRoles != null && !includedRoles.contains(role)
                || excludedRoles != null && !excludedRoles.contains(role)).collect(Collectors.toList());

        adapterMainList.addAll(otherRoles);
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


        OnRoleChipClickInterface onRoleChipClickInterface = new OnRoleChipClickInterface() {
            @Override
            public void onRoleChipClick(RolesItem role, int position) {
                includedRoles.remove(role);
                otherRoles.add(role);
                adapterMainList.add(role);
                updateUI();
                includedRoleAdapter.notifyItemRemoved(position);
                selectRoleAdapter.notifyDataSetChanged();

            }
        };

        OnRoleSelectedInterface onRoleSelectedInterface = new OnRoleSelectedInterface() {
            @Override
            public void onRoleSelected(RolesItem role, int position) {
                includedRoles.add(role);
                otherRoles.remove(role);
                adapterMainList.remove(role);
                updateUI();
                selectRoleAdapter.notifyItemRemoved(position);
                includedRoleAdapter.notifyItemInserted(includedRoles.size() - 1);

            }
        };

        includedRoleAdapter = new IncludedRoleAdapter(ctx,
                includedRoles,
                onRoleChipClickInterface);

        selectRoleAdapter = new SelectRoleAdapter(ctx,
                adapterMainList,
                onRoleSelectedInterface);



        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        availableRV.setLayoutManager(llm);
        availableRV.setAdapter(selectRoleAdapter);

        FlexboxLayoutManager flm = new FlexboxLayoutManager(ctx);
        flm.setFlexDirection(FlexDirection.ROW);
        flm.setJustifyContent(JustifyContent.SPACE_EVENLY);
        flm.setAlignItems(AlignItems.FLEX_START);
        selectedRV.setLayoutManager(flm);
        selectedRV.setAdapter(includedRoleAdapter);


        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 2){
                    List<BoundExtractedResult<RolesItem>> match = FuzzySearch
                            .extractAll(s.toString(),
                                    otherRoles,
                                    RolesItem::getName,
                                    80);
                    List<Integer> indexes = match.stream().map(
                            BoundExtractedResult::getIndex).collect(Collectors.toList());

                    List<RolesItem> tempRoles = new ArrayList<>();
                    indexes.forEach(tempI ->{
                        tempRoles.add(otherRoles.get(tempI));
                    });

                    adapterMainList.clear();
                    adapterMainList.addAll(tempRoles);

                    if(tempRoles.isEmpty()){
                        nothing_found_layout.setVisibility(View.VISIBLE);
                        rv_layout.setVisibility(View.GONE);
                    }else{
                        nothing_found_layout.setVisibility(View.GONE);
                        rv_layout.setVisibility(View.VISIBLE);
                    }

                    //updateAdapter
                    selectRoleAdapter.notifyDataSetChanged();
                }else{
                    nothing_found_layout.setVisibility(View.GONE);
                    rv_layout.setVisibility(View.VISIBLE);
                    adapterMainList.clear();
                    adapterMainList.addAll(otherRoles);
                    //updateAdapter
                    selectRoleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void updateUI() {

        nothing_found_layout.setVisibility(View.GONE);
        empty_layout.setVisibility(View.GONE);
        rv_layout.setVisibility(View.GONE);

        if(includedRoles.isEmpty()){
            selectedRV.setVisibility(View.GONE);
        }
        if(adapterMainList.isEmpty()){
            empty_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        List<String> ids = includedRoles.stream().map(RolesItem::getId).collect(Collectors.toList());
        if(required){
            settingsModel.setRequiredRoleIds(ids);
        }else {
            settingsModel.setIgnoredRoleIds(ids);
        }
        viewModel.updateSettings(settingsModel);
    }
}