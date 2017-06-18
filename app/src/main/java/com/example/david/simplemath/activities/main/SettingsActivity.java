package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
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

    BackgroundMusicService musicService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            BackgroundMusicService.ServiceBinder binder = (BackgroundMusicService.ServiceBinder) service;
            musicService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

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


        if (musicService.State) {
            music.setBackgroundResource(R.drawable.music_on);
        } else {
            music.setBackgroundResource(R.drawable.music_off);
        }

        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && musicService.State) {
                    music.setBackgroundResource(R.drawable.music_off);
                    musicService.pauseMusic();
                } else if (isChecked && !musicService.State) {
                    music.setBackgroundResource(R.drawable.music_on);
                    musicService.resumeMusic();
                } else if (musicService.State) {
                    music.setBackgroundResource(R.drawable.music_off);
                    musicService.pauseMusic();
                } else if (!musicService.State) {
                    music.setBackgroundResource(R.drawable.music_on);
                    musicService.resumeMusic();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BackgroundMusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
