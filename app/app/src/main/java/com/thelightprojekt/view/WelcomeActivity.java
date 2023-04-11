package com.thelightprojekt.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.thelightprojekt.R;

public class WelcomeActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 1500; // After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This method is used so that your splash activity can cover the entire screen.
        setContentView(R.layout.activity_welcome); // this will bind your MainActivity.class file with activity_main.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent is used to switch from one activity to another.
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i); // invoke the SecondActivity.
                finish(); // the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}