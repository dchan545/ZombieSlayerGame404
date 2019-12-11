package com.zombie.menu.Views;
/**
 * Saves the game various variables.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/19/2019
 */

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zombie.menu.R;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import Database.DBTranslator;

public class SaveState extends AppCompatActivity {

    public final static String googleFile = "googleSignIn";
    public final static String languageFile = "language";
    //Names of the save files.
    final String FILE_NAME_1 = "save1";
    final String FILE_NAME_2 = "save2";
    final String FILE_NAME_3 = "save3";
    //Linked list that will hold current game's data to save or load a game state given this list.
    //Used by save and load methods.
    public LinkedList<String> saveObjects = new LinkedList<>();
    //Create the context needed for opening/editing files.
    Context ctx = this;
    private String english = "en";
    private String spanish = "sp";
    private FullScreen fullScreen = new FullScreen();

    /**
     * Sets the screen to the related XML, creates the save files, and initializes the button functions.
     *
     * @param _savedInstanceState
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        //Sets the related XML document on the screen.
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_save_state);

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);

        //This if else block only executes if Settings.class was to transition into this class.
        //If that were to happen, the language they chose will be saved in the database.
        if (getIntent().hasExtra("en")) {
            load(languageFile);
            if (saveObjects.contains("sp")) {
                saveObjects.remove("sp");
                save(saveObjects, languageFile);
            }
            if (!saveObjects.contains("en")) {
                saveObjects.add("en");
                save(saveObjects, languageFile);
            }
            finish();
        } else if (getIntent().hasExtra("sp")) {
            load(languageFile);
            if (saveObjects.contains("en")) {
                saveObjects.remove("en");
                save(saveObjects, languageFile);
            }
            if (!saveObjects.contains("sp")) {
                saveObjects.add("sp");
                save(saveObjects, languageFile);
            }
            finish();
        }

        //This if block only executes if GoogelSignInApi.class was to transition into this class.
        //If that were to happen, the User's Google account information would be saved to the database.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("email")) {
                saveObjects.add(extras.getString("email"));
                saveObjects.add(extras.getString("name"));
                saveObjects.add(extras.getString("id"));
                save(saveObjects, googleFile);
                finish();
            }
        }


        Button backBtn = findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Goes back to the class that transitioned to it.
                finish();
            }
        });

        //put into "initialize" method.
        //Buttons that write to the save files.
        final Button SAVE_FILE_1 = findViewById(R.id.button_save1);
        final Button SAVE_FILE_2 = findViewById(R.id.button_save2);
        final Button SAVE_FILE_3 = findViewById(R.id.button_save3);

        //Buttons that read to the save files.
        final Button LOAD_FILE_1 = findViewById(R.id.button_load1);
        final Button LOAD_FILE_2 = findViewById(R.id.button_load2);
        final Button LOAD_FILE_3 = findViewById(R.id.button_load3);

        //Buttons that clear the save files.
        final Button DELETE_FILE_1 = findViewById(R.id.button_delete1);
        final Button DELETE_FILE_2 = findViewById(R.id.button_delete2);
        final Button DELETE_FILE_3 = findViewById(R.id.button_delete3);

        //Create text files to save/load from.
        DBTranslator.createObject(ctx, FILE_NAME_1);
        DBTranslator.createObject(ctx, FILE_NAME_2);
        DBTranslator.createObject(ctx, FILE_NAME_3);

        //Set up the save buttons.
        createButton(SAVE_FILE_1, buttonType.SAVE);
        createButton(SAVE_FILE_2, buttonType.SAVE);
        createButton(SAVE_FILE_3, buttonType.SAVE);

        //Set up the load buttons.
        createButton(LOAD_FILE_1, buttonType.LOAD);
        createButton(LOAD_FILE_2, buttonType.LOAD);
        createButton(LOAD_FILE_3, buttonType.LOAD);

        //Set up the delete buttons.
        createButton(DELETE_FILE_1, buttonType.DELETE);
        createButton(DELETE_FILE_2, buttonType.DELETE);
        createButton(DELETE_FILE_3, buttonType.DELETE);
    }

    /**
     * Given a button and button type, apply the button's function when it's clicked.
     *
     * @param _button
     * @param _type
     */
    private void createButton(final Button _button, final buttonType _type) {

        //Create a button click response.
        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the button's function depending on given button type.
                if (_type.equals(buttonType.SAVE)) {
                    save(saveObjects, getFileName(_button));
                } else if (_type.equals(buttonType.LOAD)) {
                    load(getFileName(_button));
                } else if (_type.equals(buttonType.DELETE)) {
                    delete(getFileName(_button));
                }
            }
        });
    }

    /**
     * Given a button, return the file associated with it.
     * NOTE: Searches for the file number in reverse order so, for example, 11 is found before 1.
     *
     * @param _button
     * @return FILE_NAME_#
     */
    private String getFileName(Button _button) {

        try {
            if (_button.getTag().toString().contains("3")) {
                return FILE_NAME_3;
            } else if (_button.getTag().toString().contains("2")) {
                return FILE_NAME_2;
            } else if (_button.getTag().toString().contains("1")) {
                return FILE_NAME_1;
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ActionButton does not correspond to a save file.");
        }
        return null;
    }

    /**
     * Method that saves the current game state to a file.
     * Currently hold dummy data.
     *
     * @param _savedObjects
     * @param _file
     */
    public void save(LinkedList<String> _savedObjects, String _file) {
        //Put objects gotten from current game state in a linked list.


        //Pass all to translator.
        DBTranslator.updateObject(ctx, _file, _savedObjects);

        //Clear the linked list after use.
        _savedObjects.clear();
    }

    /**
     * Method that overrides current game state with given save file.
     *
     * @param _file
     */
    public void load(String _file) {
        //Pass all to translator.
        saveObjects = DBTranslator.readObject(ctx, _file);
    }

    /**
     * Clears the file associated with the button pressed.
     *
     * @param _file
     */
    public void delete(String _file) {
        DBTranslator.deleteObject(ctx, _file);
    }

    //Enumeration of button types for QOL.
    enum buttonType {
        LOAD, SAVE, DELETE
    }
}
