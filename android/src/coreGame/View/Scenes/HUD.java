package coreGame.View.Scenes;
/**
 * This is the class which creates and displays the HUD, the joystick, and the button.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */

import android.content.Context;
import android.view.SurfaceView;

import coreGame.Util.GameConstants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Locale;

public class HUD extends SurfaceView implements Disposable {

    //Creates a new stage and viewport where the HUD will be in.
    private Stage stage;
    private Viewport viewport;

    //Joystick that will be used for movement.
    private Touchpad joystick;
    private Touchpad.TouchpadStyle joystickStyle;
    private Skin joystickSkin;
    private Drawable joystickBackground;
    private Drawable joystickKnob;
    private int joystickRadius;
    private int joystickXpos = 8;
    private int joystickYpos = 8;
    private int joystickWidth = 64;
    private int joystickHeight = 64;

    //Button that will be used for firing projectiles.
    private static Button button;
    private Button.ButtonStyle buttonStyle;
    private Skin buttonSkin;
    private Drawable buttonReleased;
    private Drawable buttonPressed;
    private int buttonXpos = 350;
    private int buttonYpos = 16;
    private int buttonWidth = 40;
    private int buttonHeight = 40;

    //HUD literals.
    private Integer worldTimer = 100;
    private float timeCount = 0;
    private static Integer scoreCount = 0;
    private String nameLabel = "Survivor";
    private String timeLabel = "Time";
    private static Integer healthLabel = 100;
    private static String scoreLabel = "SCORE";

    //Font and color of the HUD items.
    private static Label.LabelStyle textFont;
    //HUD components formatted.
    private Label countdownHUD;
    private static Label scoreHUD;
    private Label timeHUD;
    private static Label healthHUD;
    private Label worldHUD;
    private Label nameHUD;
    //Size of the HUD's top margin.
    private int menuTopPad = 10;

    /**
     * Creates the HUD within the stage by using a table and labels.
     *
     * @param sb
     */
    public HUD(SpriteBatch sb, Context ctx) {
        super(ctx);
        //Create a new camera showing a stage that will hold the HUD. The viewport is the same size as the world.
        OrthographicCamera viewCamera = new OrthographicCamera();
        viewport = new FitViewport(GameConstants.V_WIDTH, GameConstants.V_HEIGHT, viewCamera);
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);

        //Initialize labels.
        textFont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        countdownHUD = new Label (String.format(Locale.getDefault(),"%03d", worldTimer),textFont);
        scoreHUD = new Label(String.format(Locale.getDefault(),"%06d", scoreCount),textFont);
        timeHUD = new Label(timeLabel, textFont);
        healthHUD = new Label(String.format(Locale.getDefault(),"%03d", healthLabel), textFont);
        worldHUD = new Label(scoreLabel, textFont);
        nameHUD = new Label(nameLabel, textFont);

        //Joystick movement controls.
        joystickSkin = new Skin();
        joystickSkin.add("touchBackground", new Texture("touchpad.png"));
        joystickSkin.add("touchKnob", new Texture("touchpad-knob.png"));
        joystickSkin.getDrawable("touchKnob").setMinHeight(joystickSkin.getDrawable("touchKnob").getMinHeight() / 2);
        joystickSkin.getDrawable("touchKnob").setMinWidth(joystickSkin.getDrawable("touchKnob").getMinWidth() / 2);
        joystickStyle = new Touchpad.TouchpadStyle();
        joystickBackground = joystickSkin.getDrawable("touchBackground");
        joystickKnob = joystickSkin.getDrawable("touchKnob");
        joystickStyle.background = joystickBackground;
        joystickStyle.knob = joystickKnob;

        joystick = new Touchpad(joystickRadius, joystickStyle);
        joystick.setBounds(joystickXpos, joystickYpos, joystickWidth, joystickHeight);

        //Button for firing projectiles.
        buttonSkin = new Skin();
        buttonSkin.add("buttonReleased", new Texture("button-arcade.png"));
        buttonSkin.add("buttonPressed", new Texture("button-arcade-pressed.png"));
        buttonSkin.getDrawable("buttonReleased").setMinHeight(buttonSkin.getDrawable("buttonReleased").getMinHeight() / 2);
        buttonSkin.getDrawable("buttonReleased").setMinWidth(buttonSkin.getDrawable("buttonReleased").getMinWidth() / 2);
        buttonSkin.getDrawable("buttonPressed").setMinHeight(buttonSkin.getDrawable("buttonPressed").getMinHeight() / 2);
        buttonSkin.getDrawable("buttonPressed").setMinWidth(buttonSkin.getDrawable("buttonPressed").getMinWidth() / 2);
        buttonStyle = new Button.ButtonStyle();
        buttonReleased = buttonSkin.getDrawable("buttonReleased");
        buttonPressed = buttonSkin.getDrawable("buttonPressed");

        button = new Button(buttonReleased, buttonPressed);
        button.setBounds(buttonXpos, buttonYpos, buttonWidth, buttonHeight);

        //Creates a table at the top of the game's window.
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //Add the labels to the table and give the first row a top margin.
        //.expandX() Fills the table across the viewport's x-axis.
        table.add(nameHUD).expandX().padTop(menuTopPad);
        table.add(worldHUD).expandX().padTop(menuTopPad);
        table.add(timeHUD).expandX().padTop(menuTopPad);
        table.row();
        table.add(healthHUD).expandX();
        table.add(scoreHUD).expandX();
        table.add(countdownHUD).expandX();

        //Add the created table to the stage.
        stage.addActor(table);
        stage.addActor(joystick);
        stage.addActor(button);
    }

    /**
     * Updates the HUD counter.
     *
     * @param _dt is the time since the last update method.
     */
    public void update(float _dt){
        timeCount += _dt;
        if (timeCount >= 15){
            worldTimer++;
            countdownHUD.setText(String.format(Locale.getDefault(),"%03d", worldTimer));
            timeCount = 5;
        }
    }

    /**
     * Determines if the button for firing projectiles is currently being pressed.
     *
     * @return boolean if the weapon fire button is pressed.
     */
    public static boolean isButtonPressed() {
        return button.isPressed();
    }

    /**
     * Method that handles the joystick input.
     *
     * @return a Vector2 of the direction the joystick is pointing to.
     */
    public Vector2 handleJoystickInput() {
        if(joystick.getKnobPercentX() == 0 && joystick.getKnobPercentY() == 0) {
            return Vector2.Zero;
        } else {
            return new Vector2(joystick.getKnobPercentX(), joystick.getKnobPercentY());
        }
    }

    /**
     * Subtracts the given value from the current health.
     *
     * @param _value is the amount the health should be reduced by.
     */
    public static void changeHealth(int _value){
        healthLabel -= _value;
        healthHUD.setText(String.format(Locale.getDefault(),"%03d", healthLabel));
    }

    /**
     * Adds to the score.
     *
     * @param _value is an integer that's added to the score.
     */
    public static void addScore(int _value){
        scoreCount += _value;
        scoreHUD.setText(String.format(Locale.getDefault(),"%06d", scoreCount));
    }

    /**
     * Disposes of any resources used within the stage to prevent memory leaks.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    //=================== GETTERS =====================

    public Stage getStage() { return this.stage; }
    public Touchpad getJoystick() { return this.joystick; }
}