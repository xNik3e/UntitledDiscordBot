package com.example.untitleddiscordbot.Models;

import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
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
        //DEPLOYMENT
        return createDefaultSettings(guildId);
    }

    public static SettingsModel getDummySettingsModel(String guildId, List<RolesItem> roles){
        return createDummySettings(guildId, roles);
    }

    private static SettingsModel createDummySettings(String guildId, List<RolesItem> roles){
        SettingsModel settingsModel = new SettingsModel();
        settingsModel.setGuildId(guildId);
        settingsModel.setPrefix("*");

        int roleSize = roles.size();
        int randomRequireRoleSize = (int) ((Math.random() * roleSize/3));
        int randomIgnoreRoleSize = (int) ((Math.random() * roleSize/3));


        List<String> requiredDummyRoleId = new ArrayList<>();
        for(int i = 0; i < randomRequireRoleSize; i++){
            int randomIndex = (int) ((Math.random() * roleSize));
            if(requiredDummyRoleId.contains(roles.get(randomIndex).getId()))
                i--;
            else
                requiredDummyRoleId.add(roles.get(randomIndex).getId());
        }
        settingsModel.setRequiredRoleIds(requiredDummyRoleId);

        List<String> ignoredDummyRoleId = new ArrayList<>();
        for(int i = 0; i < randomIgnoreRoleSize; i++){
            int randomIndex = (int) ((Math.random() * roleSize));
            if(ignoredDummyRoleId.contains(roles.get(randomIndex).getId()))
                i--;
            else
                ignoredDummyRoleId.add(roles.get(randomIndex).getId());
        }
        settingsModel.setIgnoredRoleIds(ignoredDummyRoleId);

        settingsModel.setAutoDeleteTrigger(0);
        settingsModel.setAutoDeleteResponse(0);
        settingsModel.setAutoDeleteTriggerEnabled(false);
        settingsModel.setAutoDeleteResponseEnabled(false);

        settingsModel.setChannelPermissions(new ArrayList<>());
        settingsModel.setDefaultChannelId("0");
        settingsModel.setDefaultChannelEnabled(false);

        return settingsModel;
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
