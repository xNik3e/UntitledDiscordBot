package com.example.untitleddiscordbot.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.untitleddiscordbot.Models.DetailedGuild.DetailedGuildItem;
import com.example.untitleddiscordbot.Models.UserGuildsModel.UserGuildModelItem;
import com.example.untitleddiscordbot.Models.UserModel.UserModel;
import com.example.untitleddiscordbot.repositories.MainRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    private final LiveData<UserModel> userModel;
    private final LiveData<List<UserGuildModelItem>> userGuildModel;
    private MutableLiveData<Boolean> isUserGuildsUpdated = new MutableLiveData<>(false);

    private LiveData<UserGuildModelItem> selectedServer;
    private final LiveData<DetailedGuildItem> detailedGuildItemModel;

    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;

        userModel = mainRepository.getUserModel();
        userGuildModel =  mainRepository.getUserGuildModel();
        selectedServer = mainRepository.getSelectedServer();
        detailedGuildItemModel = mainRepository.getDetailedGuildModel();
    }


    public LiveData<List<UserGuildModelItem>> getUserGuildModel() {
        return userGuildModel;
    }

    public LiveData<UserModel> getUserModel() {
        return userModel;
    }

    public UserModel getStoredUserModel(){
        return userModel.getValue();
    }

    public List<UserGuildModelItem> getStoredUserGuildModel(){
        return userGuildModel.getValue();
    }

    public LiveData<Boolean> isUserGuildsUpdated(){
        return isUserGuildsUpdated;
    }

    public void fetchUserModel(String auth) {
        mainRepository.fetchUserModel(auth);
    }

    public void fetchUserGuilds(String auth) {
        mainRepository.fetchUserGuilds(auth);
    }

    public void updateUserGuilds(){
        isUserGuildsUpdated.setValue(true);
        List<String> ids = userGuildModel.getValue().stream().map(UserGuildModelItem::getId).collect(Collectors.toList());
        mainRepository.getUpdatedGuilds(ids);
    }

    public void clearAllData() {
        mainRepository.clearAllData();
        isUserGuildsUpdated.setValue(false);
    }

    public void setSelectedServer(UserGuildModelItem userGuildModelItem){
        mainRepository.setSelectedServer(userGuildModelItem);
    }

    public LiveData<UserGuildModelItem> getSelectedServerLiveData(){
        return selectedServer;
    }

    public UserGuildModelItem getSelectedServer(){
        return selectedServer.getValue();
    }

    public LiveData<DetailedGuildItem> getDetailedGuild(){
        return detailedGuildItemModel;
    }

    public void fetchDetailedGuild(String guildId){
        mainRepository.getDetailedGuild(guildId);
    }

}
