package coreGame.Events;
/**
 * This class is called when two fixtures in our box 2d world collide.
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import coreGame.Model.Enemy;
import coreGame.Model.InteractiveTileObject;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;

public class WorldContactListener implements ContactListener {
    private int count = 0;
    private Fixture object;

    /**
     * This method identifies what fixtures are colliding when they do collide.
     *
     * @param _contact is the contact that has occurred between two fixtures.
     */
    @Override
    public void beginContact(Contact _contact) {

        Fixture fixA = _contact.getFixtureA();
        Fixture fixB = _contact.getFixtureB();

        objectIdentifier(fixA, fixB);
    }

    /**
     * This is a helper method for beginContact to identify what is being collided.
     *
     * @param _fixA is one of two fixtures participating in the contact.
     * @param _fixB is the other fixture participating in the contact.
     */
    public void objectIdentifier(Fixture _fixA, Fixture _fixB){

        switch (_fixA.getFilterData().categoryBits) {
            case GameConstants.SURVIVOR_BIT:
                count = 0;
                objectIdentifierWithSurvivor(_fixB, _fixA);
                break;
            case GameConstants.ZOMBIE_BIT:
                count = 0;
                objectIdentifierWithZombie(_fixB, _fixA);
                break;
            case GameConstants.PROJECTILE_BIT:
                count = 0;
                objectIdentifierWithSurvivor(_fixB, _fixA);
                break;
            default:
                {
                    count++;
                    if (count == 1){
                        objectIdentifier(_fixB, _fixA);
                        break;
                    }
                    else {
                        count = 0;
                        Gdx.app.log("unknown", "Collision");
                        break;
                    }
            }
        }
    }

    /**
     * This method is called when one of the objects has been identified as a Survivor object.
     * Defines what happens with the other fixture when it's filter has been determined.
     *
     * @param _fixture is the unknown fixture.
     * @param _survivor is the known Survivor fixture.
     */
    private void objectIdentifierWithSurvivor(Fixture _fixture, Fixture _survivor){
        object = _fixture;
        //This fires the event between survivor and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).survivorCollision();
        }
        //This fires the event between survivor and an enemy.
        else if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Enemy) object.getUserData()).damageSurvivor();
        }
    }

    /**
     * This method is called when one of the objects has been identified as a Zombie object.
     * Defines what happens with the other fixture when it's filter has been determined.
     *
     * @param _fixture is the unknown fixture.
     * @param _Zombie is the known Zombie fixture.
     */
    private void objectIdentifierWithZombie(Fixture _fixture, Fixture _Zombie){
        object = _fixture;
        //This fires the event between a zombie and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).zombieHit();
        }
        //This fires the event between a zombie and a survivor.
        else if (object.getUserData() != null && Survivor.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Enemy) _Zombie.getUserData()).damageSurvivor();
        }
    }

    /**
     * This method returns a message to the Logcat when the collision has ended.
     *
     * @param _contact is the contact that has ended.
     */
    @Override
    public void endContact(Contact _contact) {
        Gdx.app.log("Collision ends.", "");
    }

    /**
     * Unused libgdx ContactListener operation.
     *
     * @param _contact is the contact.
     * @param _oldManifold is a libgdx setting for collisions.
     */
    @Override
    public void preSolve(Contact _contact, Manifold _oldManifold) {
    }

    /**
     * Unused libgdx ContactListener operation.
     *
     * @param _contact is the contact.
     * @param _impulse is a libgdx setting for collisions.
     */
    @Override
    public void postSolve(Contact _contact, ContactImpulse _impulse) {
    }
}
