package com.example.david.simplemath.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.david.simplemath.R;

/**
 * Created by david on 17.2.2017..
 */

public class BackgroundMusicService extends Service {

    MediaPlayer player;
    public static final String MY_SERVICE = ".services.BackgroundMusicService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.background_sound);
        player.setLooping(true);
        player.setVolume(20,0);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }


    public void onStop() {
        player.stop();
        player.release();
    }


    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}
