package com.barrel.barreldodger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by conno_000 on 4/22/2017.
 */

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;

    // When user tapping screen, boost diddy kong
    private boolean boosting;

    // Gravitational constant for the player
    private final int GRAVITY = -10;

    // Controlling Y coordinate so that ship won't go outside the screen
    private int bottomY;
    private int topY;

    // Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public Player(Context context) {
        x = 75;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.donkey);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);

        // Not boosting at first
        boosting = false;
    }

    Player(Context context, int screenX, int screenY) {
        x = 75;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.donkey);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);

        bottomY = screenY - bitmap.getHeight();
        topY = 0;

        // Not boosting at first
        boosting = false;
    }

    void startBoosting() {
        boosting = true;
    }
    void stopBoosting() {
        boosting = false;
    }

    void update() {
        if (boosting) {
            // Increase upward speed
            speed += 4;
        } else {
            // Decrease upward speed
            speed -= 5;
        }
        // Keep speed under MAX_SPEED, over MIN_SPEED
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        // Adjust position, within bounds
        y -= speed + GRAVITY;
        if (y < topY) {
            y = topY;
        }
        if (y > bottomY) {
            y = bottomY;
        }
    }

    Bitmap getBitmap() {
        return bitmap;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getSpeed() {
        return speed;
    }
}