package com.example.superball.gamepanel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.R;

public class GameButton {
    private int buttonNum = -1;
    private int x;
    private int y;
    private int width;
    private int height;
    private int buttonRight;
    private int buttonBottom;

    private String label = "";
    private int labelX = 0;
    private int labelY = 0;
    private int textWidth = 0;
    private int textHeight = 0;
    private int outlineSize = 0;

    private Paint paint, paintOutline;
    private TextPaint textPaint;

    private Bitmap buttonImage;
    private int buttonImageX = 0;
    private int buttonImageY = 0;

    private boolean hideBackground = false;

    public GameButton(int x, int y, int width, int height, String label, Paint paint, TextPaint textPaint) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.outlineSize = (int) (width * .1);

        // Center Button If X Or Y == -1 //
        if(x == -1) { this.x = Game.SCREEN_WIDTH - width - ((Game.SCREEN_WIDTH - width) / 2); }
        if(y == -1) { this.y = Game.SCREEN_HEIGHT - height - ((Game.SCREEN_HEIGHT - height) / 2); }

        buttonRight = this.x + width;
        buttonBottom = this.y + height;

        // Button & Text Paint Objects //
        this.paint = paint;
        this.textPaint = textPaint;

        // Get Label Width, Height, & Location //
        if(label != "" && textPaint != null) {
            Rect bounds = new Rect();
            textPaint.getTextBounds(label, 0, label.length(), bounds);
            textWidth = bounds.width();
            textHeight = bounds.height();

            labelX = buttonRight - textWidth - ((width - textWidth) / 2);
            labelY = this.y + textHeight + (height / 2) - (textHeight / 2);
        }
    }

    public void draw(Canvas canvas, boolean selectedCheck) {
        Paint buttonPaint = paint;
        if(selectedCheck) { buttonPaint = Game.paintRed; }

        if(hideBackground == false) {
            if(paintOutline == null)
                canvas.drawRect(x, y, buttonRight, buttonBottom, buttonPaint);
            else {
                canvas.drawRect(x, y, buttonRight, buttonBottom, buttonPaint);
                canvas.drawRect(x + outlineSize, y + outlineSize, buttonRight - outlineSize, buttonBottom - outlineSize, paintOutline);
            }

            if(buttonImage != null)
                canvas.drawBitmap(buttonImage, x + buttonImageX, y + buttonImageY, null);
        }
        if(textPaint != null)
            canvas.drawText(label, labelX, labelY, textPaint);
    }

    // Getters & Setters //
    public int getButtonNum() { return buttonNum; }
    public void setButtonNum(int buttonNum) { this.buttonNum = buttonNum; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setHideBackground(boolean hideBackground) { this.hideBackground = hideBackground; }
    public void setOutline(Paint paintOutline) { this.paintOutline = paintOutline; }

    public void setBold(boolean setBold) {
        if(setBold)
            textPaint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        else
            textPaint.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
    }

    public void setButtonImage(Context context, int buttonImageResource, int buttonImageX, int buttonImageY) {
        buttonImage = BitmapFactory.decodeResource(context.getResources(), buttonImageResource);

        // Get Image Width & Height //
        if(buttonImageX == -1 || buttonImageY == -1) {
            int buttonImageWidth = buttonImage.getWidth();
            int buttonImageHeight = buttonImage.getHeight();

            this.buttonImageX = buttonImageX;
            if(buttonImageX == -1)
                this.buttonImageX = (width / 2) - (buttonImageWidth / 2);
            this.buttonImageY = buttonImageY;
            if(buttonImageY == -1)
                this.buttonImageY = (height / 2) - (buttonImageHeight / 2);
        }
    }
}
