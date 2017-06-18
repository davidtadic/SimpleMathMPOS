package com.example.david.simplemath.activities.main.play;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
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
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.simplemath.R;
import com.example.david.simplemath.activities.main.MainActivity;
import com.example.david.simplemath.activities.main.TrainingActivity;
import com.example.david.simplemath.activities.practise.PractiseArrayActivity;
import com.example.david.simplemath.activities.practise.PractiseRomanLesson;
import com.example.david.simplemath.database.DatabaseHelper;
import com.example.david.simplemath.models.ArrayModel;
import com.example.david.simplemath.services.BackgroundMusicService;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PlayArrayActivity extends Activity {

    private ImageButton back;
    private Button question1;
    private Button question2;
    private Button question3;
    private Button question4;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private TextView score;

    private DatabaseHelper dbHelper = null;
    private List<ArrayModel> arrayModelList;
    private int counter;
    private int points;

    private int numberAnswer;
    private String questionButtonValue;

    Context context = this;

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
        setContentView(R.layout.activity_play_array);

        back = (ImageButton) findViewById(R.id.back_array_play);
        question1 = (Button) findViewById(R.id.question_array1_play);
        question2 = (Button) findViewById(R.id.question_array2_play);
        question3 = (Button) findViewById(R.id.question_array3_play);
        question4 = (Button) findViewById(R.id.question_array4_play);
        answer1 = (Button) findViewById(R.id.answer1_array_play);
        answer2 = (Button) findViewById(R.id.answer2_array_play);
        answer3 = (Button) findViewById(R.id.answer3_array_play);
        answer4 = (Button) findViewById(R.id.answer4_array_play);
        score = (TextView) findViewById(R.id.score_array_play);

        sharedPreferencesScore = getSharedPreferences("highscore", MODE_PRIVATE);
        points = sharedPreferencesScore.getInt("score", 0);

        score.setText(String.valueOf(points));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBack();
            }
        });

        dbHelper = new DatabaseHelper(PlayArrayActivity.this);
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

        arrayModelList = dbHelper.getQuestionsArray();
        Collections.shuffle(arrayModelList);

        // Log.e("DA LI RADI", arrayModelList.get(1).toString());

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

    public void dialogBack() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_back_play);
        dialog.setCancelable(false);
        ImageButton yes = (ImageButton) dialog.findViewById(R.id.yes);
        ImageButton no = (ImageButton) dialog.findViewById(R.id.cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayArrayActivity.this, MainActivity.class);
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

    public void changeQuestion(int counter) {
        if (counter <= 2) {
            setEnabledButtons();
            returnBackground();
            setAnswer(arrayModelList.get(counter));
            checkAnswer(arrayModelList.get(counter), counter);
        } else {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_roman_play);
            dialog.setCancelable(false);

            ImageButton ok = (ImageButton) dialog.findViewById(R.id.ok_practise);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferencesScore.edit();
                    editor.putInt("score", points);
                    editor.apply();

                    Intent i = new Intent(PlayArrayActivity.this, PractiseRomanLesson.class);
                    i.putExtra("PlayIntentFlag", "FromArrayToRomanLesson");
                    startActivity(i);
                    finish();
                }
            });

            dialog.show();

        }

    }

    public void checkAnswer(ArrayModel arrayModel, int counter) {
        final int correctAnswer = arrayModel.getCorrectAnswer();
        counter++;

        final int finalCounter = counter;

        answer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        answer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        answer3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        answer4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, dragShadowBuilder, v, 0);
                return true;
            }
        });

        question1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();
                View view = (View) event.getLocalState();
                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        question1.setBackgroundResource(R.drawable.question_array_selected);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        question1.setBackgroundResource(R.drawable.question_arrayy);
                        break;
                    case DragEvent.ACTION_DROP:
                        Button draggedButton = (Button) view.findViewById(view.getId());
                        numberAnswer = Integer.valueOf(draggedButton.getText().toString());
                        Button questionButton = (Button) v.findViewById(v.getId());
                        questionButtonValue = questionButton.getText().toString();

                        if (numberAnswer == correctAnswer && questionButtonValue.equals("_")) {
                            question1.setBackgroundResource(R.drawable.question_array_correct);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    correctDialog(finalCounter);
                                }
                            }, 1000);

                            points += 10;
                        } else {
                            question1.setBackgroundResource(R.drawable.question_array_wrong);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    wrongDialog(finalCounter);
                                }
                            }, 1000);
                        }

                        question1.setText(String.valueOf(numberAnswer));
                        break;
                }
                return true;
            }
        });

        question2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();
                View view = (View) event.getLocalState();
                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        question2.setBackgroundResource(R.drawable.question_array_selected);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        question2.setBackgroundResource(R.drawable.question_arrayy);
                        break;
                    case DragEvent.ACTION_DROP:
                        Button draggedButton = (Button) view.findViewById(view.getId());
                        numberAnswer = Integer.valueOf(draggedButton.getText().toString());
                        Button questionButton = (Button) v.findViewById(v.getId());
                        questionButtonValue = questionButton.getText().toString();

                        if (numberAnswer == correctAnswer && questionButtonValue.equals("_")) {
                            question2.setBackgroundResource(R.drawable.question_array_correct);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    correctDialog(finalCounter);
                                }
                            }, 1000);

                            points += 10;
                        } else {
                            question2.setBackgroundResource(R.drawable.question_array_wrong);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    wrongDialog(finalCounter);
                                }
                            }, 1000);
                        }

                        question2.setText(String.valueOf(numberAnswer));
                        break;
                }
                return true;
            }
        });

        question3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();
                View view = (View) event.getLocalState();
                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        question3.setBackgroundResource(R.drawable.question_array_selected);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        question3.setBackgroundResource(R.drawable.question_arrayy);
                        break;
                    case DragEvent.ACTION_DROP:
                        Button draggedButton = (Button) view.findViewById(view.getId());
                        numberAnswer = Integer.valueOf(draggedButton.getText().toString());
                        Button questionButton = (Button) v.findViewById(v.getId());
                        questionButtonValue = questionButton.getText().toString();

                        if (numberAnswer == correctAnswer && questionButtonValue.equals("_")) {
                            question3.setBackgroundResource(R.drawable.question_array_correct);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    correctDialog(finalCounter);
                                }
                            }, 1000);

                            points += 10;
                        } else {
                            question3.setBackgroundResource(R.drawable.question_array_wrong);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    wrongDialog(finalCounter);
                                }
                            }, 1000);
                        }

                        question3.setText(String.valueOf(numberAnswer));
                        break;
                }
                return true;
            }
        });

        question4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();
                View view = (View) event.getLocalState();
                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        question4.setBackgroundResource(R.drawable.question_array_selected);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        question4.setBackgroundResource(R.drawable.question_arrayy);
                        break;
                    case DragEvent.ACTION_DROP:
                        Button draggedButton = (Button) view.findViewById(view.getId());
                        numberAnswer = Integer.valueOf(draggedButton.getText().toString());
                        Button questionButton = (Button) v.findViewById(v.getId());
                        questionButtonValue = questionButton.getText().toString();

                        if (numberAnswer == correctAnswer && questionButtonValue.equals("_")) {
                            question4.setBackgroundResource(R.drawable.question_array_correct);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    correctDialog(finalCounter);
                                }
                            }, 1000);

                            points += 10;
                        } else {
                            question4.setBackgroundResource(R.drawable.question_array_wrong);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    wrongDialog(finalCounter);
                                }
                            }, 1000);
                        }

                        question4.setText(String.valueOf(numberAnswer));
                        break;
                }
                return true;
            }
        });

        score.setText(String.valueOf(points));

    }

    public void setAnswer(ArrayModel arrayModel) {
        List<Integer> answers = arrayModel.getMixedArrayQuestions();

        String expression = arrayModel.getExpression();
        String[] expressionArray = expression.split(" ");
        question1.setText(expressionArray[0]);
        question2.setText(expressionArray[1]);
        question3.setText(expressionArray[2]);
        question4.setText(expressionArray[3]);

        answer1.setText(String.valueOf(answers.get(0)));
        answer2.setText(String.valueOf(answers.get(1)));
        answer3.setText(String.valueOf(answers.get(2)));
        answer4.setText(String.valueOf(answers.get(3)));

    }

    private void returnBackground() {
        question1.setBackgroundResource(R.drawable.question_arrayy);
        question2.setBackgroundResource(R.drawable.question_arrayy);
        question3.setBackgroundResource(R.drawable.question_arrayy);
        question4.setBackgroundResource(R.drawable.question_arrayy);

    }

    private void setEnabledButtons() {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
    }

    private void setDisabledButtons() {
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        answer4.setEnabled(false);
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

    @Override
    public void onBackPressed() {
        dialogBack();
    }
}
