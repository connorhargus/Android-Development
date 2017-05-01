package com.barrel.barreldodger;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class PlayActivity extends AppCompatActivity {

    private GameView gameView;
//    MediaPlayer mp_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // For this activity, GameView provides the view
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);

        // create main menu music
//        mp_background = MediaPlayer.create(this, R.raw.main);

        // start background music
//        mp_background.start();
//        mp_background.setLooping(true);
    }

    // Pause game
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // Resume activity and gameView
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();

//        if (MainActivity.musicOn) {
//            mp_background.start();
//        } else {
//            mp_background.pause();
//        }
    }
}