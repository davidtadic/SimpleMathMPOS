package com.example.david.simplemath.activities.main.play;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.MainActivity;
import com.example.david.simplemath.activities.main.TrainingActivity;
import com.example.david.simplemath.activities.practise.PractiseArrayLessonActivity;
import com.example.david.simplemath.activities.practise.PractisePlusMinusActivity;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.PlusMinusModel;
import com.example.david.simplemath.services.BackgroundMusicService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PlayPlusMinusActivity extends Activity {

    private ImageButton back;
    private Button firstNumberButton;
    private Button plusOrMinusButton;
    private Button secondNumberButton;
    private Button equalButton;
    private Button resultButton;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;
    private TextView score;

    Context context = this;

    private int points;

    private DatabaseHelper dbHelper = null;
    private List<PlusMinusModel> plusMinusModelList;
    private int counter;

    private SharedPreferences sharedPreferencesScore = null;

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
        setContentView(R.layout.activity_play_plus_minus);

        back = (ImageButton) findViewById(R.id.back_plus_minus_play);
        firstNumberButton = (Button) findViewById(R.id.question_plus_minus1_play);
        plusOrMinusButton = (Button) findViewById(R.id.question_plus_minus2_play);
        secondNumberButton = (Button) findViewById(R.id.question_plus_minus3_play);
        equalButton = (Button) findViewById(R.id.question_plus_minus4_play);
        resultButton = (Button) findViewById(R.id.question_plus_minus5_play);
        answer1Button = (Button) findViewById(R.id.answer1_plus_minus_play);
        answer2Button = (Button) findViewById(R.id.answer2_plus_minus_play);
        answer3Button = (Button) findViewById(R.id.answer3_plus_minus_play);
        answer4Button = (Button) findViewById(R.id.answer4_plus_minus_play);
        score = (TextView) findViewById(R.id.score_plus_minus);

        score.setText(String.valueOf(points));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBack();
            }
        });

        dbHelper = new DatabaseHelper(PlayPlusMinusActivity.this);
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

        plusMinusModelList = dbHelper.getQuestionsPlusMinus();
        Collections.shuffle(plusMinusModelList);

        // Log.e("DA LI RADI", plusMinusModelList.get(1).toString());

        counter = 0;
        changeQuestion(counter);
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

    public void changeQuestion(int counter) {
        if (counter <= 2) {
            setEnabledButtons();
            returnBackground();
            setAnswer(plusMinusModelList.get(counter));
            checkAnswer(plusMinusModelList.get(counter), counter);
        } else {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_array_play);
            dialog.setCancelable(false);
            ImageButton ok = (ImageButton) dialog.findViewById(R.id.ok_practise);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferencesScore = getSharedPreferences("highscore", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferencesScore.edit();
                    editor.putInt("score", points);
                    editor.apply();

                    Intent i = new Intent(PlayPlusMinusActivity.this, PractiseArrayLessonActivity.class);
                    i.putExtra("PlayIntentFlag", "FromPlusMinusToArrayLesson");
                    startActivity(i);
                    finish();
                }
            });

            dialog.show();


        }

    }

    public void setAnswer(PlusMinusModel plusMinusModel) {
        //mix answers
        List<Integer> answers = plusMinusModel.getMixedPlusMinusQuestions();
        String expression = plusMinusModel.getExpression();
        String[] expressionArray = expression.split(" ");

        firstNumberButton.setText(expressionArray[0]);
        if (expressionArray[1].equals("+")) {
            plusOrMinusButton.setBackgroundResource(R.drawable.pluss);
        } else if (expressionArray[1].equals("-")) {
            plusOrMinusButton.setBackgroundResource(R.drawable.minuss);
        }
        secondNumberButton.setText(expressionArray[2]);

        answer1Button.setText(String.valueOf(answers.get(0)));
        answer2Button.setText(String.valueOf(answers.get(1)));
        answer3Button.setText(String.valueOf(answers.get(2)));
        answer4Button.setText(String.valueOf(answers.get(3)));
    }

    public void checkAnswer(PlusMinusModel plusMinusModel, int counter) {
        final int correctAnswer = plusMinusModel.getCorrectAnswer();
        counter++;

        final int finalCounter = counter;

        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1Button.getText().toString().equals(String.valueOf(correctAnswer))) {
                    answer1Button.setBackgroundResource(R.drawable.plus_minus_answer_correctt);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);

                    points += 10;

                } else {
                    answer1Button.setBackgroundResource(R.drawable.plus_minus_answer_wrongg);
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
                if (answer2Button.getText().toString().equals(String.valueOf(correctAnswer))) {
                    answer2Button.setBackgroundResource(R.drawable.plus_minus_answer_correctt);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);

                    points += 10;

                } else {
                    answer2Button.setBackgroundResource(R.drawable.plus_minus_answer_wrongg);
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
                if (answer3Button.getText().toString().equals(String.valueOf(correctAnswer))) {
                    answer3Button.setBackgroundResource(R.drawable.plus_minus_answer_correctt);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);

                    points += 10;

                } else {
                    answer3Button.setBackgroundResource(R.drawable.plus_minus_answer_wrongg);
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
                if (answer4Button.getText().toString().equals(String.valueOf(correctAnswer))) {
                    answer4Button.setBackgroundResource(R.drawable.plus_minus_answer_correctt);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            correctDialog(finalCounter);
                        }
                    }, 1000);

                    points += 10;

                } else {
                    answer4Button.setBackgroundResource(R.drawable.plus_minus_answer_wrongg);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            wrongDialog(finalCounter);
                        }
                    }, 1000);
                }
            }
        });

        score.setText(String.valueOf(points));
    }

    private void returnBackground() {
        answer1Button.setBackgroundResource(R.drawable.plus_minus_answerr);
        answer2Button.setBackgroundResource(R.drawable.plus_minus_answerr);
        answer3Button.setBackgroundResource(R.drawable.plus_minus_answerr);
        answer4Button.setBackgroundResource(R.drawable.plus_minus_answerr);

    }

    private void setEnabledButtons() {
        answer1Button.setEnabled(true);
        answer2Button.setEnabled(true);
        answer3Button.setEnabled(true);
        answer4Button.setEnabled(true);
    }

    private void setDisabledButtons() {
        answer1Button.setEnabled(false);
        answer2Button.setEnabled(false);
        answer3Button.setEnabled(false);
        answer4Button.setEnabled(false);
    }

    public void correctDialog(final int counter) {
        setDisabledButtons();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_correct);
        dialog.setCancelable(false);

        if (musicService.State) {
            musicService.pauseMusic();
            musicService.State = true;
        }

        final MediaPlayer player = MediaPlayer.create(this, R.raw.correct);
        player.setLooping(false);
        player.setVolume(100, 100);
        player.start();

        ImageButton next = (ImageButton) dialog.findViewById(R.id.forward_correct);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.stop();
                player.release();
                if (musicService.State) {
                    musicService.resumeMusic();
                }
                changeQuestion(counter);
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void wrongDialog(final int counter) {
        setDisabledButtons();

        if (musicService.State) {
            musicService.pauseMusic();
            musicService.State = true;
        }

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_wrong);
        dialog.setCancelable(false);

        final MediaPlayer player = MediaPlayer.create(this, R.raw.wrong);
        player.setLooping(false);
        player.setVolume(100, 100);
        player.start();

        ImageButton next = (ImageButton) dialog.findViewById(R.id.forward_wrong);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                if (musicService.State) {
                    musicService.resumeMusic();
                }
                changeQuestion(counter);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void dialogBack() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_back_play);
        dialog.setCancelable(false);
        ImageButton yes = (ImageButton) dialog.findViewById(R.id.yes);
        ImageButton no = (ImageButton) dialog.findViewById(R.id.cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPlusMinusActivity.this, MainActivity.class);
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

    @Override
    public void onBackPressed() {
        dialogBack();
    }
}
