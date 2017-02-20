package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.practise.PractiseLessGreaterActivity;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.AdapterModel;
import com.example.david.simplemath.models.HighscoreModel;

import java.io.IOException;
import java.util.ArrayList;

public class RecordsActivity extends Activity {

    private ImageButton backRecords;
    private ListView listView;

    private DatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        ArrayList<HighscoreModel> highscoreList = new ArrayList<>();
        AdapterModel adapterModel = new AdapterModel(this, highscoreList);

        backRecords = (ImageButton)findViewById(R.id.back_records);
        listView = (ListView)findViewById(R.id.list_view);

        listView.setAdapter(adapterModel);
        registerForContextMenu(listView);

        backRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecordsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        dbHelper = new DatabaseHelper(RecordsActivity.this);

        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        highscoreList = dbHelper.getHighscores();
        Log.e("DA LI RADI", highscoreList.get(1).toString());

        adapterModel.addAll(highscoreList);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RecordsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


}
