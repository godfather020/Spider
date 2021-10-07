package com.example.spider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spider.ui.activity.Otp_Activity;
import com.example.spider.utils.AppSharedPref;

public class Splash_Activity  extends AppCompatActivity {

    Animation rotate_zoom;
    ImageView imgLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        imgLogo=findViewById(R.id.splashLogo);
        rotate_zoom= AnimationUtils.loadAnimation(this,R.anim.zoom_in_fade_in_anim);


        imgLogo.setAnimation(rotate_zoom);
        new Thread(new Runnable() {
            @Override
            public void run() {



            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (new AppSharedPref(getApplicationContext()).getBoolean("login")) {
                        Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Splash_Activity.this, Otp_Activity.class);
                        startActivity(intent);
                        finish();
                    }

            }
        },2000);
    }
}
