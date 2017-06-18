package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.services.BackgroundMusicService;

public class MainActivity extends Activity {

    private ImageButton exit;
    private ImageView records;
    private ImageView settings;
    private ImageView play;
    private ImageView training;
    private Context context = this;


    BackgroundMusicService musicService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
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
        setContentView(R.layout.activity_main);

        exit = (ImageButton) findViewById(R.id.exit_main);
        records = (ImageView) findViewById(R.id.records_main);
        settings = (ImageView) findViewById(R.id.settings_main);
        play = (ImageView) findViewById(R.id.play_main);
        training = (ImageView) findViewById(R.id.practise_main);

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
                dialogBack();
            }
        });

        if (musicService.State) {
            Intent i = new Intent(this, BackgroundMusicService.class);
            startService(i);
        }

    }

    public void dialogBack() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_exit);

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.yes_exit);
        ImageButton no = (ImageButton) dialog.findViewById(R.id.cancel_exit);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent service = new Intent(MainActivity.this, BackgroundMusicService.class);
                stopService(service);
                startActivity(startMain);
                finish();
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
        dialogBack();
    }
}
