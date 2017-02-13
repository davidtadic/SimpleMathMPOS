package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.david.simplemath.R;

public class SettingsActivity extends Activity {

    private ToggleButton music;
    private ToggleButton voice;
    private ImageButton backSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        music = (ToggleButton)findViewById(R.id.toggleMusic);
        voice = (ToggleButton)findViewById(R.id.toggleVoice);
        backSettings = (ImageButton)findViewById(R.id.back_settings);

        backSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
