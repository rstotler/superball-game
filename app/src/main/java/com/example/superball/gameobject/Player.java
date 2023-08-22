package com.example.superball.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.Image;
import com.example.superball.R;
import com.example.superball.gamepanel.MainMenu;
import com.example.superball.gamepanel.MenuListObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class Player {
    public final double MAX_VELOCITY = 100.0;
    public final double MAX_SLAM_VELOCITY = 200.0;

    private int x = 0;
    private int y = 0;
    private int oldY = 0;
    private double velocity = 0.0;
    private double dashVelocity = 0.0;
    private int bounceDir = 1;

    private int radius = 0;
    private double massMod = 0.0;

    private boolean slamCheck = false;
    private boolean longSlamCheck = false;
    private boolean airBrakeCheck = false;
    private boolean doubleJumpCheck = false;
    private boolean dashCheck = false;

    private boolean inSpeedGate = false, speedGateCheck = false;

    private Vector<MenuListObject> ballList = new Vector<MenuListObject>();
    private Vector<MenuListObject> abilityList = new Vector<MenuListObject>();
    private Vector<Integer> itemList;
    private int gear1 = -1, gear2 = -1;

    private boolean trailToggle = true;
    private Vector<Vector<Integer>> trailList = new Vector<Vector<Integer>>();

    // Images //
    private Bitmap imgBall;

    // Constructor & Basic Functions //
    public Player() {
        itemList = new Vector<Integer>();
        for(int i = 0; i < 6; i++)
            itemList.add(-1);

        MainMenu.loadMenuListObjects(this);

        // Debug Items//
        itemList.set(0, 1);
        itemList.set(1, 2);
        itemList.set(2, 3);
        itemList.set(3, 4);
        itemList.set(4, 5);
        itemList.set(5, 6);
    }

    public String update(Ground ground) {
        String returnString = "";

        // Update Velocity //
        double newVelocity = velocity;
        oldY = y;
        if(trailToggle) {
            Vector<Integer> trailElement = new Vector<Integer>();
            trailElement.add((int) (ground.getScrollSpeed() + dashVelocity));
            trailElement.add(y - radius);
            trailList.insertElementAt(trailElement, 0);

            if(trailList.size() * Image.ballTrail.get(0).getWidth() > 200)
                trailList.remove(trailList.size() - 1);
        }

        if(dashVelocity == 0.0 || speedGateCheck == true) {

            // Player Fall (Increase Player Velocity) //
            if(bounceDir == 1) {
                if(slamCheck == false) {
                    if(velocity < MAX_VELOCITY)
                        newVelocity += 1.0 * massMod;
                } else {
                    if(velocity < MAX_SLAM_VELOCITY)
                        newVelocity += (1.0 * massMod) + 3.0;
                }
                setVelocity(newVelocity);
            }

            // Player Rise (Decrease Player Velocity) //
            else if(bounceDir == -1) {
                if(airBrakeCheck == false)
                    newVelocity -= 1.5 * massMod;
                else
                    newVelocity -= (1.5 * massMod) + 2.5;
                setVelocity(newVelocity);

                if(velocity == 0.0) {
                    reverseBounceDir();
                    if(airBrakeCheck)
                        airBrakeCheck = false;
                }
            }

            // Update Player Y Position //
            if(velocity != 0.0)
                y += ((int) velocity * bounceDir);
        }
        if(dashVelocity > 0.0 && inSpeedGate == false) {
            dashVelocity--;
            if(dashVelocity == 0.0)
                speedGateCheck = false;
        }

        // End Game Check //
        if(y > Game.SCREEN_HEIGHT + 400)
            returnString = "End Game";

        return returnString;
    }

    public void draw(Canvas canvas) {
        if(trailToggle && Image.ballTrail != null) {
            int trailX = x;
            for(Vector<Integer> trailElement : trailList) {
                Bitmap ballTrailImage = Image.ballTrail.get(0);
                if(trailElement.get(0) >= 30)
                    ballTrailImage = Image.ballTrail.get(3);
                else if(trailElement.get(0) >= 20)
                    ballTrailImage = Image.ballTrail.get(2);
                else if(trailElement.get(0) >= 10)
                    ballTrailImage = Image.ballTrail.get(1);

                trailX -= ballTrailImage.getWidth();
                canvas.drawBitmap(ballTrailImage, trailX, trailElement.get(1), null);
                if(trailX < -Image.ballTrail.get(0).getWidth())
                    break;
            }
        }

        if(imgBall != null)
            canvas.drawBitmap(imgBall, x - (imgBall.getWidth() / 2), y -(imgBall.getHeight() / 2), null);
        else
            canvas.drawCircle(x, y, radius, Game.paintDarkRed);
    }

    // User-Defined Functions //
    public void doubleJump() {
        if(doubleJumpCheck == false && inSpeedGate == false) {
            bounceDir = -1;
            slamCheck = false;
            doubleJumpCheck = true;

            double doubleJumpMassMod = (massMod * .75);
            if(doubleJumpMassMod < 1.0) { doubleJumpMassMod = 1.0; }
            velocity = 50 * doubleJumpMassMod;
        }
    }

    public void powerSlam() {
        if(inSpeedGate == false) {
            bounceDir = 1;
            velocity = 50;
            airBrakeCheck = false;
            doubleJumpCheck = true; // Prevent Double Jumping After Power Slam
            dashCheck = true; // Prevent Dashing After Power Slam
        }
    }

    public void dash() {
        if(dashCheck == false && inSpeedGate == false) {
            dashVelocity = 30.0;
            velocity = 0.0;
            bounceDir = 1;
            dashCheck = true;
            speedGateCheck = false;
        }
    }

    public void reset(Context context, MainMenu menuMain) {
        x = 137;
        y = 1000;
        oldY = y;
        velocity = 0.0;
        dashVelocity = 0.0;
        bounceDir = 1;

        slamCheck = false;
        longSlamCheck = false;
        airBrakeCheck = false;
        doubleJumpCheck = false;
        dashCheck = false;

        radius = Player.getBallRadius(menuMain.getSelectedBall());
        massMod = Player.getBallMass(menuMain.getSelectedBall());

        trailList = new Vector<Vector<Integer>>();

        imgBall = BitmapFactory.decodeResource(context.getResources(), Image.getBallResource(menuMain.getSelectedBall()));
    }

    // Static Functions //
    public static int getBallRadius(int selectedBall) {
        if(Image.getBallResource(selectedBall) == R.drawable.superball)
            return (int) (Game.SCREEN_WIDTH * .018);
        else if(Image.getBallResource(selectedBall) == R.drawable.basketball)
            return (int) (Game.SCREEN_WIDTH * .06);
        else if(Image.getBallResource(selectedBall) == R.drawable.beach_ball)
            return (int) (Game.SCREEN_WIDTH * .075);
        else if(Image.getBallResource(selectedBall) == R.drawable.bowling_ball)
            return (int) (Game.SCREEN_WIDTH * .05);
        else if(Image.getBallResource(selectedBall) == R.drawable.tennis_ball)
            return (int) (Game.SCREEN_WIDTH * .028);
        else
            return (int) (Game.SCREEN_WIDTH * .018);
    }

    public static double getBallMass(int selectedBall) {
        if(Image.getBallResource(selectedBall) == R.drawable.superball)
            return .85;
        else if(Image.getBallResource(selectedBall) == R.drawable.basketball)
            return 1.45;
        else if(Image.getBallResource(selectedBall) == R.drawable.beach_ball)
            return .6;
        else if(Image.getBallResource(selectedBall) == R.drawable.bowling_ball)
            return 1.8;
        else if(Image.getBallResource(selectedBall) == R.drawable.tennis_ball)
            return .95;
        else
            return .85;
    }

    // Getters & Setters //
    public void setY(int y) { this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getOldY() { return oldY; }
    public int getRadius() { return radius; }
    public double getVelocity() { return velocity; }
    public int getBounceDir() { return bounceDir; }
    public void setBounceDir(int bounceDir) { this.bounceDir = bounceDir; }
    public void reverseBounceDir() { bounceDir *= -1; }
    public void setSlamCheck(boolean slamCheck) { this.slamCheck = slamCheck; }
    public boolean getSlamCheck() { return slamCheck; }
    public void setLongSlamCheck(boolean longSlamCheck) { this.longSlamCheck = longSlamCheck; }
    public void setAirBrakeCheck(boolean airBrakeCheck) { this.airBrakeCheck = airBrakeCheck; }
    public boolean getAirBrakeCheck() { return airBrakeCheck; }
    public void setDoubleJumpCheck(boolean doubleJumpCheck) { this.doubleJumpCheck = doubleJumpCheck; }
    public double getDashVelocity() { return dashVelocity; }
    public void setDashVelocity(double dashVelocity) { this.dashVelocity = dashVelocity; }
    public void setDashCheck(boolean dashCheck) { this.dashCheck = dashCheck; }
    public boolean getInSpeedGate() { return inSpeedGate; }
    public void setInSpeedGate(boolean inSpeedGate) { this.inSpeedGate = inSpeedGate; }
    public boolean getSpeedGateCheck() { return speedGateCheck; }
    public void setSpeedGateCheck(boolean speedGateCheck) { this.speedGateCheck = speedGateCheck; }
    public Vector<MenuListObject> getBallList() { return ballList; }
    public Vector<MenuListObject> getAbilityList() { return abilityList; }
    public Vector<Integer> getItemList() { return itemList; }
    public int getGear1() { return gear1; }
    public void setGear1(int gear1) { this.gear1 = gear1; }
    public int getGear2() { return gear2; }
    public void setGear2(int gear2) { this.gear2 = gear2; }
    public Bitmap getImgBall() { return imgBall; }

    public void setVelocity(double velocity) {
        this.velocity = velocity;

        // Bounds Check //
        if(bounceDir == 1) {
            if(slamCheck == false && this.velocity > MAX_VELOCITY && longSlamCheck == false)
                this.velocity = MAX_VELOCITY;
            else if(slamCheck == true && this.velocity > MAX_SLAM_VELOCITY)
                this.velocity = MAX_SLAM_VELOCITY;
        } else {
            if(this.velocity < 0.0)
                this.velocity = 0.0;
        }
    }
}
