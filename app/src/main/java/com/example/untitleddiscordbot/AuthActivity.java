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

import com.example.untitleddiscordbot.Utils.AuthUtil;
import com.example.untitleddiscordbot.databinding.CustomCarouselLayoutBinding;
import com.google.android.material.button.MaterialButton;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ResponseTypeValues;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;



public class AuthActivity extends AppCompatActivity {

    private MaterialButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auth);


        ImageCarousel imageCarousel = findViewById(R.id.carousel);
        loginButton = findViewById(R.id.login_button);
        imageCarousel.registerLifecycle(getLifecycle());

        List<CarouselItem> dataList = new ArrayList<>();
        dataList.add(new CarouselItem(
                R.drawable.pawel
        ));
        dataList.add(new CarouselItem(
                R.drawable.pawel
        ));
        dataList.add(new CarouselItem(
                R.drawable.pawel
        ));
        dataList.add(new CarouselItem(
                R.drawable.pawel
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
                AuthorizationService authService = new AuthorizationService(AuthActivity.this);

                //Create pending intent with additional flag
                PendingIntent pendingIntentComplete = PendingIntent.getActivity(
                        AuthActivity.this,
                        0,
                        new Intent(AuthActivity.this, MainActivity.class),
                        0
                );

                authService.performAuthorizationRequest(
                        AuthUtil.getAuthRequest(),
                        pendingIntentComplete
                );

            }
        });

    }
}