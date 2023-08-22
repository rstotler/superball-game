package com.example.superball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.superball.gameobject.GameObject;
import com.example.superball.gameobject.Platform;
import com.example.superball.gameobject.Ground;
import com.example.superball.gameobject.Player;
import com.example.superball.gamepanel.GameButton;
import com.example.superball.gamepanel.MainMenu;
import com.example.superball.gamepanel.Overlay;
import com.example.superball.gamepanel.SplashScreen;

import java.util.Iterator;
import java.util.Vector;

// To Do:
// -Breakable (count) /vanishing platforms
// -Move platform movement to subtype
// -Cancel dash(?)
// -Use existing velocity in powerslam
// -Death animation (r-type/megaman xD)
// -armor/powerups?
// -Screen elements - up/down direction
// -Corner hopping edge/style bonus, tie moves together point system(?) xD
// -Progress Menu Area
// -# of bounces per level (lower == better score?)

// Items:
// Increase double jump by one
// Magnetized ball (collects balls in proximity)
// Increase ball collect multiplier
// After-image trail
// Rainbow trail
// Sparkling ball

// Platform Types: Default, Speed Up, Speed Down, Full Speed, Full Stop, Bounce, Wall, Death
// Subtype To-Do: Movement (Move), Breakable (Count), Multiplier, Percentage

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    public static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static final int MAX_DOUBLE_CLICK_TIME = 14;
    private static final double CLICK_DRAG_DISTANCE = 60.0;

    // Game Variables //
    private GameLoop gameLoop;
    private Image image;
    private SplashScreen splashScreen;
    private MainMenu menuMain;
    private Overlay overlay;
    private Player player;
    private Ground ground;
    private Vector<GameObject> gameObjectList = new Vector<GameObject>();

    private int gameState = 4; // 0 - Game Over, 1 - Playing Game, 2 - Initialize Game, 3 - Splash Screen, 4 - Main Menu
    private boolean isPressed = false, isPaused = false;
    private int doubleClickTimer = -1;
    private float lastClickX = -1, lastClickY = -1, backgroundX = 0, gameOverScrollSpeed = 0;

    private int gamePointCount = 0;
    private int gameSeconds = 0;

    public static Paint paintBlack, paintGrey, paintLightGrey, paintDarkGrey, paintDarkBrown, paintBrown, paintRed, paintLightRed, paintMediumRed, paintDarkRed, paintYellow;
    public static TextPaint textPaintWhite36, textPaintBlack36, textPaintWhite73, textPaintWhite73Bold;

    private GameButton labelRestart, btnMainMenu;

    // Constructor & Basic Functions //
    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // Load Paint & TextPaint //
        if(true) {
            textPaintWhite73 = new TextPaint();
            textPaintWhite73.setTextSize(73);
            textPaintWhite73.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
            textPaintWhite73.setColor(ContextCompat.getColor(context, R.color.white));
            textPaintWhite73Bold = new TextPaint();
            textPaintWhite73Bold.setTextSize(73);
            textPaintWhite73Bold.setTypeface(Typeface.create("Arial", Typeface.BOLD));
            textPaintWhite73Bold.setColor(ContextCompat.getColor(context, R.color.white));
            textPaintWhite36 = new TextPaint();
            textPaintWhite36.setColor(ContextCompat.getColor(context, R.color.white));
            textPaintWhite36.setTextSize(36);
            textPaintBlack36 = new TextPaint();
            textPaintBlack36.setColor(ContextCompat.getColor(context, R.color.black));
            textPaintBlack36.setTextSize(36);

            paintBlack = new Paint();
            paintBlack.setColor(ContextCompat.getColor(context, R.color.black));
            paintGrey = new Paint();
            paintGrey.setColor(ContextCompat.getColor(context, R.color.grey));
            paintLightGrey = new Paint();
            paintLightGrey.setColor(ContextCompat.getColor(context, R.color.light_grey));
            paintDarkGrey = new Paint();
            paintDarkGrey.setColor(ContextCompat.getColor(context, R.color.dark_grey));
            paintDarkGrey.setStrokeWidth(10);

            paintDarkBrown = new Paint();
            paintDarkBrown.setColor(ContextCompat.getColor(context, R.color.dark_brown));
            paintBrown = new Paint();
            paintBrown.setColor(ContextCompat.getColor(context, R.color.brown));
            paintRed = new Paint();
            paintRed.setColor(ContextCompat.getColor(context, R.color.red));
            paintLightRed = new Paint();
            paintLightRed.setColor(ContextCompat.getColor(context, R.color.light_red));
            paintMediumRed = new Paint();
            paintMediumRed.setColor(ContextCompat.getColor(context, R.color.medium_red));
            paintDarkRed = new Paint();
            paintDarkRed.setColor(ContextCompat.getColor(context, R.color.dark_red));
            paintYellow = new Paint();
            paintYellow.setColor(ContextCompat.getColor(context, R.color.yellow));
        }

        // Initialize Game, Game Objects //
        gameLoop = new GameLoop(this, surfaceHolder);
        image = new Image(context);
        splashScreen = new SplashScreen(context);
        menuMain = new MainMenu(context);
        overlay = new Overlay();
        player = new Player();
        ground = new Ground(context);

        // Load Background, Buttons, Etc //
        labelRestart = new GameButton(-1, 1000, 350, 130, "Restart", paintLightRed, textPaintWhite73Bold);
        labelRestart.setBold(true);
        labelRestart.setHideBackground(true);

        btnMainMenu = new GameButton(SCREEN_WIDTH - 170, 20, 150, 150, "", paintLightRed, null);
        btnMainMenu.setButtonImage(context, R.drawable.menu_back_arrow, -1, -1);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle Touch Event Actions //
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;

                // Skip Splash Screen //
                if(gameState == 3) {
                    gameState = 4;
                }

                // Start New Game //
                else if(gameState == 0) {
                    if(rectRectCollide(btnMainMenu.getX(), btnMainMenu.getY(), btnMainMenu.getWidth(), btnMainMenu.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1))
                        gameState = 4;
                    else
                        gameState = 2;
                }

                // Playing Game //
                else if(gameState == 1 && player.getInSpeedGate() == false) {

                    // Pause/Scroll (Debug) //
                    if(rectRectCollide(0, 0, 200, 200, (int) event.getX(), (int) event.getY(), 1, 1)) {
                        if(isPaused == true)
                            isPaused = false;
                        else
                            isPaused = true;
                    }
                    else if(rectRectCollide(Game.SCREEN_WIDTH - 200, 0, 200, 200, (int) event.getX(), (int) event.getY(), 1, 1)) {
                        if(isPaused == true) {
                            for(int i = 0; i < 20; i++) {
                                ground.update(getContext(), menuMain, player);
                                for(GameObject gameObject : gameObjectList)
                                    gameObject.update(ground.getScrollSpeed());
                            }
                        }
                    }

                    // Falling - Normal Slam/Double Jump //
                    else if(player.getBounceDir() == 1 && isPaused == false) {
                        player.setSlamCheck(true);
                        player.setLongSlamCheck(true);

                        // Double Click //
                        if(doubleClickTimer == -1)
                            doubleClickTimer = 0;
                        else if(doubleClickTimer > MAX_DOUBLE_CLICK_TIME)
                            doubleClickTimer = 0;
                        else {
                            player.doubleJump();
                        }
                    }

                    // Rising - Air Brake/Power Slam //
                    else if(player.getBounceDir() == -1 && isPaused == false) {
                        player.setAirBrakeCheck(true);

                        // Double Click //
                        if(doubleClickTimer == -1)
                            doubleClickTimer = 0;
                        else if(doubleClickTimer > MAX_DOUBLE_CLICK_TIME)
                            doubleClickTimer = 0;
                        else {
                            player.powerSlam();
                        }
                    }
                }

                // Main Menu //
                else if(gameState == 4) {
                    String returnData = menuMain.onClick(getContext(), event, player);
                    if(returnData == "Start Game")
                        gameState = 2;
                }

                return true;

            case MotionEvent.ACTION_UP:
                isPressed = false;
                lastClickX = -1;
                lastClickY = -1;

                // Game - Release Slam/Air-Brake //
                if(gameState == 1) {
                    if(player.getSlamCheck())
                        player.setSlamCheck(false);
                    else if(player.getAirBrakeCheck())
                        player.setAirBrakeCheck(false);
                }

                // Main Menu - Release Click //
                else if(gameState == 4)
                    menuMain.onClickUp(event, player);

                return true;

            case MotionEvent.ACTION_MOVE:

                // Game - Speed Dash //
                if(gameState == 1 && isPaused == false) {
                    if(lastClickX != -1 && lastClickY != -1) {
                        double clickDragDistance = Math.sqrt(Math.pow(lastClickX - event.getX(), 2) + Math.pow(lastClickY - event.getY(), 2));
                        if(clickDragDistance >= CLICK_DRAG_DISTANCE) {
                            player.dash();
                        }
                    }
                }
                lastClickX = event.getX();
                lastClickY = event.getY();

                return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {

        // Game Over Screen //
        if(gameState == 0) {
            backgroundX -= gameOverScrollSpeed;
            if(backgroundX <= -Image.background.getWidth())
                backgroundX = 0;
        }

        // Play Game State //
        if(gameState == 1 && isPaused == false) {

            // Scroll Background //
            backgroundX -= ((ground.getScrollSpeedLevel() + 1) / 4.5) + (player.getDashVelocity() / 20.0);
            if(backgroundX <= -Image.background.getWidth())
                backgroundX = 0;

            // Player //
            String playerUpdateString = player.update(ground);
            if(playerUpdateString == "End Game") {
                gameState = 0;
                gameOverScrollSpeed = (float) (((ground.getScrollSpeedLevel() + 1) / 4.5) + (player.getDashVelocity() / 20.0));
                Log.d("Debug123",String.valueOf(gameOverScrollSpeed));
            }

            // Scroll Ground //
            ground.update(getContext(), menuMain, player);

            // Update GameObjects //
            Iterator<GameObject> iteratorGameObject = gameObjectList.iterator();
            while(iteratorGameObject.hasNext()) {
                GameObject gameObject = iteratorGameObject.next();
                gameObject.update(ground.getScrollSpeed() + (int) player.getDashVelocity());
                if(gameObject.getX() + gameObject.getWidth() < 0)
                    iteratorGameObject.remove();
                else if((gameObject.getCollideShape() == "Rect" && rectCircleCollide(gameObject.getX(), gameObject.getY(), gameObject.getWidth(), gameObject.getHeight(), player.getX(), player.getY(), player.getRadius()))
                        || (gameObject.getCollideShape() == "Circle" && circleCircleCollide(player.getX(), player.getY(), gameObject.getX(), gameObject.getY(), player.getRadius(), gameObject.getRadius()))) {

                    // Game Point //
                    if(gameObject.getType() == "Point") {
                        iteratorGameObject.remove();
                        gamePointCount++;
                    }

                    // Bomb //
                    else if(gameObject.getType() == "Bomb") {
                        iteratorGameObject.remove();
                        gameState = 0;
                    }
                }
            }

            // Collision Detection //
            collisionDetection();

            // Update Random Level //
            if(menuMain.getSelectedLevel() == 21)
                Level.updateRandomLevel();

            // Double Click Timer //
            if(doubleClickTimer != -1)
                doubleClickTimer++;

            // Update Game Time //
            gameSeconds++;
        }

        // Initialize Game //
        else if(gameState == 2) {
            startNewGame();
        }

        // Splash Screen //
        else if(gameState == 3) {
            String returnString = splashScreen.update();
            if(returnString == "Skip Splash Screen")
                gameState = 4;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Splash Screen //
        if(gameState == 3)
            splashScreen.draw(canvas);

        // Background //
        if(gameState != 3 && Image.background != null) {
            canvas.drawBitmap(Image.background, backgroundX, 0, null);
            if(backgroundX + Image.background.getWidth() < Game.SCREEN_WIDTH)
                canvas.drawBitmap(Image.background, backgroundX + Image.background.getWidth(), 0, null);
        }

        // Game Objects //
        if(gameState == 1) {
            ground.draw(canvas);
            for(GameObject gameObject : gameObjectList)
                gameObject.draw(canvas);
            player.draw(canvas);
            overlay.draw(canvas, this);
        }

        // New Game Screen //
        else if(gameState == 0) {
            labelRestart.draw(canvas, false);
            btnMainMenu.draw(canvas, false);
        }

        // Main Menu //
        else if(gameState == 4)
            menuMain.draw(getContext(), canvas, this, player);

        // Draw Performance //
        if(gameState != 3 && gameState != 4 && false)
            drawPerformance(canvas, String.valueOf(isPressed));
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Debug-Game.java", "surfaceCreated()");
        if(gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Debug-Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Debug-Game.java", "surfaceDestroyed()");
    }

    public void pause() {
        gameLoop.stopLoop();
    }

    // User-Defined Functions //
    public void collisionDetection() {
        ground.platformCollisionDetection(this, player, ground.getPlatformTopList());
        ground.platformCollisionDetection(this, player, ground.getPlatformBottomList());

        // GameObject Collision Detection //
        boolean inSpeedGate = false;
        for(GameObject gameObject : gameObjectList) {
            if(gameObject.getX() + gameObject.getMovementX() > player.getX() + player.getRadius())
                break;

            // Checkpoint //
            if(gameObject.getType() == "Checkpoint" && rectCircleCollide(gameObject.getX(), -5000, gameObject.getWidth(), 10000, player.getX(), player.getY(), player.getRadius())) {
                int a = 1;
            }

            else if(rectCircleCollide(gameObject.getX() + gameObject.getMovementX(), gameObject.getY() + gameObject.getMovementY(), gameObject.getWidth(), gameObject.getHeight(), player.getX(), player.getY(), player.getRadius())) {

                // Speed Gate //
                if(gameObject.getType() == "Speed Gate") {
                    if(ground.getScrollSpeedLevel() < gameObject.getSpeedGateLevel()) {
                        gameState = 0;
                        break;
                    }
                    else {
                        inSpeedGate = true;
                        player.setVelocity(0.0);
                        player.setBounceDir(1);
                        player.setSpeedGateCheck(true);
                        if(player.getDashVelocity() < 30.0)
                            player.setDashVelocity(player.getDashVelocity() + 1.0);
                    }
                }
            }
        }

        // Speed Gate Check //
        if(inSpeedGate != player.getInSpeedGate())
            player.setInSpeedGate(inSpeedGate);
    }

    public void startNewGame() {
        lastClickX = -1;
        lastClickY = -1;
        backgroundX = 0;

        player.reset(getContext(), menuMain);
        ground.reset();

        gameState = 1;
        gameSeconds = 0;
        Iterator<GameObject> iteratorGameObject = gameObjectList.iterator();
        while(iteratorGameObject.hasNext()) {
            iteratorGameObject.next();
            iteratorGameObject.remove();
        }

        // Ball Trail Images //
        Bitmap ballTrail = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ball_trail_rainbow);
        Image.ballTrail = new Vector<Bitmap>();
        int ballTrailWidth = ballTrail.getWidth() * 4;
        for(int i = 0; i < 4; i++) {
            Bitmap ballTrailImage = Bitmap.createScaledBitmap(ballTrail, ballTrailWidth * (i + 1), player.getRadius() * 2, true);
            Image.ballTrail.add(ballTrailImage);
        }

        // Load Level //
        Level.loadLevel(getContext(), this, menuMain.getSelectedLevel());

        /// Debug - Start Level At Target Time ///
        //updateLevelPosition(1500, -1); // Square Steps
        //updateLevelPosition(2000, -1); // Speed Platforms
        //updateLevelPosition(3470, -1); // Speed Gate
        //updateLevelPosition(3700, -1); // Checkpoint
        //updateLevelPosition(3900, -1); // Walls Start
        //updateLevelPosition(4650, 950); // Walls End
        //updateLevelPosition(4800, 400); // Area 3 Start
    }

    public static boolean rectRectCollide(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
        if(x1 + width1 >= x2 && x1 <= x2 + width2
        && y1 + height1 >= y2 && y1 <= y2 + height2) {
            return true;
        }

        return false;
    }

    public static boolean rectCircleCollide(int rectX, int rectY, int rectWidth, int rectHeight, int circleX, int circleY, int circleRadius) {
        int testX = circleX;
        int testY = circleY;

        if(circleX < rectX)
            testX = rectX;
        else if(circleX > rectX + rectWidth)
            testX = rectX + rectWidth;
        if(circleY < rectY)
            testY = rectY;
        else if(circleY > rectY + rectHeight)
            testY = rectY + rectHeight;

        int distX = circleX - testX;
        int distY = circleY - testY;
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        if(distance <= circleRadius)
            return true;

        return false;
    }

    public static boolean circleCircleCollide(int x1, int y1, int x2, int y2, int radius1, int radius2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        double dr = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        if(dr < radius1 + radius2)
            return true;
        return false;
    }

    public void drawPerformance(Canvas canvas, String stringClick) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        if(averageUPS.length() > 5)
            averageUPS = averageUPS.substring(0, 5);
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        if(averageFPS.length() > 5)
            averageFPS = averageFPS.substring(0, 5);

        canvas.drawText("UPS: " + averageUPS + ", FPS: " + averageFPS, 10, 40, textPaintWhite36);
        canvas.drawText("Velocity: (" + player.getBounceDir() + ") " + player.getVelocity(), 10, 84, textPaintWhite36);
        canvas.drawText("Click: " + stringClick + ", Slam: " + player.getSlamCheck() + ", Brake: " + player.getAirBrakeCheck(), 10, 128, textPaintWhite36);
        canvas.drawText("Platforms: " + ground.getPlatformList().size() + " (" + ground.getScrollSpeedLevel() + ")", 10, 172, textPaintWhite36);
        canvas.drawText("Points: " + getGamePointCount(), 10, 216, textPaintWhite36);
    }

    public void updateLevelPosition(int targetSeconds, int yLoc) {
        for(Platform platform : ground.getPlatformList())
            platform.update(targetSeconds * ground.DEFAULT_SCROLL_SPEED);
        for(GameObject gameObject : gameObjectList)
            gameObject.update(targetSeconds * ground.DEFAULT_SCROLL_SPEED);

        if(yLoc != -1)
            player.setY(yLoc);

        gameSeconds = targetSeconds;
    }

    //  Getters & Setters //
    public Player getPlayer() { return player; }
    public Ground getGround() { return ground; }
    public Vector<GameObject> getGameObjectList() { return gameObjectList; }
    public void setGameState(int gameState) { this.gameState = gameState; }
    public void setDoubleClickTimer(int doubleClickTimer) { this.doubleClickTimer = doubleClickTimer; }
    public float getLastClickX() { return lastClickX; }
    public float getLastClickY() { return lastClickY; }
    public int getGamePointCount() { return gamePointCount; }
    public int getGameSeconds() { return gameSeconds; }
}
