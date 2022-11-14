package com.example.untitleddiscordbot.remote;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiService {

    @GET("users/@me")
    Call<UserModel> getUser(@Header("Authorization") String auth);

    @GET("users/@me/guilds")
    Call<List<UserGuildModelItem>> getGuilds(@Header("Authorization") String auth);

}
