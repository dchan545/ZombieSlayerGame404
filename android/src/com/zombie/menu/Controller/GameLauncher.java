package com.zombie.menu.Controller;
/**
 * This class is the file that launches for Android devices and emulators.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
 */

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import coreGame.Game.ZombieGame;

public class GameLauncher extends AndroidApplication {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Returns the app to immersion mode if the app looses and then regains focus
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //Launcher for the app
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        ZombieGame game = new ZombieGame(getContext());
        initialize(game, config);

        //Puts the device into immersion mode (hide the top navigation bars) if the device has that feature (KitKat and up)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }
    }
}