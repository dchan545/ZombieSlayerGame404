package coreGame.Model;
/**
 * This class defines a type of Projectile and creates, updates, and renders the object given a
 * screen where they will be contained.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Bullet extends Projectile {

    //The necessary variables that are used for Box2D creation and updating.
    protected World world;
    protected PlayScreen screen;
    private Body b2body;
    private int radius;
    private float speed = 2f;
    private Sprite sprite;
    private static Texture texture;
    private Vector2 velocity;

    //Says if the button to fire the weapon, located in the HUD, is pressed or not.
    boolean fireWeapon;
    //Marks the object to be removed from rendering and updating. Destroys the Box2D body.
    boolean setToDestroy;
    //Says if the bullet has been destroyed already or not.
    boolean isDestroyed;
    //The time since the object has been created in the screen.
    float stateTime;

    /**
     * Constructor that initializes the bullet object's world, construction of the Box2D body, and
     * sets the sprite.
     *
     * @param _screen is the screen where the bullet has been called from.
     * @param _x is the X starting position.
     * @param _y is the Y starting position.
     * @param _fireWeapon is the boolean that says if the weapon the Survivor has is being fired.
     */
    public Bullet(PlayScreen _screen, float _x, float _y, boolean _fireWeapon) {
        super(_screen, _x, _y, _fireWeapon);
        this.world = _screen.getWorld();
        this.screen = _screen;
        this.fireWeapon = _fireWeapon;
        this.defineProjectile( _x, _y);

        //Constructs the sprite.
        texture = new Texture("touchpad-knob.png");
        sprite = new Sprite(texture);
        sprite.setBounds(sprite.getX(), sprite.getY(), 6 / GameConstants.PPM, 6 / GameConstants.PPM);
        sprite.setPosition(_x,_y);

        //Initialize as not set to be destroyed, and has not been destroyed.
        setToDestroy = false;
        isDestroyed = false;
    }

    /**
     * This method creates the projectile's body and its fixture in Box2D.
     */
    @Override
    public void defineProjectile(float _x, float _y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(_x, _y);
        //The projectile is dynamic and is affected by the physics of the B2World.
        bdef.type = BodyDef.BodyType.DynamicBody;
        //This checks if we are in a middle of a time step (60 per second).
        if(!world.isLocked())
            b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / GameConstants.PPM);

        //Filters what type of other objects the bullet object can collide with.
        fdef.filter.categoryBits = GameConstants.PROJECTILE_BIT;
        fdef.filter.maskBits = GameConstants.DEFAULT_BIT |
                GameConstants.COIN_BIT |
                GameConstants.BRICK_BIT |
                GameConstants.ZOMBIE_BIT |
                GameConstants.GHOST_BIT |
                GameConstants.OBJECT_BIT;

        fdef.shape = shape;
        //Don't allow the bullet to have friction.
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);

    }
    /**
     * This method updates the position of the bullet.
     *
     * @param _dt is the time since the last update was called.
     */
    public void update(float _dt){
        //Updates the time since the bullet has been created.
        stateTime =+ _dt;
        //For now, only have the bullet to to the right.
        velocity = new Vector2(speed ,0);
        /*
        Update the Body's position only if the bullet is still alive. The bullet will die if it
        lives past the defined time it's allowed or if another method sets it to be destroyed.
         */
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !isDestroyed) {
            world.destroyBody(b2body);
            isDestroyed = true;
        }
        else if (!isDestroyed){
            b2body.setLinearVelocity(velocity);
            sprite.setPosition(this.getPositionX(), this.getPositionY());
        }
    }

    /**
     * This method marks the bullet to be destroyed from the screen, and for the Box2D body to be
     * disposed of.
     */
    public void setToDestroy(){
        this.setToDestroy = true;
    }

    /**
     * Checks if the bullet has been destroyed.
     *
     * @return boolean that's true if the bullet has been destroyed.
     */
    public boolean isDestroyed(){
        return this.isDestroyed;
    }

    /**
     * Draws the bullet's sprite.
     *
     * @param batch is the collection of sprites shared by the entire game.
     */
    @Override
    public void draw(Batch batch){
        if (!isDestroyed)
            sprite.draw(batch);
    }

    //==================================== Getters ==================================//

    public float getPositionX(){
        return b2body.getPosition().x - (sprite.getWidth() / 2f);
    }

    public float getPositionY(){
        return b2body.getPosition().y - (sprite.getWidth() / 2f);
    }

    public Body getBody() {
        return this.b2body;
    }
}
