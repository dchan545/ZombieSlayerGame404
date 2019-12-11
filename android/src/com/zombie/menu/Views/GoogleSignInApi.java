package com.zombie.menu.Views;
/**
 * Google Sign In API mostly supplied by Android Studio.
 * This class will allow users to log into their Google account
 * and once after they log in, their information will display
 * to the screen.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.zombie.menu.R;

public class GoogleSignInApi extends AppCompatActivity {

    private static boolean loggedIn = false;
    private static String stringPersonEmail;
    private static String stringPersonName;
    private static String stringPersonId;
    private static Uri photo;

    private GoogleSignInClient mGoogleSignInClient;
    private Button signIn;
    private Button signOut;
    private TextView personEmail;
    private TextView personName;
    private TextView personId;
    private ImageView personPhoto;
    private FullScreen fullScreen = new FullScreen();
    private final int RC_SIGN_IN = 0;
    private GoogleSignInOptions gso;


    /**
     * Initializes objects that are viewed on the user screen. Also creates a client
     * to google.
     *
     * @param _savedInstanceState Object that android studio uses to save memory of this class when
     *                            the user leaves the app(not quit/force quit) and returns.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_google_sign_in_api);

        initialize();

        if (loggedIn) {
            updateUI(true);
        }

        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }

    /**
     * Allows the user to sign out of their Google account once after they logged in.
     *
     */
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //The user interface will update to reflect that the user signed out.
                        updateUI(false);
                    }
                });

    }

    /**
     * Initializes the objects on the screen that the user sees.
     *
     */
    private void initialize() {
        this.signIn = findViewById(R.id.btnSignIn);
        this.personEmail = findViewById(R.id.text_view_email);
        this.personName = findViewById(R.id.text_view_name);
        this.personId = findViewById(R.id.text_view_id);
        this.personPhoto = findViewById(R.id.image_view_photo);
        this.signOut = findViewById(R.id.btnSignOut);
    }

    /**
     * Starts an activity made by Google and returns the result.
     *
     * @param _requestCode Identifies what from which intent you came back.
     * @param _resultCode function identifier
     * @param _data The data given back from the intent
     */
    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (_requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(_data);
            handleSignInResult(task);
        }
    }

    /**
     * If the Google sign in was successful a container containing the information is
     * given to this method. From the container, we can assign variables based on the
     * user's google account information.
     *
     * @param _completedTask A container that holds some information of the user's google
     *                       account.
     */
    private void handleSignInResult(Task<GoogleSignInAccount> _completedTask) {
        try {
            // account is not used, but it saved on a file.
            GoogleSignInAccount account = _completedTask.getResult(ApiException.class);


            // Signed in successfully, show authenticated UI.
            //Retrieve data from user's google account
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                loggedIn = true;

                //Sets the text for the objects in the UI.
                stringPersonEmail = acct.getEmail();
                stringPersonName = acct.getDisplayName();
                stringPersonId = acct.getId();
                photo = acct.getPhotoUrl();

                //Passes the information to be saved by the database.
                Intent toSaveState = new Intent(getApplicationContext(), SaveState.class);
                Bundle extras = new Bundle();
                extras.putString("email", stringPersonEmail);
                extras.putString("name", stringPersonName);
                extras.putString("id", stringPersonId);
                toSaveState.putExtras(extras);
                startActivity(toSaveState);


                updateUI(true);
            } else {
                updateUI(false);
            }


        } catch (ApiException _e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + _e.getStatusCode());

        }
    }


    /**
     * If the user was able to log into their Google account, the UI
     * update to reflect that.
     *
     * @param _logIn If the user was able to sign in, true;
     *               If the user was not able to sign in, false.
     */
    private void updateUI(boolean _logIn) {
        if (_logIn) {
            this.personEmail.setText(stringPersonEmail);
            this.personName.setText(stringPersonName);
            this.personId.setText(stringPersonId);
            Glide.with(this)
                    .load(String.valueOf(personPhoto))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(this.personPhoto);
            //Sets objects that are seen right now to invisible, and sets the objects that
            //are hidden in the UI to visible.
            this.personEmail.setVisibility(View.VISIBLE);
            this.personName.setVisibility(View.VISIBLE);
            this.personId.setVisibility(View.VISIBLE);
            this.personPhoto.setVisibility(View.VISIBLE);
            this.signOut.setVisibility(View.VISIBLE);
            this.signIn.setVisibility(View.INVISIBLE);
        } else {
            this.personEmail.setVisibility(View.INVISIBLE);
            this.personName.setVisibility(View.INVISIBLE);
            this.personId.setVisibility(View.INVISIBLE);
            this.personPhoto.setVisibility(View.INVISIBLE);
            this.signOut.setVisibility(View.INVISIBLE);
            this.signIn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Allows the user to sign in of their Google account.
     *
     */
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Whenever one of the buttons are clicked, they will launch a method.
     *
     * @param _view The UI that the user see's currently.
     */
    public void buttonClicked(View _view) {
        switch (_view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSignIn:
                signIn();
                break;
            case R.id.btnSignOut:
                signOut();
                break;
        }
    }
}
