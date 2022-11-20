package com.example.untitleddiscordbot.Models;

import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsModel {
    private String guildId;
    private String prefix;
    private List<String> requiredRoleIds;
    private List<String> ignoredRoleIds;
    private int autoDeleteTrigger;
    private int autoDeleteResponse;
    private boolean autoDeleteTriggerEnabled;
    private boolean autoDeleteResponseEnabled;

    private boolean isDefaultChannelEnabled;
    private String defaultChannelId;

    private List<ChannelPermissionsModel> channelPermissions;


    public static SettingsModel getSettingsModel(String guildId){
        return createDefaultSettings(guildId);
    }

    private static SettingsModel createDefaultSettings(String guildId){
        SettingsModel settingsModel = new SettingsModel();

        settingsModel.setGuildId(guildId);
        settingsModel.setPrefix("!");
        settingsModel.setRequiredRoleIds(new ArrayList<>());
        settingsModel.setIgnoredRoleIds(new ArrayList<>());
        settingsModel.setAutoDeleteTrigger(0);
        settingsModel.setAutoDeleteResponse(0);
        settingsModel.setAutoDeleteTriggerEnabled(false);
        settingsModel.setAutoDeleteResponseEnabled(false);
        settingsModel.setDefaultChannelId("");
        settingsModel.setChannelPermissions(new ArrayList<>());

        return settingsModel;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getRequiredRoleIds() {
        return requiredRoleIds;
    }

    public void setRequiredRoleIds(List<String> requiredRoleIds) {
        this.requiredRoleIds = requiredRoleIds;
    }

    public List<String> getIgnoredRoleIds() {
        return ignoredRoleIds;
    }

    public void setIgnoredRoleIds(List<String> ignoredRoleIds) {
        this.ignoredRoleIds = ignoredRoleIds;
    }

    public int getAutoDeleteTrigger() {
        return autoDeleteTrigger;
    }

    public void setAutoDeleteTrigger(int autoDeleteTrigger) {
        this.autoDeleteTrigger = autoDeleteTrigger;
    }

    public int getAutoDeleteResponse() {
        return autoDeleteResponse;
    }

    public void setAutoDeleteResponse(int autoDeleteResponse) {
        this.autoDeleteResponse = autoDeleteResponse;
    }

    public boolean isAutoDeleteTriggerEnabled() {
        return autoDeleteTriggerEnabled;
    }

    public void setAutoDeleteTriggerEnabled(boolean autoDeleteTriggerEnabled) {
        this.autoDeleteTriggerEnabled = autoDeleteTriggerEnabled;
    }

    public boolean isAutoDeleteResponseEnabled() {
        return autoDeleteResponseEnabled;
    }

    public void setAutoDeleteResponseEnabled(boolean autoDeleteResponseEnabled) {
        this.autoDeleteResponseEnabled = autoDeleteResponseEnabled;
    }

    public boolean isDefaultChannelEnabled() {
        return isDefaultChannelEnabled;
    }

    public void setDefaultChannelEnabled(boolean defaultChannelEnabled) {
        isDefaultChannelEnabled = defaultChannelEnabled;
    }

    public String getDefaultChannelId() {
        return defaultChannelId;
    }

    public void setDefaultChannelId(String defaultChannelId) {
        this.defaultChannelId = defaultChannelId;
    }

    public List<ChannelPermissionsModel> getChannelPermissions() {
        return channelPermissions;
    }

    public void setChannelPermissions(List<ChannelPermissionsModel> channelPermissions) {
        this.channelPermissions = channelPermissions;
    }
}
