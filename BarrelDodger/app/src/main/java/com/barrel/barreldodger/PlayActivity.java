package com.barrel.barreldodger;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class PlayActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // For this activity, GameView provides the view
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);
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
    }
}