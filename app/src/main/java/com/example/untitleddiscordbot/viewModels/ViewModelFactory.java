package com.example.untitleddiscordbot.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.untitleddiscordbot.remote.ApiClient;
import com.example.untitleddiscordbot.remote.DiscordApiService;
import com.example.untitleddiscordbot.remote.MyApiService;
import com.example.untitleddiscordbot.repositories.MainRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MainRepository mainRepository;

    public ViewModelFactory(){
        DiscordApiService discordApiService = ApiClient.getRetrofitDiscord().create(DiscordApiService.class);
        MyApiService myApiService = ApiClient.getRetrofitAPI().create(MyApiService.class);
        mainRepository = MainRepository.getInstance(discordApiService, myApiService);
    }

    public <T extends ViewModel> T create(@NonNull Class<T>modelClass){
        if(modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(mainRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
