package com.zombie.menu.Views;
/**
 * This class is used to play music throughout the game.
 * This class is also static so the Activities can access it.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    public static MediaPlayer player;
    private static int leftVolume = 100;
    private static int rightVolume = 100;

    /**
     * Initializes objects that are viewed on the user screen. Also starts music to be
     * played throughout the game.
     *
     * @param _ctx UI of the given activity.
     * @param _rawId music file.
     */
    public static void soundPlayer(Context _ctx, int _rawId) {
        player = MediaPlayer.create(_ctx, _rawId);

        // Set looping
        player.setLooping(true);
        player.setVolume(leftVolume, rightVolume);

        //Starts the music.
        player.start();
    }
}
