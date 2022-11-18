package com.example.untitleddiscordbot.remote;

import com.example.untitleddiscordbot.Models.DefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyApiService {

    @POST("getGuildsByIds")
    Call<DefaultResponse<List<String>>> getGuildsByIds(@Body List<String> ids);
}
