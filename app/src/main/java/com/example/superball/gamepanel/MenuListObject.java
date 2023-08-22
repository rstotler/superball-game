package com.example.superball.gamepanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;

import com.example.superball.Game;
import com.example.superball.gameobject.Player;

public class MenuListObject {
    private String listId = "";
    private String label = "";
    private int cost = 0;
    private boolean locked = true;
    private boolean isActive = false;
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    private Bitmap imgIcon, imgIconIsActive;

    public MenuListObject(String listId, String label, String longestLabel, int x, int y) {
        this.listId = listId;
        this.label = label;
        this.x = x - MainMenu.BUFFER_SIZE;
        this.y = y - MainMenu.BUFFER_SIZE;

        // Get Menu List Object Width & Height //
        Rect bounds = new Rect();
        MainMenu.textPaintLight.getTextBounds(longestLabel, 0, longestLabel.length(), bounds);
        width = bounds.width() + (MainMenu.BUFFER_SIZE * 2);
        height = bounds.height() + (MainMenu.BUFFER_SIZE * 2);
    }

    public void draw(Canvas canvas, TextPaint textPaint) {
        if(isActive)
            canvas.drawRect(x, y, x + width, y + height, Game.paintGrey);
        else
            canvas.drawRect(x, y, x + width, y + height, Game.paintDarkGrey);

        int labelXOffset = 0;
        if(imgIcon != null) {
            if(imgIconIsActive != null && isActive) {
                canvas.drawBitmap(imgIconIsActive, x + MainMenu.BUFFER_SIZE, y + (height / 2) - (imgIconIsActive.getHeight() / 2), null);
                labelXOffset = imgIconIsActive.getWidth() + MainMenu.BUFFER_SIZE;
            }
            else{
                canvas.drawBitmap(imgIcon, x + MainMenu.BUFFER_SIZE, y + (height / 2) - (imgIcon.getHeight() / 2), null);
                labelXOffset = imgIcon.getWidth() + MainMenu.BUFFER_SIZE;
            }
        }

        canvas.drawText(getLabel(), x + MainMenu.BUFFER_SIZE + labelXOffset, y + MainMenu.TEXT_HEIGHT + MainMenu.BUFFER_SIZE, textPaint);
    }

    // Getters & Setters //
    public void setLocked(boolean locked) { this.locked = locked; }
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public void switchIsActive() { isActive = !isActive; }
    public String getListId() { return listId; }
    public String getLabel() {
        if(locked)
            return "?????";
        return label;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void addImage(Bitmap image, Bitmap imageIsActive) {
        this.imgIcon = image;
        if(imageIsActive != null)
            this.imgIconIsActive = imageIsActive;
        width += image.getWidth() + MainMenu.BUFFER_SIZE;
    }
}
