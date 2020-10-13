package com.vnat.materialdesign.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vnat.materialdesign.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @BindView(R.id.txtSlogan)
    TextView txtSlogan;

    private static final int TIME_SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        funAnim();
        funIntent();
    }

    private void funIntent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(imgLogo, "imgLogo");
                pairs[1] = new Pair<View, String>(txtSlogan, "txtSlogan");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pairs);

                startActivity(intent, options.toBundle());
                finish();
            }
        }, TIME_SPLASH);
    }

    private void funAnim() {
        Animation animTop = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        Animation animBottom = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);

        imgLogo.setAnimation(animTop);
        txtSlogan.setAnimation(animBottom);
    }
}