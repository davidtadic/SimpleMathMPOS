package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.practise.PractiseArrayLessonActivity;
import com.example.david.simplemath.activities.practise.PractiseLessGreaterActivity;
import com.example.david.simplemath.activities.practise.PractiseLessGreaterLessonActivity;
import com.example.david.simplemath.activities.practise.PractisePlusMinusActivity;
import com.example.david.simplemath.activities.practise.PractiseRomanLesson;

public class TrainingActivity extends Activity {

    private ImageButton back;
    private ImageButton romanButton;
    private ImageButton plusMinusButton;
    private ImageButton arrayButton;
    private ImageButton lessGreaterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        back = (ImageButton) findViewById(R.id.back_settings);
        romanButton = (ImageButton) findViewById(R.id.roman_numerals_practise);
        plusMinusButton = (ImageButton) findViewById(R.id.plus_minus_practise);
        arrayButton = (ImageButton) findViewById(R.id.array_practise);
        lessGreaterButton = (ImageButton) findViewById(R.id.less_greater_practise);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        romanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainingActivity.this, PractiseRomanLesson.class);
                startActivity(i);
            }
        });

        plusMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainingActivity.this, PractisePlusMinusActivity.class);
                startActivity(i);
            }
        });

        arrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainingActivity.this, PractiseArrayLessonActivity.class);
                startActivity(i);
            }
        });

        lessGreaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainingActivity.this, PractiseLessGreaterLessonActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TrainingActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
