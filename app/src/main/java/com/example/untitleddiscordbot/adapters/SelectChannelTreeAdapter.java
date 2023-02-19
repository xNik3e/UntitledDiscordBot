package com.example.untitleddiscordbot.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.Utils.AlwaysMarqueeTextView;
import com.example.untitleddiscordbot.interfaces.OnChannelSettingsClickInterface;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectChannelTreeAdapter extends RecyclerView.Adapter<SelectChannelTreeAdapter.ViewHolder> {

    private Context ctx;
    private List<DetailedChannelItem> channels;
    private List<ChannelPermissionsModel> channelPermissions;
    private OnChannelSettingsClickInterface onChannelSettingsClickInterface;

    public SelectChannelTreeAdapter(Context ctx, List<DetailedChannelItem> channels, List<ChannelPermissionsModel> channelPermissions, OnChannelSettingsClickInterface onChannelSettingsClickInterface) {
        this.ctx = ctx;
        this.channels = channels;
        this.channelPermissions = channelPermissions;
        this.onChannelSettingsClickInterface = onChannelSettingsClickInterface;
    }

    @NonNull
    @Override
    public SelectChannelTreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_channel_main_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectChannelTreeAdapter.ViewHolder holder, int position) {
        DetailedChannelItem item = channels.get(position);
        String channelId = item.getId();
        holder.selector.setVisibility(View.GONE);
        //holder.channelWelcomeIcon.setVisibility(View.GONE);

        Drawable imageResourceId;
        switch (item.getType()) {
            case 2:
                //Voice channel
                imageResourceId = ResourcesCompat.getDrawable(ctx.getResources(), R.drawable.ic_voice, null);
                break;
            case 4:
                //Category
                imageResourceId = ResourcesCompat.getDrawable(ctx.getResources(), R.drawable.ic_folder, null);
                break;
            case 13:
                //Stage channel
                imageResourceId = ResourcesCompat.getDrawable(ctx.getResources(), R.drawable.ic_stage, null);
                break;
            default:
                //Unknown
                imageResourceId = ResourcesCompat.getDrawable(ctx.getResources(), R.drawable.ic_hash, null);
                break;
        }


        if(item.getType() == 4){
            holder.channelCard.setVisibility(View.GONE);
            holder.folderCard.setVisibility(View.VISIBLE);
            holder.channelWelcomeIcon.setVisibility(View.GONE);
            holder.folderName.setText(item.getName());
            holder.folderCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.transparent));
            ChannelPermissionsModel model = channelPermissions.stream().filter(x->x.getChannelId().equals(channelId)).findFirst().orElse(null);
            if(model != null){
                if(model.isChecked()){
                    holder.folderCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.channel_chip_background));
                }

                boolean isRoles = model.getRequiredRoleIds().size()> 0;
                boolean isMembers = model.getMemberIds().size()> 0;

                holder.folderMembersLayout.setVisibility(isMembers ? View.VISIBLE : View.GONE);
                holder.folderRolesLayout.setVisibility(isRoles ? View.VISIBLE : View.GONE);

                holder.folderMembersCount.setText(String.valueOf(model.getMemberIds().size()));
                holder.folderRolesCount.setText(String.valueOf(model.getRequiredRoleIds().size()));
            }else{
                holder.folderMembersLayout.setVisibility(View.GONE);
                holder.folderRolesLayout.setVisibility(View.GONE);
            }
        }else{
            holder.channelCard.setVisibility(View.VISIBLE);
            holder.folderCard.setVisibility(View.GONE);
            holder.channelIcon.setImageDrawable(imageResourceId);
            holder.channelName.setText(item.getName());
            holder.channelCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.transparent));
            ChannelPermissionsModel model = channelPermissions.stream().filter(x->x.getChannelId().equals(channelId)).findFirst().orElse(null);
            if(model != null){
                if(model.isGrouped()){
                    holder.selector.setVisibility(View.VISIBLE);
                }
                if(model.isChecked()){
                    holder.channelCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.channel_chip_background));
                }

                boolean isRoles = model.getRequiredRoleIds().size()> 0;
                boolean isMembers = model.getMemberIds().size()> 0;

                holder.channelMembersLayout.setVisibility(isMembers ? View.VISIBLE : View.GONE);
                holder.channelRolesLayout.setVisibility(isRoles ? View.VISIBLE : View.GONE);
                holder.channelMembersCount.setText(String.valueOf(model.getMemberIds().size()));
                holder.channelRolesCount.setText(String.valueOf(model.getRequiredRoleIds().size()));

                if(model.isDefaultChannel()){
                    holder.channelWelcomeIcon.setVisibility(View.VISIBLE);
                }else
                    holder.channelWelcomeIcon.setVisibility(View.GONE);
            }else{
                holder.channelMembersLayout.setVisibility(View.GONE);
                holder.channelRolesLayout.setVisibility(View.GONE);
                holder.channelWelcomeIcon.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return channels.size();
    }


    public void onItemClick(@NonNull SelectChannelTreeAdapter.ViewHolder holder, int position){
        DetailedChannelItem item = channels.get(position);
        String channelId = item.getId();
        ChannelPermissionsModel model = channelPermissions.stream().filter(x->x.getChannelId().equals(channelId)).findFirst().orElse(null);

        if(item.getType() == 4){
            List<DetailedChannelItem> childrenChannels = channels.stream().filter(x->{
                String id = x.getParentId();
                if(id == null){
                    return false;
                }else{
                    return id.equals(channelId);
                }
            }).collect(Collectors.toList());
            List<String> childrenChannelIds = childrenChannels.stream().map(DetailedChannelItem::getId).collect(Collectors.toList());
            holder.folderCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.channel_chip_background));

            if(model != null){
                if(!model.isChecked()){
                    model.setChecked(true);

                    for(String id: childrenChannelIds){
                        int pos = channels.indexOf(childrenChannels.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null));
                        ChannelPermissionsModel channelPermissionsModel = channelPermissions.stream().filter(x->x.getChannelId().equals(id)).findFirst().orElse(null);
                        DetailedChannelItem child = childrenChannels.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
                        if(channelPermissionsModel != null){
                            channelPermissionsModel.setGrouped(true);
                        }else{
                            channelPermissionsModel = new ChannelPermissionsModel();
                            channelPermissionsModel.setChecked(false);
                            channelPermissionsModel.setParentId(channelId);
                            channelPermissionsModel.setChannelId(id);
                            channelPermissionsModel.setGrouped(true);
                            channelPermissionsModel.setType(child.getType());
                            channelPermissionsModel.setMemberIds(new ArrayList<>());
                            channelPermissionsModel.setRequiredRoleIds(new ArrayList<>());
                            channelPermissionsModel.setDefaultChannel(false);
                            channelPermissionsModel.setChannelName(child.getName());

                            channelPermissions.add(channelPermissionsModel);
                        }
                        notifyItemChanged(pos);
                    }
                }else{
                    model.setChecked(false);
                    holder.folderCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.transparent));

                    for(String id: childrenChannelIds){
                        int pos = channels.indexOf(childrenChannels.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null));
                        ChannelPermissionsModel channelPermissionsModel = channelPermissions.stream().filter(x->x.getChannelId().equals(id)).findFirst().orElse(null);
                        if(channelPermissionsModel != null){
                            channelPermissionsModel.setGrouped(false);
                        }
                        notifyItemChanged(pos);
                    }
                }
            }else{
                ChannelPermissionsModel channelModel = new ChannelPermissionsModel();
                channelModel.setChecked(true);
                channelModel.setParentId(null);
                channelModel.setChannelId(channelId);
                channelModel.setGrouped(false);
                channelModel.setType(item.getType());
                channelModel.setMemberIds(new ArrayList<>());
                channelModel.setRequiredRoleIds(new ArrayList<>());
                channelModel.setDefaultChannel(false);
                channelModel.setChannelName(item.getName());
                channelPermissions.add(channelModel);

                for(String id: childrenChannelIds){
                    int pos = channels.indexOf(childrenChannels.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null));
                    ChannelPermissionsModel channelPermissionsModel = channelPermissions.stream().filter(x->x.getChannelId().equals(id)).findFirst().orElse(null);
                    DetailedChannelItem child = childrenChannels.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
                    if(channelPermissionsModel != null){
                        channelPermissionsModel.setGrouped(true);
                    }else{
                        channelPermissionsModel = new ChannelPermissionsModel();
                        channelPermissionsModel.setChecked(false);
                        channelPermissionsModel.setParentId(channelId);
                        channelPermissionsModel.setChannelId(id);
                        channelPermissionsModel.setGrouped(true);
                        channelPermissionsModel.setType(child.getType());
                        channelPermissionsModel.setMemberIds(new ArrayList<>());
                        channelPermissionsModel.setRequiredRoleIds(new ArrayList<>());
                        channelPermissionsModel.setDefaultChannel(false);
                        channelPermissionsModel.setChannelName(child.getName());
                        channelPermissions.add(channelPermissionsModel);
                    }
                    notifyItemChanged(pos);
                }
            }
        }else{
            if(model != null){
                if(model.isChecked()){
                    model.setChecked(false);
                    holder.channelCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.transparent));
                }else{
                    model.setChecked(true);
                    holder.channelCard.setCardBackgroundColor(ctx.getResources().getColor(R.color.channel_chip_background));
                }
            }else{
                model = new ChannelPermissionsModel();
                model.setChecked(true);
                model.setParentId(item.getParentId());
                model.setChannelId(channelId);
                model.setGrouped(false);
                model.setType(item.getType());
                model.setMemberIds(new ArrayList<>());
                model.setRequiredRoleIds(new ArrayList<>());
                model.setDefaultChannel(false);
                model.setChannelName(item.getName());
                channelPermissions.add(model);
            }
            notifyItemChanged(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View selector;
        MaterialCardView folderCard, channelCard;
        AlwaysMarqueeTextView folderName, channelName;
        AlwaysMarqueeTextView folderRolesCount, folderMembersCount, channelRolesCount, channelMembersCount;
        LinearLayout folderRolesLayout, folderMembersLayout, channelRolesLayout, channelMembersLayout;
        ImageView channelWelcomeIcon, channelIcon;

        FrameLayout channelSettings, folderSettings;

        public ViewHolder(@NonNull View v) {
            super(v);

            selector = v.findViewById(R.id.selector);
            folderCard = v.findViewById(R.id.folder_card);
            channelCard = v.findViewById(R.id.channel_card);
            folderName = v.findViewById(R.id.folder_name);
            channelName = v.findViewById(R.id.channel_name);
            folderRolesCount = v.findViewById(R.id.folder_roles_count);
            folderMembersCount = v.findViewById(R.id.folder_members_count);
            channelRolesCount = v.findViewById(R.id.channel_roles_count);
            channelMembersCount = v.findViewById(R.id.channel_members_count);
            folderRolesLayout = v.findViewById(R.id.folder_roles_layout);
            folderMembersLayout = v.findViewById(R.id.folder_members_layout);
            channelRolesLayout = v.findViewById(R.id.channel_roles_layout);
            channelMembersLayout = v.findViewById(R.id.channel_members_layout);
            channelWelcomeIcon = v.findViewById(R.id.welcome_channel_icon);
            channelIcon = v.findViewById(R.id.channel_icon);
            channelSettings = v.findViewById(R.id.channel_settings_container);
            folderSettings = v.findViewById(R.id.folder_settings_container);

            channelCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(ViewHolder.this, getAdapterPosition());
                }
            });

            folderCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(ViewHolder.this, getAdapterPosition());
                }
            });

            folderSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChannelSettingsClickInterface.onChannelSettingsClick(channels.get(getAdapterPosition()));
                }
            });

            channelSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChannelSettingsClickInterface.onChannelSettingsClick(channels.get(getAdapterPosition()));
                    }
            });


        }
    }
}
