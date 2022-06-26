package de.info3.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//wird nur bei ersten appstart augerufen stuft fähigkeiten de nutzers eun
public class FirstActivity extends AppCompatActivity {
    private TextView questionView;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Profile profile;
    private ProfileDao profileDao;
    private SettingDao settingDao;
    private StringBuilder stringBuilder = new StringBuilder();
    private int countDialogsUsed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        this.profileDao = db.profileDao();
        this.settingDao=db.settingDao();
        this.profile=profileDao.getProfile().get(0);
        this.questionView=findViewById(R.id.textView5);
        this.button0=findViewById(R.id.button_no_sport);
        this.button1=findViewById(R.id.button1);
        this.button2=findViewById(R.id.button2);
        this.button3=findViewById(R.id.button3);
        this.button4=findViewById(R.id.button4);

        button0.setOnClickListener(new View.OnClickListener() {     //buttons fragen sportlichkeit ab
            @Override
            public void onClick(View view) {
                questionBodyparts();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setLevelPoints(10);
                profileDao.insertProfile(profile);
                questionBodyparts();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setLevelPoints(20);
                profileDao.insertProfile(profile);
                questionBodyparts();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setLevelPoints(30);
                profileDao.insertProfile(profile);
                questionBodyparts();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setLevelPoints(50);
                profileDao.insertProfile(profile);
                questionBodyparts();
            }
        });

    }

    private void questionBodyparts(){           //fragt körperliche einschrenkungen ab
        this.questionView.setText("Hast du körperliche\nEinschränkungen?");
        this.button0.setVisibility(View.INVISIBLE);
        this.button2.setVisibility(View.INVISIBLE);
        this.button4.setVisibility(View.INVISIBLE);
        this.button1.setText("JA");
        this.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyPartsResticted();
            }
        });
        this.button3.setText("NEIN");
        this.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setLevelPoints(profile.getLevelPoints()+50);
                profileDao.insertProfile(profile);
                for(int i=0;i<5;i++){       //ohne körperliche inschrenkungn wird der wert für jedes körperteil auf 1 gesetzt
                    stringBuilder.append('1');
                }
                nextStep();
            }
        });
    }


    private void bodyPartsResticted(){      //öffnet dialoge für jedes körperteil

        showQuestionDialog("deine Beine");
        showQuestionDialog("deinen Bauch");
        showQuestionDialog("deinen Rücken");
        showQuestionDialog("deine Brust");
        showQuestionDialog("deine Arme");


    }

    private void showQuestionDialog(String bodyPart){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final char[] char1 = new char[1];
        final boolean[] userAnswered=new boolean[1];

        builder.setMessage("Hast du Einschränkungen, die "+bodyPart+" betreffen?") //Strings in Ressourcen auslagern
                .setTitle("Geh ins Detail...");

        builder.setPositiveButton("JA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                char1[0] ='0';


                stringBuilder.append('0');
                userAnswered[0]=true;
                countDialogsUsed++;
                if(countDialogsUsed==5){
                    nextStep();
                }
            }
        });


        builder.setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                profile.setLevelPoints(profile.getLevelPoints()+10);
                profileDao.insertProfile(profile);
                char1[0]='1';
                stringBuilder.append('1');
                userAnswered[0]=true;
                countDialogsUsed++;
                if(countDialogsUsed==5){
                    nextStep();
                }
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void nextStep(){ //speichert ergebnisse und öffent mainactivity
        Setting setting=settingDao.getSettings().get(0);
        setting.setBodyPartsUsable(this.stringBuilder.toString());
        settingDao.insertSetting(setting);
        Intent intent=new Intent(FirstActivity.this,MainActivity.class);
        intent.putExtra("origin","firststart");
        startActivity(intent);
    }
}