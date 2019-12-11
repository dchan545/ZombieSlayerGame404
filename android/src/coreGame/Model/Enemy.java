package coreGame.Model;
/**
 * This class serves as a parent class for any type of enemy.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import coreGame.View.Screens.PlayScreen;

public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;

    /**
     * Identifies the world the enemy object will be spawned into.
     *
     * @param _screen is the screen that will draw the spawned enemies.
     * @param _x is the X starting position.
     * @param _y is the Y starting position.
     */
    public Enemy(PlayScreen _screen, float _x, float _y){
        this.world = _screen.getWorld();
        this.screen = _screen;
    }

    /**
     * Enemies need to be able to update their B2Body's position.
     * The AI of enemy movements will go here.
     *
     * @param _dt is the time difference since update was last called.
     */
    public abstract void update(float _dt);

    /**
     * Enemies need to be able to draw their sprites.
     *
     * @param batch is the collection of sprites used for the entire game.
     */
    public void draw(Batch batch) {
        super.draw(batch);
    }

    /**
     * Enemies need to be able to deal damage to the Survivor player.
     */
    public abstract void damageSurvivor();

    /**
     * Enemies need to be able to dispose of their resources to prevent memory leaks.
     */
    public abstract void dispose();

    /**
     * Enemies are required to have a Box2D body defined for object movement and collisions.
     *
     * @param _x is the X starting position.
     * @param _y is the Y starting position.
     */
    protected abstract void defineEnemy(float _x, float _y);
}
