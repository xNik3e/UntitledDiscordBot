package com.example.untitleddiscordbot.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.remote.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static MainRepository instance;
    private final ApiService apiService;
    private final MutableLiveData<UserModel> mutableUserModel;
    private final MutableLiveData<List<UserGuildModelItem>> mutableUserGuildModel;


    public static MainRepository getInstance(ApiService apiService){
        if(instance == null){
            instance = new MainRepository(apiService);
        }
        return instance;
    }

    public MainRepository(ApiService apiService) {
        this.apiService = apiService;
        mutableUserModel = new MutableLiveData<>(null);
        mutableUserGuildModel = new MutableLiveData<>(null);
    }



    public LiveData<UserModel> fetchUserModel(String auth) {
        String header = "Bearer " + auth;
        Call<UserModel> call = apiService.getUser(header);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    mutableUserModel.setValue(userModel);
                }else{
                    mutableUserModel.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        return mutableUserModel;
    }

    public LiveData<List<UserGuildModelItem>> fetchUserGuilds(String auth){
        String header = "Bearer " + auth;
        Call<List<UserGuildModelItem>> call = apiService.getGuilds(header);
        call.enqueue(new Callback<List<UserGuildModelItem>>() {
            @Override
            public void onResponse(Call<List<UserGuildModelItem>> call, Response<List<UserGuildModelItem>> response) {
                if(response.isSuccessful()){
                    List<UserGuildModelItem> userGuildModelItems = response.body();
                    mutableUserGuildModel.setValue(userGuildModelItems);
                }else{
                    mutableUserGuildModel.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<UserGuildModelItem>> call, Throwable t) {

            }
        });
        return mutableUserGuildModel;
    }


    public LiveData<UserModel> getUserModel() {
        return mutableUserModel;
    }

    public LiveData<List<UserGuildModelItem>> getUserGuildModel() {
        return mutableUserGuildModel;
    }
}
