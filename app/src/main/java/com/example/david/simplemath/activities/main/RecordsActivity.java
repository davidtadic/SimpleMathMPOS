package com.example.david.simplemath.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.david.simplemath.R;

public class RecordsActivity extends Activity {

    private ImageButton backRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        backRecords = (ImageButton)findViewById(R.id.back_records);

        backRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecordsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
