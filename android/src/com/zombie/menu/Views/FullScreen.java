package com.zombie.menu.Views;
/**
 * This class is used as a helper class for all the other class.
 * Makes Android not display the notification bar or navigation bar.
 * The two bars can be redisplayed by user gesture swipe.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreen {

    private final int VISIBLE = 0;

    /**
     * This method is used to make android studio run a class, that is used to
     * display to the screen, to fit the entire user's phone screen.
     *
     * @param _Screen Object that contains everything that the user sees on the screen.
     */
    public void hideSystem(AppCompatActivity _Screen) {
        View decorView = _Screen.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * If the screen is ever reseted back to the default view, this method
     * will set it back to fullscreen.
     *
     * @param _Screen Object that contains everything that the user sees on the screen.
     */
    public void checkSystem(final AppCompatActivity _Screen) {
        View decorView = _Screen.getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        // The sytem bars are visible, we will change it back to fullscreen mode.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == VISIBLE) {
                            hideSystem(_Screen);
                        }
                    }
                });

    }
}
