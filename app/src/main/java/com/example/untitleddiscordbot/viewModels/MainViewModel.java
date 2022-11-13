package com.example.untitleddiscordbot.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.repositories.MainRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    private final LiveData<UserModel> userModel;
    private final LiveData<List<UserGuildModelItem>> userGuildModel;

    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;

        userModel = mainRepository.getUserModel();
        userGuildModel = mainRepository.getUserGuildModel();
    }


    public LiveData<List<UserGuildModelItem>> getUserGuildModel() {
        return userGuildModel;
    }

    public LiveData<UserModel> getUserModel() {
        return userModel;
    }


    public void fetchUserModel(String auth) {
        mainRepository.fetchUserModel(auth);
    }

    public void fetchUserGuilds(String auth) {
        mainRepository.fetchUserGuilds(auth);
    }

}