package com.example.untitleddiscordbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.untitleddiscordbot.databinding.ItemCustomFixedSizeLayout4Binding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class AuthActivity extends AppCompatActivity {

    private ImageCarousel imageCarousel;
    private CircleIndicator2 indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        imageCarousel = findViewById(R.id.carousel);
        imageCarousel.registerLifecycle(getLifecycle());

        indicator = findViewById(R.id.indicator);
        imageCarousel.setIndicator(indicator);

        imageCarousel.setCarouselListener(new CarouselListener() {
            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return ItemCustomFixedSizeLayout4Binding.inflate(layoutInflater, viewGroup, false);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
                ItemCustomFixedSizeLayout4Binding binding = (ItemCustomFixedSizeLayout4Binding) viewBinding;

            }

            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {

            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {

            }
        });
    }
}