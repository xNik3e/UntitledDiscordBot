package com.example.untitleddiscordbot.Models.AllModel;

import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.DetailedGuildItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.EmojisItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.RolesItem;
import com.example.untitleddiscordbot.Models.DetailedMembers.DetailedMemberItem;
import com.example.untitleddiscordbot.Models.SettingsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        prettify();
    }

    private void prettify(){
        //prettify members
        members.sort((o1, o2) -> {
            return o1.getUser().getUsername().compareTo(o2.getUser().getUsername());
        });
        //prettify channels
        List<DetailedChannelItem> categories = channels.stream().filter(x -> x.getType() == 4).collect(Collectors.toList());
        categories.sort((o1, o2) -> {
            return o1.getPosition() - o2.getPosition();
        });

        List<DetailedChannelItem> newChannels = new ArrayList<>();

        for (DetailedChannelItem category: categories) {
            List<DetailedChannelItem> temp = channels.stream().filter(x -> {
                if(x.getParentId() == null){
                    return false;
                }
                return x.getParentId().equals(category.getId());
            }).sorted((o1, o2) -> {
                return o1.getPosition() - o2.getPosition();
            }).sorted((o1, o2) -> {
                return o1.getType() - o2.getType();
            }).collect(Collectors.toList());
            newChannels.add(category);
            newChannels.addAll(temp);
            temp.clear();
        }

        setChannels(newChannels);

        //prettify roles
        roles.sort((o1, o2) -> {
            return o1.getPosition() - o2.getPosition();
        });

        //prettify emojis
        emojis.sort((o1, o2) -> {
            return o1.getName().compareTo(o2.getName());
        });

    }

    @Deprecated
    public void printChannelHierarchy(){
        StringBuilder sb = new StringBuilder();
        for (DetailedChannelItem channel: channels) {
            if(channel.getType() == 4){
                sb.append(channel.getName()).append("\n");
            }else{
                sb.append("----->").append(channel.getName()).append("\n");
            }
        }
        System.out.println(sb.toString());
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
