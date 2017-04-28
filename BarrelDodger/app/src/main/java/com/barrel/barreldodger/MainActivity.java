package com.barrel.barreldodger;

import android.os.Bundle;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Play button
    private ImageButton buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Play button
        buttonPlay = (ImageButton) findViewById(R.id.playButton);
        buttonPlay.setOnClickListener(this);

        // Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onClick(View v) {
        // Play button starts PlayActivity
        startActivity(new Intent(this, PlayActivity.class));
    }
}
