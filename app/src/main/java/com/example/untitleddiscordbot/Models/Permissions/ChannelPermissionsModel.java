package com.example.untitleddiscordbot.Models.Permissions;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChannelPermissionsModel {
    private String channelId;
    private String parentId;
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
        this.parentId = "";
    }

    public static ChannelPermissionsModel getDummyChannelPermissionsModel(){
        ChannelPermissionsModel temp = new ChannelPermissionsModel();
        List<String> tempRoleIds = List.of("123", "456", "789");
        List<String> tempMemberIds = List.of("123", "456", "789");
        temp.setRequiredRoleIds(tempRoleIds);
        temp.setMemberIds(tempMemberIds);
        return temp;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelPermissionsModel that = (ChannelPermissionsModel) o;

        if(requiredRoleIds.size() == that.requiredRoleIds.size())
            for (int i = 0; i < requiredRoleIds.size(); i++) {
                if(!requiredRoleIds.get(i).equals(that.requiredRoleIds.get(i)))
                    return false;
            }
        else {
            return false;
        }

        if(memberIds.size() == that.memberIds.size())
            for (int i = 0; i < memberIds.size(); i++) {
                if(!memberIds.get(i).equals(that.memberIds.get(i)))
                    return false;
            }
        else {
            return false;
        }

        return channelId.equals(that.channelId) &&
                parentId.equals(that.parentId) &&
                channelName.equals(that.channelName) &&
                isGroup == that.isGroup &&
                type == that.type &&
                isDefault == that.isDefault &&
                checked == that.checked;
    }
}


