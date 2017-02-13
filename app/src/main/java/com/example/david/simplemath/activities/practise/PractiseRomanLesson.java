package com.example.david.simplemath.activities.practise;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.david.simplemath.R;

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
                Intent i = new Intent(PractiseRomanLesson.this, PractiseRomanActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
