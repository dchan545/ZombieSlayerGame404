package coreGame.Model;
/**
 * This class creates the coin model in Box2D as an interactive tile object.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/14/2019
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Coin extends InteractiveTileObject{

    /**
     * This constructor creates a new coin tile object in Box2D.
     *
     * @param _screen is the screen where the tile objects will be rendered.
     * @param _bounds is the rectangle object that surrounds each tile.
     */
    public Coin(PlayScreen _screen, Rectangle _bounds){
        super(_screen, _bounds);
        this.getFixture().setUserData(this);
        this.getFixture().setSensor(true);
        this.setCategoryFilter(GameConstants.COIN_BIT);
    }

    /**
     * Determines what happens when the tile is generically hit.
     */
    @Override
    public void objectHit() {
        Gdx.app.log("Coin", "Collision");
    }

    /**
     * Determines what happens when a survivor hits the tile.
     */
    @Override
    public void survivorCollision() {
        Gdx.app.log("Survivor-Coin", "Collision");
    }

    /**
     * Determines what happens when a zombie hits the tile.
     */
    @Override
    public void zombieHit() {
        Gdx.app.log("Zombie-Coin", "Collision");
    }

    /**
     * Determines what happens when a bullet hits the tile.
     */
    @Override
    public void bulletHit() {
        Gdx.app.log("Bullet-Coin", "Collision");
    }
}
