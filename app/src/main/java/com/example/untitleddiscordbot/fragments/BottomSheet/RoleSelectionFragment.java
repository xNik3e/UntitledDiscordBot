package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;


public class RoleSelectionFragment extends BottomSheetDialogFragment {

    private Context ctx;
    private RecyclerView selectedRV, availableRV;
    private TextInputLayout search_layout;
    private TextInputEditText search_edit_text;
    private LinearLayout nothing_found_layout, empty_layout;
    private RelativeLayout rv_layout;
    private ImageView clearBtn;

    private FrameLayout bottomSheet;

    private List<RolesItem> includedRoles, excludedRoles, otherRoles, roleItems, adapterMainList;
    private SettingsModel settingsModel;
    private MainViewModel viewModel;

    private boolean required;
    private boolean isText = false;

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
                && excludedRoles != null && !excludedRoles.contains(role)).collect(Collectors.toList());

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

    @SuppressLint("ClickableViewAccessibility")
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
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        clearBtn = view.findViewById(R.id.clear_selection);

        availableRV.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        availableRV.setFocusable(true);
        availableRV.setFocusableInTouchMode(true);



        updateUI();

        selectedRV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //enable scrolling
                    selectedRV.setNestedScrollingEnabled(true);
                }else{
                    selectedRV.setNestedScrollingEnabled(false);
                }
            }
        });

        OnRoleChipClickInterface onRoleChipClickInterface = new OnRoleChipClickInterface() {
            @Override
            public void onRoleChipClick(RolesItem role, int position) {

                includedRoles.remove(role);
                otherRoles.add(role);
                otherRoles.sort((o1, o2) -> {
                    return o1.getPosition() - o2.getPosition();
                });
                if(!isText){
                    adapterMainList.clear();
                    adapterMainList.addAll(otherRoles);
                }else{
                    adapterMainList.remove(role);
                }
                updateUI();
                int pos = adapterMainList.indexOf(role);
                includedRoleAdapter.notifyItemRemoved(position);
                selectRoleAdapter.notifyItemInserted(pos);

            }
        };

        OnRoleSelectedInterface onRoleSelectedInterface = new OnRoleSelectedInterface() {
            @Override
            public void onRoleSelected(RolesItem role, int position) {
                includedRoles.add(role);
                otherRoles.remove(role);
                otherRoles.sort((o1, o2) -> {
                    return o1.getPosition() - o2.getPosition();
                });
                if(!isText){
                    adapterMainList.clear();
                    adapterMainList.addAll(otherRoles);
                }else{
                    adapterMainList.remove(role);
                }

                updateUI();
                selectRoleAdapter.notifyItemRemoved(position);
                includedRoleAdapter.notifyDataSetChanged();
                selectedRV.scrollToPosition(0);
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


        search_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }

            }
        });


        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 2){
                    isText = true;
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
                    updateUI();
                }else{
                    isText = false;
                    nothing_found_layout.setVisibility(View.GONE);
                    rv_layout.setVisibility(View.VISIBLE);
                    adapterMainList.clear();
                    adapterMainList.addAll(otherRoles);
                    //updateAdapter
                    selectRoleAdapter.notifyDataSetChanged();
                    updateUI();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherRoles.addAll(includedRoles);
                includedRoles.clear();
                otherRoles.sort((o1, o2) -> {
                    return o1.getPosition() - o2.getPosition();
                });
                adapterMainList.clear();
                adapterMainList.addAll(otherRoles);

                updateUI();
                includedRoleAdapter.notifyDataSetChanged();
                selectRoleAdapter.notifyDataSetChanged();

            }
        });



    }

    private void updateUI() {

        nothing_found_layout.setVisibility(View.GONE);
        selectedRV.setVisibility(View.GONE);
        empty_layout.setVisibility(View.GONE);
        rv_layout.setVisibility(View.GONE);

        if(!includedRoles.isEmpty()){
            selectedRV.setVisibility(View.VISIBLE);
        }else{
            selectedRV.setVisibility(View.GONE);
        }
        if(adapterMainList.isEmpty()){
            if(isText){
                nothing_found_layout.setVisibility(View.VISIBLE);
            }else{
                empty_layout.setVisibility(View.VISIBLE);
            }
            rv_layout.setVisibility(View.GONE);
        }else{
            rv_layout.setVisibility(View.VISIBLE);
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

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        if(v instanceof TextInputEditText){
            TextInputEditText editText = (TextInputEditText) v;
            editText.clearFocus();
        }
        if(v instanceof TextInputLayout){
            TextInputLayout editText = (TextInputLayout) v;
            editText.clearFocus();
        }

    }
}