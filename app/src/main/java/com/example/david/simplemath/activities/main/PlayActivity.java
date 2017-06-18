package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.play.PlayPlusMinusActivity;
import com.example.david.simplemath.activities.practise.PractiseArrayLessonActivity;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.HighscoreModel;

public class PlayActivity extends Activity {
    private ImageButton back;
    private ImageButton forward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        back = (ImageButton) findViewById(R.id.back_play_lesson);
        forward = (ImageButton) findViewById(R.id.forward_play_lesson);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, PlayPlusMinusActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
