package com.example.untitleddiscordbot.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.Models.DetailedMembers.DetailedMemberItem;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.interfaces.OnMemberChipClickInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class IncludedMemberAdapter extends RecyclerView.Adapter<IncludedMemberAdapter.ViewHolder> {

    private Context ctx;
    private List<DetailedMemberItem> members;
    private OnMemberChipClickInterface onMemberChipClickInterface;

    public IncludedMemberAdapter(Context ctx, List<DetailedMemberItem> members, OnMemberChipClickInterface onMemberChipClickInterface) {
        this.ctx = ctx;
        this.members = members;
        this.onMemberChipClickInterface = onMemberChipClickInterface;
    }

    @NonNull
    @Override
    public IncludedMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_selected_member_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncludedMemberAdapter.ViewHolder holder, int position) {
        DetailedMemberItem item = members.get(position);
        holder.memberName.setText(item.getUser().getUsername());
        if (item.getUser().getAvatar() != null) {
            String url = "https://cdn.discordapp.com/avatars/" + item.getUser().getId() + "/" + item.getUser().getAvatar() + ".png";
            Glide.with(ctx).load(url).placeholder(R.drawable.discord_placeholder).into(holder.memberAvatar);
            try {
                URL url_value = new URL(url);
                Bitmap mIcon1 =
                        BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
                Palette.from(mIcon1).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        Palette.Swatch swatch = palette.getDominantSwatch();
                        if (swatch != null) {
                            holder.memberChip.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
                        }else{
                            String hexColor = String.format("#%06X", (0xFFFFFF & 0xfca41c));
                            holder.memberChip.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(hexColor)));

                        }
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Glide.with(ctx).load(R.drawable.discord_placeholder).into(holder.memberAvatar);
            String hexColor = String.format("#%06X", (0xFFFFFF & 0xfca41c));
            holder.memberChip.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(hexColor)));
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout memberChip;
        TextView memberName;
        ImageView memberAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            memberChip = itemView.findViewById(R.id.member_chip);
            memberName = itemView.findViewById(R.id.member_chip_name);
            memberAvatar = itemView.findViewById(R.id.member_chip_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMemberChipClickInterface.onMemberChipClick(members.get(getAdapterPosition()),
                            getAdapterPosition());
                }
            });
        }
    }
}
