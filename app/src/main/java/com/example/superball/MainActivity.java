package com.example.superball;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug-MainActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);

        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        Log.d("Debug-MainActivity.java", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("Debug-MainActivity.java", "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("Debug-MainActivity.java", "onPause()");
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("Debug-MainActivity.java", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("Debug-MainActivity.java", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d("Debug-MainActivity.java", "onBackPressed()");
        // Comment Out Super To Prevent Any Back Press Action //
        //super.onBackPressed();
    }
}