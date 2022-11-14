package com.example.untitleddiscordbot.Models.UserGuildsModel;

import com.example.untitleddiscordbot.Utils.AuthUtil;
import com.example.untitleddiscordbot.Utils.ServerStatus;

import net.openid.appauth.AuthState;

public class ServerModel {
    private String iconUrl;
    private String serverName;
    private ServerStatus status;
    private String role;

    public ServerModel(UserGuildModelItem item) {
        this.iconUrl = item.getIcon() != null ?
                "https://cdn.discordapp.com/icons/"
                + item.getId()
                + "/"
                + item.getIcon()
                + ".png" :
                null;
        this.serverName = item.getName();

    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
