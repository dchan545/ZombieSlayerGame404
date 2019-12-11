package coreGame.Model;
/**
 * This abstract class defines the functions every subclass of a Projectile needs, like Bullet.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import coreGame.View.Screens.PlayScreen;

public abstract class Projectile extends Sprite {
    protected World world;
    protected PlayScreen screen;
    boolean fireWeapon;

    /**
     * This constructor defines the space the Projectile will be created in.
     *
     * @param _screen is the PlayScreen which creates the Projectile.
     * @param _x is the X starting position.
     * @param _y is the Y starting position.
     * @param _fireWeapon is weather or not the weapon is set to fire.
     */
    public Projectile(PlayScreen _screen, float _x, float _y, boolean _fireWeapon){
        this.world = _screen.getWorld();
        this.screen = _screen;
        this.fireWeapon = _fireWeapon;
    }

    /**
     * This method creates the projectile's body and its fixtures.
     */
    public abstract void defineProjectile(float _x, float _y);

    /**
     * This method updates the position of the bullet, but also destroys the body if it is
     * set to be destroyed--bullet colliding with an object.
     *
     * @param _dt is the time difference since the last update was called.
     */
    public abstract void update(float _dt);

    /**
     * This method sets the Projectile object to be marked as destroyed.
     */
    public abstract void setToDestroy();

    /**
     * This method checks if the Projectile object is destroyed or not.
     *
     * @return boolean true if destroyed, otherwise false.
     */
    public abstract boolean isDestroyed();
}