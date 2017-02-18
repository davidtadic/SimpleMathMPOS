package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.practise.PractiseRomanActivity;
import com.example.david.simplemath.services.BackgroundMusicService;

public class MainActivity extends Activity {

    private ImageButton exit;
    private ImageView records;
    private ImageView settings;
    private ImageView play;
    private ImageView training;
    private Context context = this;

    private SharedPreferences sharedPreferences = null;
    private String musicState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exit = (ImageButton)findViewById(R.id.exit_main);
        records = (ImageView)findViewById(R.id.records_main);
        settings = (ImageView)findViewById(R.id.settings_main);
        play = (ImageView)findViewById(R.id.play_main);
        training = (ImageView)findViewById(R.id.practise_main);

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TrainingActivity.class);
                startActivity(i);
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(i);
                finish();

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                finish();
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecordsActivity.class);
                startActivity(i);
                finish();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_exit);

                ImageButton yes = (ImageButton)dialog.findViewById(R.id.yes_exit);
                ImageButton no = (ImageButton)dialog.findViewById(R.id.cancel_exit);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent intent = new Intent(MainActivity.this, BackgroundMusicService.class);
                        stopService(intent);
                        startActivity(startMain);
                        finishAffinity();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        Intent i = new Intent(MainActivity.this, BackgroundMusicService.class);
        startService(i);


        try{
            sharedPreferences = getSharedPreferences("music",MODE_PRIVATE);
            musicState = sharedPreferences.getString("musicControl","");
            Log.e("INFO", musicState);

            if(musicState.equals("off")){
                Intent intent = new Intent(MainActivity.this, BackgroundMusicService.class);
                stopService(intent);
            }
        }catch (Exception e){
            Log.e("TAG_MUSIC", e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent(MainActivity.this, BackgroundMusicService.class);
        stopService(intent);
        startActivity(startMain);
        finish();
    }


}
