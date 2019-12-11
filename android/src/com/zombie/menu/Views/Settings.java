package com.zombie.menu.Views;
/**
 * This class will hold different options that the user can interact with to change some
 * attributes of the game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.zombie.menu.R;


public class Settings extends AppCompatActivity {

    private static boolean musicChecked = false;
    private static String languageChosen = "en";
    private ToggleButton tggleMusic;
    private Button btnMusic;
    private Button btnLanguage;
    private Button btnBack;
    private FullScreen fullScreen = new FullScreen();
    private Spinner language;

    /**
     * Initializes objects that are viewed on the user screen.
     *
     * @param _savedInstanceState Object that android studio uses to save memory of this class when
     *                            the user leaves the app(not quit/force quit) and returns.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();

        //Sets the drop down button to display text.
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {
                    Toast.makeText(parent.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    languageChosen = "en";

                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Spanish", Toast.LENGTH_SHORT)
                            .show();

                    languageChosen = "sp";

                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }

    /**
     * Initialize the class fields.
     */
    private void initialize() {
        this.tggleMusic = findViewById(R.id.toggleMusic);
        this.btnMusic = findViewById(R.id.btnMusic);
        this.btnLanguage = findViewById(R.id.btnLanguage);
        this.btnBack = findViewById(R.id.btnBack);
        if (musicChecked) {
            tggleMusic.setChecked(true);
        }

        language = findViewById(R.id.spinnerLanguage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter;


        //Sets the objects in the screen to either English or Spanish.
        if (languageChosen.contains("sp")) {
            if (musicChecked) {
                this.tggleMusic.setText(getResources().getString(R.string.off_sp));
            } else {
                this.tggleMusic.setText(getResources().getString(R.string.off_sp));
            }
            this.btnLanguage.setText(getResources().getString(R.string.language_sp));
            this.btnMusic.setText(getResources().getString(R.string.music_sp));
            this.btnBack.setText(getResources().getString(R.string.back_sp));
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.Languages_sp, android.R.layout.simple_spinner_item);
        } else {

            adapter = ArrayAdapter.createFromResource(this,
                    R.array.Languages, android.R.layout.simple_spinner_item);

            if (musicChecked) {
                this.tggleMusic.setText(getResources().getString(R.string.off));
            } else {
                this.tggleMusic.setText(getResources().getString(R.string.off));
            }
            this.btnLanguage.setText(getResources().getString(R.string.language));
            this.btnMusic.setText(getResources().getString(R.string.music));
            this.btnBack.setText(getResources().getString(R.string.back));
        }

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        language.setAdapter(adapter);
    }

    /**
     * When one of the button is clicked, a switch statement then decides what button
     * was clicked and then executes an appropriate method for it.
     *
     * @param _view Object that the user sees, like a button on screen
     */
    public void buttonClicked(View _view) {
        switch (_view.getId()) {
            case R.id.toggleMusic:

                //Button is ON
                if (tggleMusic.isChecked()) {
                    Music.player.pause();
                    musicChecked = true;
                } else {
                    Music.player.start();
                    musicChecked = false;
                }
                break;
            case R.id.btnSoundEffect:
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
