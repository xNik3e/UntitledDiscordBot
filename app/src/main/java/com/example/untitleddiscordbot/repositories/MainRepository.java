package com.example.untitleddiscordbot.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.untitleddiscordbot.Models.RepoDataModel;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModel;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository instance;
    private final ApiService apiService;
    private final MutableLiveData<RepoDataModel> mutableRepoDataModel;


    public static MainRepository getInstance(ApiService apiService){
        if(instance == null){
            instance = new MainRepository(apiService);
        }
        return instance;
    }

    public MainRepository(ApiService apiService) {
        this.apiService = apiService;
        mutableRepoDataModel = new MutableLiveData<>(new RepoDataModel());
    }



    public LiveData<UserModel> fetchUserModel(String auth) {
        MutableLiveData<UserModel> user = new MutableLiveData<>(null);
        String header = "Bearer " + auth;
        Call<UserModel> call = apiService.getUser(header);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    user.postValue(userModel);
                    mutableRepoDataModel.getValue().setUser(userModel);
                }else{
                    user.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        return user;
    }

    public LiveData<UserGuildModel> fetchUserGuilds(String auth){
        MutableLiveData<UserGuildModel> userGuilds = new MutableLiveData<>(null);
        String header = "Bearer " + auth;
        Call<UserGuildModel> call = apiService.getGuilds(header);
        call.enqueue(new Callback<UserGuildModel>() {
            @Override
            public void onResponse(Call<UserGuildModel> call, Response<UserGuildModel> response) {
                if(response.isSuccessful()){
                    UserGuildModel userGuildModel = response.body();
                    userGuilds.postValue(userGuildModel);
                    mutableRepoDataModel.getValue().setUserGuilds(userGuildModel);
                }else{
                    userGuilds.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserGuildModel> call, Throwable t) {

            }
        });
        return userGuilds;
    }

    public LiveData<RepoDataModel> getRepoDataModel() {
        return mutableRepoDataModel;
    }


    public MutableLiveData<UserModel> getUserModel() {
        MutableLiveData<UserModel> userModel = new MutableLiveData<>();
        userModel.setValue(mutableRepoDataModel.getValue().getUser());
        return userModel;
    }

    public MutableLiveData<UserGuildModel> getUserGuildModel() {
        MutableLiveData<UserGuildModel> userGuildModel = new MutableLiveData<>();
        userGuildModel.setValue(mutableRepoDataModel.getValue().getUserGuilds());
        return userGuildModel;
    }
}
