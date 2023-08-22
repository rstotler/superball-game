package com.example.superball.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.R;

public class GameObject {
    private int idNum;
    private String type, collideShape;
    private int x, y, width, height, radius;
    private int movementX = 0, movementY = 0;

    private int speedGateLevel = 0;

    private Paint paint;

    // Constructor & Basic Functions //
    public GameObject(Context context, int idNum, String type, int x, int y, int width, int height) {
        this.idNum = idNum;
        this.type = type;
        this.collideShape = "Rect";
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        radius = 1;
        paint = new Paint();

        if(type == "Speed Gate")
            paint.setColor(ContextCompat.getColor(context, R.color.yellow));
        else if(type == "Checkpoint")
            paint.setColor(ContextCompat.getColor(context, R.color.aqua));

        else if(type == "Point" || type == "Bomb") {
            collideShape = "Circle";
            radius = 20;

            if(type == "Point") { paint = Game.paintDarkRed; }
            else if(type == "Death") { paint = Game.paintBlack; }
        }
    }

    public void update(int scrollSpeed) {
        x -= scrollSpeed;
    }

    public void draw(Canvas canvas) {
        if(paint != null) {
            if(collideShape == "Rect")
                canvas.drawRect(x, y, x + width, y + height, paint);
            else if(collideShape == "Circle")
                canvas.drawCircle((float) x, (float) y, (float) radius, paint);
        }
    }

    // Getters & Setters //
    public String getType() { return type; }
    public String getCollideShape() { return collideShape; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getRadius() { return radius; }
    public int getMovementX() { return movementX; }
    public int getMovementY() { return movementY; }
    public int getSpeedGateLevel() { return speedGateLevel; }
}
