package com.peacecorps.pcsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

/**
 * This class handles showing splash screen for 2500 ms when the application is launched
 * @author rohan
 * @since 2016-06-10.
 */
public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 2500;
    private ProgressBar progressBar;

    /**
     * Instance of Handler class is used to load the main screen after 2500 ms
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        progressBar = (ProgressBar)findViewById(R.id.splash_screen_progress);

        final int backgroundTextViewColor = ContextCompat.getColor(this, R.color.background_textview);
        progressBar.getIndeterminateDrawable().setColorFilter(backgroundTextViewColor, android.graphics.PorterDuff.Mode.MULTIPLY);

        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Intent i = new Intent(SplashScreenActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
