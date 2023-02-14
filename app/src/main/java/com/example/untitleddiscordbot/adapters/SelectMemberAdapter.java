package com.example.untitleddiscordbot.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Layout;
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
import com.example.untitleddiscordbot.Models.DetailedMembers.DetailedMemberItem;
import com.example.untitleddiscordbot.R;
import com.example.untitleddiscordbot.interfaces.OnMemberSelectedInterface;

import java.net.URL;
import java.util.List;

public class SelectMemberAdapter extends RecyclerView.Adapter<SelectMemberAdapter.ViewHolder> {

    private Context ctx;
    private List<DetailedMemberItem> members;
    private OnMemberSelectedInterface onMemberSelectedInterface;

    public SelectMemberAdapter(Context ctx, List<DetailedMemberItem> members, OnMemberSelectedInterface onMemberSelectedInterface) {
        this.ctx = ctx;
        this.members = members;
        this.onMemberSelectedInterface = onMemberSelectedInterface;
    }

    @NonNull
    @Override
    public SelectMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_select_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectMemberAdapter.ViewHolder holder, int position) {
        DetailedMemberItem item = members.get(position);
        holder.memberName.setText(item.getUser().getUsername());
        if (item.getUser().getAvatar() != null) {
            String url = "https://cdn.discordapp.com/avatars/" + item.getUser().getId() + "/" + item.getUser().getAvatar() + ".png";
            Glide.with(ctx).load(url).placeholder(R.drawable.discord_placeholder).into(holder.memberAvatar);

        } else {
            Glide.with(ctx).load(R.drawable.discord_placeholder).into(holder.memberAvatar);
            String hexColor = String.format("#%06X", (0xFFFFFF & 0xfca41c));
            holder.memberChip.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(hexColor)));
        }
        String fullName = item.getUser().getUsername() + "#" + item.getUser().getDiscriminator();
        holder.memberDiscriminator.setText(fullName);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView memberAvatar;
        TextView memberName;
        TextView memberDiscriminator;
        LinearLayout memberChip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            memberAvatar = itemView.findViewById(R.id.member_chip_image);
            memberName = itemView.findViewById(R.id.member_chip_name);
            memberDiscriminator = itemView.findViewById(R.id.member_chip_discriminator);
            memberChip = itemView.findViewById(R.id.member_chip);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMemberSelectedInterface.onMemberSelected(
                            members.get(getAdapterPosition()),
                            getAdapterPosition()
                    );
                }
            });
        }
    }
}
