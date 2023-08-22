package com.example.superball;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

import com.example.superball.gameobject.GameObject;
import com.example.superball.gameobject.Platform;

public class Level {

    // Load Level Functions //
    public static void loadLevel(Context context, Game game, int selectedLevel) {

        // Load Level Objects //
        if(selectedLevel == 0)
            loadLevel01(context, game);
        else if(selectedLevel == 1)
            loadLevel02(context, game);
        else if(selectedLevel == 10)
            loadLevelRandom(context, game);

        // Initialize Platform Distances //
        initializePlatforms(game);
    }

    public static void loadLevel01(Context context, Game game) {
        int platformIdNum = 1;
        int gameObjectIdNum = 1;
        int platformHeight = 250;
        int bottomGapSize = 50;
        int gapSize;

        // Area 1 - Intro //

        // Platform 1 - Long Platform //
        int platformX = 300;
        int platformWidth = 5000;
        Platform platform1 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform1.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform1);

        // Platform 2 - Small Gap & Medium Platform //
        gapSize = 200;
        platformX = platform1.getX() + platform1.getWidth() + gapSize;
        platformWidth = 2000;
        Platform platform2 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform2.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform2);

        // Platform 3 - Medium Gap & Medium Platform //
        gapSize = 300;
        platformX = platform2.getX() + platform2.getWidth() + gapSize;
        platformWidth = 1500;
        Platform platform3 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform3.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform3);

        // Platform 4 - Between The Gap //
        gapSize = 250;
        platformX = platform3.getX() + platform3.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 115;
        bottomGapSize = 115;
        Platform platform4 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform4.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform4);

        // Platform 5 - Medium-Large Gap & Medium Platform //
        gapSize = 250;
        platformX = platform4.getX() + platform4.getWidth() + gapSize;
        platformWidth = 1000;
        platformHeight = 250;
        bottomGapSize = 50;
        Platform platform5 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform5.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform5);

        // Platform 6 - Large Gap & Medium Platform //
        gapSize = 500;
        platformX = platform5.getX() + platform5.getWidth() + gapSize;
        platformWidth = 750;
        Platform platform6 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform6.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform6);

        // Platform 7 - Between The Gap 2 //
        gapSize = 250;
        platformX = platform6.getX() + platform6.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 115;
        bottomGapSize = 115;
        Platform platform7 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform7.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform7);

        // Platform 8 - Medium-Large Gap & Medium Platform //
        gapSize = 250;
        platformX = platform7.getX() + platform7.getWidth() + gapSize;
        platformWidth = 1000;
        platformHeight = 250;
        bottomGapSize = 50;
        Platform platform8 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform8.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform8);

        // Platform 9 - Square Step 1 //
        gapSize = 300;
        platformX = platform8.getX() + platform8.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 350;
        bottomGapSize = 250;
        Platform platform9 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform9.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform9);

        // Platform 10 - Square Step 2 //
        gapSize = 200;
        platformX = platform9.getX() + platform9.getWidth() + gapSize;
        bottomGapSize = 750;
        Platform platform10 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform10.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform10);

        // Platform 11 - Square Step 3 //
        gapSize = 200;
        platformX = platform10.getX() + platform10.getWidth() + gapSize;
        bottomGapSize = 1250;
        Platform platform11 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform11.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform11);

        // Platform 12 - Landing Platform //
        gapSize = 1200;
        platformX = platform10.getX() + platform10.getWidth() + gapSize;
        platformWidth = 2500;
        platformHeight = 250;
        bottomGapSize = 50;
        Platform platform12 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform12.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform12);

        // Platform 13 - Speed Platform 1 //
        gapSize = 250;
        platformX = platform12.getX() + platform12.getWidth() + gapSize;
        platformWidth = 300;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform13 = new Platform(context, platformIdNum++, "Speed Up", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform13.setImageType("Default");
        platform13.setNextPlatformDistance(-2);
        game.getGround().getPlatformBottomList().add(platform13);

        // Platform 14 - Decorative Platform 1 //
        platformX = platform13.getX() + platform13.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 830;
        bottomGapSize = -50;
        Platform platform14 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform14.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform14);

        // Platform 15 - Decorative Platform 2 //
        gapSize = 200;
        platformX = platform14.getX() + platform14.getWidth() + gapSize;
        platformHeight = 700;
        Platform platform15 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform15.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform15);

        // Platform 16 - Default Platform //
        gapSize = 250;
        platformX = platform15.getX() + platform15.getWidth() + gapSize;
        platformWidth = 550;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform16 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform16.setImageType("Default");
        platform16.setNextPlatformDistance(-2);
        game.getGround().getPlatformBottomList().add(platform16);

        // Platform 17 - Decorative Platform 1 //
        platformX = platform16.getX() + platform16.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 1000;
        bottomGapSize = -50;
        Platform platform17 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform17.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform17);

        // Platform 18 - Decorative Platform 2 //
        gapSize = 200;
        platformX = platform17.getX() + platform17.getWidth() + gapSize;
        platformHeight = 1400;
        Platform platform18 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform18.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform18);

        // Platform 19 - Speed Platform 2 //
        gapSize = 250;
        platformX = platform18.getX() + platform18.getWidth() + gapSize;
        platformWidth = 550;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform19 = new Platform(context, platformIdNum++, "Speed Up", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform19.setImageType("Default");
        platform19.setNextPlatformDistance(-2);
        game.getGround().getPlatformBottomList().add(platform19);

        // Platform 20 - Decorative Platform 1 //
        gapSize = 350;
        platformX = platform19.getX() + platform19.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 450;
        bottomGapSize = -50;
        Platform platform20 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform20.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform20);

        // Platform 21 - Decorative Platform 2 //
        gapSize = 250;
        platformX = platform20.getX() + platform20.getWidth() + gapSize;
        Platform platform21 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform21.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform21);

        // Platform 22 - Decorative Platform 3 //
        platformX = platform21.getX() + platform21.getWidth() + gapSize;
        Platform platform22 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform22.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform22);

        // Platform 23 - Default Platform //
        gapSize = 350;
        platformX = platform22.getX() + platform22.getWidth() + gapSize;
        platformWidth = 600;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform23 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform23.setImageType("Default");
        platform23.setNextPlatformDistance(-2);
        game.getGround().getPlatformBottomList().add(platform23);

        // Platform 24 - Decorative Platform 1 //
        platformX = platform23.getX() + platform23.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 450;
        bottomGapSize = -50;
        Platform platform24 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform24.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform24);

        // Platform 25 - Decorative Platform 2 //
        gapSize = 250;
        platformX = platform24.getX() + platform24.getWidth() + gapSize;
        Platform platform25 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform25.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform25);

        // Platform 26 - Decorative Platform 3 //
        platformX = platform25.getX() + platform25.getWidth() + gapSize;
        Platform platform26 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform26.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform26);

        // Platform 27 - Speed Platform 3 //
        gapSize = 350;
        platformX = platform26.getX() + platform26.getWidth() + gapSize;
        platformWidth = 600;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform27 = new Platform(context, platformIdNum++, "Speed Up", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform27.setImageType("Default");
        platform27.setNextPlatformDistance(-2);
        game.getGround().getPlatformBottomList().add(platform27);

        // Platform 28 - Decorative Platform 1 //
        platformX = platform27.getX() + platform27.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 450;
        bottomGapSize = -50;
        Platform platform28 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform28.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform28);

        // Platform 29 - Decorative Platform 2 //
        gapSize = 250;
        platformX = platform28.getX() + platform28.getWidth() + gapSize;
        Platform platform29 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform29.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform29);

        // Platform 30 - Decorative Platform 3 //
        platformX = platform29.getX() + platform29.getWidth() + gapSize;
        Platform platform30 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform30.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform30);

        // Platform 31 - Decorative Platform 4 //
        platformX = platform30.getX() + platform30.getWidth() + gapSize;
        Platform platform31 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform31.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform31);

        // Platform 32 - Default Platform //
        gapSize = 350;
        platformX = platform31.getX() + platform31.getWidth() + gapSize;
        platformWidth = 650;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform32 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform32.setImageType("Default");
        platform32.setNextPlatformDistance(-2);
        game.getGround().getPlatformBottomList().add(platform32);

        // Platform 33 - Bottom Wall //
        gapSize = 1500;
        platformX = platform32.getX() + platform32.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 450;
        bottomGapSize = -50;
        Platform platform33 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform33.setImageType("Default");
        platform33.setNextPlatformDistance(-3);
        game.getGround().getPlatformBottomList().add(platform33);

        // Platform 34 - Top Wall //
        platformX = platform33.getX();
        platformHeight = 19400;
        bottomGapSize = -19000;
        Platform platform34 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform34.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform34);

        // GameObject 1 - Speed Gate //
        GameObject gameObject1 = new GameObject(context, gameObjectIdNum++, "Speed Gate", platformX, platform34.getY() + platform34.getHeight(), 1000, platform33.getY() - (platform34.getY() + platform34.getHeight()));
        //gameObject1.setSpeedGateLevel(3);
        game.getGameObjectList().add(gameObject1);

        // Area 2 - Walls //

        // Platform 35 - Landing Platform (Full Stop) //
        gapSize = 750;
        platformX = gameObject1.getX() + gameObject1.getWidth() + gapSize;
        platformWidth = 1500;
        platformHeight = 250;
        bottomGapSize = 50;
        Platform platform35 = new Platform(context, platformIdNum++, "Full Stop", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform35.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform35);

        // Platform 35B - Landing Platform (Default) //
        platformX = platform35.getX() + platform35.getWidth();
        platformWidth = 2500;
        platformHeight = 250;
        bottomGapSize = 50;
        Platform platform35B = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform35B.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform35B);

        // GameObject 2 - Checkpoint //
        platformX = platform35B.getX() + 250;
        platformHeight = 250;
        GameObject gameObject2 = new GameObject(context, gameObjectIdNum++, "Checkpoint", platformX, platform35.getY() - platformHeight, 100, platformHeight);
        game.getGameObjectList().add(gameObject2);

        // Platform 36 - Bottom Wall 1 //
        gapSize = 0;
        platformX = platform35B.getX() + platform35B.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 1050;
        bottomGapSize = -50;
        Platform platform36 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform36.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform36);

        // Platform 37 - Top Wall 1 //
        int areaGapSize = 400;
        platformWidth = 350;
        platformX = platform36.getX() - areaGapSize - platformWidth;
        platformHeight = 20200;
        bottomGapSize = -19000;
        Platform platform37 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform37.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform37);

        // Platform 38 - Top Platform 1 (Wall) //
        gapSize = 0;
        platformX = platform37.getX() + platform37.getWidth() + gapSize;
        platformHeight = 250;
        platformWidth = platform36.getWidth() + (areaGapSize * 2);
        bottomGapSize = Game.SCREEN_HEIGHT - platformHeight - 50;
        Platform platform38 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform38.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform38);

        // Platform 39 - Bottom Platform 1 //
        platformX = platform36.getX() + platform36.getWidth() + gapSize;
        platformWidth = (platform38.getX() + platform38.getWidth() + 350) - (platform36.getX() + platform36.getWidth()) + (areaGapSize / 2);
        platformHeight = 400;
        bottomGapSize = 50;
        Platform platform39 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform39.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform39);

        // Platform 40 - Bottom Wall 2 //
        platformX = platform39.getX() + platform39.getWidth() + (areaGapSize / 2);
        platformWidth = 350;
        platformHeight = 900;
        bottomGapSize = -50;
        Platform platform40 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform40.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform40);

        // Platform 41 - Top Wall 2 //
        gapSize = 0;
        platformX = platform38.getX() + platform38.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 20150;
        bottomGapSize = -19000;
        Platform platform41 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform41.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform41);

        // Platform 42 - Top Platform 2 (Wall) //
        platformX = platform41.getX() + platform41.getWidth() + gapSize;
        platformWidth = platform40.getWidth() + (areaGapSize * 2);
        platformHeight = 500;
        bottomGapSize = Game.SCREEN_HEIGHT - platformHeight - 50;
        Platform platform42 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform42.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform42);

        // Platform 43 - Bottom Platform 2 //
        platformWidth = 500;
        platformX = platform42.getX() + platform42.getWidth() + gapSize - ((platformWidth - 350) / 2);
        platformHeight = 400;
        bottomGapSize = 50;
        Platform platform43 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform43.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform43);

        // Platform 44 - Bottom Wall 3 //
        gapSize = 300;
        platformX = platform43.getX() + platform43.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 1050;
        bottomGapSize = -50;
        Platform platform44 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform44.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform44);

        // Platform 45 - Top Wall 3 //
        gapSize = 0;
        platformX = platform42.getX() + platform42.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 20250;
        bottomGapSize = -19000;
        Platform platform45 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform45.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform45);

        // Platform 46 - Top Platform 3 (Wall) //
        platformX = platform45.getX() + platform45.getWidth() + gapSize;
        platformWidth = platform44.getWidth() + (areaGapSize * 2);
        platformHeight = 650;
        bottomGapSize = Game.SCREEN_HEIGHT - platformHeight - 50;
        Platform platform46 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform46.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform46);

        // Platform 47 - Bottom Platform 3 //
        platformWidth = 500;
        platformX = platform46.getX() + platform46.getWidth() - ((platformWidth - 350) / 2);
        platformHeight = 500;
        bottomGapSize = 50;
        Platform platform47 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform47.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform47);

        // Platform 48 - Bottom Wall 4 //
        gapSize = 300;
        platformX = platform47.getX() + platform47.getWidth() + gapSize;
        platformWidth = 700;
        platformHeight = 950;
        bottomGapSize = -50;
        Platform platform48 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform48.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform48);

        // Platform 49 - Top Wall 4 //
        gapSize = 0;
        platformX = platform46.getX() + platform46.getWidth() + gapSize;
        platformWidth = 350;
        platformHeight = 20250;
        bottomGapSize = -19000;
        Platform platform49 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform49.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform49);

        // Platform 50 - Top Platform 4 (Wall) //
        platformX = platform49.getX() + platform49.getWidth() + gapSize;
        platformWidth = (platform48.getX() + platform48.getWidth()) - (platform49.getX() + platform49.getWidth()) + ((int) (areaGapSize / 1.1));
        platformHeight = 850;
        bottomGapSize = Game.SCREEN_HEIGHT - platformHeight - 50;
        Platform platform50 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform50.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform50);

        // Platform 51 - Bottom Platform 4 //
        platformX = platform48.getX() + platform48.getWidth();
        platformWidth = (((platform50.getX() + platform50.getWidth()) - (platform48.getX() + platform48.getWidth())) + 450 + 1000);
        platformHeight = 200;
        bottomGapSize = 50;
        Platform platform51 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform51.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform51);

        // Platform 52 - Top Wall 4 //
        gapSize = 0;
        platformX = platform50.getX() + platform50.getWidth() + gapSize;
        platformWidth = 110;
        platformHeight = 20500;
        bottomGapSize = -19000;
        Platform platform52 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform52.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform52);

        // Platform 53 - Default Step 1 //
        platformX = platform52.getX() + platform52.getWidth();
        platformHeight = 150;
        platformWidth = 1000;
        int platformY = platform52.getY() + platform52.getHeight() - platformHeight;
        Platform platform53 = new Platform(context, platformIdNum++, "Default", platformX, platformY, platformWidth, platformHeight);
        platform53.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform53);

        // Platform 54 - Decorative Wall //
        platformX = platform52.getX() + platform52.getWidth();
        platformWidth = areaGapSize;
        platformHeight = 20000;
        platformY = platform53.getY() - 750 + 150 - platformHeight;
        Platform platform54 = new Platform(context, platformIdNum++, "Wall", platformX, platformY, platformWidth, platformHeight);
        platform54.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform54);

        // Platform 55 - Default Step 2 //
        platformWidth = platform53.getWidth() - areaGapSize;
        platformHeight = 150;
        platformX = platform52.getX() + platform52.getWidth() + areaGapSize;
        platformY = platform53.getY() - 750;
        Platform platform55 = new Platform(context, platformIdNum++, "Default", platformX, platformY, platformWidth, platformHeight);
        platform55.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform55);

        // Area 3 - Speed Platforms & Walls //

        // Platform 56 - Side Wall //
        platformX = platform53.getX() + platform53.getWidth();
        platformY = platform55.getY();
        platformWidth = 350;
        platformHeight = 1500;
        Platform platform56 = new Platform(context, platformIdNum++, "Wall", platformX, platformY, platformWidth, platformHeight);
        platform56.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform56);

        // Platform 57 - Speed Platform 1 //
        gapSize = 400;
        platformX = platform56.getX() + platform56.getWidth() + gapSize;
        platformWidth = 600;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform57 = new Platform(context, platformIdNum++, "Speed Up", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform57.setImageType("Default");
        platform57.setNextPlatformDistance(-2);
        platform57.setNextPlatformArcDistance(-2);
        game.getGround().getPlatformBottomList().add(platform57);

        // Platform 58 - Bottom Wall 1A //
        platformX = platform57.getX() + platform57.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 1150;
        bottomGapSize = -50;
        Platform platform58 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform58.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform58);

        // Platform 59 - Bottom Wall 1B //
        gapSize = 125;
        platformX = platform58.getX() + platform58.getWidth() + gapSize;
        Platform platform59 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform59.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform59);

        // Platform 60 - Top Death Platform //
        platformX = platform57.getX() + platform57.getWidth();
        platformWidth = (platform59.getX() + platform59.getWidth()) - (platform57.getX() + platform57.getWidth()) + 400 + 350 - 75;
        platformHeight = 20500;
        platformY = 125 - platformHeight;
        Platform platform60 = new Platform(context, platformIdNum++, "Death", platformX, platformY, platformWidth, platformHeight);
        platform60.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform60);

        // Platform 61 - Top Death Wall //
        gapSize = 0;
        platformX = platform60.getX() + platform60.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 20150;
        bottomGapSize = -19000;
        Platform platform61 = new Platform(context, platformIdNum++, "Death", platformX, bottomGapSize, platformWidth, platformHeight);
        platform61.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform61);

        // Platform 62 - Speed Platform 2 //
        gapSize = 400;
        platformX = platform59.getX() + platform59.getWidth() + gapSize;
        platformWidth = 700;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform62 = new Platform(context, platformIdNum++, "Speed Up", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform62.setImageType("Default");
        platform62.setNextPlatformDistance(-2);
        platform62.setNextPlatformArcDistance(-2);
        game.getGround().getPlatformBottomList().add(platform62);

        // Platform 63 - Top Death Platform 2A //
        gapSize = 0;
        platformX = platform61.getX() + platform61.getWidth() + gapSize;
        platformWidth = 500;
        platformHeight = 20500;
        platformY = 125 - platformHeight;
        Platform platform63 = new Platform(context, platformIdNum++, "Death", platformX, platformY, platformWidth, platformHeight);
        platform63.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform63);

        // Platform 64 - Top Wall 2A //
        platformX = platform63.getX() + platform63.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 19650;
        bottomGapSize = -19000;
        Platform platform64 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform64.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform64);

        // Platform 65 - Top Wall 2B //
        gapSize = 125;
        platformX = platform64.getX() + platform64.getWidth() + gapSize;
        Platform platform65 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform65.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform65);

        // Platform 66 - Top Wall 2C //
        platformX = platform65.getX() + platform65.getWidth() + gapSize;
        Platform platform66 = new Platform(context, platformIdNum++, "Wall", platformX, bottomGapSize, platformWidth, platformHeight);
        platform66.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform66);

        // Platform 67 - Bottom Wall Platform 2A //
        platformX = platform64.getX();
        platformWidth = 150;
        platformHeight = 1050;
        bottomGapSize = -50;
        Platform platform67 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform67.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform67);

        // Platform 68 - Bottom Wall Platform 2B //
        gapSize = 125;
        platformX = platform67.getX() + platform67.getWidth() + gapSize;
        Platform platform68 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform68.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform68);

        // Platform 69 - Bottom Wall Platform 2C //
        platformX = platform68.getX() + platform68.getWidth() + gapSize;
        Platform platform69 = new Platform(context, platformIdNum++, "Wall", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform69.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform69);

        // Platform 70 - Speed Platform 3 //
        gapSize = 350;
        platformX = platform69.getX() + platform69.getWidth() + gapSize;
        platformWidth = 600;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform70 = new Platform(context, platformIdNum++, "Speed Up", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform70.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform70);

        // Platform 71 - Top Death Platform 2B //
        platformX = platform66.getX() + platform66.getWidth();
        platformWidth = gapSize + (platform70.getWidth() / 2) - 75;
        platformHeight = 20500;
        platformY = 125 - platformHeight;
        Platform platform71 = new Platform(context, platformIdNum++, "Death", platformX, platformY, platformWidth, platformHeight);
        platform71.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform71);

        // Platform 72 - Top Death Wall //
        gapSize = 0;
        platformX = platform71.getX() + platform71.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 20150;
        bottomGapSize = -19000;
        Platform platform72 = new Platform(context, platformIdNum++, "Death", platformX, bottomGapSize, platformWidth, platformHeight);
        platform72.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform72);

        // Platform 73 - Bottom Tall Death A //
        gapSize = 220;
        platformX = platform70.getX() + platform70.getWidth() + gapSize;
        platformWidth = 150;
        platformHeight = 1300;
        bottomGapSize = -50;
        Platform platform73 = new Platform(context, platformIdNum++, "Death", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform73.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform73);

        // Platform 74 - Bottom Tall Death B //
        gapSize = 150;
        platformX = platform73.getX() + platform73.getWidth() + gapSize;
        platformHeight = 1600;
        Platform platform74 = new Platform(context, platformIdNum++, "Death", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform74.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform74);

        // Platform 75 - Bottom Tall Death C //
        platformX = platform74.getX() + platform74.getWidth() + gapSize;
        platformHeight = 1900;
        Platform platform75 = new Platform(context, platformIdNum++, "Death", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform75.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform75);

        // Platform 76 - Bottom Tall Death D //
        platformX = platform75.getX() + platform75.getWidth() + gapSize;
        platformHeight = 2200;
        Platform platform76 = new Platform(context, platformIdNum++, "Death", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform76.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform76);

        // Platform 77 - Landing Platform //
        gapSize = 150;
        platformX = platform76.getX() + platform76.getWidth() + gapSize;
        platformWidth = 2500;
        platformHeight = 150;
        bottomGapSize = 150;
        Platform platform77 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform77.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform77);
    }

    public static void loadLevel02(Context context, Game game) {
        int platformIdNum = 1;
        int platformHeight = 250;
        int bottomGapSize = 50;

        // Platform 1 //
        int platformX = 300;
        int platformWidth = 3500;
        Platform platform1 = new Platform(context, platformIdNum++, "Default", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform1.setImageType("Default");
        game.getGround().getPlatformBottomList().add(platform1);

        // Platform 2 //
        platformX = 1500;
        platformWidth = 250;
        platformHeight = 150;
        bottomGapSize = 700;
        Platform platform2 = new Platform(context, platformIdNum++, "Movement", platformX, Game.SCREEN_HEIGHT - platformHeight - bottomGapSize, platformWidth, platformHeight);
        platform2.setMovementType("Semicircle");
        platform2.setImageType("Default");
        game.getGround().getPlatformTopList().add(platform2);
    }

    public static void loadLevelRandom(Context context, Game game) {
        int startPlatformWidth = 1000;
        Platform platformStart = new Platform(context, 1, "Default", 0, Game.SCREEN_HEIGHT - 100, startPlatformWidth, 200);
        game.getGround().getPlatformBottomList().add(platformStart);
    }

    // Utility Functions //
    public static void initializePlatforms(Game game) {
        Platform previousPlatformSign = null, previousPlatformArc = null;
        Rect bounds = new Rect();

        for(Platform platform : game.getGround().getSortedPlatformList()) {

            // Next Platform Distance Sign //
            if(platform.getNextPlatformDistance() == -2 || platform.getNextPlatformDistance() == -3) {
                if(previousPlatformSign == null)
                    previousPlatformSign = platform;
                else {

                    // Set Previous Platform Distance Sign Data //
                    int distance = (int) ((platform.getX() - (previousPlatformSign.getX() + previousPlatformSign.getWidth())) / 73.0);
                    previousPlatformSign.setNextPlatformDistance(distance);

                    Game.textPaintWhite73.getTextBounds(String.valueOf(distance), 0, String.valueOf(distance).length(), bounds);
                    int distanceStringWidth = bounds.width();
                    int distanceStringHeight = bounds.height();
                    previousPlatformSign.distanceStringWidth = distanceStringWidth;
                    previousPlatformSign.distanceStringHeight = distanceStringHeight;
                    Game.textPaintWhite36.getTextBounds("ft", 0, "ft".length(), bounds);
                    int suffixStringWidth = bounds.width();
                    previousPlatformSign.suffixStringHeight = bounds.height();

                    int distanceSignMarginSize = (int) (Game.SCREEN_HEIGHT * .0075);
                    previousPlatformSign.setDistanceSignMarginSize(distanceSignMarginSize);
                    int distanceSignWidth = distanceStringWidth + suffixStringWidth + (distanceSignMarginSize * 3);
                    previousPlatformSign.setDistanceSignWidth(distanceSignWidth); // Add One For Space Between # And "ft"
                    int distanceSignHeight = distanceStringHeight + (distanceSignMarginSize * 2);
                    previousPlatformSign.setDistanceSignHeight(distanceSignHeight);
                    previousPlatformSign.setDistanceSignX((int) ((previousPlatformSign.getWidth() - distanceSignWidth) - (Game.SCREEN_WIDTH * .02)));
                    previousPlatformSign.setPostWidth((int) (Game.SCREEN_HEIGHT * .01));
                    int postHeight = (int) (Game.SCREEN_HEIGHT * .025);
                    previousPlatformSign.setPostHeight(postHeight);
                    previousPlatformSign.setDistanceSignY(-distanceSignHeight - postHeight);

                    // Update Previous Platform //
                    previousPlatformSign = platform;
                }

                if(platform.getNextPlatformDistance() == -3)
                    previousPlatformSign = null;
            }

            // Next Platform Arc //
            if(platform.getNextPlatformArcDistance() == -2 || platform.getNextPlatformArcDistance() == -3) {
                if(previousPlatformArc == null)
                    previousPlatformArc = platform;
                else {

                    // Set Previous Platform Arc Data //
                    int distance = platform.getX() - (previousPlatformArc.getX() + previousPlatformArc.getWidth()) + (platform.getWidth() / 2) + (previousPlatformArc.getWidth() / 2);
                    previousPlatformArc.setNextPlatformArcDistance(distance);
                }

                if(platform.getNextPlatformArcDistance() == -3)
                    previousPlatformArc = null;
            }
        }
    }

    public static void updateRandomLevel() {
        int i = 1;

//        if(powerUpList.size() < 5 && (new Random().nextInt(10)) == 0) {
//            int powerUpX = 0;
//            if (powerUpList.size() == 0)
//                powerUpX = SCREEN_WIDTH + new Random().nextInt(250);
//            else
//                powerUpX = powerUpList.lastElement().getX() + (new Random().nextInt(350) + 250);
//            int gamePointY = new Random().nextInt(600) + 400;
//            powerUpList.add(new PowerUp(getContext(), "Point", powerUpX, gamePointY));
//        }
    }

    public static String getName(int selectedLevel) {
        String returnString = "";
        if(selectedLevel == 0)
            returnString = "Level 01 - Introduction";
        else if(selectedLevel == 1)
            returnString = "Level 02";
        else if(selectedLevel == 2)
            returnString = "Level 03";
        else if(selectedLevel == 3)
            returnString = "Level 04";
        else if(selectedLevel == 4)
            returnString = "Level 05";
        else if(selectedLevel == 5)
            returnString = "Level 06";
        else if(selectedLevel == 6)
            returnString = "Level 07";
        else if(selectedLevel == 7)
            returnString = "Level 08";
        else if(selectedLevel == 8)
            returnString = "Level 09";
        else if(selectedLevel == 9)
            returnString = "Level 10";
        else if(selectedLevel == 10)
            returnString = "Random Platforms";
        else if(selectedLevel == 11)
            returnString = "Level 11";
        else if(selectedLevel == 12)
            returnString = "Level 12";
        else if(selectedLevel == 13)
            returnString = "Level 13";
        else if(selectedLevel == 14)
            returnString = "Level 14";
        else if(selectedLevel == 15)
            returnString = "Level 15";
        else if(selectedLevel == 16)
            returnString = "Level 16";
        else if(selectedLevel == 17)
            returnString = "Level 17";
        else if(selectedLevel == 18)
            returnString = "Level 18";
        else if(selectedLevel == 19)
            returnString = "Level 19";
        else if(selectedLevel == 20)
            returnString = "Level 20";
        else if(selectedLevel == 21)
            returnString = "Level 21";

        return returnString;
    }
}
