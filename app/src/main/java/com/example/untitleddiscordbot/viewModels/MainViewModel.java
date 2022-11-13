package com.example.untitleddiscordbot.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.untitleddiscordbot.Models.RepoDataModel;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModel;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.repositories.MainRepository;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    private final LiveData<RepoDataModel> repoDataModel;
    private final LiveData<UserModel> userModel;
    private final LiveData<UserGuildModel> userGuildModel;

    public MainViewModel(MainRepository mainRepository){
        this.mainRepository = mainRepository;
        repoDataModel = mainRepository.getRepoDataModel();
        userModel = mainRepository.getUserModel();
        userGuildModel = mainRepository.getUserGuildModel();
    }



    public LiveData<UserGuildModel> getUserGuildModel(){
        return userGuildModel;
    }
    public LiveData<UserModel> getUserModel(){
        return userModel;
    }

    public LiveData<UserModel> fetchUserModel(String auth){
        return mainRepository.fetchUserModel(auth);
    }

    public LiveData<UserGuildModel> fetchUserGuilds(String auth){
        return mainRepository.fetchUserGuilds(auth);
    }

}
