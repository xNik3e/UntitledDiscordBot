package com.example.untitleddiscordbot.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.airbnb.lottie.animation.content.Content;
import com.example.untitleddiscordbot.MainActivity;
import com.example.untitleddiscordbot.Models.AuthResponseModel;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthUtil {
    private static AuthorizationServiceConfiguration serviceConfig;
    private static AuthorizationRequest authRequest;
    private static AuthState authState;
    private static final String CLIENT_ID = "689502885159108692";
    private static final String REDIRECT_URI = "com.example.untitleddiscordbot.comm://callback";
    private static final String SCOPE = "guilds guilds.join guilds.members.read identify messages.read";

    public static AuthorizationServiceConfiguration getServiceConfig() {
        if(serviceConfig == null){
            serviceConfig = new AuthorizationServiceConfiguration(
                    Uri.parse("https://discord.com/oauth2/authorize"),
                    Uri.parse("https://discord.com/api/oauth2/token")
            );
        }
        return serviceConfig;
    }

    public static AuthState getAuthState(){
        if(authState == null){
            authState = new AuthState(getServiceConfig());
        }
        return authState;
    }


    public static AuthorizationRequest getAuthRequest(){
        if(authRequest == null){
            AuthorizationRequest.Builder authRequestBuilder =
                    new AuthorizationRequest.Builder(
                            AuthUtil.getServiceConfig(),
                            CLIENT_ID,
                            ResponseTypeValues.CODE,
                            Uri.parse(REDIRECT_URI)
                    );
            authRequest = authRequestBuilder
                    .setScope(SCOPE)
                    .build();
        }
        return authRequest;
    }

    public static void setAuthResponse(AuthorizationResponse res, AuthorizationException ex){
        AuthState authState = getAuthState();
        authState.update(res, ex);
    }

    public static void setAuthResponse(TokenResponse res, AuthorizationException ex){
        AuthState authState = getAuthState();
        authState.update(res, ex);
    }

    public static AuthResponseModel createAuthResponseModel(TokenResponse response){
        AuthResponseModel model = new AuthResponseModel();
        JSONObject json = response.jsonSerialize();
        try{
            model.setAccessToken(json.getString("access_token"));
            model.setRefreshToken(json.getString("refresh_token"));
            model.setExpiresAt(json.getLong("expires_at"));
            model.setScope(json.getString("scope"));
            model.setTokenType(json.getString("token_type"));
        }catch(JSONException e){
            e.printStackTrace();
        }
        return model;
    }

    public static AuthResponseModel createAuthResponseModel(){
        AuthResponseModel model = new AuthResponseModel();
        try{
            JSONObject json = authState.jsonSerialize().getJSONObject("mLastTokenResponse");
            model.setAccessToken(json.getString("access_token"));
            model.setRefreshToken(json.getString("refresh_token"));
            model.setExpiresAt(json.getLong("expires_at"));
            model.setScope(json.getString("scope"));
            model.setTokenType(json.getString("token_type"));
        }catch(Exception e){
            e.printStackTrace();
            model = null;
        }
        return model;
    }

    public static AuthState readAuthState(@NonNull Context context) {
        SharedPreferences authPrefs = context.getSharedPreferences("auth", MODE_PRIVATE);
        String stateJson = authPrefs.getString("stateJson", null);
        if (stateJson != null) {
            try{
                authState = AuthState.jsonDeserialize(stateJson);
                return authState;
            }catch(JSONException e){
                return null;
            }
        } else {
            return null;
        }
    }

    public static void writeAuthState(@NonNull Context context) {
        SharedPreferences authPrefs = context.getSharedPreferences("auth", MODE_PRIVATE);
        authPrefs.edit()
                .putString("stateJson", authState.jsonSerializeString())
                .apply();
    }


    public static void clearAuthState(Context context) {
        SharedPreferences authPrefs = context.getSharedPreferences("auth", MODE_PRIVATE);
        authPrefs.edit()
                .remove("stateJson")
                .apply();
        authState = null;
    }
}
