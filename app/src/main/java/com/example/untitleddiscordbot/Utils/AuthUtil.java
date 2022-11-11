package com.example.untitleddiscordbot.Utils;

import android.net.Uri;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

public class AuthUtil {
    private static AuthorizationServiceConfiguration serviceConfig;
    private static AuthorizationRequest authRequest;
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
        return new AuthState(getServiceConfig());
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



}
