package com.example.explore;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SpashActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameTv;
    long animTime = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spash);

        imageView = findViewById(R.id.splash_logo);
        nameTv = findViewById(R.id.splash_name);

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "y", 200f);
        ObjectAnimator animatorName = ObjectAnimator.ofFloat(nameTv, "x", 200f);
        animatorY.setDuration(animTime);
        animatorName.setDuration(animTime);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY, animatorName);
        animatorSet.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}