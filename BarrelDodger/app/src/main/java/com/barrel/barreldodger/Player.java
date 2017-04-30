package com.barrel.barreldodger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;

// Created by conno_000 on 4/22/2017.

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;

    private Rect collisionrect;

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

    // boosting music
    MediaPlayer mp_touch;

    Player(Context context, int screenX, int screenY) {
        x = 75;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.donkey);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);

        bottomY = 0;
        topY = screenY - bitmap.getHeight();

        // Not boosting at first
        boosting = false;

        collisionrect = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        mp_touch = MediaPlayer.create(context, R.raw.touch); // initialize boost music
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
            mp_touch.start();
            mp_touch.setLooping(true);
        } else {
            // Decrease upward speed
            speed -= 5;
            if(mp_touch.isPlaying()) {
                mp_touch.pause();
            }
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
        if (y < bottomY) {
            y = bottomY;
        }
        if (y > topY) {
            y = topY;
        }
        collisionrect.left=x;
        collisionrect.top=y;
        collisionrect.right=x + bitmap.getWidth();
        collisionrect.bottom=y + bitmap.getHeight();
    }

    Rect getCollisionrect(){
        return collisionrect;
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