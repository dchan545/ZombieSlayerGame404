package coreGame.View.Screens;
/**
 * This class updates and renders the game's map, camera, and entities.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */

import android.content.Context;
import android.content.Intent;

import coreGame.Events.WorldContactListener;
import coreGame.Model.Bullet;
import coreGame.Model.Enemy;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;
import coreGame.Game.ZombieGame;
import coreGame.Tools.B2WorldCreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.menu.Views.MainMenu;
import com.zombie.menu.Views.SaveState;

import java.util.ArrayList;

import coreGame.View.Scenes.HUD;

public class PlayScreen implements Screen {

    private final int VEL_ITERATIONS = 6;
    private final int POS_ITERATIONS = 2;
    private final float FPS = 1 / 60f;

    //This creates Survivor.
    private Survivor player;
    //Declares a new game.
    private ZombieGame game;
    //Creates an arrayList for bullets.
    ArrayList<Bullet> bullets;
    //Camera that follows the game.
    private OrthographicCamera gameCam;
    //Zoom level of the camera.
    private float zoom = 1f;
    //The viewport determines the aspect ratio for different devices.
    private Viewport gamePort;
    //HUD displays the informational text across the top of the screen.
    private HUD hud;

    //Loads the map into the game.
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private Texture spriteSheet;
    //Cuts the sprite sheet into individual textures.
    private TextureRegion[][] tiles;
    //Creates a renderer for this map.
    private OrthogonalTiledMapRenderer renderer;

    //The world where all physics will take place using Box2D. The world will have "bodies."
    private World world;
    //This renderer gives graphical representation of the bodies in the world.
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private boolean fireWeapon;

    private Context ctx;

    /**
     * The game's "world" where the camera, screen, map, and objects are created and rendered.
     *
     * @param _game is the ZombieGame object.
     * @param _ctx is the context for this activity.
     */
    public PlayScreen(ZombieGame _game, Context _ctx) {
        this.ctx = _ctx;
        //Loads sprite sheet image.
        spriteSheet = new Texture(Gdx.files.internal("spritesheet.png"));
        tiles = TextureRegion.split(spriteSheet,GameConstants.PIXEL_SIZE,GameConstants.PIXEL_SIZE);
        //Creates a new instance of the game.
        this.game = _game;
        //New world with no gravity and allows sleeping bodies.
        world = new World(Vector2.Zero, true);
        player = new Survivor(this);
        bullets = new ArrayList<Bullet>();
        fireWeapon = true;

        gameCam = new OrthographicCamera();
        //Extends what the screen can see if being used on bigger devices without stretching or resizing.
        gamePort = new ExtendViewport(zoom, zoom, gameCam);
        //Camera starting position.
        float initCameraX = gamePort.getWorldWidth() / 2;
        float initCameraY = gamePort.getWorldHeight() / 2;
        float initCameraZ = 0;
        gameCam.position.set(initCameraX, initCameraY, initCameraZ);
        //gameCam.setToOrtho(false, gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2);

        //gamePort.setScreenSize(GameConstants.V_WIDTH, GameConstants.V_HEIGHT);
        hud = new HUD(game.batch, _ctx);

        //Loads in the map asset and renderer.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Mapv2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameConstants.PPM);

        //Creates a new Box2D world for physical movements.
        Box2D.init();
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());
    }

    /**
     * This method is called to show this screen as the current screen.
     * To be implemented
     */
    @Override
    public void show() {
    }

    /**
     * This updates the camera position continuously. Called by render(float _dt).
     *
     * @param _dt is delta time.
     */
    public void update(float _dt) {
        //This sets the fps of the game. Iterations are just for precision for the physics.
        world.step(FPS, VEL_ITERATIONS, POS_ITERATIONS);

        //This updates the HUD, specifically the timer countdown.
        hud.update(_dt);
        //Pass it hud for now. Will make controller later
        player.update(_dt, hud);

        //Fires weapon if boolean value is true.
        if (fireWeapon){
            bullets.add(new Bullet(this, player.getPositionX(), player.getPositionY(), fireWeapon));
            fireWeapon = false;
        }
        //Updates the bullets.
        for (Bullet bullet : bullets){
            bullet.update(_dt);
        }
        //Updates the enemies.
        for (Enemy enemy : creator.getZombies()) {
            enemy.update(_dt);
        }
        //This makes the game camera follow the player.
        gameCam.position.x = player.getPositionX();
        gameCam.position.y = player.getPositionY();
        gameCam.update();
        // This renders only what can be seen by the player -- the gameCam.
        renderer.setView(gameCam);
    }

    /**
     * Clears the map and re-renders each update.
     * The _delta is the time in seconds since the last render.
     *
     * @param _delta
     */
    @Override
    public void render(float _delta) {
        update(_delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders the tiles as interactive objects; this shows the render lines.
        b2dr.render(world, gameCam.combined);

        renderer.render();

        //This sets what we can see only in the game camera.
        game.batch.setProjectionMatrix(gameCam.combined);
        //This opens the "box" to put our textures for the game camera.
        game.batch.begin();

        //This draws the batch.
        player.draw(game.batch);
        //Draws the projectiles.
        for (Bullet bullet : bullets){
            bullet.draw(game.batch);
        }
        //Draws the enemies
        for (Enemy enemy : creator.getZombies())
            enemy.draw(game.batch);
        //This stops putting textures for the game camera.
        game.batch.end();
        //This draws the what is in the HUD camera.
        game.batch.setProjectionMatrix(this.hud.getStage().getCamera().combined);
        hud.getStage().draw();
    }

    /**
     * Updates the gamePort if the window is re-sized.
     *
     * @param _width
     * @param _height
     */
    @Override
    public void resize(int _width, int _height) {
        gamePort.update(_width, _height);
    }

    /**
     * To be implemented
     */
    @Override
    public void pause() {
    }

    /**
     * To be implemented
     */
    @Override
    public void resume() {
    }

    /**
     * This method hides the screen when the screen is no longer the current screen.
     * To be implemented
     */
    @Override
    public void hide() {
    }

    /**
     * Removes all resources in the PlayScreen to prevent memory leaks.
     */
    @Override
    public void dispose() {
        player.dispose();
        for (Enemy enemy : creator.getZombies()) {
            enemy.dispose();
        }
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
    //=================== GETTERS =====================

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }
    public TextureRegion[][] getTextures() {
        return tiles;
    }
    public Survivor getSurvivor() { return this.player; }
}

