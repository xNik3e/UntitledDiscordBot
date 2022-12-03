package com.example.untitleddiscordbot.Models.Permissions;

import java.util.ArrayList;
import java.util.List;

public class ChannelPermissionsModel {
    private String channelId;
    private String channelName;
    private boolean isGroup;
    private int type;
    private List<String> requiredRoleIds;
    private List<String> memberIds;
    private boolean isDefault;
    private boolean checked;

    public ChannelPermissionsModel() {
        this.channelId = "";
        this.isGroup = false;
        this.requiredRoleIds = new ArrayList<>();
        this.memberIds = new ArrayList<>();
        this.isDefault = false;
        this.checked = false;
        this.channelName = "";
        this.type = 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public List<String> getRequiredRoleIds() {
        return requiredRoleIds;
    }

    public void setRequiredRoleIds(List<String> requiredRoleIds) {
        this.requiredRoleIds = requiredRoleIds;
    }

    public List<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
