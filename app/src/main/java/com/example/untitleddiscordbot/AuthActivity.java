package com.example.untitleddiscordbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.untitleddiscordbot.databinding.CustomCarouselLayoutBinding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auth);


        ImageCarousel imageCarousel = findViewById(R.id.carousel);
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
    }
}