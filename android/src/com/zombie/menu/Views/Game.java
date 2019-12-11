package com.zombie.menu.Views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zombie.menu.R;


public class Game extends AppCompatActivity {


    private RelativeLayout gameLayout;
    private Button analog;

    private float x;
    private float y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        analog = findViewById(R.id.human);

        gameLayout = findViewById(R.id.gameScreen);

        //repeated fires over and over again?
        gameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getApplicationContext(), "i was touched", Toast.LENGTH_SHORT).show();
                x = motionEvent.getX();
                y = motionEvent.getY();

                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    analog.setX(x);
                    analog.setY(y);
                }

                return true; //if true, then event fires
            }
        });


    }

}
