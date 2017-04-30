package com.barrel.barreldodger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

// Created by conno_000 on 4/22/2017.

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private Barrel[] barrels;
    private int barrelCount = 3;
    MediaPlayer mp_collision;
    Canvas canvas;
    TextView score;
    int playerScore = 0;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();
        score = (TextView) findViewById(R.id.score);
        barrels = new Barrel[barrelCount];
        for(int i=0; i<barrelCount; i++){
            barrels[i] = new Barrel(context, screenX, screenY);
        }
        // initialize touch and collision music
        mp_collision = MediaPlayer.create(context, R.raw.collision);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    // Updates positions of player and barrels
    private void update() {
        player.update();
        playerScore += 2;
        //score.setText("Score: (" + Integer.toString(playerScore) + ")");
        for(int i = 0; i < barrelCount; i++){
            barrels[i].update(player.getSpeed());
            if(Rect.intersects(player.getCollisionrect(), barrels[i].getCollisionrect())) {
                barrels[i].setX(-200);
                playerScore -= 5;
                //score.setText("Score: (" + Integer.toString(playerScore) + ")");
                mp_collision.start();
            }
        }
    }

    // Draws objects to canvas
    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(getResources().getColor(R.color.game_background));

            paint.setColor(Color.WHITE);

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            for (int i = 0; i < barrelCount; i++) {
                canvas.drawBitmap(
                        barrels[i].getBitmap(),
                        barrels[i].getX(),
                        barrels[i].getY(),
                        paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    // New frame every 17ms
    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Pause game
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
        }
    }

    // Resume game
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Control boosting with touch
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.startBoosting();
                break;
        }
        return true;
    }
}