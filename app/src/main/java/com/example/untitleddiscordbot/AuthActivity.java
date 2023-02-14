package com.example.untitleddiscordbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.untitleddiscordbot.Models.AuthResponseModel;
import com.example.untitleddiscordbot.Utils.AuthUtil;
import com.example.untitleddiscordbot.databinding.CustomCarouselLayoutBinding;
import com.google.android.material.button.MaterialButton;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuthActivity extends AppCompatActivity {

    private MaterialButton loginButton;
    private AuthorizationService authorizationService;
    private static final int RC_AUTH = 3861;
    private String code;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auth);


        ImageCarousel imageCarousel = findViewById(R.id.carousel);
        loginButton = findViewById(R.id.login_button);
        imageCarousel.registerLifecycle(getLifecycle());



        List<CarouselItem> dataList = new ArrayList<>();
        dataList.add(new CarouselItem(
                null,
                R.drawable.prompt_1,
                "Feature 1 detailed description",
                Map.of("feature_name", "Feature 1")
        ));
        dataList.add(new CarouselItem(
                null,
                R.drawable.prompt_2,
                "Feature 2 detailed description",
                Map.of("feature_name", "Feature 2")
        ));
        dataList.add(new CarouselItem(
                null,
                R.drawable.prompt_3,
                "Feature 3 detailed description",
                Map.of("feature_name", "Feature 3")
        ));
        dataList.add(new CarouselItem(
                null,
                R.drawable.prompt_4,
                "Feature 4 detailed description",
                Map.of("feature_name", "Feature 4")
        ));

        imageCarousel.setData(dataList);

        imageCarousel.setCarouselListener(new CarouselListener() {
            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return CustomCarouselLayoutBinding.inflate(layoutInflater, viewGroup, false);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
                CustomCarouselLayoutBinding binding = (CustomCarouselLayoutBinding) viewBinding;
                Glide.with(binding.getRoot()).load(carouselItem.getImageDrawable()).into(binding.imageView);
                binding.featureName.setText(carouselItem.getHeaders().get("feature_name"));
                binding.featureDescription.setText(carouselItem.getCaption());
            }

            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {

            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {

            }
        });

        imageCarousel.start();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorizationService = new AuthorizationService(AuthActivity.this);
                Intent authIntent = authorizationService.getAuthorizationRequestIntent(AuthUtil.getAuthRequest());
                startActivityForResult(authIntent, RC_AUTH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_AUTH){
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);
            if(resp != null){
                AuthUtil.setAuthResponse(resp, ex);

                JSONObject json = resp.jsonSerialize();
                authorizationService.performTokenRequest(
                        resp.createTokenExchangeRequest(),
                        new AuthorizationService.TokenResponseCallback() {
                            @Override
                            public void onTokenRequestCompleted(@Nullable TokenResponse response, @Nullable AuthorizationException ex) {
                                AuthUtil.setAuthResponse(response, ex);
                                AuthUtil.writeAuthState(AuthActivity.this);
                                startActivity(new Intent(AuthActivity.this, LoadingActivity.class));
                                finish();
                            }
                        }
                );
            }else{
                Toast.makeText(this, ex.errorDescription, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}