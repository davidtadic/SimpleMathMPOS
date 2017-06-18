package com.example.david.simplemath.activities.practise;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.TrainingActivity;
import com.example.david.simplemath.activities.main.play.PlayArrayActivity;

public class PractiseArrayLessonActivity extends Activity {
    private ImageButton back;
    private ImageButton forward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_array_lesson);

        back = (ImageButton) findViewById(R.id.back_array_lesson);
        forward = (ImageButton) findViewById(R.id.forward_array_lesson);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PractiseArrayLessonActivity.this, TrainingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String infoIntent = getIntent().getStringExtra("PlayIntentFlag");
                if (infoIntent == null) {
                    Intent intent = new Intent(PractiseArrayLessonActivity.this, PractiseArrayActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (infoIntent.equals("FromPlusMinusToArrayLesson")) {
                        Intent intent = new Intent(PractiseArrayLessonActivity.this, PlayArrayActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }
}
