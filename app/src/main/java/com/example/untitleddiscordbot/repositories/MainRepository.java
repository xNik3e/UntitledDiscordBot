package com.example.untitleddiscordbot.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.untitleddiscordbot.Models.AllModel.AllDataModel;
import com.example.untitleddiscordbot.Models.DefaultResponse;
import com.example.untitleddiscordbot.Models.DetailedChannels.DetailedChannelItem;
import com.example.untitleddiscordbot.Models.DetailedGuild.DetailedGuildItem;
import com.example.untitleddiscordbot.Models.DetailedMembers.DetailedMemberItem;
import com.example.untitleddiscordbot.Models.DetailedMembers.User;
import com.example.untitleddiscordbot.Models.Permissions.ChannelPermissionsModel;
import com.example.untitleddiscordbot.Models.SettingsModel;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.Utils.PermissionUtil;
import com.example.untitleddiscordbot.remote.DiscordApiService;
import com.example.untitleddiscordbot.remote.MyApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository instance;
    private final DiscordApiService discordApiService;
    private final MyApiService myApiService;

    private final MutableLiveData<UserModel> mutableUserModel;
    private final MutableLiveData<List<UserGuildModelItem>> mutableUserGuildModel;
    private final MutableLiveData<UserGuildModelItem> selectedServer;
    private final MutableLiveData<DetailedGuildItem> mutableDetailedGuildItemModel;

    private final MutableLiveData<AllDataModel> mutableAllDataModel;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    public static MainRepository getInstance(DiscordApiService discordApiService, MyApiService myApiService) {
        if (instance == null) {
            instance = new MainRepository(discordApiService, myApiService);
        }
        return instance;
    }

    public MainRepository(DiscordApiService discordApiService, MyApiService myApiService) {
        this.discordApiService = discordApiService;
        this.myApiService = myApiService;
        mutableUserModel = new MutableLiveData<>(null);
        mutableUserGuildModel = new MutableLiveData<>(null);
        selectedServer = new MutableLiveData<>(null);
        mutableDetailedGuildItemModel = new MutableLiveData<>(null);
        mutableAllDataModel = new MutableLiveData<>(null);
    }


    public LiveData<UserModel> fetchUserModel(String auth) {
        String header = "Bearer " + auth;
        Call<UserModel> call = discordApiService.getUser(header);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    mutableUserModel.setValue(userModel);
                } else {
                    mutableUserModel.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        return mutableUserModel;
    }

    public LiveData<List<UserGuildModelItem>> fetchUserGuilds(String auth) {
        String header = "Bearer " + auth;
        Call<List<UserGuildModelItem>> call = discordApiService.getGuilds(header);
        call.enqueue(new Callback<List<UserGuildModelItem>>() {
            @Override
            public void onResponse(Call<List<UserGuildModelItem>> call, Response<List<UserGuildModelItem>> response) {
                if (response.isSuccessful()) {
                    List<UserGuildModelItem> userGuildModelItems = response.body();
                    List<UserGuildModelItem> filteredResults = new ArrayList<>();
                    if (userGuildModelItems != null) {
                        filteredResults = userGuildModelItems.stream().filter(
                                PermissionUtil::canManageServer
                        ).collect(Collectors.toList());
                    }
                    mutableUserGuildModel.setValue(filteredResults);
                } else {
                    mutableUserGuildModel.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<UserGuildModelItem>> call, Throwable t) {
                UserGuildModelItem item = new UserGuildModelItem("error");
                mutableUserGuildModel.setValue(List.of(item));
            }
        });
        return mutableUserGuildModel;
    }

    public void getUpdatedGuilds(List<String> ids) {
//        String bodyJSON = new Gson().toJson(ids);
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), bodyJSON);
        Call<DefaultResponse<List<String>>> call = myApiService.getGuildsByIds(ids);
        call.enqueue(new Callback<DefaultResponse<List<String>>>() {
            @Override
            public void onResponse(Call<DefaultResponse<List<String>>> call, Response<DefaultResponse<List<String>>> response) {
                List<String> guilds = response.body().getData();
//                List<UserGuildModelItem> updatedGuilds = mutableUserGuildModel.getValue().stream().filter(item ->{
//                    return guilds.contains(item.getId());
//                }).collect(Collectors.toList());

                List<UserGuildModelItem> tempGuilds = mutableUserGuildModel.getValue();
                List<UserGuildModelItem> updatedGuilds = tempGuilds != null ? mutableUserGuildModel.getValue().stream().map(item -> {
                    if (guilds.contains(item.getId())) {
                        item.setBotAdded(true);
                    }
                    return item;
                }).collect(Collectors.toList()) : new ArrayList<UserGuildModelItem>();
                mutableUserGuildModel.postValue(updatedGuilds);
            }

            @Override
            public void onFailure(Call<DefaultResponse<List<String>>> call, Throwable t) {
                UserGuildModelItem item = new UserGuildModelItem("error");
                mutableUserGuildModel.setValue(List.of(item));
            }
        });

    }

    public void getDetailedGuild(String id) {
        mutableDetailedGuildItemModel.setValue(new DetailedGuildItem());
        AllDataModel.resetInstance();
        Map<String, Object> body = new HashMap<>();
        body.put("guildId", id);
        Call<DefaultResponse<Map<String, Object>>> call = myApiService.getDetailedGuild(body);
        call.enqueue(new Callback<DefaultResponse<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<DefaultResponse<Map<String, Object>>> call, Response<DefaultResponse<Map<String, Object>>> response) {
                if (response.isSuccessful()) {
                    Map<String, Object> data = response.body().getData();
                    List<String> stringList = (List<String>) response.body().getData().get("jsonData");
                    Gson gson = new Gson();
                    DetailedGuildItem detailedGuildItem = gson.fromJson(stringList.get(0), DetailedGuildItem.class);
                    List<DetailedChannelItem> channels = gson.fromJson(stringList.get(1), new TypeToken<List<DetailedChannelItem>>() {
                    }.getType());
                    List<DetailedMemberItem> members = gson.fromJson(stringList.get(2), new TypeToken<List<DetailedMemberItem>>() {
                    }.getType());

                    AllDataModel allDataModel = AllDataModel.createInstance(detailedGuildItem, channels, members);

                    if (data.get("settings") != null) {
                        String json = gson.toJson(data.get("settings"));
                        SettingsModel settingsModel = gson.fromJson(json, SettingsModel.class);
                        allDataModel.setSettings(settingsModel);
                    }

                    mutableAllDataModel.setValue(allDataModel);
                    mutableDetailedGuildItemModel.postValue(detailedGuildItem);

                    //testing
                    //mutableDetailedGuildItemModel.setValue(DetailedGuildItem.createError());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse<Map<String, Object>>> call, Throwable t) {
                mutableDetailedGuildItemModel.setValue(DetailedGuildItem.createError());
            }
        });
    }

    public void saveSettings(SettingsModel settingsModel) {
        //dummy data
        Call<DefaultResponse<String>> call = myApiService.saveSettings(settingsModel);
        call.enqueue(new Callback<DefaultResponse<String>>() {
            @Override
            public void onResponse(Call<DefaultResponse<String>> call, Response<DefaultResponse<String>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Success");
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse<String>> call, Throwable t) {

            }
        });
        updateSettings(settingsModel);
    }

    public LiveData<DetailedGuildItem> getDetailedGuildModel() {
        return mutableDetailedGuildItemModel;
    }

    public LiveData<UserModel> getUserModel() {
        return mutableUserModel;
    }

    public LiveData<List<UserGuildModelItem>> getUserGuildModel() {
        return mutableUserGuildModel;
    }

    public LiveData<UserGuildModelItem> getSelectedServer() {
        return selectedServer;
    }

    public void clearAllData() {
        mutableUserModel.setValue(null);
        mutableUserGuildModel.setValue(null);
        mutableDetailedGuildItemModel.setValue(null);
    }

    public void setSelectedServer(UserGuildModelItem userGuildModelItem) {
        selectedServer.setValue(userGuildModelItem);
    }

    public LiveData<AllDataModel> getAllDataModel() {
        return mutableAllDataModel;
    }

    public void updateSettings(SettingsModel settings) {
        AllDataModel ADL = mutableAllDataModel.getValue();
        ADL.setSettings(settings);
        mutableAllDataModel.setValue(ADL);
    }
}
