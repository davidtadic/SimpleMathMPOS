package com.example.david.simplemath.activities.practise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.SQLException;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.MainActivity;
import com.example.david.simplemath.activities.main.TrainingActivity;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.RomanArabianModel;
import com.example.david.simplemath.services.BackgroundMusicService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.in;

public class PractiseRomanActivity extends Activity {

    private ImageButton backRoman;
    private Button questionButton;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;

    private int counter;

    final Context context = this;

    private List<RomanArabianModel> romanList;
    private DatabaseHelper dbHelper = null;

    public boolean correct = false;

    private SharedPreferences sharedPreferences = null;
    private String musicControl;
    private Intent intentMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_roman);

        backRoman = (ImageButton) findViewById(R.id.back_roman);

        questionButton = (Button)findViewById(R.id.question_roman);
        answer1Button = (Button)findViewById(R.id.answer1_roman);
        answer2Button = (Button)findViewById(R.id.answer2_roman);
        answer3Button = (Button)findViewById(R.id.answer3_roman);
        answer4Button = (Button)findViewById(R.id.answer4_roman);

        sharedPreferences = getSharedPreferences("music", MODE_PRIVATE);
        musicControl = sharedPreferences.getString("musicControl", "");


        backRoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialogBack();
            }
        });


        dbHelper = new DatabaseHelper(PractiseRomanActivity.this);
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        romanList = dbHelper.getQuestionsRomanArabian();

        List<RomanArabianModel> shuffleRomanList = new ArrayList<RomanArabianModel>();

        Collections.shuffle(romanList);

        Log.e("DA LI RADI", romanList.get(1).toString());
        counter = 0;
        changeQuestion(counter);

    }

    public void dialogBack(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_back);

        ImageButton yes = (ImageButton)dialog.findViewById(R.id.yes);
        ImageButton no = (ImageButton)dialog.findViewById(R.id.cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PractiseRomanActivity.this, TrainingActivity.class);
                startActivity(i);
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

    public void changeQuestion(int counter){
        if(counter <= 6) {
            setEnabledButtons();
            returnBackground();
            setAnswer(romanList.get(counter));
            checkAnswer(romanList.get(counter), counter);
        }else{
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_finish_practise);

            ImageButton ok = (ImageButton)dialog.findViewById(R.id.ok_practise);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PractiseRomanActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            dialog.show();


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setAnswer(RomanArabianModel romanArabianModel){
        //mix answers
        List<String> answers = romanArabianModel.getMixedRomanQuestion();

        questionButton.setText(romanArabianModel.getExpression());
        answer1Button.setText(answers.get(0));
        answer2Button.setText(answers.get(1));
        answer3Button.setText(answers.get(2));
        answer4Button.setText(answers.get(3));
    }

    public void checkAnswer(RomanArabianModel romanArabianModel, int counter){
        final String correctAnswer = romanArabianModel.getCorrectAnswer();
        counter++;

        final int finalCounter = counter;
        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer1Button.getText().toString().equals(correctAnswer)){
                    answer1Button.setBackgroundResource(R.drawable.answer_roman_correct);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);

                }else{
                    answer1Button.setBackgroundResource(R.drawable.answer_roman_wrong);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            wrongDialog(finalCounter);
                        }
                    }, 1000);
                }
            }
        });

        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer2Button.getText().toString().equals(correctAnswer)){
                    answer2Button.setBackgroundResource(R.drawable.answer_roman_correct);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);
                }else{
                    answer2Button.setBackgroundResource(R.drawable.answer_roman_wrong);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            wrongDialog(finalCounter);
                        }
                    }, 1000);
                }
            }
        });

        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer3Button.getText().toString().equals(correctAnswer)){
                    answer3Button.setBackgroundResource(R.drawable.answer_roman_correct);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);
                }else{
                    answer3Button.setBackgroundResource(R.drawable.answer_roman_wrong);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            wrongDialog(finalCounter);
                        }
                    }, 1000);
                }
            }
        });

        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4Button.getText().toString().equals(correctAnswer)){
                    answer4Button.setBackgroundResource(R.drawable.answer_roman_correct);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);
                }else{
                    answer4Button.setBackgroundResource(R.drawable.answer_roman_wrong);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            wrongDialog(finalCounter);
                        }
                    }, 1000);
                }
            }
        });

    }

    private void returnBackground(){
        answer1Button.setBackgroundResource(R.drawable.answers_roman);
        answer2Button.setBackgroundResource(R.drawable.answers_roman);
        answer3Button.setBackgroundResource(R.drawable.answers_roman);
        answer4Button.setBackgroundResource(R.drawable.answers_roman);

    }

    private void setEnabledButtons(){
        answer1Button.setEnabled(true);
        answer2Button.setEnabled(true);
        answer3Button.setEnabled(true);
        answer4Button.setEnabled(true);
    }

    private void setDisabledButtons(){
        answer1Button.setEnabled(false);
        answer2Button.setEnabled(false);
        answer3Button.setEnabled(false);
        answer4Button.setEnabled(false);
    }

    public void correctDialog(final int counter){
        setDisabledButtons();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_correct);
        dialog.setCancelable(false);

        if(musicControl.equals("on")){
            intentMusic = new Intent(PractiseRomanActivity.this, BackgroundMusicService.class);
            stopService(intentMusic);
        }


        final MediaPlayer player = MediaPlayer.create(this, R.raw.correct);
        player.setLooping(false);
        player.setVolume(100, 100);
        player.start();

        ImageButton next = (ImageButton)dialog.findViewById(R.id.forward_correct);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.stop();
                player.release();
                if(musicControl.equals("on")){
                    startService(intentMusic);
                }
                changeQuestion(counter);
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void wrongDialog(final int counter){
        setDisabledButtons();

        if(musicControl.equals("on")){
            intentMusic = new Intent(PractiseRomanActivity.this, BackgroundMusicService.class);
            stopService(intentMusic);
        }

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_wrong);
        dialog.setCancelable(false);

        final MediaPlayer player = MediaPlayer.create(this, R.raw.wrong);
        player.setLooping(false);
        player.setVolume(100, 100);
        player.start();

        ImageButton next = (ImageButton)dialog.findViewById(R.id.forward_wrong);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                if(musicControl.equals("on")){
                    startService(intentMusic);
                }
                changeQuestion(counter);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        dialogBack();
    }

}
