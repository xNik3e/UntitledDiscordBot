package com.example.untitleddiscordbot.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.Utils.ServerStatus;
import com.example.untitleddiscordbot.interfaces.ServerSelectionInterface;

import java.util.List;

public class ServerSelectionAdapter extends RecyclerView.Adapter<ServerSelectionAdapter.ViewHolder> {

    private List<UserGuildModelItem> guilds;
    private Context ctx;
    private ServerSelectionInterface anInterface;

    public ServerSelectionAdapter(List<UserGuildModelItem> guilds,
                                  Context ctx,
                                  ServerSelectionInterface anInterface) {
        this.guilds = guilds;
        this.ctx = ctx;
        this.anInterface = anInterface;
    }

    @NonNull
    @Override
    public ServerSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.add_server_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerSelectionAdapter.ViewHolder holder, int position) {
        UserGuildModelItem guild = guilds.get(position);
        ServerStatus status = guild.isBotAdded() ? ServerStatus.ADDED : ServerStatus.NOT_ADDED;

        if(guild.getIcon() != null){
            String iconUrl = "https://cdn.discordapp.com/icons/"
                    + guild.getId()
                    + "/"
                    + guild.getIcon()
                    + ".png";

            Glide
                    .with(ctx)
                    .load(iconUrl)
                    .centerCrop()
                    .placeholder(R.drawable.discord_placeholder)
                    .into(holder.serverIcon);
        }else{
            Glide
                    .with(ctx)
                    .load(R.drawable.discord_placeholder)
                    .centerCrop()
                    .into(holder.serverIcon);
        }

        String role = guild.isOwner() ? "Owner" : "Member";
        holder.role.setText(role);
        holder.serverName.setText(guild.getName());
        if(guild.isBotAdded()){
            holder.add_to_server.setVisibility(View.GONE);
            holder.already_in_server.setVisibility(View.VISIBLE);
        }else{
            holder.add_to_server.setVisibility(View.VISIBLE);
            holder.already_in_server.setVisibility(View.GONE);
        }
        holder.icon.setImageDrawable(
                ctx.getResources().getDrawable(status.getIconResourceId()));
        holder.icon.setImageTintList(
                ColorStateList.valueOf(ctx.getColor(status.getColorResourceId())));


    }

    @Override
    public int getItemCount() {
        return guilds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView serverIcon, icon;
        TextView serverName, role, already_in_server, add_to_server;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serverIcon = itemView.findViewById(R.id.server_picture);
            icon = itemView.findViewById(R.id.icon);
            serverName = itemView.findViewById(R.id.server_name);
            role = itemView.findViewById(R.id.role);
            already_in_server = itemView.findViewById(R.id.already_in_server);
            add_to_server = itemView.findViewById(R.id.add_to_server);
        }
    }
}
