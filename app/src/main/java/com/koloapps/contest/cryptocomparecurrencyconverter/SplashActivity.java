package com.koloapps.contest.cryptocomparecurrencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashActivity extends Activity {
    private Thread splashscreen;
    public Animation fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fade = AnimationUtils.loadAnimation(this, R.anim.magfade);
        findViewById(R.id.appimage).startAnimation(fade);
        splashscreen = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(6000);
                    }
                } catch (Exception e) {
                } finally {
                    Intent startpt = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(startpt);
                    finish();
                }
            }
        };
        splashscreen.start();
    }
}
