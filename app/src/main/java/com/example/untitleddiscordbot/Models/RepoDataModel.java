package com.example.untitleddiscordbot.Models;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModel;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;

public class RepoDataModel {
    private UserModel user;
    private UserGuildModel userGuilds;

    public RepoDataModel() {
        this.user = new UserModel();
        this.userGuilds = new UserGuildModel();
    }

    public RepoDataModel(UserModel user, UserGuildModel userGuilds) {
        this.user = user;
        this.userGuilds = userGuilds;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserGuildModel getUserGuilds() {
        return userGuilds;
    }

    public void setUserGuilds(UserGuildModel userGuilds) {
        this.userGuilds = userGuilds;
    }
}
