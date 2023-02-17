package com.example.untitleddiscordbot.interfaces;

import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;

import java.nio.channels.Channel;
import java.util.List;

public interface OnChannelSettingsClickInterface {
    void onChannelSettingsClick(DetailedChannelItem item);
}
