package coreGame.Model;
/**
 * This class creates the default model -- everything else that is not a
 * brick or coin tile on the map. The default model serves as a tile that
 * is not interactive with any fixtures besides simply colliding -- no other
 * event is triggered.
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

public class Default extends InteractiveTileObject{

    /**
     * This constructor creates a new default tile object in Box2D.
     *
     * @param _screen is the screen where the tile objects will be rendered.
     * @param _bounds is the rectangle object that surrounds each tile.
     */
    public Default(PlayScreen _screen, Rectangle _bounds){
        super(_screen, _bounds);
        this.getFixture().setUserData(this);
        this.setCategoryFilter(GameConstants.DEFAULT_BIT);
    }

    /**
     * Determines what happens when the tile is generically hit.
     */
    @Override
    public void objectHit() {
        Gdx.app.log("Default", "Collision");
    }

    /**
     * Determines what happens when a survivor hits the tile.
     */
    @Override
    public void survivorCollision() {
        Gdx.app.log("Survivor-Default", "Collision");
    }

    /**
     * Determines what happens when a zombie hits the tile.
     */
    @Override
    public void zombieHit() {
        Gdx.app.log("Zombie-Default", "Collision");
    }

    /**
     * Determines what happens when a bullet hits the tile.
     */
    @Override
    public void bulletHit() {
        Gdx.app.log("Bullet-Default", "Collision");
    }
}
