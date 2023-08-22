package com.example.superball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Vector;

public class Image {
    public static Bitmap mainMenuBackground, mainMenuBallBackground;
    public static Bitmap menuIconActive, menuIconInactive;

    public static Bitmap background, speedLevelArrowInactive, speedLevelArrowActive, offscreenWindow;
    public static Bitmap platformDefaultTopLeft, platformDefaultTopRight, platformDefaultBottomLeft, platformDefaultBottomRight, platformDefaultLeft, platformDefaultTop, platformDefaultRight, platformDefaultBottom;
    public static Vector<Bitmap> ballTrail;

    public Image(Context context) {

        // Main Menu Elements //
        mainMenuBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_background);
        mainMenuBackground = Bitmap.createScaledBitmap(mainMenuBackground, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, true);

        mainMenuBallBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.current_ball_background);
        int imgBallWidth = (int) (Game.SCREEN_WIDTH * .225);
        double imageRatio = (imgBallWidth + 0.0) / mainMenuBallBackground.getWidth();
        int imgBallHeight = (int) (mainMenuBallBackground.getHeight() * imageRatio);
        mainMenuBallBackground = Bitmap.createScaledBitmap(mainMenuBallBackground, imgBallWidth, imgBallHeight, true);

        int imgIconSize = (int) (Game.SCREEN_WIDTH * .025);
        menuIconActive = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_icon_active);
        menuIconActive = Bitmap.createScaledBitmap(menuIconActive, imgIconSize, imgIconSize, true);
        menuIconInactive = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_icon_inactive);
        menuIconInactive = Bitmap.createScaledBitmap(menuIconInactive, imgIconSize, imgIconSize, true);

        // Game Elements //
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_01);

        speedLevelArrowInactive = BitmapFactory.decodeResource(context.getResources(), R.drawable.speed_level_arrow_inactive);
        speedLevelArrowActive = BitmapFactory.decodeResource(context.getResources(), R.drawable.speed_level_arrow_active);
        offscreenWindow = BitmapFactory.decodeResource(context.getResources(), R.drawable.offscreen_window);

        platformDefaultTop = BitmapFactory.decodeResource(context.getResources(), R.drawable.platform_default_top);
        platformDefaultTopLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.platform_default_top_left);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        platformDefaultTopRight = Bitmap.createBitmap(platformDefaultTopLeft, 0, 0, platformDefaultTopLeft.getWidth(), platformDefaultTopLeft.getHeight(), matrix, true);
        matrix.postRotate(90);
        platformDefaultBottomRight = Bitmap.createBitmap(platformDefaultTopLeft, 0, 0, platformDefaultTopLeft.getWidth(), platformDefaultTopLeft.getHeight(), matrix, true);
        matrix.postRotate(90);
        platformDefaultBottomLeft = Bitmap.createBitmap(platformDefaultTopLeft, 0, 0, platformDefaultTopLeft.getWidth(), platformDefaultTopLeft.getHeight(), matrix, true);
    }

    public static int getBallResource(int targetBallNum) {
        if(targetBallNum == 0)
            return R.drawable.superball;
        else if(targetBallNum == 1)
            return R.drawable.tennis_ball;
        else if(targetBallNum == 2)
            return  R.drawable.basketball;
        else if(targetBallNum == 3)
            return  R.drawable.bowling_ball;
        else if(targetBallNum == 4)
            return R.drawable.beach_ball;
        else
            return -1;
    }

    public static int getItemResource(int targetItemNum) {
        if(targetItemNum == 1)
            return R.drawable.item_01;
        else if(targetItemNum == 2)
            return R.drawable.item_02;
        else if(targetItemNum == 3)
            return R.drawable.item_03;
        else if(targetItemNum == 4)
            return R.drawable.item_04;
        else if(targetItemNum == 5)
            return R.drawable.item_05;
        else if(targetItemNum == 6)
            return R.drawable.item_06;
        else if(targetItemNum == 7)
            return R.drawable.item_07;
        else
            return -1;
    }
}
