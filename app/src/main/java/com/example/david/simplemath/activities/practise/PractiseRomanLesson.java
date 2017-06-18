package com.example.david.simplemath.activities.practise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.play.PlayRomanActivity;

public class PractiseRomanLesson extends Activity {

    private ImageButton forward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_roman_lesson);

        forward = (ImageButton) findViewById(R.id.forward_roman);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String infoIntent = getIntent().getStringExtra("PlayIntentFlag");
                if (infoIntent == null) {
                    Intent intent = new Intent(PractiseRomanLesson.this, PractiseRomanActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (infoIntent.equals("FromArrayToRomanLesson")) {
                        Intent intent = new Intent(PractiseRomanLesson.this, PlayRomanActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
}
