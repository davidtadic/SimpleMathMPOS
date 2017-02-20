package com.example.david.simplemath.activities.main.play;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.MainActivity;
import com.example.david.simplemath.activities.main.TrainingActivity;
import com.example.david.simplemath.activities.practise.PractiseLessGreaterActivity;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.LessGreaterModel;
import com.example.david.simplemath.services.BackgroundMusicService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PlayLessActivity extends Activity {

    private ImageButton back;
    private Button firstNumberButton;
    private Button resultButton;
    private Button secondNumberButton;
    private Button lessButton;
    private Button equalButton;
    private Button greaterButton;
    private TextView score;

    private int points;

    private DatabaseHelper dbHelper = null;
    private List<LessGreaterModel> lessGreaterModelList;
    private int counter;

    Context context = this;
    private String signAnswer;
    private SharedPreferences sharedPreferencesScore = null;

    private SharedPreferences sharedPreferences = null;
    private String musicControl;
    private Intent intentMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_less);

        back = (ImageButton)findViewById(R.id.back_less_play);
        firstNumberButton = (Button)findViewById(R.id.first_number_less_play);
        resultButton = (Button)findViewById(R.id.result_less_play);
        secondNumberButton = (Button)findViewById(R.id.second_number_less_play);
        lessButton = (Button)findViewById(R.id.less_practise_play);
        equalButton = (Button)findViewById(R.id.equal_practise_play);
        greaterButton = (Button)findViewById(R.id.greater_practise_play);
        score = (TextView)findViewById(R.id.score_less);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBack();
            }
        });

        sharedPreferences = getSharedPreferences("music", MODE_PRIVATE);
        musicControl = sharedPreferences.getString("musicControl", "");

        sharedPreferencesScore = getSharedPreferences("highscore", MODE_PRIVATE);
        points = sharedPreferencesScore.getInt("score", 0);

        score.setText(String.valueOf(points));

        dbHelper = new DatabaseHelper(PlayLessActivity.this);
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

        lessGreaterModelList = dbHelper.getQuestionsLessGreater();
        Collections.shuffle(lessGreaterModelList);

        Log.e("DA LI RADI", lessGreaterModelList.get(1).toString());

        counter = 0;
        changeQuestion(counter);
    }

    public void dialogBack(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_back_play);
        dialog.setCancelable(false);
        ImageButton yes = (ImageButton)dialog.findViewById(R.id.yes);
        ImageButton no = (ImageButton)dialog.findViewById(R.id.cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayLessActivity.this, MainActivity.class);
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
        if(counter <= 2) {
            setEnabledButtons();
            returnBackground();
            setAnswer(lessGreaterModelList.get(counter));
            checkAnswer(lessGreaterModelList.get(counter), counter);
        }else{
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_finish_play);
            dialog.setCancelable(false);
            ImageButton ok = (ImageButton)dialog.findViewById(R.id.ok_practise);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferencesScore.edit();
                    editor.putInt("score", points);
                    editor.apply();

                    Intent i = new Intent(PlayLessActivity.this, PlayFinishActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            dialog.show();

        }

    }

    public void checkAnswer(LessGreaterModel lessGreaterModel, int counter){
        final String correctAnswer = lessGreaterModel.getCorrectAnswer();
        counter++;

        final int finalCounter = counter;

        lessButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        equalButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        greaterButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        resultButton.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();
                View view = (View) event.getLocalState();
                switch (dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        resultButton.setBackgroundResource(R.drawable.result_selected);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        resultButton.setBackgroundResource(R.drawable.result_lesss);
                        break;
                    case DragEvent.ACTION_DROP:
                        Button draggedButton = (Button)view.findViewById(view.getId());
                        signAnswer = draggedButton.getText().toString();

                        if(signAnswer.equals(correctAnswer)){
                            if(signAnswer.equals("less")){
                                resultButton.setBackgroundResource(R.drawable.less_correct);
                            }else if(signAnswer.equals("equal")){
                                resultButton.setBackgroundResource(R.drawable.equal_correct);
                            }else if(signAnswer.equals("greater")){
                                resultButton.setBackgroundResource(R.drawable.greater_correct);
                            }
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    correctDialog(finalCounter);
                                }
                            }, 1000);

                            points += 10;
                        }else{
                            if(signAnswer.equals("less")){
                                resultButton.setBackgroundResource(R.drawable.less_wrong);
                            }else if(signAnswer.equals("equal")){
                                resultButton.setBackgroundResource(R.drawable.equal_wrong);
                            }else if(signAnswer.equals("greater")){
                                resultButton.setBackgroundResource(R.drawable.greater_wrong);
                            }
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    wrongDialog(finalCounter);
                                }
                            }, 1000);
                        }

                        break;
                }
                return true;
            }
        });
        score.setText(String.valueOf(points));
    }

    public void setAnswer(LessGreaterModel lessGreaterModel){

        String expression = lessGreaterModel.getExpression();
        String[] expressionArray = expression.split(" ");
        firstNumberButton.setText(expressionArray[0]);
        secondNumberButton.setText(expressionArray[1]);
    }

    private void returnBackground(){
        resultButton.setBackgroundResource(R.drawable.result_lesss);
    }

    private void setEnabledButtons(){
        lessButton.setEnabled(true);
        equalButton.setEnabled(true);
        greaterButton.setEnabled(true);
    }

    private void setDisabledButtons(){
        lessButton.setEnabled(false);
        equalButton.setEnabled(false);
        greaterButton.setEnabled(false);
    }

    public void correctDialog(final int counter){
        setDisabledButtons();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_correct);
        dialog.setCancelable(false);

        if(musicControl.equals("on")){
            intentMusic = new Intent(PlayLessActivity.this, BackgroundMusicService.class);
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
            intentMusic = new Intent(PlayLessActivity.this, BackgroundMusicService.class);
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
