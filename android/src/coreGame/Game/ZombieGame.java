package coreGame.Game;
/**
 * This class loads the graphics and calls for the start of the game.
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/06/2019
 */

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import coreGame.View.Screens.PlayScreen;

public class ZombieGame extends Game {

    //Context is needed to pass non-static information between activities.
    private Context ctx;
    //Declaring the graphics of the game. Used by all other classes and is memory heavy.
    public SpriteBatch batch;

    /**
     * Initializes a new game to be created.
     *
     * @param _ctx is the context of the application instance.
     */
    public ZombieGame(Context _ctx) {
        this.ctx = _ctx;
    }

    /**
     * Initializes the SpriteBatch and creates a new instance of PlayScreen, where the game will launch.
     */
    public void create () {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this, ctx));
    }

    /**
     * Renders what you see on the screen.
     */
    @Override
    public void render () {
        super.render();
    }

    /**
     * Manually dispose of game's assets after game exits. Prevents memory leaks.
     */
    @Override
    public void dispose () {
        batch.dispose();
    }
}
