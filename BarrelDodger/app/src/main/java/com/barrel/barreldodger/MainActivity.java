package com.barrel.barreldodger;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Play button
    ImageButton buttonPlay;
    Button pauseMusic;
    MediaPlayer mp_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Play button
        buttonPlay = (ImageButton) findViewById(R.id.playButton);
        buttonPlay.setOnClickListener(this);

        // pause music button
        pauseMusic = (Button) findViewById(R.id.pauseMusic);

        // Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // create main menu music
        mp_background = MediaPlayer.create(this, R.raw.main);

        // start background music
        mp_background.start();
        mp_background.setLooping(true);

        pauseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp_background.isPlaying()) {
                    mp_background.pause();
                    pauseMusic.setTextColor(Color.RED);
                    String message = "Music paused!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    mp_background.start();
                    pauseMusic.setTextColor(Color.WHITE);
                    String message = "Music resumed!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Play button starts PlayActivity
        mp_background.stop();
        startActivity(new Intent(this, PlayActivity.class));
    }

    @Override
    protected void onDestroy() {
        mp_background.stop();
        super.onDestroy();
    }
}
