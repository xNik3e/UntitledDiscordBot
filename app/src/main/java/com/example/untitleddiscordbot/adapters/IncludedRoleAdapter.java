package com.example.untitleddiscordbot.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.interfaces.OnRoleChipClickInterface;

import java.util.List;

public class IncludedRoleAdapter extends RecyclerView.Adapter<IncludedRoleAdapter.ViewHolder> {

    private Context ctx;
    private List<RolesItem> roles;
    private OnRoleChipClickInterface onRoleChipClickInterface;

    public IncludedRoleAdapter(Context ctx, List<RolesItem> roles, OnRoleChipClickInterface onRoleChipClickInterface) {
        this.ctx = ctx;
        this.roles = roles;
        this.onRoleChipClickInterface = onRoleChipClickInterface;
    }

    @NonNull
    @Override
    public IncludedRoleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_selected_role_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncludedRoleAdapter.ViewHolder holder, int position) {
        RolesItem item = roles.get(position);
        holder.roleName.setText(item.getName());
        String hexColor = String.format("#%06X", (0xFFFFFF & item.getColor()));
        //holder.roleName.setTextColor(Color.parseColor(hexColor));
        holder.roleChip.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(hexColor)));
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout roleChip;
        TextView roleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roleChip = itemView.findViewById(R.id.role_chip);
            roleName = itemView.findViewById(R.id.role_chip_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRoleChipClickInterface.onRoleChipClick(roles.get(getAdapterPosition()),
                            getAdapterPosition());
                }
            });
        }
    }
}
