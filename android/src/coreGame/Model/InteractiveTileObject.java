package coreGame.Model;
/**
 * This class defines the functions needed for all the interactive tile objects gotten from the
 * tile map.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveTileObject {
    protected World world;
    private TiledMap map;
    protected TiledMapTile tile;
    private Rectangle bounds;
    private Body body;
    private Fixture fixture;

    /**
     * This constructor creates a new tile object in Box2D.
     *
     * @param _screen is the screen where the tile objects will be rendered.
     * @param _bounds is the rectangle object that surrounds each tile.
     */
    public InteractiveTileObject(PlayScreen _screen, Rectangle _bounds){
        this.world = _screen.getWorld();
        this.map = _screen.getMap();
        this.bounds = _bounds;

        //Defines a new Box2D body to use for the tile object.
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //This sets the body-type of the body to static. A static body is not affected by physics in the 2d world.
        bdef.type = BodyDef.BodyType.StaticBody;
        //Sets the position of the body to the center of the tile.
        bdef.position.set((_bounds.getX() + _bounds.getWidth() / 2) / GameConstants.PPM, (_bounds.getY() + _bounds.getHeight() / 2) / GameConstants.PPM);

        //This creates the body in the Box2D world.
        body = world.createBody(bdef);
        shape.setAsBox((_bounds.getWidth() / 2) / GameConstants.PPM, (_bounds.getHeight() / 2) / GameConstants.PPM);
        fdef.shape = shape;
        //Attaches a fixture to the body for collisions.
        fixture = body.createFixture(fdef);
    }

    /**
     * Determines what happens when the tile is hit (default).
     */
    public abstract void objectHit();

    /**
     * Determines what happens when a survivor hits the tile.
     */
    public abstract void survivorCollision();

    /**
     * Determines what happens when a zombie hits the tile.
     */
    public abstract void zombieHit();

    /**
     * Determines what happens when a bullet hits the tile.
     */
    public abstract void bulletHit();

    // ======================================== GETTERS ==================================================

    public Fixture getFixture() {
        return this.fixture;
    }

    // ======================================== SETTERS ==================================================

    /**
     * Defines the type of tile created.
     *
     * @param filterBit is enumerated in the GameConstants class.
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    /**
     * This method receives this specific cell from the tile map.
     *
     * @return the cell object of this TileObject.
     */
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        //Gets the cell at the position of the B2Body scaled to the tile map's size and the Box2D scaling.
        return layer.getCell((int) (body.getPosition().x * GameConstants.PPM / GameConstants.PIXEL_SIZE), (int) (body.getPosition().y * GameConstants.PPM / GameConstants.PIXEL_SIZE));
    }
}
