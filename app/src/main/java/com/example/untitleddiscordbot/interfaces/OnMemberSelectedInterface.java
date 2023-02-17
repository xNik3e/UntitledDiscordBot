package com.example.untitleddiscordbot.interfaces;

import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.DetailedMembers.DetailedMemberItem;

public interface OnMemberSelectedInterface {
    void onMemberSelected(DetailedMemberItem member, int position);
}
