package de.info3.navigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;
import java.util.Locale;

public class Training extends AppCompatActivity {

    private long startTimeInMs;
    private TextView Timer_text_view;
    private ImageButton ButtonStart;
    private ImageButton ButtonReset;
    private ImageButton ButtonPause;
    private ImageButton ButtonBack;
    private android.os.CountDownTimer CountDownTimer;
    private boolean TimerRunning;
    private long TimeLeftInMillis = startTimeInMs;
    public ImageButton ButtonInfo;
    long time;

    private Exercice excercise1;
    String longdecription;

    int receiveValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Timer_text_view = findViewById(R.id.timerTextView);


        ButtonPause = findViewById(R.id.pause_button);
        ButtonPause.setVisibility(View.GONE);

        ButtonInfo = findViewById(R.id.button_Info);

        ButtonStart = findViewById(R.id.play_button);

        ButtonReset = findViewById(R.id.reset_button);
        ButtonReset.setVisibility(View.GONE);

        ButtonBack = findViewById(R.id.button_back);

        receiveValue = getIntent().getIntExtra("id", 0);

        Context c = getApplicationContext();
        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class,
                "fitAtWorkDatabase").allowMainThreadQueries().build();
        ExerciceDao exerciceDao = db.exerciceDao();


        if (receiveValue > 0) {  //checkt das recivevalue nicht 0 ist und lädt dann auf grundlage des übergebenen id werts die gewollten dinge aus der datenbank

            this.excercise1 = exerciceDao.getExcerciseById(receiveValue);

            String picture1 = excercise1.getPictureLink();  //Lädt bildpfad zur liste exercice

            String titel = excercise1.getTitle();

            TextView TextViewtestTitel = findViewById(R.id.textViewtitel);
            TextViewtestTitel.setText(titel);

            this.time = excercise1.getTimeInMs();
            this.startTimeInMs=this.time;
            this.TimeLeftInMillis=this.startTimeInMs;
            longdecription = excercise1.getLongDecription();

            int id11 = c.getResources().getIdentifier(picture1, "drawable", c.getPackageName());
            ((ImageView) findViewById(R.id.imageView)).setImageResource(id11);

        }

        ButtonStart.setOnClickListener(new View.OnClickListener() {  //buttons ermöglichen kontrolle über den angezeigten timer
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
                if (TimerRunning) {
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


        Button buttonSkipp=findViewById(R.id.button_skip);
        buttonSkipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNextActivity();
            }
        });

        updateCountDownText();

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning) {
                    pauseTimer();
                }
                openacitivity_main();
            }
        });

        ButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning) {
                    pauseTimer();
                    ButtonPause.setVisibility(View.GONE);

                }


                AlertDialog.Builder builder = new AlertDialog.Builder(Training.this);

                builder.setMessage(longdecription) //Strings in Ressourcen auslagern
                        .setTitle("Das musst du machen:");


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


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
                toNextActivity();

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
        TimeLeftInMillis = startTimeInMs;
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

    private void toNextActivity(){
        TimerRunning = false;
        ButtonStart.setVisibility(View.GONE);
        ButtonStart.setVisibility(View.INVISIBLE);
        ButtonReset.setVisibility(View.VISIBLE);
        Bundle bundle = new Bundle();
        bundle.putInt("followingExercice", 3);
        bundle.putInt("Exercice3", excercise1.getId());
        Intent intent = new Intent(Training.this, Evaluation_Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openacitivity_main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}