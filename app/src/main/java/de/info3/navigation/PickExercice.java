package de.info3.navigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;
import java.util.Random;


public class PickExercice extends AppCompatActivity {

    private ImageButton ButtonBack;
    private ImageView ButtonExercise1;
    private ImageView ButtonExercise2;
    private ImageView ButtonExercise3;
    private ImageButton ButtonInfo1;
    private ImageButton ButtonInfo2;
    private ImageButton ButtonInfo3;


    private Random rn = new Random();
    private int random1 = 0;   //random zahl von 1-ende des arrys auslesen nicht von 0 da ansonsten zu bug kommen kann mit if schleife in Training
    private int random2 = 0;   //random zahl von 1-ende des arrys auslesen nicht von 0 da ansonsten zu bug kommen kann mit if schleife in Training
    private int random3 = 0;   //random zahl von 1-ende des arrys auslesen nicht von 0 da ansonsten zu bug kommen kann mit if schleife in Training


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pick_exercice);

        ButtonBack = findViewById(R.id.ButtonBack);
        ButtonExercise1 = findViewById(R.id.ButtonExercice1);
        ButtonExercise2 = findViewById(R.id.ButtonExercice2);
        ButtonExercise3 = findViewById(R.id.ButtonExercice3);
        ButtonInfo1 = findViewById(R.id.infobuttonExercice1);
        ButtonInfo2 = findViewById(R.id.infobuttonExercice2);
        ButtonInfo3 = findViewById(R.id.infobuttonExercice3);


        Context c = getApplicationContext();
        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class,
                "fitAtWorkDatabase").allowMainThreadQueries().build();
        ExerciceDao exerciceDao = db.exerciceDao();


        List<Exercice> exerciceList1 = exerciceDao.getExercicesWithDifficulty(1);        //lädt verschieden dinge welche bötigt werden aus der datenbank

        this.random1 = this.rn.nextInt(exerciceList1.size());

        Exercice exercice1 = exerciceList1.get(this.random1);
        int id1 = exercice1.getId();

        String picture1 = exercice1.getPictureLink();  //Lädt bildpfad zur liste exercice1

        String titel1 = exercice1.getTitle();

        TextView TextViewtestTitel1 = findViewById(R.id.textViewtitel1);
        TextViewtestTitel1.setText(titel1);

        int id11 = c.getResources().getIdentifier(picture1, "drawable", c.getPackageName());
        ((ImageView) findViewById(R.id.ButtonExercice1)).setImageResource(id11);


        List<Exercice> exerciceList2 = exerciceDao.getExercicesWithDifficulty(2);

        this.random2 = this.rn.nextInt(exerciceList2.size()); //nimmt nur eine random wie lng das array ist

        Exercice exercice2 = exerciceList2.get(this.random2);
        int id2 = exercice2.getId();  //id1 = id der zufällig gewählte übung

        String picture2 = exercice2.getPictureLink();  //Lädt bildpfad zur liste exercice1

        String titel2 = exercice2.getTitle();

        TextView TextViewtestTitel2 = findViewById(R.id.textViewtitel2);
        TextViewtestTitel2.setText(titel2);

        int id22 = c.getResources().getIdentifier(picture2, "drawable", c.getPackageName());
        ((ImageView) findViewById(R.id.ButtonExercice2)).setImageResource(id22);


        List<Exercice> exerciceList3 = exerciceDao.getExercicesWithDifficulty(3);        //sollte lvl 3 pullen
        this.random3 = this.rn.nextInt(exerciceList3.size()); //nimmt nur eine random wie lng das array ist

        Exercice exercice3 = exerciceList3.get(random3);
        int id3 = exercice3.getId();
        String picture3 = exercice3.getPictureLink();  //Lädt bildpfad zur liste exercice1

        String titel3 = exercice3.getTitle();

        TextView TextViewtestTitel3 = findViewById(R.id.textViewtitel3);
        TextViewtestTitel3.setText(titel3);


        int id33 = c.getResources().getIdentifier("drawable/" + picture3, null, c.getPackageName());
        ((ImageView) findViewById(R.id.ButtonExercice3)).setImageResource(id33);


        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openacitivity_main();
            }
        });

        ButtonExercise1.setOnClickListener(new View.OnClickListener() {     //wartet auf klick des buttons und übergibt in neue activity die id der übung
            @Override
            public void onClick(View v) {


                Intent passIntent = new Intent(getApplicationContext(), Training.class);
                passIntent.putExtra("id", id1);
                startActivity(passIntent);

                openacitivity_training();
            }
        });

        ButtonExercise2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passIntent = new Intent(getApplicationContext(), Training.class);
                passIntent.putExtra("id", id2);
                startActivity(passIntent);

                openacitivity_training();
            }
        });

        ButtonExercise3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passIntent = new Intent(getApplicationContext(), Training.class);
                passIntent.putExtra("id", id3);
                startActivity(passIntent);

                openacitivity_training();
            }
        });

        ButtonInfo1.setOnClickListener(new View.OnClickListener() {         //bei klick wird popup fenster geöfnet und info text angeziegt
            @Override
            public void onClick(View view) {

                Context c = getApplicationContext();
                AppDatabase db = Room.databaseBuilder(c,
                        AppDatabase.class,
                        "fitAtWorkDatabase").allowMainThreadQueries().build();


                ExerciceDao exerciceDao = db.exerciceDao();
                List<Exercice> exerciceList = exerciceDao.getExercicesWithDifficulty(1); //solte lvl 1 sein
                Exercice exercice = exerciceList.get(PickExercice.this.random1);                                          //solte id verwendn um oben gezogene übungs erklärung zu laden
                String longdecription = exercice.getLongDecription();

                AlertDialog.Builder builder = new AlertDialog.Builder(PickExercice.this);

                builder.setMessage(longdecription) //Strings in Ressourcen auslagern
                        .setTitle("Das Musst du Machen:");


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        ButtonInfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = getApplicationContext();
                AppDatabase db = Room.databaseBuilder(c,
                        AppDatabase.class,
                        "fitAtWorkDatabase").allowMainThreadQueries().build();


                ExerciceDao exerciceDao = db.exerciceDao();
                List<Exercice> exerciceList = exerciceDao.getExercicesWithDifficulty(2);  //solte lvl 2 sein
                Exercice exercice = exerciceList.get(PickExercice.this.random2);                                           //solte random zahl aus größe des arry picken nicht immer 0
                String longdecription = exercice.getLongDecription();

                AlertDialog.Builder builder = new AlertDialog.Builder(PickExercice.this);

                builder.setMessage(longdecription) //Strings in Ressourcen auslagern
                        .setTitle("Das Musst du Machen:");


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        ButtonInfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Context c = getApplicationContext();
                AppDatabase db = Room.databaseBuilder(c,
                        AppDatabase.class,
                        "fitAtWorkDatabase").allowMainThreadQueries().build();


                ExerciceDao exerciceDao = db.exerciceDao();
                List<Exercice> exerciceList = exerciceDao.getExercicesWithDifficulty(3);
                Exercice exercice = exerciceList.get(PickExercice.this.random3);                                            //solte random zahl aus größe des arry picken nicht immer 0
                String longdecription = exercice.getLongDecription();

                AlertDialog.Builder builder = new AlertDialog.Builder(PickExercice.this);

                builder.setMessage(longdecription) //Strings in Ressourcen auslagern
                        .setTitle("Das Musst du Machen:");


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }


    public void openacitivity_main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openacitivity_training() {
        /*Intent intent = new Intent(this, Training.class);
        startActivity(intent);*/
    }


}
