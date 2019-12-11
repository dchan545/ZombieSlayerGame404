package com.zombie.menu.Views;
/**
 * This class is the main menu of the game. There exists various buttons on it and
 * when one of the buttons is clicked, it will transition to another activity.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zombie.menu.R;

public class MainMenu extends AppCompatActivity {


    private Button btnStartGame;
    private Button btnLoad;
    private Button btnSettings;
    private FullScreen fullScreen = new FullScreen();
    private Button btnsignIn;

    /**
     * Initializes objects that are viewed on the user screen. Also starts music to be
     * played throughout the game.
     *
     * @param _savedInstanceState Object that android studio uses to save memory of this class when
     *                            the user leaves the app(not quit/force quit) and returns.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        //How the class will look
        setContentView(R.layout.activity_main_menu);

        //Initialize all the button fields of this class.
        initialize();

        //Plays Music throughout the application.
        Music.soundPlayer(this, R.raw.zombi);

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }


    /**
     * Initialize the class fields.
     */
    private void initialize() {
        this.btnStartGame = findViewById(R.id.btnStartGame);
        this.btnLoad = findViewById(R.id.btnLoad);
        this.btnSettings = findViewById(R.id.btnSettings);
        this.btnsignIn = findViewById(R.id.btnAccount);
    }


    /**
     * When one of the button is clicked, a switch statement then decides what button
     * was clicked and then executes an appropriate method for it.
     *
     * @param _view Object that the user sees, like a button on screen
     */
    public void clicked(View _view) {
        switch (_view.getId()) {
            case R.id.btnStartGame:
                Intent toLoad = new Intent(getApplicationContext(), Load.class);
                startActivity(toLoad);
                break;
            case R.id.btnLoad:
                Intent toSaveState = new Intent(getApplicationContext(), SaveState.class);
                startActivity(toSaveState);
                break;
            case R.id.btnSettings:
                Intent toSettings = new Intent(getApplicationContext(), Settings.class);
                startActivity(toSettings);
                break;
            case R.id.btnAccount:
                Intent toGoogleSignInApiAdapter = new Intent(getApplicationContext(), GoogleSignInApi.class);
                startActivity(toGoogleSignInApiAdapter);
                break;
        }
    }
}
