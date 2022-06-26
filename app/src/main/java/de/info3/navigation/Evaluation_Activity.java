package de.info3.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//abfrage wir eine Tranigseinheit abeglealufen ist

public class Evaluation_Activity extends AppCompatActivity {
    private int followingExercice;
    private Bundle bundle;
    private Exercice exercice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        ExerciceDao exerciceDao = db.exerciceDao();
        Intent intent = getIntent();
        this.bundle = intent.getExtras();
        this.followingExercice = (int) this.bundle.get("followingExercice");
        List<Exercice> exerciceList = new ArrayList<>();
        exerciceList = exerciceDao.getAllExercices();
        //in dem Bundel der die Exercice-Activity startet, werden immer 3 Exercices übergeben, soll nur eine gemacht werden, beginnt der int following Exercice mit 3, sont mit 1
        int id=this.bundle.getInt("Exercice" + followingExercice);
        Exercice exercice = exerciceList.get(id);
        this.followingExercice = this.followingExercice + 1;
        this.bundle.putInt("followingExercice", followingExercice);
        this.exercice=exercice;


        TextView exerciceTitle = findViewById(R.id.titel_exercice);
        TextView viewSuccesQuestion = findViewById(R.id.questionView);

        String titel = exercice.getTitle();
        exerciceTitle.setText(titel);
        String succesQuestion = exercice.getSuccesQuestion();
        viewSuccesQuestion.setText(succesQuestion);

        ListView lv = findViewById(R.id.list_view_evaluation);

        ArrayList<String> list = new ArrayList<>();
        list.add(exercice.getAnswer1());
        list.add(exercice.getAnswer2());
        list.add(exercice.getAnswer3());
        list.add(exercice.getAnswer4());
        list.add(exercice.getAnswer5());
        list.add("Ich habe die Übung nicht versucht");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {         //je nach auswahl werden verschieden viele levelpunkte gutgeschrieben
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
                ProfileDao profileDao = db.profileDao();
                List<Profile> profileList = profileDao.getProfile();
                Profile profile = profileList.get(0);
                int positionId = i;
                int levelpoints;
                int bonuspoints;
                int minimuLevelpoints = 0;
                int maximumLevelpoints = 100;
                levelpoints = profile.getLevelPoints();
                bonuspoints = profile.getBonusPoints();
                if (positionId == 4) {
                    levelpoints = levelpoints + 2;
                    if (levelpoints > maximumLevelpoints) {
                        levelpoints = maximumLevelpoints;
                    }

                    bonuspoints = bonuspoints + exercice.getBonusPoints();
                    profile.setBonusPoints(bonuspoints);
                    profile.setLevelPoints(levelpoints);
                    profileDao.insertProfile(profile);
                    startNextActivity(2, exercice.getBonusPoints());
                } else if (positionId == 3) {
                    levelpoints = levelpoints + 1;
                    if (levelpoints > maximumLevelpoints) {
                        levelpoints = maximumLevelpoints;
                    }
                    bonuspoints = bonuspoints + exercice.getBonusPoints();
                    profile.setBonusPoints(bonuspoints);
                    profile.setLevelPoints(levelpoints);
                    profileDao.insertProfile(profile);
                    startNextActivity(1, exercice.getBonusPoints());
                } else if (positionId == 2) {
                    bonuspoints = bonuspoints + exercice.getBonusPoints();
                    profile.setBonusPoints(bonuspoints);
                    profile.setLevelPoints(levelpoints);
                    profileDao.insertProfile(profile);
                    startNextActivity(0, exercice.getBonusPoints());
                } else if (positionId == 1) {
                    if (levelpoints > minimuLevelpoints) {
                        levelpoints = levelpoints - 1;
                    }
                    bonuspoints = bonuspoints + exercice.getBonusPoints();
                    profile.setBonusPoints(bonuspoints);
                    profile.setLevelPoints(levelpoints);
                    profileDao.insertProfile(profile);
                    startNextActivity(-1, exercice.getBonusPoints());
                } else if (positionId == 0) {
                    levelpoints = levelpoints - 2;
                    if (levelpoints < minimuLevelpoints) {
                        levelpoints = minimuLevelpoints;
                    }
                    bonuspoints = bonuspoints + exercice.getBonusPoints();
                    profile.setBonusPoints(bonuspoints);
                    profile.setLevelPoints(levelpoints);
                    profileDao.insertProfile(profile);
                    startNextActivity(-2, exercice.getBonusPoints());
                } else {
                    startNextActivity(0, 0);
                }

            }
        });

    }



    private void startNextActivity(int wonLevelPoints, int wonBonusPoints) {
        if (wonBonusPoints==0) {                                                                    //wenn die übung nicht gemacht wurde öffnet sich ein dialog und frag weiterres vorgehen ab
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Wähle eine der Optionen unten:") //Strings in Ressourcen auslagern
                    .setTitle("Bist du sicher?");

            builder.setPositiveButton("andere Übung wählen", new DialogInterface.OnClickListener() {



                public void onClick(DialogInterface dialog, int id) {
                    Intent intent =new Intent(Evaluation_Activity.this,PickExercice.class);
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("Übung wiederholen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                   Intent intent=new Intent(Evaluation_Activity.this, Training.class);
                   intent.putExtra("id",exercice.getId());
                   startActivity(intent);
                }
            });
            builder.setNeutralButton("Übungen abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(Evaluation_Activity.this, MainActivity.class);
                    startActivity(intent);
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();

        } else {                                                                                    //wenn die übung gemacht wurde wird ein toast angezeigt und wechselt in main acitvity
            Toast.makeText(Evaluation_Activity.this, "Gewonnene Levelpunkte: " + wonLevelPoints + "\nGewonnene Bonuspunkte: " + wonBonusPoints, Toast.LENGTH_LONG).show();
            if (this.followingExercice > 3) {
                Intent tent = new Intent(Evaluation_Activity.this, MainActivity.class);  //soll ermöglichen 3 Übungn in Folge zu machen noch nicht implimentiert
                startActivity(tent);
            } else {
                Intent tent = new Intent(Evaluation_Activity.this, Training.class);
                tent.putExtras(this.bundle);
                startActivity(tent);
            }

        }
    }
}