package com.example.superball.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.R;
import com.example.superball.gamepanel.MainMenu;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Ground {
    private static final int MAX_SPEED_LEVEL = 5;
    public static final int DEFAULT_SCROLL_SPEED = 9;
    public static final int SPEED_TIMER_SECONDS = 4;
    private int scrollSpeed = DEFAULT_SCROLL_SPEED;
    private int scrollSpeedTimer = -1;
    private int scrollSpeedLevel = 0;

    private Vector<Platform> platformTopList = new Vector<Platform>();
    private Vector<Platform> platformBottomList = new Vector<Platform>();

    public Paint paintArc;

    // Constructor & Basic Functions //
    public Ground(Context context) {
        paintArc = new Paint();
        paintArc.setColor(ContextCompat.getColor(context, R.color.red));
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeWidth(16);
    }

    public void update(Context context, MainMenu menuMain, Player player) {
        Iterator<Platform> iteratorPlatform = platformTopList.iterator();
        boolean generatePlatform = false;
        String platformType = "Default";
        int newPlatformX, randomNum;

        // Top Platforms //
        while(iteratorPlatform.hasNext()) {
            Platform platform = iteratorPlatform.next();
            platform.update(scrollSpeed + (int) player.getDashVelocity());
            int deleteLoc = platform.getX() + platform.getWidth() + platform.getMovementRadius();
            if(platform.getNextPlatformArcDistance() > 0)
                deleteLoc += platform.getNextPlatformArcDistance() + 50;
            if(deleteLoc < 0) {
                iteratorPlatform.remove();
                if(menuMain.getSelectedLevel() == 21)
                    generatePlatform = true;
            }
        }
        if(generatePlatform) {
            randomNum = new Random().nextInt(4);
            if(randomNum == 0) { platformType = "Default"; }
            else if(randomNum == 1) { platformType = "Speed Up"; }
            else if(randomNum == 2) { platformType = "Bounce"; }
            else if(randomNum == 3) { platformType = "Death"; }
            newPlatformX = Game.SCREEN_WIDTH;
            if(platformTopList.size() > 0)
                newPlatformX = platformTopList.lastElement().getX() + platformTopList.lastElement().getWidth() + (new Random().nextInt(200) + 100);
            platformTopList.add(new Platform(context, -1, platformType, newPlatformX, Game.SCREEN_HEIGHT - (new Random().nextInt(450) + 350), 200, 100));
            generatePlatform = false;
        }

        // Bottom Platforms //
        iteratorPlatform = platformBottomList.iterator();
        while(iteratorPlatform.hasNext()) {
            Platform platform = iteratorPlatform.next();
            platform.update(scrollSpeed + (int) player.getDashVelocity());
            int deleteLoc = platform.getX() + platform.getWidth() + platform.getWidth();
            if(platform.getNextPlatformArcDistance() > 0)
                deleteLoc += platform.getNextPlatformArcDistance() + 50;
            if(deleteLoc < 0) {
                iteratorPlatform.remove();
                if(menuMain.getSelectedLevel() == 21)
                    generatePlatform = true;
            }
        }
        if(generatePlatform) {
            randomNum = new Random().nextInt(4);
            if(randomNum == 0) { platformType = "Default"; }
            else if(randomNum == 1) { platformType = "Speed Up"; }
            else if(randomNum == 2) { platformType = "Bounce"; }
            else if(randomNum == 3) { platformType = "Death"; }
            newPlatformX = Game.SCREEN_WIDTH;
            if(platformBottomList.size() > 0)
                newPlatformX = platformBottomList.lastElement().getX() + platformBottomList.lastElement().getWidth() + (new Random().nextInt(200) + 100);
            platformBottomList.add(new Platform(context, -1, platformType, newPlatformX, Game.SCREEN_HEIGHT - 100, 200, 100));
        }

        // Update Speed Timer //
        if(scrollSpeedTimer != -1) {
            scrollSpeedTimer--;
            if(scrollSpeedTimer == 0) {
                scrollSpeedLevel--;
                scrollSpeed = DEFAULT_SCROLL_SPEED;
                if(scrollSpeedLevel > 0) {
                    scrollSpeedTimer = SPEED_TIMER_SECONDS * 60;
                    for(int i = 0; i < scrollSpeedLevel; i++)
                        scrollSpeed += (MAX_SPEED_LEVEL + 2) - i;
                } else
                    scrollSpeedTimer = -1;
            }
        }
    }

    public void draw(Canvas canvas) {
        for(Platform platform : platformTopList) {
            if(platform.getX() - platform.getMovementRadius() >= Game.SCREEN_WIDTH)
                break;
            platform.draw(canvas, scrollSpeedLevel);
            if(platform.getNextPlatformArcDistance() != -1)
                drawArcDistance(canvas, platform);
        }
        for(Platform platform : platformBottomList) {
            if(platform.getX() - platform.getMovementRadius() >= Game.SCREEN_WIDTH)
                break;
            platform.draw(canvas, scrollSpeedLevel);
            if(platform.getNextPlatformArcDistance() != -1)
                drawArcDistance(canvas, platform);
        }
    }

    public void drawArcDistance(Canvas canvas, Platform platform) {
        RectF oval = new RectF();
        int arcX = platform.getX() + (platform.getWidth() / 2);
        int arcHeight = 1500;
        oval.set(arcX, platform.getY() - arcHeight, arcX + platform.getNextPlatformArcDistance(), platform.getY() + arcHeight - 75);
        canvas.drawArc(oval, 180, 180, false, paintArc);
    }

    // User-Defined Functions //
    public void platformCollisionDetection(Game game, Player player, Vector<Platform> platformList) {

        // Collision Detection //
        for(Platform platform : platformList) {
            if(platform.getX() + platform.getMovementX() > player.getX() + player.getRadius())
                break;

            // Death Platform Check //
            if(platform.getType() == "Death") {
                if(Game.rectCircleCollide(platform.getX() + platform.getMovementX(), platform.getY() + platform.getMovementY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getY(), player.getRadius())
                && !Game.rectCircleCollide(platform.getX() + platform.getOldMovementX() + scrollSpeed + (int) player.getDashVelocity(), platform.getY() + platform.getOldMovementY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getOldY(), player.getRadius())) {
                    game.setGameState(0);
                    break;
                }
            }

            // Falling Down //
            if(player.getBounceDir() == 1) {
                if(((player.getDashVelocity() == 0.0 || player.getSpeedGateCheck() == true) && Game.rectCircleCollide(platform.getX() + platform.getMovementX(), platform.getY() + platform.getMovementY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getY(), player.getRadius())
                && !Game.rectCircleCollide(platform.getX() + platform.getOldMovementX() + scrollSpeed + (int) player.getDashVelocity(), platform.getY() + platform.getOldMovementY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getOldY(), player.getRadius())
                && player.getOldY() <= platform.getY() + platform.getOldMovementY())
                ||
                ((player.getDashVelocity() == 0.0 || player.getSpeedGateCheck() == true) && platform.getX() + (platform.getMovementX() / 2) + platform.getWidth() + (scrollSpeed / 2) + ((int) player.getDashVelocity() / 2) >= player.getX()
                && player.getY() - player.getRadius() >= platform.getY() + platform.getHeight() + platform.getOldMovementX()
                && player.getOldY() <= platform.getY() + platform.getOldMovementY())) {

                    player.setY(platform.getY() + platform.getMovementY() - player.getRadius() - 1);
                    player.setDoubleJumpCheck(false);
                    player.setLongSlamCheck(false);
                    player.setDashCheck(false);
                    player.reverseBounceDir();
                    game.setDoubleClickTimer(-1);

                    if(player.getSlamCheck() == true) {
                        player.setSlamCheck(false);
                    }

                    // Speed Up Platform //
                    if(platform.getType() == "Speed Up") {
                        if(scrollSpeedLevel < MAX_SPEED_LEVEL) {
                            scrollSpeedLevel++;
                            scrollSpeed = DEFAULT_SCROLL_SPEED;
                            for(int i = 0; i < scrollSpeedLevel; i++)
                                scrollSpeed += (MAX_SPEED_LEVEL + 2) - i;
                            scrollSpeedTimer = SPEED_TIMER_SECONDS * 60;
                        }
                    }

                    // Speed Down Platform //
                    else if(platform.getType() == "Speed Down") {
                        if(scrollSpeedLevel > 0) {
                            scrollSpeedLevel--;
                            scrollSpeed = DEFAULT_SCROLL_SPEED;
                            for(int i = 0; i < scrollSpeedLevel; i++)
                                scrollSpeed += (MAX_SPEED_LEVEL + 2) - i;
                            scrollSpeedTimer = SPEED_TIMER_SECONDS * 60;
                        }
                    }

                    // Full Speed Platform //
                    else if(platform.getType() == "Full Speed") {
                        if(scrollSpeedLevel < MAX_SPEED_LEVEL) {
                            scrollSpeedLevel = MAX_SPEED_LEVEL;
                            scrollSpeed = DEFAULT_SCROLL_SPEED;
                            for(int i = 0; i < scrollSpeedLevel; i++)
                                scrollSpeed += (MAX_SPEED_LEVEL + 2) - i;
                            scrollSpeedTimer = SPEED_TIMER_SECONDS * 60;
                        }
                    }

                    // Full Stop Platform //
                    else if(platform.getType() == "Full Stop") {
                        if(scrollSpeedLevel < MAX_SPEED_LEVEL) {
                            scrollSpeedLevel = 0;
                            scrollSpeed = DEFAULT_SCROLL_SPEED;
                            scrollSpeedTimer = -1;
                        }
                    }

                    // High Bounce Platform //
                    else if(platform.getType() == "Bounce") {
                        player.setVelocity(player.getVelocity() + 60.0);
                    }

                    break;
                }

                // Walls //
                else if(platform.getType() == "Wall" && Game.rectCircleCollide(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getY(), player.getRadius())) {
                    game.setGameState(0);
                    break;
                }
            }

            // Bouncing Up //
            else if(player.getBounceDir() == -1 && (platform.getType() == "Wall" || platform.getType() == "Death")) {
                if(Game.rectCircleCollide(platform.getX() + platform.getMovementX(), platform.getY() + platform.getMovementY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getY(), player.getRadius())
                && !Game.rectCircleCollide(platform.getX() + platform.getOldMovementX() + scrollSpeed + (int) player.getDashVelocity(), platform.getY() + platform.getOldMovementY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getOldY(), player.getRadius())
                && player.getOldY() >= platform.getY() + platform.getOldMovementY() + platform.getHeight()) {

                    player.setY(platform.getY() + platform.getHeight() + player.getRadius() + 1);
                    player.reverseBounceDir();
                }

                // Walls //
                else if(Game.rectCircleCollide(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight(), player.getX(), player.getY(), player.getRadius())) {
                    game.setGameState(0);
                    break;
                }
            }
        }
    }

    public void reset() {
        scrollSpeed = DEFAULT_SCROLL_SPEED;
        scrollSpeedLevel = 0;
        scrollSpeedTimer = 0;

        Iterator<Platform> iteratorPlatform = platformTopList.iterator();
        while(iteratorPlatform.hasNext()) {
            iteratorPlatform.next();
            iteratorPlatform.remove();
        }
        iteratorPlatform = platformBottomList.iterator();
        while(iteratorPlatform.hasNext()) {
            iteratorPlatform.next();
            iteratorPlatform.remove();
        }
    }

    // Getters & Setters //
    public Vector<Platform> getPlatformTopList() { return platformTopList; }
    public Vector<Platform> getPlatformBottomList() { return platformBottomList; }
    public Vector<Platform> getPlatformList() {
        Vector<Platform> platformList = new Vector<Platform>();
        platformList.addAll(platformTopList);
        platformList.addAll(platformBottomList);

        return platformList;
    }
    public Vector<Platform> getSortedPlatformList() {
        Vector<Platform> platformList = new Vector<Platform>();
        platformList.setSize(getPlatformList().size() + 1);
        for(Platform platform : getPlatformTopList())
            platformList.set(platform.getIdNum(), platform);
        for(Platform platform : getPlatformBottomList())
            platformList.set(platform.getIdNum(), platform);
        platformList.remove(0);

        return platformList;
    }
    public int getScrollSpeed() { return scrollSpeed; }
    public int getScrollSpeedTimer() { return scrollSpeedTimer; }
    public int getScrollSpeedLevel() { return scrollSpeedLevel; }
}
