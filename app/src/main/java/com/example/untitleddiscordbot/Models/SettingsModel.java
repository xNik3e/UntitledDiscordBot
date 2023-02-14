package com.example.untitleddiscordbot.Models;

import androidx.annotation.Nullable;

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


    public static SettingsModel getSettingsModel(String guildId) {
        //DEPLOYMENT
        return createDefaultSettings(guildId);
    }

    public static SettingsModel getDummySettingsModel(String guildId, List<RolesItem> roles) {
        return createDummySettings(guildId, roles);
    }

    public SettingsModel() {
        this.guildId = "";
        this.prefix = "";
        this.requiredRoleIds = new ArrayList<>();
        this.ignoredRoleIds = new ArrayList<>();
        this.autoDeleteTrigger = 0;
        this.autoDeleteResponse = 0;
        this.autoDeleteTriggerEnabled = false;
        this.autoDeleteResponseEnabled = false;
        this.isDefaultChannelEnabled = false;
        this.defaultChannelId = "";
        this.channelPermissions = new ArrayList<>();
    }

    private static SettingsModel createDummySettings(String guildId, List<RolesItem> roles) {
        SettingsModel settingsModel = new SettingsModel();
        settingsModel.setGuildId(guildId);
        settingsModel.setPrefix("*");

        int roleSize = roles.size();
        int randomRequireRoleSize = (int) ((Math.random() * roleSize / 3));
        int randomIgnoreRoleSize = (int) ((Math.random() * roleSize / 3));


        List<String> requiredDummyRoleId = new ArrayList<>();
        for (int i = 0; i < randomRequireRoleSize; i++) {
            int randomIndex = (int) ((Math.random() * roleSize));
            if (requiredDummyRoleId.contains(roles.get(randomIndex).getId()))
                i--;
            else
                requiredDummyRoleId.add(roles.get(randomIndex).getId());
        }
        settingsModel.setRequiredRoleIds(requiredDummyRoleId);

        List<String> ignoredDummyRoleId = new ArrayList<>();
        for (int i = 0; i < randomIgnoreRoleSize; i++) {
            int randomIndex = (int) ((Math.random() * roleSize));
            if (ignoredDummyRoleId.contains(roles.get(randomIndex).getId()))
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


    private static SettingsModel createDefaultSettings(String guildId) {
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

    public SettingsModel(SettingsModel settingsModel) {
        this.guildId = settingsModel.getGuildId();
        this.prefix = settingsModel.getPrefix();
        this.requiredRoleIds = settingsModel.getRequiredRoleIds();
        this.ignoredRoleIds = settingsModel.getIgnoredRoleIds();
        this.autoDeleteTrigger = settingsModel.getAutoDeleteTrigger();
        this.autoDeleteResponse = settingsModel.getAutoDeleteResponse();
        this.autoDeleteTriggerEnabled = settingsModel.isAutoDeleteTriggerEnabled();
        this.autoDeleteResponseEnabled = settingsModel.isAutoDeleteResponseEnabled();
        this.isDefaultChannelEnabled = settingsModel.isDefaultChannelEnabled();
        this.defaultChannelId = settingsModel.getDefaultChannelId();
        this.channelPermissions = settingsModel.getChannelPermissions();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingsModel that = (SettingsModel) o;

        if (channelPermissions.size() == that.channelPermissions.size())
            for (int i = 0; i < channelPermissions.size(); i++) {
                if (!channelPermissions.get(i).equals(that.channelPermissions.get(i)))
                    return false;
            }
        else {
            return false;
        }

        if (requiredRoleIds.size() == that.requiredRoleIds.size())
            for (int i = 0; i < requiredRoleIds.size(); i++) {
                if (!requiredRoleIds.get(i).equals(that.requiredRoleIds.get(i)))
                    return false;
            }
        else {
            return false;
        }

        if (ignoredRoleIds.size() == that.ignoredRoleIds.size())
            for (int i = 0; i < ignoredRoleIds.size(); i++) {
                if (!ignoredRoleIds.get(i).equals(that.ignoredRoleIds.get(i)))
                    return false;
            }
        else {
            return false;
        }


        return guildId.equals(that.guildId) &&
                prefix.equals(that.prefix) &&
                autoDeleteTrigger == that.autoDeleteTrigger &&
                autoDeleteResponse == that.autoDeleteResponse &&
                autoDeleteTriggerEnabled == that.autoDeleteTriggerEnabled &&
                autoDeleteResponseEnabled == that.autoDeleteResponseEnabled &&
                isDefaultChannelEnabled == that.isDefaultChannelEnabled &&
                defaultChannelId.equals(that.defaultChannelId);
    }
}
