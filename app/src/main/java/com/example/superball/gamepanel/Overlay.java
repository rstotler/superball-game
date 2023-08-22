package com.example.superball.gamepanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.superball.Game;
import com.example.superball.Image;
import com.example.superball.gameobject.Ground;
import com.example.superball.gameobject.Player;

public class Overlay {
    private int speedTimerBarX, speedTimerBarY, speedTimerBarWidth, speedTimerBarHeight, speedTimerBarMargin;
    private int speedArrowX, speedArrowY;
    private int stringGameTimerX, stringGameTimerY;
    private int offscreenWindowX, offscreenWindowY;

    public Overlay() {

        // Speed Level Timer Bar //
        speedTimerBarX = (int) (Game.SCREEN_WIDTH * .025);
        speedTimerBarY = (int) (Game.SCREEN_WIDTH * .025);
        speedTimerBarWidth = Game.SCREEN_WIDTH - (int) (Game.SCREEN_WIDTH * .05);
        speedTimerBarHeight = (int) (Game.SCREEN_WIDTH * .05);
        speedTimerBarMargin = (int) (Game.SCREEN_WIDTH * .005);

        // Speed Level Arrows //
        speedArrowX = (int) (Game.SCREEN_WIDTH * .025);
        speedArrowY = (int) (Game.SCREEN_WIDTH * .09);

        // Game Timer //
        stringGameTimerX = Game.SCREEN_WIDTH - (int) (Game.SCREEN_WIDTH * .295);
        stringGameTimerY = (int) (Game.SCREEN_WIDTH * .14);

        // Offscreen Window //
        offscreenWindowX = (int) (Game.SCREEN_WIDTH * .025);
        offscreenWindowY = (int) (Game.SCREEN_WIDTH * .175);
    }

    public void draw(Canvas canvas, Game game) {

        // Speed Level Timer Bar //
        double currentSpeedTimerPercentage = 1.0;
        if(game.getGround().getScrollSpeedLevel() > 0 && game.getGround().SPEED_TIMER_SECONDS * 60.0 > 0)
            currentSpeedTimerPercentage = game.getGround().getScrollSpeedTimer() / (game.getGround().SPEED_TIMER_SECONDS * 60.0);

        int currentSpeedTimerBarWidth = (int) ((speedTimerBarWidth - (speedTimerBarMargin * 4)) * currentSpeedTimerPercentage);
        canvas.drawRect(speedTimerBarX, speedTimerBarY, speedTimerBarX + speedTimerBarWidth, speedTimerBarY + speedTimerBarHeight, Game.paintDarkGrey);
        canvas.drawRect(speedTimerBarX + speedTimerBarMargin, speedTimerBarY + speedTimerBarMargin, speedTimerBarX + speedTimerBarWidth - speedTimerBarMargin, speedTimerBarY + speedTimerBarHeight - speedTimerBarMargin, Game.paintLightGrey);
        if(game.getGround().getScrollSpeedLevel() > 0)
            canvas.drawRect(speedTimerBarX + (speedTimerBarMargin * 2), speedTimerBarY + (speedTimerBarMargin * 2), speedTimerBarX + (speedTimerBarMargin * 2) + currentSpeedTimerBarWidth, speedTimerBarY + speedTimerBarHeight - (speedTimerBarMargin * 2), Game.paintDarkGrey);

        // Speed Level Arrows //
        int speedArrowXMod = 0;
        for(int i = 0; i < 5; i++) {
            Bitmap arrowImage = Image.speedLevelArrowInactive;
            if(i < game.getGround().getScrollSpeedLevel())
                arrowImage = Image.speedLevelArrowActive;

            canvas.drawBitmap(arrowImage, speedArrowX + speedArrowXMod, speedArrowY, null);
            speedArrowXMod += Image.speedLevelArrowInactive.getWidth() * .75;
        }

        // Game Timer //
        String stringGameTimer = getTimerString(game.getGameSeconds());
        canvas.drawText(stringGameTimer, stringGameTimerX, stringGameTimerY, Game.textPaintWhite73);

        // Offscreen Window //
        if(game.getPlayer().getY() < 0) {
            canvas.drawBitmap(Image.offscreenWindow, offscreenWindowX, offscreenWindowY, null);
            if(game.getPlayer().getImgBall() != null)
                canvas.drawBitmap(game.getPlayer().getImgBall(), offscreenWindowX + (Image.offscreenWindow.getWidth() / 2) - (game.getPlayer().getImgBall().getWidth() / 2), (int) (offscreenWindowY + (Image.offscreenWindow.getHeight() / 1.8) - (game.getPlayer().getImgBall().getHeight() / 2)), null);
            else
                canvas.drawCircle(offscreenWindowX + (Image.offscreenWindow.getWidth() / 2), (int) (offscreenWindowY + (Image.offscreenWindow.getHeight() / 1.8)), game.getPlayer().getRadius(), Game.paintDarkRed);

            String offscreenHeightString = String.format("%,d", (int) (((Game.SCREEN_HEIGHT / 2) + Math.abs(game.getPlayer().getY())) / 73.0)) + " Feet";
            canvas.drawText(offscreenHeightString, offscreenWindowX, offscreenWindowY + Image.offscreenWindow.getHeight() + (int) (Game.SCREEN_HEIGHT * .02), Game.textPaintWhite36);
        }
    }

    public String getTimerString(int totalSeconds) {
        int minutes = totalSeconds / 3600;
        int seconds = (totalSeconds / 60) % 60;
        int milliseconds = totalSeconds % 60;

        String stringMinutes = String.valueOf(minutes);
        if(minutes < 10)
            stringMinutes = "0" + stringMinutes;
        String stringSeconds = String.valueOf(seconds);
        if(seconds < 10)
            stringSeconds = "0" + stringSeconds;
        String stringMilliseconds = String.valueOf(milliseconds);
        if(milliseconds < 10)
            stringMilliseconds = "0" + stringMilliseconds;

        String gameTimerString = stringMinutes + ":" + stringSeconds + ":" + stringMilliseconds;
        return gameTimerString;
    }
}
