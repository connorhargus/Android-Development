package com.barrel.barreldodger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private Barrel[] barrels;
    private int barrelCount = 4, totalLives, totalScore;
    MediaPlayer mp_collision;
    Canvas canvas;
    private static boolean gameOver = false;

    private TextView scoreView;
    private TextView livesView;

    private int screenX;
    private int screenY;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        player = new Player(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();
        barrels = new Barrel[barrelCount];
        gameOver = false;
        for(int i=0; i<barrelCount; i++){
            barrels[i] = new Barrel(context, screenX, screenY);
        }
        // initialize touch and collision music
        mp_collision = MediaPlayer.create(context, R.raw.collision);
        totalLives = 10;

        this.screenX = screenX;
        this.screenY = screenY;
    }

    @Override
    public void run(){
        while (playing) {
            update();
            draw();
            control();
        }
    }

    // Updates positions of player and barrels
    private void update(){
        player.update();
        totalScore++; // increase total score
        for(int i = 0; i < barrelCount; i++){
            barrels[i].update(player.getSpeed());
            if(Rect.intersects(player.getCollisionrect(), barrels[i].getCollisionrect())) {
                barrels[i].setX(-200);
                totalLives--; // decreases available lives
                mp_collision.start();
//                totalScore -= 50;
                if (totalScore < 0) { totalScore = 0; };
                if (totalLives <= 0){
                                        gameOver = true; // can go back to main menu

                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        public void run() {

                            player.stopBoosting();
                            player.update();

                            CharSequence options[] = new CharSequence[] {"Play Again?"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("You scored " + totalScore + "!");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int choice) {
                                    totalLives = 10;
                                    totalScore = 0;
                                    if (choice == 0) {
                                        resume();
                                    }
                                }
                            });
                            builder.show();
                        }
                    });
                    draw();
                    pause();
                }
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

            paint.setTextSize(30);
            canvas.drawText("Score: " + totalScore, screenX - 200, screenY - 20, paint);
            canvas.drawText("Lives: " + totalLives, screenX - 350, screenY - 20, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void reset() {
        totalLives = 10;
        totalScore = 0;
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
        player.stopBoosting();
        player.update();
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
        reset();
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