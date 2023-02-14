package com.example.untitleddiscordbot.interfaces;

import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.Models.SettingsModel;

import java.util.List;

public interface OnDialogDismissNewPermissionsListener {
    void onClick(List<ChannelPermissionsModel> permissionsModels);
}
