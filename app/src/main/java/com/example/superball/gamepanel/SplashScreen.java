package com.example.superball.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.R;

public class SplashScreen {
    private final int INTRO_OUTRO_TIME = 60;
    private final int PAUSE_WHITE_TIME = 120;
    private final int FADE_TIMER_MAX = 100;

    private int textX = 0;
    private int textY = 0;
    private int textWidth = 0;
    private int textHeight = 0;
    private int subtitleX = 0;
    private int subtitleY = 0;
    private int subtitleWidth = 0;
    private int subtitleHeight = 0;

    private int fadeStep = 0;
    private int fadeTimer = 0;

    private Paint paintAlpha;

    public SplashScreen(Context context) {

        // Initialize Label Width & Height, Set X & Y //
        Rect bounds = new Rect();
        Game.textPaintWhite73Bold.getTextBounds("MISIWI", 0, "MISIWI".length(), bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();
        textX = (Game.SCREEN_WIDTH / 2) - (textWidth / 2);
        textY = (Game.SCREEN_HEIGHT / 2) - (textHeight / 2);

        Game.textPaintWhite36.getTextBounds("(Make It So It's Worth It)", 0, "(Make It So It's Worth It)".length(), bounds);
        subtitleWidth = bounds.width();
        subtitleHeight = bounds.height();
        subtitleX = (Game.SCREEN_WIDTH / 2) - (subtitleWidth / 2);
        subtitleY = Game.SCREEN_HEIGHT - (int) (Game.SCREEN_HEIGHT * .025);

        // Initialize Paint //
        paintAlpha = new Paint();
        paintAlpha.setColor(ContextCompat.getColor(context, R.color.black));
    }

    public String update() {
        String returnString = "";

        if(fadeStep == 0) {
            fadeTimer++;
            if(fadeTimer >= INTRO_OUTRO_TIME) {
                fadeStep = 1;
                fadeTimer = FADE_TIMER_MAX;
            }
        }
        else if(fadeStep == 1) {
            fadeTimer--;
            if(fadeTimer <= 0) {
                fadeStep = 2;
                fadeTimer = PAUSE_WHITE_TIME;
            }
        }
        else if(fadeStep == 2) {
            fadeTimer--;
            if(fadeTimer <= 0)
                fadeStep = 3;
        }
        else if(fadeStep == 3) {
            fadeTimer++;
            if(fadeTimer >= FADE_TIMER_MAX) {
                fadeStep = 4;
            }
        }
        else if(fadeStep == 4) {
            fadeTimer++;
            if(fadeTimer >= FADE_TIMER_MAX + INTRO_OUTRO_TIME) {
                returnString = "Skip Splash Screen";
            }
        }

        if(fadeStep != 0 && fadeStep != 2) {
            double fadePercent = fadeTimer / (FADE_TIMER_MAX + 0.0);
            paintAlpha.setAlpha((int) (fadePercent * 255));
        }

        return returnString;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, Game.paintBlack);
        canvas.drawText("MISIWI", textX, textY, Game.textPaintWhite73Bold);
        canvas.drawRect(textX + 2, textY + 6, textX + textWidth + 2, textY + 12, Game.textPaintWhite73Bold);
        canvas.drawText("Games", textX, textY + 70, Game.textPaintWhite73);
        canvas.drawText("(Make It So It's Worth It)", subtitleX, subtitleY, Game.textPaintWhite36);
        canvas.drawRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, paintAlpha);
    }
}
