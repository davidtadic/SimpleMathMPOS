package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.david.simplemath.R;
import com.example.david.simplemath.services.BackgroundMusicService;

public class SettingsActivity extends Activity {

    private ToggleButton music;
    private ImageButton aboutUs;
    private ImageButton backSettings;
    private SharedPreferences sharedPreferencesMusic = null;

    private String musicState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        music = (ToggleButton) findViewById(R.id.toggleMusic);
        aboutUs = (ImageButton) findViewById(R.id.aboutUs);
        backSettings = (ImageButton) findViewById(R.id.back_settings);

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(i);
                finish();
            }
        });

        backSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        sharedPreferencesMusic = getSharedPreferences("music", MODE_PRIVATE);


        musicState = sharedPreferencesMusic.getString("musicControl", "");
        Log.e("INFO_MUSIC", musicState);

        if (musicState.equals("off")) {
            music.setBackgroundResource(R.drawable.music_off);
        } else if (musicState.equals("on")) {
            music.setBackgroundResource(R.drawable.music_on);
        }


        final SharedPreferences.Editor editor = sharedPreferencesMusic.edit();

        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && musicState.equals("on")) {
                    music.setBackgroundResource(R.drawable.music_off);
                    editor.putString("musicControl", "off");
                    editor.apply();
                    Intent i = new Intent(SettingsActivity.this, BackgroundMusicService.class);
                    stopService(i);
                } else if (isChecked && musicState.equals("off")) {
                    music.setBackgroundResource(R.drawable.music_on);
                    editor.putString("musicControl", "on");
                    editor.apply();
                    Intent i = new Intent(SettingsActivity.this, BackgroundMusicService.class);
                    startService(i);
                }
                musicState = sharedPreferencesMusic.getString("musicControl", "");

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
