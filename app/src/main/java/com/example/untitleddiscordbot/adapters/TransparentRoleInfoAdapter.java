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

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.R;

import java.util.List;

public class TransparentRoleInfoAdapter extends RecyclerView.Adapter<TransparentRoleInfoAdapter.ViewHolder> {

    private List<RolesItem> rolesItems;
    private Context ctx;
    private AllDataModel allDataModel;


    public TransparentRoleInfoAdapter(Context ctx, List<String> roleIds) {
        this.allDataModel = AllDataModel.getInstance();
        this.rolesItems = allDataModel.getRolesByIds(roleIds);
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public TransparentRoleInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_item_role_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransparentRoleInfoAdapter.ViewHolder holder, int position) {
        RolesItem item = rolesItems.get(position);
        holder.roleName.setText(item.getName());
        String hexColor = String.format("#%06X", (0xFFFFFF & item.getColor()));
        //holder.roleName.setTextColor(Color.parseColor(hexColor));
        holder.roleChip.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(hexColor)));
    }

    @Override
    public int getItemCount() {
        return rolesItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout roleChip;
        TextView roleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roleChip = itemView.findViewById(R.id.role_chip);
            roleName = itemView.findViewById(R.id.role_chip_name);
        }
    }
}
