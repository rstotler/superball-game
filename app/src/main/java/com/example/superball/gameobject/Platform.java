package com.example.superball.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.Image;
import com.example.superball.R;

import java.util.Vector;

public class Platform {
    private int idNum;
    private String type;
    private int x, y, width, height;

    private String movementType = "";
    private int movementTick = 0;
    private int movementTickMax = 125;
    private int movementRadius = 0;
    private int movementX = 0, movementY = 0;
    private int oldMovementX = 0, oldMovementY = 0;

    // -1 = Do Not Check Next Platform Distance, -2 = Check Next Platform Distance
    private int nextPlatformDistance = -1, nextPlatformArcDistance = -1;
    private int distanceSignX = 0, distanceSignY = 0, distanceSignWidth = 0, distanceSignHeight = 0, postWidth = 0, postHeight = 0, distanceSignMarginSize = 0;
    public int distanceStringWidth = 0, distanceStringHeight = 0, suffixStringHeight = 0;

    private Vector<Paint> paintList = new Vector<Paint>();
    private int imageInnerMarginSize = (int) (Game.SCREEN_WIDTH * .005);
    private String imageType = "";
    private Bitmap imgLeft, imgTop, imgRight, imgBottom, imgTypeIcon;

    // Constructor & Basic Functions //
    public Platform(Context context, int idNum, String type, int x, int y, int width, int height) {
        this.idNum = idNum;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if(type == "Speed Up")
            imgTypeIcon = Image.speedLevelArrowActive;

        // Paint //
        Paint paintPlatform = new Paint();

        if(type == "Default") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.light_red));
            paintList.add(paintPlatform);
        }
        else if(type == "Speed Up") {
            int platformColor = R.color.teal_200;
            for(int i = 0; i < 6; i++) {
                Paint paintPlatformSpeed = new Paint();
                if(i == 1) { platformColor = R.color.white; }
                else if(i == 2) { platformColor = R.color.purple_200; }
                else if(i == 3) { platformColor = R.color.purple_500; }
                else if(i == 4) { platformColor = R.color.purple_700; }
                else if(i == 5) { platformColor = R.color.green; }
                paintPlatformSpeed.setColor(ContextCompat.getColor(context, platformColor));
                paintList.add(paintPlatformSpeed);
            }
        }
        else if(type == "Speed Down") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.dark_red));
            paintList.add(paintPlatform);
        }
        else if(type == "Full Speed") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.green));
            paintList.add(paintPlatform);
        }
        else if(type == "Full Stop") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.grey));
            paintList.add(paintPlatform);
        }
        else if(type == "Wall") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.red));
            paintList.add(paintPlatform);
        }
        else if(type == "Bounce") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.orange));
            paintList.add(paintPlatform);
        }
        else if(type == "Death") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.dark_grey));
            paintList.add(paintPlatform);
        }
        else if(type == "Movement") {
            paintPlatform.setColor(ContextCompat.getColor(context, R.color.purple_200));
            paintList.add(paintPlatform);
        }
    }

    public void update(int scrollSpeed) {
        x -= scrollSpeed;

        if(type == "Movement") {
            movementTick++;
            if (movementTick >= movementTickMax)
                movementTick = 0;

            oldMovementX = movementX;
            oldMovementY = movementY;

            if(movementType == "Rotating" || movementType == "Horizontal" || movementType == "Semicircle")
                movementX = (int) (Math.cos(Math.toRadians(((movementTick + .0) / movementTickMax) * 360)) * movementRadius);
            if(movementType == "Rotating" || movementType == "Vertical" || movementType == "Semicircle") {
                movementY = (int) (Math.sin(Math.toRadians(((movementTick + .0) / movementTickMax) * 360)) * movementRadius);
                if(movementType == "Semicircle" && movementTick > (movementTickMax / 2))
                    movementY *= -1;
            }
        }
    }

    public void draw(Canvas canvas, int scrollSpeedLevel) {

        // Set Target Paint //
        Paint paintPlatform = paintList.firstElement();
        if(type == "Speed Up")
            paintPlatform = paintList.elementAt(scrollSpeedLevel);

        // Platform Distance //
        if(nextPlatformDistance >= 0)
            drawNextPlatformDistanceSign(canvas);

        // Draw Default Rect/Image //
        if(imageType == "")
            canvas.drawRect(x + movementX, y + movementY, x + width + movementX, y + height + movementY, paintPlatform);
        else {
            canvas.drawRect(x + movementX + imageInnerMarginSize, y + movementY + imageInnerMarginSize, x + width + movementX - imageInnerMarginSize, y + height + movementY - imageInnerMarginSize, paintPlatform);
            if(imgTop != null && imgBottom != null && imgLeft != null && imgRight != null) {
                canvas.drawBitmap(imgLeft, x + movementX, y + movementY + Image.platformDefaultTopLeft.getWidth(), null);
                canvas.drawBitmap(imgRight, x + movementX + width - imgRight.getWidth(), y + movementY + Image.platformDefaultTopLeft.getWidth(), null);
                canvas.drawBitmap(imgTop, x + movementX + Image.platformDefaultTopLeft.getWidth(), y + movementY, null);
                canvas.drawBitmap(imgBottom, x + movementX + Image.platformDefaultTopLeft.getWidth(), y + movementY + height - imgBottom.getHeight(), null);
            }

            canvas.drawBitmap(Image.platformDefaultTopLeft, x + movementX, y + movementY, null);
            canvas.drawBitmap(Image.platformDefaultTopRight, x + movementX + width - Image.platformDefaultTopRight.getWidth(), y + movementY, null);
            canvas.drawBitmap(Image.platformDefaultBottomRight, x + movementX + width - Image.platformDefaultBottomRight.getWidth(), y + movementY + height - Image.platformDefaultBottomRight.getWidth(), null);
            canvas.drawBitmap(Image.platformDefaultBottomLeft, x + movementX, y + movementY + height - Image.platformDefaultBottomLeft.getWidth(), null);
        }

        // Type Icon //
        if(imgTypeIcon != null) {
            int typeIconX = x + (width / 2) - (imgTypeIcon.getWidth() / 2);
            int typeIconY = y + (height / 2) - (imgTypeIcon.getHeight() / 2);
            canvas.drawBitmap(imgTypeIcon, typeIconX, typeIconY, null);
        }

        // Debug - Platform Num //
        if(true) {
            int platformNumX = x + (width / 4);
            int platformNumY = y + (height / 2);
            if(y < 0)
                platformNumY = y + height - ((int) (Game.SCREEN_HEIGHT * .05));

            canvas.drawText(String.valueOf(idNum), platformNumX, platformNumY, Game.textPaintBlack36);
        }
    }

    public void drawNextPlatformDistanceSign(Canvas canvas) {

        // Post //
        canvas.drawRect(x + distanceSignX + (distanceSignWidth / 2) - (postWidth / 2), y + distanceSignY + distanceSignHeight, x + distanceSignX + (distanceSignWidth / 2) + postWidth, y + distanceSignY + distanceSignHeight + postHeight, Game.paintDarkBrown);

        // Sign //
        canvas.drawRect(x + distanceSignX, y + distanceSignY, x + distanceSignX + distanceSignWidth, y + distanceSignY + distanceSignHeight, Game.paintDarkBrown);
        canvas.drawRect(x + distanceSignX + (distanceSignMarginSize / 2), y + distanceSignY + (distanceSignMarginSize / 2), x + distanceSignX + distanceSignWidth - (distanceSignMarginSize / 2), y + distanceSignY + distanceSignHeight - (distanceSignMarginSize / 2), Game.paintBrown);

        // Text //
        canvas.drawText(String.valueOf(nextPlatformDistance), x + distanceSignX + distanceSignMarginSize, y + distanceSignY + distanceSignMarginSize + distanceStringHeight, Game.textPaintWhite73);
        canvas.drawText("ft", x + distanceSignX + (distanceSignMarginSize * 2) + distanceStringWidth, y + distanceSignY + distanceSignMarginSize + distanceSignHeight - suffixStringHeight, Game.textPaintWhite36);
    }

    // Getters & Setters //
    public int getIdNum() { return idNum; }
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getOldMovementX() { return oldMovementX; }
    public int getOldMovementY() { return oldMovementY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getMovementRadius() { return movementRadius; }
    public int getMovementX() { return movementX; }
    public int getMovementY() { return movementY; }
    public int getNextPlatformDistance() { return nextPlatformDistance; }
    public void setNextPlatformDistance(int nextPlatformDistance) { this.nextPlatformDistance = nextPlatformDistance; }
    public int getNextPlatformArcDistance() { return nextPlatformArcDistance; }
    public void setNextPlatformArcDistance(int nextPlatformArcDistance) { this.nextPlatformArcDistance = nextPlatformArcDistance; }

    public void setDistanceSignX(int distanceSignX) { this.distanceSignX = distanceSignX; }
    public void setDistanceSignY(int distanceSignY) { this.distanceSignY = distanceSignY; }
    public void setDistanceSignWidth(int distanceSignWidth) { this.distanceSignWidth = distanceSignWidth; }
    public void setDistanceSignHeight(int distanceSignHeight) { this.distanceSignHeight = distanceSignHeight; }
    public void setDistanceSignMarginSize(int distanceSignMarginSize) {this.distanceSignMarginSize = distanceSignMarginSize; }
    public void setDistanceStringHeight(int distanceStringHeight) { this.distanceSignHeight = distanceStringHeight; }
    public void setPostWidth(int postWidth) { this.postWidth = postWidth; }
    public void setPostHeight(int postHeight) { this.postHeight = postHeight; }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
        this.movementRadius = 150;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;

        Bitmap targetTopImage = null;
        int cornerSize = 0;
        if(imageType == "Default") {
            targetTopImage = Image.platformDefaultTop;
            cornerSize = Image.platformDefaultTopLeft.getWidth();
        }

        if(targetTopImage != null) {
            Matrix matrix90 = new Matrix();
            matrix90.postRotate(270);
            Matrix matrix180 = new Matrix();
            matrix180.postRotate(180);

            imgTop = Bitmap.createScaledBitmap(targetTopImage, width - (cornerSize * 2), targetTopImage.getHeight(), true);
            imgBottom = Bitmap.createBitmap(imgTop, 0, 0, imgTop.getWidth(), imgTop.getHeight(), matrix180, true);

            imgLeft = Bitmap.createBitmap(targetTopImage, 0, 0, targetTopImage.getWidth(), targetTopImage.getHeight(), matrix90, true);
            imgLeft = Bitmap.createScaledBitmap(imgLeft, imgLeft.getWidth(), height - (cornerSize * 2), true);
            imgRight = Bitmap.createBitmap(imgLeft, 0, 0, imgLeft.getWidth(), imgLeft.getHeight(), matrix180, true);
        }
    }
}
