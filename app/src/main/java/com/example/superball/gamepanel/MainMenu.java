package com.example.superball.gamepanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.superball.Game;
import com.example.superball.Image;
import com.example.superball.Level;
import com.example.superball.R;
import com.example.superball.gameobject.Player;

import java.util.Collections;
import java.util.Vector;

public class MainMenu {
    private static int PLAYER_AREA_Y_TOP = (int) (Game.SCREEN_HEIGHT * .35);
    public static int BUFFER_SIZE = (int) (Game.SCREEN_WIDTH * .01);
    public static int TEXT_HEIGHT;

    private Vector<GameButton> levelSelectButtonList = new Vector<GameButton>();
    private int selectedLevel = 0;
    private static int selectedBall = 0;

    private int ballListTitleLength, abilityListTitleLength, gearTitleLength, itemListTitleLength, detailTitleLength;
    private int itemDetailX, itemDetailY, itemDetailWidth, itemDetailHeight;
    private int selectedBallDetail = -1, selectedAbilityDetail = -1, selectedGear = -1, selectedItem = -1;

    private GameButton btnGear1, btnGear2;
    private Vector<GameButton> itemButtonList;
    private int imgBallBackgroundX, imgBallBackgroundY;
    public float[][] ballBackgroundLineList;

    private GameButton btnStart;

    private Bitmap imgBall;

    public Paint paintSelectedAlpha;
    public static TextPaint textPaintLight, textPaintDark;

    // Constructor & Basic Functions //
    public MainMenu(Context context) {
        loadLevelSelectButtons();

        // Debug Start Button //
        int btnStartSize = 150;
        int btnStartX = (int) (Game.SCREEN_WIDTH * .05);
        int btnStartY = Game.SCREEN_HEIGHT - btnStartSize - (int) (Game.SCREEN_WIDTH * .05);
        btnStart = new GameButton(btnStartX, btnStartY, btnStartSize, btnStartSize, "", Game.paintDarkGrey, null);

        // Selected Ball Background //
        imgBallBackgroundX = (Game.SCREEN_WIDTH / 2) - (Image.mainMenuBallBackground.getWidth() / 2);
        imgBallBackgroundY = (int) (Game.SCREEN_HEIGHT * .42);
        int ballResource = Image.getBallResource(selectedBall);
        if(ballResource != -1)
            imgBall = BitmapFactory.decodeResource(context.getResources(), ballResource);

        // Lines Behind Selected Ball Background //
        if(true) {
            float[] ballBackgroundLineList1 = new float[4];
            ballBackgroundLineList1[0] = (Game.SCREEN_WIDTH / 2);
            ballBackgroundLineList1[1] = imgBallBackgroundY + (Image.mainMenuBallBackground.getHeight() / 2) + ((int) (Game.SCREEN_WIDTH * .018));
            ballBackgroundLineList1[2] = ballBackgroundLineList1[0] + (int) (Game.SCREEN_WIDTH * .25);
            ballBackgroundLineList1[3] = ballBackgroundLineList1[1];

            float[] ballBackgroundLineList2 = new float[4];
            ballBackgroundLineList2[0] = ballBackgroundLineList1[2] - (int) (Game.SCREEN_WIDTH * .002);
            ballBackgroundLineList2[1] = ballBackgroundLineList1[3] + (int) (Game.SCREEN_WIDTH * .001);
            ballBackgroundLineList2[2] = ballBackgroundLineList2[0] + (int) (Game.SCREEN_WIDTH * .05);
            ballBackgroundLineList2[3] = ballBackgroundLineList2[1] - (int) (Game.SCREEN_WIDTH * .075);

            float[] ballBackgroundLineList3 = new float[4];
            ballBackgroundLineList3[0] = (Game.SCREEN_WIDTH / 2);
            ballBackgroundLineList3[1] = imgBallBackgroundY + (Image.mainMenuBallBackground.getHeight() / 2);
            ballBackgroundLineList3[2] = ballBackgroundLineList1[0] - (int) (Game.SCREEN_WIDTH * .1);
            ballBackgroundLineList3[3] = ballBackgroundLineList1[1] - (int) (Game.SCREEN_WIDTH * .118);

            float[] ballBackgroundLineList4 = new float[4];
            ballBackgroundLineList4[0] = ballBackgroundLineList3[2] + (int) (Game.SCREEN_WIDTH * .002);
            ballBackgroundLineList4[1] = ballBackgroundLineList3[3] + (int) (Game.SCREEN_WIDTH * .001);
            ballBackgroundLineList4[2] = ballBackgroundLineList4[0] - (int) (Game.SCREEN_WIDTH * .1);
            ballBackgroundLineList4[3] = ballBackgroundLineList4[1];

            float[] ballBackgroundLineList5 = new float[4];
            ballBackgroundLineList5[0] = (Game.SCREEN_WIDTH / 2);
            ballBackgroundLineList5[1] = imgBallBackgroundY + Image.mainMenuBallBackground.getHeight() - (int) (Game.SCREEN_WIDTH * .01);
            ballBackgroundLineList5[2] = ballBackgroundLineList5[0] - (int) (Game.SCREEN_WIDTH * .04);
            ballBackgroundLineList5[3] = ballBackgroundLineList5[1] + (int) (Game.SCREEN_WIDTH * .04);

            float[] ballBackgroundLineList6 = new float[4];
            ballBackgroundLineList6[0] = ballBackgroundLineList5[2] + (int) (Game.SCREEN_WIDTH * .002);
            ballBackgroundLineList6[1] = ballBackgroundLineList5[3] + (int) (Game.SCREEN_WIDTH * .001);
            ballBackgroundLineList6[2] = ballBackgroundLineList6[0] - (int) (Game.SCREEN_WIDTH * .2);
            ballBackgroundLineList6[3] = ballBackgroundLineList6[1];

            ballBackgroundLineList = new float[][] {ballBackgroundLineList1, ballBackgroundLineList2, ballBackgroundLineList3, ballBackgroundLineList4, ballBackgroundLineList5, ballBackgroundLineList6};
        }

        // Item Buttons //
        int btnGearSize = (int) (Game.SCREEN_WIDTH * .11);
        int btnGearX = (int) (Game.SCREEN_WIDTH * .095);
        int btnGearY = (int) ballBackgroundLineList[5][3] - (btnGearSize / 2);
        btnGear1 = new GameButton(btnGearX, btnGearY, btnGearSize, btnGearSize, null, Game.paintDarkGrey, null);
        btnGearX += btnGearSize;
        btnGear2 = new GameButton(btnGearX, btnGearY, btnGearSize, btnGearSize, null, Game.paintDarkGrey, null);

        // Item List Buttons //
        itemButtonList = new Vector<GameButton>();
        btnGearSize = (int) (Game.SCREEN_WIDTH * .11);
        btnGearX = (int) (Game.SCREEN_WIDTH * .06);
        btnGearY = (int) (Game.SCREEN_HEIGHT * .625);
        for(int i = 0; i < 6; i++) {
            GameButton itemListButton = new GameButton(btnGearX, btnGearY, btnGearSize, btnGearSize, "", Game.paintDarkGrey, null);
            itemButtonList.add(itemListButton);

            btnGearX += btnGearSize;
            if(i == 2) {
                btnGearX = (int) (Game.SCREEN_WIDTH * .06);
                btnGearY += btnGearSize;
            }
        }

        // Item Detail Pane //
        itemDetailWidth = (int) (Game.SCREEN_WIDTH * .45);
        itemDetailHeight = btnGearSize * 2;
        itemDetailX = (int) (Game.SCREEN_WIDTH - itemDetailWidth - (Game.SCREEN_WIDTH * .06));
        itemDetailY = (int) (Game.SCREEN_HEIGHT * .625);

        // Initialize Paint & TextPaint //
        paintSelectedAlpha = new Paint();
        paintSelectedAlpha.setAlpha(100);

        textPaintLight = new TextPaint();
        textPaintLight.setColor(ContextCompat.getColor(context, R.color.light_grey));
        textPaintLight.setTextSize(40);
        textPaintLight.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        textPaintDark = new TextPaint();
        textPaintDark.setColor(ContextCompat.getColor(context, R.color.dark_grey));
        textPaintDark.setTextSize(40);
        textPaintDark.setTypeface(Typeface.create("Arial", Typeface.BOLD));

        // Get Label Width & Height //
        Rect bounds = new Rect();
        textPaintLight.getTextBounds("BALL", 0, "BALL".length(), bounds);
        TEXT_HEIGHT = bounds.height();
        ballListTitleLength = bounds.width();
        textPaintLight.getTextBounds("ABILITY", 0, "ABILITY".length(), bounds);
        abilityListTitleLength = bounds.width();
        textPaintLight.getTextBounds("GEAR", 0, "GEAR".length(), bounds);
        gearTitleLength = bounds.width();
        textPaintLight.getTextBounds("ITEMS", 0, "ITEMS".length(), bounds);
        itemListTitleLength = bounds.width();
        textPaintLight.getTextBounds("DETAIL", 0, "DETAIL".length(), bounds);
        detailTitleLength = bounds.width();
    }

    public String onClick(Context context, MotionEvent event, Player player) {

        // Change Level //
        for(GameButton gameButton : levelSelectButtonList) {
            if(Game.rectRectCollide(gameButton.getX(), gameButton.getY(), gameButton.getWidth(), gameButton.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                selectedLevel = gameButton.getButtonNum();
                return "";
            }
        }

        // Ball List //
        for(int i = 0; i < player.getBallList().size(); i++) {
            MenuListObject menuListObject = player.getBallList().get(i);
            if(Game.rectRectCollide(menuListObject.getX(), menuListObject.getY(), menuListObject.getWidth(), menuListObject.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                for(MenuListObject menuListObjectReset : player.getBallList())
                    menuListObjectReset.setIsActive(false);
                menuListObject.setIsActive(true);
                selectedBall = i;
                selectedBallDetail = i;
                int ballResource = Image.getBallResource(selectedBall);
                if(i == -1) { imgBall = null; }
                else { imgBall = BitmapFactory.decodeResource(context.getResources(), ballResource); }
                return "";
            }
        }

        // Ability List //
        for(int i = 0; i < player.getAbilityList().size(); i++) {
            MenuListObject menuListObject = player.getAbilityList().get(i);
            if(Game.rectRectCollide(menuListObject.getX(), menuListObject.getY(), menuListObject.getWidth(), menuListObject.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                selectedAbilityDetail = i;
                return "";
            }
        }

        // Gear Slots //
        if(Game.rectRectCollide(btnGear1.getX(), btnGear1.getY(), btnGear1.getWidth(), btnGear1.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
            if(player.getGear1() != -1) {
                selectedGear = player.getGear1();
                return "";
            }
        }
        if(Game.rectRectCollide(btnGear2.getX(), btnGear2.getY(), btnGear2.getWidth(), btnGear2.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
            if(player.getGear2() != -1) {
                selectedGear = player.getGear2();
                return "";
            }
        }

        // Item List //
        for(int i = 0; i < itemButtonList.size(); i++) {
            GameButton itemButton = itemButtonList.get(i);
            if(Game.rectRectCollide(itemButton.getX(), itemButton.getY(), itemButton.getWidth(), itemButton.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                if(player.getItemList().get(i) != -1) {
                    selectedItem = player.getItemList().get(i);
                    return "";
                }
            }
        }

        // Start Game Button //
        if(Game.rectRectCollide(btnStart.getX(), btnStart.getY(), btnStart.getWidth(), btnStart.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1))
            return "Start Game";

        return "";
    }

    public void onClickUp(MotionEvent event, Player player) {
        int targetSlot = -1;
        int oldSlotItem = -1;

        // Move Gear //
        if(selectedGear != -1) {

            // Swap Gear Slots //
            boolean sameSlotCheck = false;
            if(Game.rectRectCollide(btnGear1.getX(), btnGear1.getY(), btnGear1.getWidth(), btnGear1.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                if(player.getGear1() == selectedGear)
                    sameSlotCheck = true;
                else {
                    oldSlotItem = player.getGear1();
                    player.setGear2(oldSlotItem);
                    player.setGear1(selectedGear);
                }
            }
            else if(Game.rectRectCollide(btnGear2.getX(), btnGear2.getY(), btnGear2.getWidth(), btnGear2.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                if(player.getGear2() == selectedGear)
                    sameSlotCheck = true;
                else {
                    oldSlotItem = player.getGear2();
                    player.setGear1(oldSlotItem);
                    player.setGear2(selectedGear);
                }
            }

            // Return Gear To Item List //
            else if(sameSlotCheck == false) {

                // Find Next Empty Slot //
                if(Game.rectRectCollide(itemButtonList.get(0).getX(), itemButtonList.get(0).getY(), itemButtonList.get(0).getWidth() * 3, itemButtonList.get(0).getHeight() * 2, (int) event.getX(), (int) event.getY(), 1, 1)) {
                    for(int i = 0; i < player.getItemList().size(); i++) {
                        if(player.getItemList().get(i) == -1) {
                            targetSlot = i;
                            break;
                        }
                    }

                    // Get Target Slot If No Empty Slots Available //
                    if(targetSlot == -1) {
                        for(int i = 0; i < itemButtonList.size(); i++) {
                            GameButton itemButton = itemButtonList.get(i);
                            if(Game.rectRectCollide(itemButton.getX(), itemButton.getY(), itemButton.getWidth(), itemButton.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                                targetSlot = i;
                                break;
                            }
                        }
                    }
                }

                // Drop Outside Of Gear & Items List //
                else {
                    for(int i = 0; i < player.getItemList().size(); i++) {
                        if(player.getItemList().get(i) == -1) {
                            targetSlot = i;
                            break;
                        }
                    }
                }
            }

            // Set Gear & Item Slots //
            if(targetSlot != -1) {
                if(targetSlot != -1) {
                    oldSlotItem = player.getItemList().get(targetSlot);
                    if(player.getGear1() == selectedGear)
                        player.setGear1(oldSlotItem);
                    else if(player.getGear2() == selectedGear)
                        player.setGear2(oldSlotItem);
                    player.getItemList().set(targetSlot, selectedGear);
                }
            }
        }

        // Move Item //
        else if(selectedItem != -1) {

            // Get Data //
            int selectedItemIndex = -1;
            int targetItemIndex = -1;
            int targetGearIndex = -1;
            for(int i = 0; i < player.getItemList().size(); i++) {
                GameButton itemButton = itemButtonList.get(i);
                if(Game.rectRectCollide(itemButton.getX(), itemButton.getY(), itemButton.getWidth(), itemButton.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1)) {
                    if(player.getItemList().get(i) != -1)
                        targetItemIndex = i;
                }
                if(player.getItemList().get(i) == selectedItem)
                    selectedItemIndex = i;
            }
            if(Game.rectRectCollide(btnGear1.getX(), btnGear1.getY(), btnGear1.getWidth(), btnGear1.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1))
                targetGearIndex = 1;
            else if(Game.rectRectCollide(btnGear2.getX(), btnGear2.getY(), btnGear2.getWidth(), btnGear2.getHeight(), (int) event.getX(), (int) event.getY(), 1, 1))
                targetGearIndex = 2;

            // Swap Item Slots //
            if(selectedItemIndex != -1 && targetItemIndex != -1 && selectedItemIndex != targetItemIndex) {
                int targetItemNum = player.getItemList().get(targetItemIndex);
                player.getItemList().set(selectedItemIndex, targetItemNum);
                player.getItemList().set(targetItemIndex, selectedItem);
            }

            // Move Into Gear Slot //
            else if(selectedItemIndex != -1 && targetGearIndex != -1) {
                if(targetGearIndex == 1) {
                    oldSlotItem = player.getGear1();
                    player.setGear1(selectedItem);
                }
                else if(targetGearIndex == 2) {
                    oldSlotItem = player.getGear2();
                    player.setGear2(selectedItem);
                }
                player.getItemList().set(selectedItemIndex, oldSlotItem);

                // Clear Inventory Empty Spaces //
                int emptyIndex = -1;
                for(int i = 0; i < player.getItemList().size(); i++) {
                    if(player.getItemList().get(i) == -1)
                        emptyIndex = i;
                    if(emptyIndex != -1 && i != player.getItemList().size() - 1) {
                        int nextItemNum = player.getItemList().get(i + 1);
                        player.getItemList().set(i, nextItemNum);
                    }
                    if(emptyIndex != -1 && i == player.getItemList().size() - 1)
                        player.getItemList().set(i, -1);
                }

            }
        }

        selectedBallDetail = -1;
        selectedAbilityDetail = -1;
        selectedGear = -1;
        selectedItem = -1;
    }

    public void draw(Context context, Canvas canvas, Game game, Player player) {
        canvas.drawBitmap(Image.mainMenuBackground, 0, 0, null);

        // Level Select Area //
        int playerCompleteCircleX = Game.SCREEN_WIDTH - (int) (Game.SCREEN_WIDTH * .27);
        for(int i = 0; i < player.getBallList().size(); i++) {
            canvas.drawCircle(playerCompleteCircleX, (int) (Game.SCREEN_HEIGHT * .192), 24, Game.paintDarkGrey);
            playerCompleteCircleX += (int) (Game.SCREEN_WIDTH * .05);
        }
        canvas.drawText(Level.getName(selectedLevel), (int) (Game.SCREEN_WIDTH * .05), (int) (Game.SCREEN_HEIGHT * .2), Game.textPaintWhite36);
        for(GameButton gameButton : levelSelectButtonList) {
            boolean selectedButtonCheck = gameButton.getButtonNum() == selectedLevel;
            gameButton.draw(canvas, selectedButtonCheck);
        }

        // Player Area //
        if(true) {
            for(float[] lineList : ballBackgroundLineList)
                canvas.drawLines(lineList, Game.paintDarkGrey);

            canvas.drawBitmap(Image.mainMenuBallBackground, imgBallBackgroundX, imgBallBackgroundY, null);
            if(imgBall == null) {
                int selectedBallRadius = Player.getBallRadius(selectedBall);
                canvas.drawCircle((Game.SCREEN_WIDTH / 2), imgBallBackgroundY + (Image.mainMenuBallBackground.getHeight() / 2), selectedBallRadius, Game.paintDarkRed);
            }
            else
                canvas.drawBitmap(imgBall, (Game.SCREEN_WIDTH / 2) - (imgBall.getWidth() / 2), imgBallBackgroundY + (Image.mainMenuBallBackground.getHeight() / 2) - (imgBall.getHeight() / 2), null);

            // Ball List //
            outlineMenuList(canvas, player.getBallList(), ballListTitleLength);
            for(MenuListObject menuListObject : player.getBallList()) {
                if(menuListObject.getIsActive()) { menuListObject.draw(canvas, textPaintDark); }
                else { menuListObject.draw(canvas, textPaintLight); }
            }

            // Ability List //
            outlineMenuList(canvas, player.getAbilityList(), abilityListTitleLength);
            for(MenuListObject menuListObject : player.getAbilityList()) {
                if(menuListObject.getIsActive()) { menuListObject.draw(canvas, textPaintDark); }
                else { menuListObject.draw(canvas, textPaintLight); }
            }

            // Gear Buttons //
            outlineRect(canvas, btnGear1.getX(), btnGear1.getY(), btnGear1.getWidth() * 2, btnGear1.getHeight(), "GEAR", gearTitleLength);
            btnGear1.draw(canvas, false);
            if(player.getGear1() != -1 && Image.getItemResource(player.getGear1()) != -1) {
                Bitmap imgGear1Item = BitmapFactory.decodeResource(context.getResources(), Image.getItemResource(player.getGear1()));
                canvas.drawBitmap(imgGear1Item, btnGear1.getX() + (btnGear1.getWidth() / 2) - (imgGear1Item.getWidth() / 2), btnGear1.getY() + (btnGear1.getHeight() / 2) - (imgGear1Item.getHeight() / 2), null);
            }
            btnGear2.draw(canvas, false);
            if(player.getGear2() != -1 && Image.getItemResource(player.getGear2()) != -1) {
                Bitmap imgGear2Item = BitmapFactory.decodeResource(context.getResources(), Image.getItemResource(player.getGear2()));
                canvas.drawBitmap(imgGear2Item, btnGear2.getX() + (btnGear2.getWidth() / 2) - (imgGear2Item.getWidth() / 2), btnGear1.getY() + (btnGear1.getHeight() / 2) - (imgGear2Item.getHeight() / 2), null);
            }
        }

        // Item List Area //
        outlineRect(canvas, itemButtonList.get(0).getX(), itemButtonList.get(0).getY(), itemButtonList.get(0).getWidth() * 3, itemButtonList.get(0).getHeight() * 2, "ITEMS", itemListTitleLength);
        for(int i = 0; i < itemButtonList.size(); i++) {
            GameButton gameButton = itemButtonList.get(i);
            gameButton.draw(canvas, false);
            int targetItemNum = player.getItemList().get(i);
            if(targetItemNum != -1 && Image.getItemResource(targetItemNum) != -1) {
                Bitmap imgItemListItem = BitmapFactory.decodeResource(context.getResources(), Image.getItemResource(targetItemNum));
                canvas.drawBitmap(imgItemListItem, gameButton.getX() + (gameButton.getWidth() / 2) - (imgItemListItem.getWidth() / 2), gameButton.getY() + (gameButton.getHeight() / 2) - (imgItemListItem.getHeight() / 2), null);
            }
        }

        // Item Detail Area //
        outlineRect(canvas, itemDetailX, itemDetailY, itemDetailWidth, itemDetailHeight, "DETAIL", detailTitleLength);
        canvas.drawRect(itemDetailX, itemDetailY, itemDetailX + itemDetailWidth, itemDetailY + itemDetailHeight, Game.paintGrey);

        if(selectedBallDetail != -1 || selectedAbilityDetail != -1 || selectedGear != -1 || selectedItem != -1) {
            String detailStringLabel = "";

            if(selectedBallDetail != -1) {
                detailStringLabel = "Ball 0" + String.valueOf(selectedBallDetail);
            }

            else if(selectedAbilityDetail != -1) {
                detailStringLabel = "Ability 0" + String.valueOf(selectedAbilityDetail);
            }

            else {
                int detailTarget = -1;
                if(selectedGear != -1)
                    detailTarget = selectedGear;
                else if(selectedItem != -1)
                    detailTarget = selectedItem;

                detailStringLabel = "Item 0" + String.valueOf(detailTarget);
            }

            // Draw Label //
            if(detailStringLabel.equals("") == false) {
                Rect bounds = new Rect();
                textPaintDark.getTextBounds(detailStringLabel, 0, detailStringLabel.length(), bounds);
                int detailStringWidth = bounds.width();
                int detailStringHeight = bounds.height();
                int detailStringX = itemDetailX + (itemDetailWidth / 2) - (detailStringWidth / 2);
                int detailStringY = itemDetailY + (itemDetailHeight / 2) - (detailStringHeight / 2) + detailStringHeight;
                canvas.drawText(detailStringLabel, detailStringX, detailStringY, textPaintDark);
            }
        }

        // Debug Start Button //
        btnStart.draw(canvas, false);

        // Selected Gear/Item //
        if(selectedGear != -1 || selectedItem != -1) {
            Bitmap imgSelectedItem = null;
            if(selectedGear != -1 && Image.getItemResource(selectedGear) != -1)
                imgSelectedItem = BitmapFactory.decodeResource(context.getResources(), Image.getItemResource(selectedGear));
            else if(selectedItem != -1 && Image.getItemResource(selectedItem) != -1)
                imgSelectedItem = BitmapFactory.decodeResource(context.getResources(), Image.getItemResource(selectedItem));

            if(imgSelectedItem != null)
                canvas.drawBitmap(imgSelectedItem, game.getLastClickX() - (imgSelectedItem.getWidth() / 2), game.getLastClickY() - (imgSelectedItem.getHeight() / 2), paintSelectedAlpha);
        }
    }

    // User-Defined Functions //
    public void outlineMenuList(Canvas canvas, Vector<MenuListObject> targetList, int listTitleLength) {

        // Outer Square //
        int x = targetList.get(0).getX() - (int) (Game.SCREEN_WIDTH * .015);
        int y = targetList.get(0).getY() - (int) (Game.SCREEN_WIDTH * .015);
        int width = targetList.get(0).getWidth() + (int) (Game.SCREEN_WIDTH * .03);
        int height = (targetList.size() * targetList.get(0).getHeight()) + (int) (Game.SCREEN_WIDTH * .03);
        canvas.drawRect(x, y, x + width, y + height, Game.paintDarkGrey);

        // Inner Square //
        int innerBuffer = (int) (Game.SCREEN_WIDTH * .0073);
        canvas.drawRect(x + innerBuffer, y + innerBuffer, x + width - innerBuffer, y + height - innerBuffer, Game.paintLightGrey);

        // List Title //
        int titleX = x + (width / 2) - (listTitleLength / 2);
        int titleY = y + (int) (Game.SCREEN_HEIGHT * .007);
        canvas.drawRect(titleX - (int) (Game.SCREEN_WIDTH * .005), titleY - TEXT_HEIGHT, titleX + listTitleLength + (int) (Game.SCREEN_WIDTH * .01), titleY, Game.paintLightGrey);
        canvas.drawText(targetList.get(0).getListId(), titleX, titleY, textPaintDark);
    }

    public void outlineRect(Canvas canvas, int x, int y, int width, int height, String stringTitle, int titleLength) {

        // Outer Square //
        x = x - (int) (Game.SCREEN_WIDTH * .015);
        y = y - (int) (Game.SCREEN_WIDTH * .015);
        width = width + (int) (Game.SCREEN_WIDTH * .03);
        height = height + (int) (Game.SCREEN_WIDTH * .03);
        canvas.drawRect(x, y, x + width, y + height, Game.paintDarkGrey);

        // Inner Square //
        int innerBuffer = (int) (Game.SCREEN_WIDTH * .0073);
        canvas.drawRect(x + innerBuffer, y + innerBuffer, x + width - innerBuffer, y + height - innerBuffer, Game.paintLightGrey);

        // Title //
        int titleX = x + (width / 2) - (titleLength / 2);
        int titleY = y + (int) (Game.SCREEN_HEIGHT * .007);
        canvas.drawRect(titleX - (int) (Game.SCREEN_WIDTH * .005), titleY - TEXT_HEIGHT, titleX + titleLength + (int) (Game.SCREEN_WIDTH * .01), titleY, Game.paintLightGrey);
        canvas.drawText(stringTitle, titleX, titleY, textPaintDark);
    }

    public void loadLevelSelectButtons() {
        int buttonX = (int) (Game.SCREEN_WIDTH * .042);
        int buttonY = (int) (Game.SCREEN_HEIGHT * .225);
        int buttonSize = (int) (Game.SCREEN_WIDTH * .075);

        for(int i = 0; i < 22; i++) {
            int iMod = 1;
            if(i >= 11)
                iMod = 0;
            String buttonLabel = String.valueOf(i + iMod);
            if(i == 10) { buttonLabel = "R"; }
            GameButton gameButton = new GameButton(buttonX, buttonY, buttonSize, buttonSize, buttonLabel, Game.paintGrey, Game.textPaintWhite36);
            gameButton.setButtonNum(i);
            gameButton.setOutline(Game.paintDarkGrey);
            levelSelectButtonList.add(gameButton);
            buttonX += buttonSize + (Game.SCREEN_WIDTH * .01);

            if(i == 10){
                buttonX = (int) (Game.SCREEN_WIDTH * .042);
                buttonY += buttonSize + (Game.SCREEN_WIDTH * .01);
            }
        }
    }

    public static void loadMenuListObjects(Player player) {

        // Ball List //
        String label = "";
        String longestLabel = "BOWLING BALL"; // Longest String In List - Used To Initialize MenuListObject Width
        int listObjectX = (int) (Game.SCREEN_WIDTH * .06);
        int listObjectY = PLAYER_AREA_Y_TOP;

        for(int i = 0; i < 5; i++) {
            if(Image.getBallResource(i) == R.drawable.superball)
                label = "SUPER BALL";
            else if(Image.getBallResource(i) == R.drawable.basketball)
                label = "BASKETBALL";
            else if(Image.getBallResource(i) == R.drawable.beach_ball)
                label = "BEACH BALL";
            else if(Image.getBallResource(i) == R.drawable.bowling_ball)
                label = "BOWLING BALL";
            else if(Image.getBallResource(i) == R.drawable.tennis_ball)
                label = "TENNIS BALL";

            MenuListObject menuListObject = new MenuListObject("BALL", label, longestLabel, listObjectX, listObjectY);
            menuListObject.addImage(Image.menuIconInactive, Image.menuIconActive);
            if(selectedBall == i)
                menuListObject.setIsActive(true);

            player.getBallList().add(menuListObject);
            listObjectY += menuListObject.getHeight();
        }

        // Ability List //
        longestLabel = "DOUBLE JUMP";
        MenuListObject tempMenuListObject = new MenuListObject("Temp", longestLabel, longestLabel, listObjectX, listObjectY);
        tempMenuListObject.addImage(Image.menuIconInactive, Image.menuIconActive);
        int abilityMenuListWidth = tempMenuListObject.getWidth();
        listObjectX = Game.SCREEN_WIDTH - abilityMenuListWidth - (int) (Game.SCREEN_WIDTH * .044);
        listObjectY = PLAYER_AREA_Y_TOP;
        for(int i = 0; i < 5; i++) {
            if(i == 0)
                label = "BOUNCE";
            else if(i == 1)
                label = "AIR-BRAKE";
            else if(i == 2)
                label = "DOUBLE JUMP";
            else if(i == 3)
                label = "POWER SLAM";
            else if(i == 4)
                label = "SPEED DASH";

            MenuListObject menuListObject = new MenuListObject("ABILITY", label, longestLabel, listObjectX, listObjectY);
            menuListObject.addImage(Image.menuIconInactive, Image.menuIconActive);
            player.getAbilityList().add(menuListObject);
            listObjectY += menuListObject.getHeight();
        }

        // Debug //
        player.getBallList().get(0).setLocked(false);
        player.getBallList().get(1).setLocked(false);
        player.getBallList().get(2).setLocked(false);
        player.getBallList().get(3).setLocked(false);
        player.getBallList().get(4).setLocked(false);
        player.getAbilityList().get(0).setLocked(false);
        player.getAbilityList().get(1).setLocked(false);
        player.getAbilityList().get(2).setLocked(false);
        player.getAbilityList().get(3).setLocked(false);
        player.getAbilityList().get(4).setLocked(false);
        player.getAbilityList().get(0).setIsActive(true);
        player.getAbilityList().get(1).setIsActive(true);
        player.getAbilityList().get(2).setIsActive(true);
        player.getAbilityList().get(3).setIsActive(true);
        player.getAbilityList().get(4).setIsActive(true);
    }

    // Getters & Setters //
    public int getSelectedBall() { return selectedBall; }
    public int getSelectedLevel() { return selectedLevel; }
}
