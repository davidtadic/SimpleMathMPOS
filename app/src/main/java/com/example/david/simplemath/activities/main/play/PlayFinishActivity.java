package com.example.david.simplemath.activities.main.play;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.MainActivity;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.HighscoreModel;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

public class PlayFinishActivity extends Activity {

    private TextView score;
    private ImageButton forward;
    private int points;
    private SharedPreferences sharedPreferencesScore = null;

    private DatabaseHelper dbHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_finish);

        score = (TextView) findViewById(R.id.score_finish);
        forward = (ImageButton) findViewById(R.id.forward_main_menu);

        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        dbHelper = new DatabaseHelper(PlayFinishActivity.this);

        Log.e("DATUM", currentDateTimeString);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighscoreModel highscoreModel = new HighscoreModel(score.getText().toString(), currentDateTimeString);
                WriteToDatabase writeToDatabase = new WriteToDatabase();
                writeToDatabase.execute(highscoreModel);

                Intent i = new Intent(PlayFinishActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        sharedPreferencesScore = getSharedPreferences("highscore", MODE_PRIVATE);
        points = sharedPreferencesScore.getInt("score", 0);

        score.setText(String.valueOf(points));


    }


    public class WriteToDatabase extends AsyncTask<HighscoreModel, Void, Boolean> {

        @Override
        protected Boolean doInBackground(HighscoreModel... params) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("score", params[0].getScore());
            contentValues.put("date", params[0].getDate());

            long result = db.insert("Highscore", null, contentValues);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }
}
