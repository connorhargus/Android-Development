package com.barrel.barreldodger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by conno_000 on 4/22/2017.
 */

public class Barrel {

    private Bitmap bitmap;

    // Coordinates of barrel
    private int x;
    private int y;

    // Speed of barrel
    private int speed = 1;

    // Coordinates of the sides of the screen
    private int leftX;
    private int rightX;
    private int bottomY;
    private int topY;


    Barrel(Context context, int screenX, int screenY) {

        // Gets barrel image from resources
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.barrel);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);

        leftX = 0;
        rightX = screenX;

        bottomY = 0;
        topY = screenY;

        // Gives the barrel a random speed and y coordinate
        Random generator = new Random();
        speed = 15;
        x = screenX;
        y = generator.nextInt(topY - bitmap.getHeight());

    }

    // Update the barrel's location according to barrel and player speeds
    void update(int playerSpeed) {

        // Decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;

        // If the enemy reaches the left edge, move back to the right
        if (x < leftX - bitmap.getWidth()) {
            Random generator = new Random();
//            speed += 1;
            x = rightX;

            y = generator.nextInt(topY - bitmap.getHeight());
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

    public int getSpeed() {
        return speed;
    }

}