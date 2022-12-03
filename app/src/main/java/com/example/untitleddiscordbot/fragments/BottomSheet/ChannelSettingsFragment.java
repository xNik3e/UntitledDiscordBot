package com.example.untitleddiscordbot.fragments.BottomSheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.Utils.AlwaysMarqueeTextView;
import com.example.untitleddiscordbot.adapters.IncludedRoleAdapter;
import com.example.untitleddiscordbot.adapters.SelectRoleAdapter;
import com.example.untitleddiscordbot.interfaces.OnChannelSettingsClickInterface;
import com.example.untitleddiscordbot.interfaces.OnRoleChipClickInterface;
import com.example.untitleddiscordbot.interfaces.OnRoleSelectedInterface;
import com.example.untitleddiscordbot.viewModels.MainViewModel;
import com.example.untitleddiscordbot.viewModels.ViewModelFactory;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;


public class ChannelSettingsFragment extends BottomSheetDialogFragment {

    private Context ctx;
    private MainViewModel mainViewModel;
    private AllDataModel ADL;

    private OnChannelSettingsClickInterface onChannelSettingsClickInterface;
    private List<ChannelPermissionsModel> channelPermissionsModel;
    private DetailedChannelItem item;

    private ImageView channelIcon;
    private AlwaysMarqueeTextView channelName;
    private LinearLayout welcomeChannelLayout;
    private MaterialSwitch welcomeChannelToggle;

    private LinearLayout rolesLayout, membersLayout;
    private TextView rolesTitle, membersTitle;
    private View selectionRole, selectionMember;

    boolean isText = false;

    //INCLUDE ROLES
    private View rolesContainer;
    private TextInputLayout roleSearchInputLayout;
    private TextInputEditText roleSearchEditText;
    private RecyclerView selectedRolesRV;
    private LinearLayout nothingFoundLayoutRoles;
    private LinearLayout noRoleLeftLayout;
    private RelativeLayout RVLayoutRoles;
    private RecyclerView availableRolesRV;

    //INCLUDE MEMBERS
    private View membersContainer;
    private TextInputLayout memberSearchInputLayout;
    private TextInputEditText memberSearchEditText;
    private RecyclerView selectedMembersRV;
    private LinearLayout nothingFoundLayoutMembers;
    private LinearLayout noMemberLeftLayout;
    private RelativeLayout RVLayoutMembers;
    private RecyclerView availableMembersRV;

    //ROLES ADAPTERS AND LISTS
    private List<RolesItem> adapterMainListRoles, includedRoles, rolesItems, otherRoles;

    //Upper RV Role Chip - includedRoles
    private IncludedRoleAdapter includedRoleAdapter;

    //Lower RV Role Full - adapterMainList
    private SelectRoleAdapter selectRoleAdapter;

    //TODO: MEMBERS ADAPTERS AND LISTS



    public ChannelSettingsFragment() {
        // Required empty public constructor
    }

    public ChannelSettingsFragment(OnChannelSettingsClickInterface onCSCL,
                                   List<ChannelPermissionsModel> permissionsModels,
                                   DetailedChannelItem detailedChannelItem,
                                   MainViewModel mainViewModel) {
        this.onChannelSettingsClickInterface = onCSCL;
        this.channelPermissionsModel = permissionsModels;
        this.mainViewModel = mainViewModel;
        this.item = detailedChannelItem;

        this.ADL = mainViewModel.getAllDataModel().getValue();

        this.adapterMainListRoles = new ArrayList<>();
        this.includedRoles = new ArrayList<>();
        this.otherRoles = new ArrayList<>();

        this.rolesItems = ADL.getRoles();

        adapterMainListRoles.addAll(rolesItems);
        otherRoles.addAll(rolesItems);
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
        return inflater.inflate(R.layout.fragment_channel_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        channelIcon = view.findViewById(R.id.channel_icon);
        channelName = view.findViewById(R.id.channel_name);
        welcomeChannelLayout = view.findViewById(R.id.welcome_channel_layout);
        welcomeChannelToggle = view.findViewById(R.id.welcome_channel_toggle);

        rolesLayout = view.findViewById(R.id.roles_layout);
        membersLayout = view.findViewById(R.id.members_layout);
        rolesTitle = view.findViewById(R.id.roles_title);
        membersTitle = view.findViewById(R.id.members_title);
        selectionRole = view.findViewById(R.id.selection_role);
        selectionMember = view.findViewById(R.id.selection_member);

        //Include Role Container
        rolesContainer = view.findViewById(R.id.roles_container);
        roleSearchInputLayout = rolesContainer.findViewById(R.id.role_search_input_layout);
        roleSearchEditText = rolesContainer.findViewById(R.id.role_search_edit_text);
        selectedRolesRV = rolesContainer.findViewById(R.id.rv_selected_roles);
        nothingFoundLayoutRoles = rolesContainer.findViewById(R.id.nothing_found_layout);
        noRoleLeftLayout = rolesContainer.findViewById(R.id.no_role_left_layout);
        RVLayoutRoles = rolesContainer.findViewById(R.id.rv_layout);
        availableRolesRV = rolesContainer.findViewById(R.id.rv_available_roles);

        //Include Member Container
        membersContainer = view.findViewById(R.id.members_container);
        memberSearchInputLayout = membersContainer.findViewById(R.id.members_search_input_layout);
        memberSearchEditText = membersContainer.findViewById(R.id.members_search_edit_text);
        selectedMembersRV = membersContainer.findViewById(R.id.rv_selected_members);
        nothingFoundLayoutMembers = membersContainer.findViewById(R.id.nothing_found_layout);
        noMemberLeftLayout = membersContainer.findViewById(R.id.no_member_left_layout);
        RVLayoutMembers = membersContainer.findViewById(R.id.rv_layout);
        availableMembersRV = membersContainer.findViewById(R.id.rv_available_members);

        availableRolesRV.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        availableRolesRV.setFocusable(true);
        availableRolesRV.setFocusableInTouchMode(true);

        updateUI();

        selectedRolesRV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //enable scrolling
                    selectedRolesRV.setNestedScrollingEnabled(true);
                }else{
                    //disable scrolling
                    selectedRolesRV.setNestedScrollingEnabled(false);
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
                    adapterMainListRoles.clear();
                    adapterMainListRoles.addAll(otherRoles);
                }else{
                    adapterMainListRoles.clear();
                }
                updateUI();
                int pos = adapterMainListRoles.indexOf(role);
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
                    adapterMainListRoles.clear();
                    adapterMainListRoles.addAll(otherRoles);
                }else{
                    adapterMainListRoles.remove(role);
                }

                updateUI();
                selectRoleAdapter.notifyItemRemoved(position);
                includedRoleAdapter.notifyDataSetChanged();
                selectedRolesRV.scrollToPosition(0);
            }
        };

        includedRoleAdapter = new IncludedRoleAdapter(ctx,
                includedRoles,
                onRoleChipClickInterface);

        selectRoleAdapter = new SelectRoleAdapter(ctx,
                adapterMainListRoles,
                onRoleSelectedInterface);

        LinearLayoutManager llm = new LinearLayoutManager(ctx);

        availableRolesRV.setLayoutManager(llm);
        availableRolesRV.setAdapter(selectRoleAdapter);

        FlexboxLayoutManager flm = new FlexboxLayoutManager(ctx);
        flm.setFlexDirection(FlexDirection.ROW);
        flm.setJustifyContent(JustifyContent.SPACE_EVENLY);
        flm.setAlignItems(AlignItems.FLEX_START);

        selectedRolesRV.setLayoutManager(flm);
        selectedRolesRV.setAdapter(includedRoleAdapter);

        roleSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    hideKeyboard(v);
            }
        });

        roleSearchEditText.addTextChangedListener(new TextWatcher() {
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

                    adapterMainListRoles.clear();
                    adapterMainListRoles.addAll(tempRoles);

                    if(tempRoles.isEmpty()){
                        nothingFoundLayoutRoles.setVisibility(View.VISIBLE);
                        RVLayoutRoles.setVisibility(View.GONE);
                    }else{
                        nothingFoundLayoutRoles.setVisibility(View.GONE);
                        RVLayoutRoles.setVisibility(View.VISIBLE);
                    }

                    //updateAdapter
                    selectRoleAdapter.notifyDataSetChanged();
                    updateUI();
                }else{
                    isText = false;
                    nothingFoundLayoutRoles.setVisibility(View.GONE);
                    RVLayoutRoles.setVisibility(View.VISIBLE);
                    adapterMainListRoles.clear();
                    adapterMainListRoles.addAll(otherRoles);
                    //updateAdapter
                    selectRoleAdapter.notifyDataSetChanged();
                    updateUI();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateUI() {
        nothingFoundLayoutRoles.setVisibility(View.GONE);
        noRoleLeftLayout.setVisibility(View.GONE);
        RVLayoutRoles.setVisibility(View.GONE);
        availableRolesRV.setVisibility(View.GONE);
        selectedRolesRV.setVisibility(View.GONE);

        selectedMembersRV.setVisibility(View.GONE);
        nothingFoundLayoutMembers.setVisibility(View.GONE);
        noMemberLeftLayout.setVisibility(View.GONE);
        RVLayoutMembers.setVisibility(View.GONE);
        availableMembersRV.setVisibility(View.GONE);

        if(!includedRoles.isEmpty()){
            selectedRolesRV.setVisibility(View.VISIBLE);
        }
        if(adapterMainListRoles.isEmpty()){
            if(isText){
                nothingFoundLayoutRoles.setVisibility(View.VISIBLE);
            }else{
                noRoleLeftLayout.setVisibility(View.VISIBLE);
            }
            availableRolesRV.setVisibility(View.GONE);
        }else{
            availableRolesRV.setVisibility(View.VISIBLE);
        }
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