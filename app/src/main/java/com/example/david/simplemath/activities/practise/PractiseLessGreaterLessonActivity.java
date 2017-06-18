package com.example.david.simplemath.activities.practise;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.TrainingActivity;
import com.example.david.simplemath.activities.main.play.PlayLessActivity;

public class PractiseLessGreaterLessonActivity extends Activity {
    private ImageButton back;
    private ImageButton forward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_less_greater_lesson);

        back = (ImageButton) findViewById(R.id.back_less_lesson);
        forward = (ImageButton) findViewById(R.id.forward_less_lesson);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PractiseLessGreaterLessonActivity.this, TrainingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String infoIntent = getIntent().getStringExtra("PlayIntentFlag");
                if (infoIntent == null) {
                    Intent intent = new Intent(PractiseLessGreaterLessonActivity.this, PractiseLessGreaterActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (infoIntent.equals("FromRomanToLessGreaterLesson")) {
                        Intent intent = new Intent(PractiseLessGreaterLessonActivity.this, PlayLessActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
