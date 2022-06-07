package de.info3.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class Training extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView Timer_text_view;
    private ImageButton ButtonStart;
    private ImageButton ButtonReset;
    private ImageButton ButtonPause;
    private ImageButton ButtonBack;
    private android.os.CountDownTimer CountDownTimer;
    private Button ButtonTest;

    private boolean TimerRunning;

    private long TimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Timer_text_view = findViewById(R.id.timerTextView);


        ButtonPause = findViewById(R.id.pause_button);
        ButtonPause.setVisibility(View.GONE);

        ButtonStart = findViewById(R.id.play_button);

        ButtonReset = findViewById(R.id.reset_button);
        ButtonReset.setVisibility(View.GONE);

        ButtonBack = findViewById(R.id.button_back);




        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+"u2", null, c.getPackageName());
        ((ImageView)findViewById(R.id.imageView)).setImageResource(id);

        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class,
                "fitAtWorkDatabase").allowMainThreadQueries().build();
        ExerciceDao exerciceDao = db.exerciceDao();
        List<Exercice> exerciceList =exerciceDao.getExercicesWithDifficulty(3);
        Exercice exercice=exerciceList.get(0);
        String titel=exercice.getTitle();

        TextView textViewTestTitle=findViewById(R.id.text_view_titel_test);
        textViewTestTitle.setText(titel);

        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                    ButtonStart.setVisibility(View.GONE);
                    ButtonPause.setVisibility(View.VISIBLE);


                }
            }
        });

        ButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning){
                    pauseTimer();
                    ButtonPause.setVisibility(View.GONE);

                }
            }
        });

        ButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning){
                    pauseTimer();
                }
                openacitivity_main();
            }
        });

        /*ButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openacitivity_pick();
            }
        });*/


    }

    private void startTimer() {
        CountDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                TimerRunning = false;
                ButtonStart.setVisibility(View.GONE);
                ButtonStart.setVisibility(View.INVISIBLE);
                ButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        TimerRunning = true;
        ButtonStart.setVisibility(View.VISIBLE);
        ButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        CountDownTimer.cancel();
        TimerRunning = false;
        ButtonStart.setVisibility(View.VISIBLE);
        ButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        ButtonReset.setVisibility(View.INVISIBLE);
        ButtonStart.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        Timer_text_view.setText(timeLeftFormatted);
    }



    public void openacitivity_main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openacitivity_pick() {
        Intent intent = new Intent(this, PickExercice.class);
        startActivity(intent);
    }

}