package com.example.untitleddiscordbot.Models.AllModel;

import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.DetailedGuildItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.EmojisItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.Models.DetailedMembers.DetailedMemberItem;
import com.example.untitleddiscordbot.Models.SettingsModel;

import java.util.List;

public class AllDataModel {
    private DetailedGuildItem guild;
    private List<DetailedMemberItem> members;
    private List<DetailedChannelItem> channels;

    private List<RolesItem> roles;
    private List<EmojisItem> emojis;

    private SettingsModel settings;


    public AllDataModel(DetailedGuildItem guild,  List<DetailedChannelItem> channels, List<DetailedMemberItem> members) {
        this.guild = guild;
        this.members = members;
        this.channels = channels;

        this.roles = guild.getRoles();
        this.emojis = guild.getEmojis();

        this.settings = SettingsModel.getSettingsModel(guild.getId());
    }

    public DetailedGuildItem getGuild() {
        return guild;
    }

    public void setGuild(DetailedGuildItem guild) {
        this.guild = guild;
    }

    public List<DetailedMemberItem> getMembers() {
        return members;
    }

    public void setMembers(List<DetailedMemberItem> members) {
        this.members = members;
    }

    public List<DetailedChannelItem> getChannels() {
        return channels;
    }

    public void setChannels(List<DetailedChannelItem> channels) {
        this.channels = channels;
    }

    public List<RolesItem> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesItem> roles) {
        this.roles = roles;
    }

    public List<EmojisItem> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<EmojisItem> emojis) {
        this.emojis = emojis;
    }

    public SettingsModel getSettings() {
        return settings;
    }

    public void setSettings(SettingsModel settings) {
        this.settings = settings;
    }
}
