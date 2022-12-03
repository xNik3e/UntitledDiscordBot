package com.example.untitleddiscordbot.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.Utils.AlwaysMarqueeTextView;
import com.example.untitleddiscordbot.interfaces.OnSelectChannelChipClickInterface;

import java.util.List;

public class SelectChannelChipAdapter extends RecyclerView.Adapter<SelectChannelChipAdapter.ViewHolder> {

    private Context ctx;
    private List<ChannelPermissionsModel> models;
    private OnSelectChannelChipClickInterface onChannelInterface;

    public SelectChannelChipAdapter(Context ctx, List<ChannelPermissionsModel> models, OnSelectChannelChipClickInterface onChannelInterface) {
        this.ctx = ctx;
        this.models = models;
        this.onChannelInterface = onChannelInterface;
    }

    @NonNull
    @Override
    public SelectChannelChipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_selected_channel_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectChannelChipAdapter.ViewHolder holder, int position) {
        ChannelPermissionsModel model = models.get(position);
        holder.channelName.setText(model.getChannelName());
        Drawable imageResourceId;
        switch (model.getType()) {
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

        holder.channelIcon.setImageDrawable(imageResourceId);
        if (model.getRequiredRoleIds().size() == 0) {
            holder.rolesLayout.setVisibility(View.GONE);
        } else {
            holder.rolesLayout.setVisibility(View.VISIBLE);
            holder.roleCount.setText(String.valueOf(model.getRequiredRoleIds().size()));
        }

        if (model.getMemberIds().size() == 0) {
            holder.membersLayout.setVisibility(View.GONE);
        } else {
            holder.membersLayout.setVisibility(View.VISIBLE);
            holder.membersCount.setText(String.valueOf(model.getMemberIds().size()));
        }

        if (model.isDefault()) {
            holder.welcomeIcon.setVisibility(View.VISIBLE);
        } else {
            holder.welcomeIcon.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView channelIcon, welcomeIcon;
        AlwaysMarqueeTextView channelName, membersCount, roleCount;
        LinearLayout rolesLayout, membersLayout;

        public ViewHolder(@NonNull View v) {
            super(v);

            channelIcon = v.findViewById(R.id.channel_type_icon);
            welcomeIcon = v.findViewById(R.id.welcome_channel_icon);
            channelName = v.findViewById(R.id.channel_name);
            membersCount = v.findViewById(R.id.members_count);
            roleCount = v.findViewById(R.id.roles_count);
            rolesLayout = v.findViewById(R.id.roles_layout);
            membersLayout = v.findViewById(R.id.members_layout);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChannelInterface.onCLick(models.get(getAdapterPosition()));
                }
            });

        }
    }
}
