package de.info3.navigation;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import de.info3.navigation.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firststart();

             binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        SettingDao settingDao = db.settingDao();
        Intent intent = getIntent();

        if((settingDao.getSettings().get(0).isShowDailyTip()& intent.hasCategory(Intent.CATEGORY_LAUNCHER)|| Objects.equals(intent.getStringExtra("origin"), "firststart"))){ //p??ft ob activity von auserhalb gestartet wurd (launbcher activity) und ob dailytips in einstellungen aktiviert sind wenn ja werden daly tips aufgerufen
            showDailyTip();
        }


    }

    public void firststart() {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        ProfileDao profileDao = db.profileDao();
        SettingDao settingDao = db.settingDao();


        try {       //versucht setting aus datenbank zu laden

            Setting setting = settingDao.getSettings().get(0);

        } catch (IndexOutOfBoundsException outOfBoundsException) {          //wenn es kein setting gibt wird catch kalmmer ausgef??hrt  erzeug default setting und profil l??dt ??bungen u. dalytips und startet first activity
            Setting setting = new Setting(1);
            settingDao.insertSetting(setting);
            Profile profile = new Profile(1);
            profileDao.insertProfile(profile);
            gerateTestFile();
            loadHardCodedExercices();
            loadHardCodedDailyTips();
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);


        }
    }

    private void loadHardCodedDailyTips() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        DailyTipDao dailyTipDao = db.dailyTipDao();

        DailyTip dailyTip1 = new DailyTip(1, "Yoga ??bungen sind auch gut f??r dein Immunsystem", "Laut einer Norwegischen Studie regt Yoga die Zirkulation der wei??en Blutk??rperchen an, die f??r unsere Immunabwehr verantwortlich sind.");
        DailyTip dailyTip2 = new DailyTip(2, "Yoga am Arbeitsplatz kann die Produktivit??t erh??hen", "Yoga ??bungen steigern deine Konzentration und reduzieren das Risiko von R??ckenschmerzen.");

        List<DailyTip> dailyTips = new ArrayList<>();
        dailyTips.add(dailyTip1);
        dailyTips.add(dailyTip2);
        dailyTipDao.insertDailyTips(dailyTips);

    }

    private void loadHardCodedExercices() {
        Exercice exercice1 = new Exercice(1, "Viparita-Karani Level 3", 180000, "00111", 3, 10, 5, "viparita_karani", "Mache eine ??bung.", "Lege dich auf dem R??cken vor einer Wand ab. Strecke nun die Beine senkrecht nach oben und st??tze sie an der Wand ab. Lasse los und bleibe einige Minuten in der Asana (in der ruhenden K??rperhaltung).", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");//aktuell m??sste Antwort 1 bitte die beste sein
        Exercice exercice2 = new Exercice(2, "Viparita-Karani Level 2", 180000, "00111", 2, 10, 5, "viparita_karani", "Mache eine ??bung.", "Lege dich auf dem R??cken vor einer Wand ab. Strecke nun die Beine senkrecht nach oben und st??tze sie an der Wand ab. Lasse los und bleibe einige Minuten in der Asana (in der ruhenden K??rperhaltung).", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice3 = new Exercice(3, "Viparita-Karani Level 1", 180000, "00111", 1, 10, 5, "viparita_karani", "Mache eine ??bung.", "Lege dich auf dem R??cken vor einer Wand ab. Strecke nun die Beine senkrecht nach oben und st??tze sie an der Wand ab. Lasse los und bleibe einige Minuten in der Asana (in der ruhenden K??rperhaltung).", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice4 = new Exercice(4, "Vierf??sslerstand Level 3", 180000, "10111", 3, 10, 5, "vierfuesslerstand", "Mache eine ??bung.", "F??r diese ??bung musst Du Dich in die kniende Position begeben. Generell ist es ratsam, eine Trainingsmatte f??r die ??bungen zu nutzen. Die Knie sollten f??r diese ??bung unter der H??fte sein und die Arme sollten sich unter den Schultern befinden. Anschlie??end solltest Du den linken Arm heben, bis der Arm auf Schulterh??he ist. Gleichzeitig musst Du das rechte Bein auf H??fth??he heben. \nPo und Bauch m??ssen bei dieser ??bung angespannt werden. Der Kopf darf nicht nach unten zeigen. Diese ??bung sollte 40-mal wiederholt werden. Nach den Wiederholungen muss die Seite gewechselt werden.", "Wieviele Durchl??ufe hast du geschafft?", "0-8", "8-16", "16-24", "24-32", "32-40");
        Exercice exercice5 = new Exercice(5, "Vierf??sslerstand Level 2", 180000, "10111", 2, 10, 5, "vierfuesslerstand", "Mache eine ??bung.", "F??r diese ??bung musst Du Dich in die kniende Position begeben. Generell ist es ratsam, eine Trainingsmatte f??r die ??bungen zu nutzen. Die Knie sollten f??r diese ??bung unter der H??fte sein und die Arme sollten sich unter den Schultern befinden. Anschlie??end solltest Du den linken Arm heben, bis der Arm auf Schulterh??he ist. Gleichzeitig musst Du das rechte Bein auf H??fth??he heben. \nPo und Bauch m??ssen bei dieser ??bung angespannt werden. Der Kopf darf nicht nach unten zeigen. Diese ??bung sollte 40-mal wiederholt werden. Nach den Wiederholungen muss die Seite gewechselt werden.", "Wieviele Durchl??ufe hast du geschafft?", "0-8", "8-16", "16-24", "24-32", "32-40");
        Exercice exercice6 = new Exercice(6, "Vierf??sslerstand Level 1", 180000, "10111", 1, 10, 5, "vierfuesslerstand", "Mache eine ??bung.", "F??r diese ??bung musst Du Dich in die kniende Position begeben. Generell ist es ratsam, eine Trainingsmatte f??r die ??bungen zu nutzen. Die Knie sollten f??r diese ??bung unter der H??fte sein und die Arme sollten sich unter den Schultern befinden. Anschlie??end solltest Du den linken Arm heben, bis der Arm auf Schulterh??he ist. Gleichzeitig musst Du das rechte Bein auf H??fth??he heben. \nPo und Bauch m??ssen bei dieser ??bung angespannt werden. Der Kopf darf nicht nach unten zeigen. Diese ??bung sollte 40-mal wiederholt werden. Nach den Wiederholungen muss die Seite gewechselt werden.", "Wieviele Durchl??ufe hast du geschafft?", "0-8", "8-16", "16-24", "24-32", "32-40");
        Exercice exercice7 = new Exercice(7, "seitlicher Plank Level 1", 180000, "00010", 1, 10, 5, "seitlicherplank", "Mache eine ??bung", "Seitliche Planks trainieren die seitlichen Bauchmuskeln. Dazu legst Du Dich zun??chst seitlich auf Deine Trainingsmatte. Dann stellst Du den rechten Ellbogen unter Deine Schulter. Anschlie??end hebst Du Deinen K??rper vom Boden ab und streckst die Beine durch. Dein K??rper muss von Kopf bis zu den F????en eine Linie bilden. Der Bauch sollte bei der ??bung angespannt sein. Die Position sollte 30 Sekunden lang gehalten werden.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice8 = new Exercice(8, "seitlicher Plank Level 2", 180000, "00010", 2, 10, 5, "seitlicherplank", "Mache eine ??bung", "Seitliche Planks trainieren die seitlichen Bauchmuskeln. Dazu legst Du Dich zun??chst seitlich auf Deine Trainingsmatte. Dann stellst Du den rechten Ellbogen unter Deine Schulter. Anschlie??end hebst Du Deinen K??rper vom Boden ab und streckst die Beine durch. Dein K??rper muss von Kopf bis zu den F????en eine Linie bilden. Der Bauch sollte bei der ??bung angespannt sein. Die Position sollte 30 Sekunden lang gehalten werden.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice9 = new Exercice(9, "seitlicher Plank Level 3", 300000, "00010", 3, 10, 5, "seitlicherplank", "Mache eine ??bung", "Seitliche Planks trainieren die seitlichen Bauchmuskeln. Dazu legst Du Dich zun??chst seitlich auf Deine Trainingsmatte. Dann stellst Du den rechten Ellbogen unter Deine Schulter. Anschlie??end hebst Du Deinen K??rper vom Boden ab und streckst die Beine durch. Dein K??rper muss von Kopf bis zu den F????en eine Linie bilden. Der Bauch sollte bei der ??bung angespannt sein. Die Position sollte 30 Sekunden lang gehalten werden.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice10 = new Exercice(10, "Balasana Level 1", 180000, "00110", 1, 10, 5, "balasana", "Mache eine ??bung", "Lege aus dem Fersensitz deinen Oberk??rper auf den Oberschenkel ab. Bringe die Stirn vor den Knien auf die Matte. Lege deine Arme mit nach oben zeigenden Handfl??chen neben deinem K??rper ab. Lass deinen Schultern nach unten sinken und bringe dein Ges???? tiefer in Richtung F????e. Bleibe f??r einige Atemz??ge in Balasana und entspanne dich.\n" +
                "Wiederhole einige Male.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice11 = new Exercice(11, "Balasana Level 2", 240000, "00110", 2, 10, 5, "balasana", "Mache eine ??bung", "Lege aus dem Fersensitz deinen Oberk??rper auf den Oberschenkel ab. Bringe die Stirn vor den Knien auf die Matte. Lege deine Arme mit nach oben zeigenden Handfl??chen neben deinem K??rper ab. Lass deinen Schultern nach unten sinken und bringe dein Ges???? tiefer in Richtung F????e. Bleibe f??r einige Atemz??ge in Balasana und entspanne dich.\n" +
                "Wiederhole einige Male.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice12 = new Exercice(12, "Balasana Level 3", 240000, "00110", 3, 10, 5, "balasana", "Mache eine ??bung", "Lege aus dem Fersensitz deinen Oberk??rper auf den Oberschenkel ab. Bringe die Stirn vor den Knien auf die Matte. Lege deine Arme mit nach oben zeigenden Handfl??chen neben deinem K??rper ab. Lass deinen Schultern nach unten sinken und bringe dein Ges???? tiefer in Richtung F????e. Bleibe f??r einige Atemz??ge in Balasana und entspanne dich.\n" +
                "Wiederhole einige Male.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice13 = new Exercice(13, "Bergsteigen Level 1", 180000, "11111", 1, 15, 5, "bergsteiger", "Mache eine ??bung", "Liegest??tz Ausgangsposition. Abwechselnd ein Bein vom Boden abheben und das Knie zur Brust ziehen. In Ausgangsposition zur??ckbewegen.\n Wiederhole 8x", "Wieviele Durchl??ufe hast du geschafft?", "0-1", "2-3", "4-5", "6-7", "8+");
        Exercice exercice14 = new Exercice(14, "Bergsteigen Level 2", 300000, "11111", 2, 15, 5, "bergsteiger", "Mache eine ??bung", "Liegest??tz Ausgangsposition. Abwechselnd ein Bein vom Boden abheben und das Knie zur Brust ziehen. In Ausgangsposition zur??ckbewegen.\n Wiederhole 8x", "Wieviele Durchl??ufe hast du geschafft?", "0-1", "2-3", "4-5", "6-7", "8+");
        Exercice exercice15 = new Exercice(15, "Bergsteigen Level 3", 360000, "11111", 3, 15, 5, "bergsteiger", "Mache eine ??bung", "Liegest??tz Ausgangsposition. Abwechselnd ein Bein vom Boden abheben und das Knie zur Brust ziehen. In Ausgangsposition zur??ckbewegen.\n Wiederhole 8x", "Wieviele Durchl??ufe hast du geschafft?", "0-1", "2-3", "4-5", "6-7", "8+");
        Exercice exercice16 = new Exercice(16, "Br??cke Level 1", 300000, "10111", 1, 10, 5, "bruecke", "Mache eine ??bung", "Begib Dich in R??ckenlage. Die Beine solltest Du anschlie??end anwinkeln. Dann hebst Du Dein Becken vom Boden ab. Dein K??rper sollte eine gerade Linie bilden. Bei der ??bung musst Du Beine und Po anspannen. Sobald Dein K??rper die gerade Linie bildet und Du Po und Beine angespannt hast, kannst Du die ??bungsposition zwei Sekunden lang halten. Anschlie??end kehrst Du wieder in die R??ckenlage auf den Boden zur??ck. Die ??bungen kannst Du 10-mal hintereinander durchf??hren.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice17 = new Exercice(17, "Br??cke Level 2", 360000, "10111", 2, 10, 5, "bruecke", "Mache eine ??bung", "Begib Dich in R??ckenlage. Die Beine solltest Du anschlie??end anwinkeln. Dann hebst Du Dein Becken vom Boden ab. Dein K??rper sollte eine gerade Linie bilden. Bei der ??bung musst Du Beine und Po anspannen. Sobald Dein K??rper die gerade Linie bildet und Du Po und Beine angespannt hast, kannst Du die ??bungsposition zwei Sekunden lang halten. Anschlie??end kehrst Du wieder in die R??ckenlage auf den Boden zur??ck. Die ??bungen kannst Du 10-mal hintereinander durchf??hren.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice18 = new Exercice(18, "Br??cke Level 3", 420000, "10111", 3, 10, 5, "bruecke", "Mache eine ??bung", "Begib Dich in R??ckenlage. Die Beine solltest Du anschlie??end anwinkeln. Dann hebst Du Dein Becken vom Boden ab. Dein K??rper sollte eine gerade Linie bilden. Bei der ??bung musst Du Beine und Po anspannen. Sobald Dein K??rper die gerade Linie bildet und Du Po und Beine angespannt hast, kannst Du die ??bungsposition zwei Sekunden lang halten. Anschlie??end kehrst Du wieder in die R??ckenlage auf den Boden zur??ck. Die ??bungen kannst Du 10-mal hintereinander durchf??hren.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice19 = new Exercice(19, "einseitige Br??cke Level 1", 180000, "10111", 1, 10, 5, "einseitigebruecke", "Mache eine ??bung", "F??r die ??bung musst Du Dich in R??ckenlage begeben und die Beine anwinkeln. Das rechte Bein hebst Du angewinkelt in die Luft. W??hrend der ??bung legst Du Deine H??nde auf den Hinterkopf. Anschlie??end musst Du Deine H??fte langsam heben und senken. Der Po darf bei dieser ??bung nicht die Matte ber??hren. Im Anschluss f??hrst du die ??bung auf der linken Seite durch.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice20 = new Exercice(20, "einseitige Br??cke Level 2", 300000, "10111", 2, 10, 5, "einseitigebruecke", "Mache eine ??bung", "F??r die ??bung musst Du Dich in R??ckenlage begeben und die Beine anwinkeln. Das rechte Bein hebst Du angewinkelt in die Luft. W??hrend der ??bung legst Du Deine H??nde auf den Hinterkopf. Anschlie??end musst Du Deine H??fte langsam heben und senken. Der Po darf bei dieser ??bung nicht die Matte ber??hren. Im Anschluss f??hrst du die ??bung auf der linken Seite durch.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice21 = new Exercice(21, "einseitige Br??cke Level 3", 360000, "10111", 3, 10, 5, "einseitigebruecke", "Mache eine ??bung", "F??r die ??bung musst Du Dich in R??ckenlage begeben und die Beine anwinkeln. Das rechte Bein hebst Du angewinkelt in die Luft. W??hrend der ??bung legst Du Deine H??nde auf den Hinterkopf. Anschlie??end musst Du Deine H??fte langsam heben und senken. Der Po darf bei dieser ??bung nicht die Matte ber??hren. Im Anschluss f??hrst du die ??bung auf der linken Seite durch.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice22 = new Exercice(22, "Katze-Kuh Level 1", 180000, "01001", 1, 10, 5, "katze_kuh", "Mache eine ??bung", "Komme in den Vierf????lerstand und mache den gesamten R??cken rund wie einen Katzenbuckel. Komme anschlie??end in ein gef??hrtes Hohlkreuz. Achte darauf, den Nacken nicht zu ??berstrecken. Wiederhole diesen Ablauf 15 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "0-3", "3-6", "6-9", "9-12", "12-15");
        Exercice exercice23 = new Exercice(23, "Katze-Kuh Level 2", 180000, "01001", 2, 10, 5, "katze_kuh", "Mache eine ??bung", "Komme in den Vierf????lerstand und mache den gesamten R??cken rund wie einen Katzenbuckel. Komme anschlie??end in ein gef??hrtes Hohlkreuz. Achte darauf, den Nacken nicht zu ??berstrecken. Wiederhole diesen Ablauf 15 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "0-3", "3-6", "6-9", "9-12", "12-15");
        Exercice exercice24 = new Exercice(24, "Katze-Kuh Level 3", 240000, "01001", 3, 10, 5, "katze_kuh", "Mache eine ??bung", "Komme in den Vierf????lerstand und mache den gesamten R??cken rund wie einen Katzenbuckel. Komme anschlie??end in ein gef??hrtes Hohlkreuz. Achte darauf, den Nacken nicht zu ??berstrecken. Wiederhole diesen Ablauf 15 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "0-3", "3-6", "6-9", "9-12", "12-15");
        Exercice exercice25 = new Exercice(25, "Liegest??tz asynchron Level 1", 180000, "11100", 1, 15, 5, "liegestuetzasynchron", "Mache eine ??bung", "Liegest??tz Ausgangsposition mit einem Arm vorne und einem Arm hinten. Oberk??rper durch Beugen der Arme senken. In Ausgangsposition zur??ckbewegen. Einfacher: Mit Knien am Boden abst??tzen. Mache mindestens 2 Durchl??ufe pro Seite.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "2-4", "4-6", "6-8", "10-12");
        Exercice exercice26 = new Exercice(26, "Liegest??tz asynchron Level 2", 300000, "11100", 2, 15, 5, "liegestuetzasynchron", "Mache eine ??bung", "Liegest??tz Ausgangsposition mit einem Arm vorne und einem Arm hinten. Oberk??rper durch Beugen der Arme senken. In Ausgangsposition zur??ckbewegen. Einfacher: Mit Knien am Boden abst??tzen. Mache mindestens 2 Durchl??ufe pro Seite.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "2-4", "4-6", "6-8", "10-12");
        Exercice exercice27 = new Exercice(27, "Liegest??tz asynchron Level 3", 540000, "11100", 3, 15, 5, "liegestuetzasynchron", "Mache eine ??bung", "Liegest??tz Ausgangsposition mit einem Arm vorne und einem Arm hinten. Oberk??rper durch Beugen der Arme senken. In Ausgangsposition zur??ckbewegen. Einfacher: Mit Knien am Boden abst??tzen. Mache mindestens 2 Durchl??ufe pro Seite.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "2-4", "4-6", "6-8", "10-12");
        Exercice exercice28 = new Exercice(28, "Makarasana Level 1", 180000, "10110", 1, 10, 5, "makarasana", "Mache eine ??bung", "Das Krokodil ist eine sanfte Twist-??bung, die in der Yogastunde meistens direkt vor der Endentspannung ge??bt wird. Aber auch ohne vorherige l??ngere Praxis ist sie ganz einfach auszuf??hren und wartet mit ??hnlich positiven Effekten wie intensivere Drehhaltungen auf: Sie wirkt stark entgiftend auf den Verdauungstrakt, l??st Stress auf und kann gleichzeitig Spannungen in der Wirbels??ule ??? besonders der Lendenwirbels??ule ??? aufl??sen. Schultern und Nacken erfahren eine sanfte Dehnung.\n" +
                "Komme in die R??ckenlage und lege die Arme ausgestreckt ab. Hebe das Ges???? an und lege es ein St??ckchen weiter rechts ab. Winkele nun die Knie an und lege sie ??bereinander nach links ab. Schaue nach rechts. Bleibe einige Atemz??ge in der Asana und ??be sie dann auf der anderen Seite. \n" +
                "Wiederhole 2 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "1", "2", "3", "-", "-");
        Exercice exercice29 = new Exercice(29, "Makarasana Level 2", 240000, "10110", 2, 10, 5, "makarasana", "Mache eine ??bung", "Das Krokodil ist eine sanfte Twist-??bung, die in der Yogastunde meistens direkt vor der Endentspannung ge??bt wird. Aber auch ohne vorherige l??ngere Praxis ist sie ganz einfach auszuf??hren und wartet mit ??hnlich positiven Effekten wie intensivere Drehhaltungen auf: Sie wirkt stark entgiftend auf den Verdauungstrakt, l??st Stress auf und kann gleichzeitig Spannungen in der Wirbels??ule ??? besonders der Lendenwirbels??ule ??? aufl??sen. Schultern und Nacken erfahren eine sanfte Dehnung.\n" +
                "Komme in die R??ckenlage und lege die Arme ausgestreckt ab. Hebe das Ges???? an und lege es ein St??ckchen weiter rechts ab. Winkele nun die Knie an und lege sie ??bereinander nach links ab. Schaue nach rechts. Bleibe einige Atemz??ge in der Asana und ??be sie dann auf der anderen Seite. \n" +
                "Wiederhole 2 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "1", "2", "3", "-", "-");
        Exercice exercice30 = new Exercice(30, "Makarasana Level 3", 300000, "10110", 3, 10, 5, "makarasana", "Mache eine ??bung", "Das Krokodil ist eine sanfte Twist-??bung, die in der Yogastunde meistens direkt vor der Endentspannung ge??bt wird. Aber auch ohne vorherige l??ngere Praxis ist sie ganz einfach auszuf??hren und wartet mit ??hnlich positiven Effekten wie intensivere Drehhaltungen auf: Sie wirkt stark entgiftend auf den Verdauungstrakt, l??st Stress auf und kann gleichzeitig Spannungen in der Wirbels??ule ??? besonders der Lendenwirbels??ule ??? aufl??sen. Schultern und Nacken erfahren eine sanfte Dehnung.\n" +
                "Komme in die R??ckenlage und lege die Arme ausgestreckt ab. Hebe das Ges???? an und lege es ein St??ckchen weiter rechts ab. Winkele nun die Knie an und lege sie ??bereinander nach links ab. Schaue nach rechts. Bleibe einige Atemz??ge in der Asana und ??be sie dann auf der anderen Seite. \n" +
                "Wiederhole 2 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "1", "2", "3", "-", "-");
        Exercice exercice31 = new Exercice(31, "Strecksprung Level 1", 180000, "10101", 1, 15, 5, "strecksprung", "Mache eine ??bung", "Schulterbreite Standposition. In die Hocke gehen und mit H??nden den Boden ber??hren. Arme seitlich nach oben f??hren und mit Schwung nach oben springen.Mache mindestens 10 Wiederholungen.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice32 = new Exercice(32, "Strecksprung Level 2", 240000, "10101", 2, 15, 5, "strecksprung", "Mache eine ??bung", "Schulterbreite Standposition. In die Hocke gehen und mit H??nden den Boden ber??hren. Arme seitlich nach oben f??hren und mit Schwung nach oben springen.Mache mindestens 10 Wiederholungen.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice33 = new Exercice(33, "Strecksprung Level 3", 240000, "10101", 3, 15, 5, "strecksprung", "Mache eine ??bung", "Schulterbreite Standposition. In die Hocke gehen und mit H??nden den Boden ber??hren. Arme seitlich nach oben f??hren und mit Schwung nach oben springen.Mache mindestens 10 Wiederholungen.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice34 = new Exercice(34, "Superman Level 1", 180000, "11111", 1, 10, 5, "superman", "Mache eine ??bung", "Die aus dem Englischen stammende ??bungsbezeichnung ist f??r Po und R??ckenstrecker gut. Leg Dich f??r diese ??bung auf den Bauch. Anschlie??end hebst Du Deine Arme sowie Deinen Kopf vom Boden. Der Oberk??rper muss, soweit wie m??glich, vom Boden abgehoben werden. Anschlie??end werden die Beine angehoben. Es reicht, die Beine so hoch, wie Du es schaffst, zu halten. Du kannst dabei Schwimmbewegungen machen, ohne abzulegen. Der Kopf darf bei der ??bung weder ??berstreckt werden, noch nach unten h??ngen. Es reicht, die Position 20 Sekunden zu halten und 15 Sekunden Pause zu machen. Wiederhole die ??bung 6 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "1", "2", "3", "4", "5-6");
        Exercice exercice35 = new Exercice(35, "Superman Level 2", 180000, "11111", 2, 10, 5, "superman", "Mache eine ??bung", "Die aus dem Englischen stammende ??bungsbezeichnung ist f??r Po und R??ckenstrecker gut. Leg Dich f??r diese ??bung auf den Bauch. Anschlie??end hebst Du Deine Arme sowie Deinen Kopf vom Boden. Der Oberk??rper muss, soweit wie m??glich, vom Boden abgehoben werden. Anschlie??end werden die Beine angehoben. Es reicht, die Beine so hoch, wie Du es schaffst, zu halten. Du kannst dabei Schwimmbewegungen machen, ohne abzulegen. Der Kopf darf bei der ??bung weder ??berstreckt werden, noch nach unten h??ngen. Es reicht, die Position 20 Sekunden zu halten und 15 Sekunden Pause zu machen. Wiederhole die ??bung 6 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "1", "2", "3", "4", "5-6");
        Exercice exercice36 = new Exercice(36, "Superman Level 3", 300000, "11111", 3, 10, 5, "superman", "Mache eine ??bung", "Die aus dem Englischen stammende ??bungsbezeichnung ist f??r Po und R??ckenstrecker gut. Leg Dich f??r diese ??bung auf den Bauch. Anschlie??end hebst Du Deine Arme sowie Deinen Kopf vom Boden. Der Oberk??rper muss, soweit wie m??glich, vom Boden abgehoben werden. Anschlie??end werden die Beine angehoben. Es reicht, die Beine so hoch, wie Du es schaffst, zu halten. Du kannst dabei Schwimmbewegungen machen, ohne abzulegen. Der Kopf darf bei der ??bung weder ??berstreckt werden, noch nach unten h??ngen. Es reicht, die Position 20 Sekunden zu halten und 15 Sekunden Pause zu machen. Wiederhole die ??bung 6 Mal.", "Wieviele Durchl??ufe hast du geschafft?", "1", "2", "3", "4", "5-6");
        Exercice exercice37 = new Exercice(37, "Totenstellung Level 1", 300000, "11111", 1, 10, 5, "totenstellung", "Mache eine ??bung", "Komme in die R??ckenlage und lege Arme und Beine entspannt ab. Lasse die F????e nach au??en fallen und lege die Handr??cken auf den Boden, damit sich die Schultern noch mehr l??sen k??nnen. Entspanne deinen gesamten K??rper und Geist f??r mindestens 3 Minuten.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice38 = new Exercice(38, "Totenstellung Level 2", 300000, "11111", 2, 10, 5, "totenstellung", "Mache eine ??bung", "Komme in die R??ckenlage und lege Arme und Beine entspannt ab. Lasse die F????e nach au??en fallen und lege die Handr??cken auf den Boden, damit sich die Schultern noch mehr l??sen k??nnen. Entspanne deinen gesamten K??rper und Geist f??r mindestens 3 Minuten.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice39 = new Exercice(39, "Totenstellung Level 3", 300000, "11111", 3, 10, 5, "totenstellung", "Mache eine ??bung", "Komme in die R??ckenlage und lege Arme und Beine entspannt ab. Lasse die F????e nach au??en fallen und lege die Handr??cken auf den Boden, damit sich die Schultern noch mehr l??sen k??nnen. Entspanne deinen gesamten K??rper und Geist f??r mindestens 3 Minuten.", "Wieviele Durchl??ufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");






        List<Exercice> exercices = new ArrayList<>();
        exercices.add(exercice1); //evtl. durch for-schleife machen sp??ter
        exercices.add(exercice2);
        exercices.add(exercice3);
        exercices.add(exercice4);
        exercices.add(exercice5);
        exercices.add(exercice6);
        exercices.add(exercice7);
        exercices.add(exercice8);
        exercices.add(exercice9);
        exercices.add(exercice10);
        exercices.add(exercice11);
        exercices.add(exercice12);
        exercices.add(exercice13);
        exercices.add(exercice14);
        exercices.add(exercice15);
        exercices.add(exercice16);
        exercices.add(exercice17);
        exercices.add(exercice18);
        exercices.add(exercice19);
        exercices.add(exercice20);
        exercices.add(exercice21);
        exercices.add(exercice22);
        exercices.add(exercice23);
        exercices.add(exercice24);
        exercices.add(exercice25);
        exercices.add(exercice26);
        exercices.add(exercice27);
        exercices.add(exercice28);
        exercices.add(exercice29);
        exercices.add(exercice30);
        exercices.add(exercice31);
        exercices.add(exercice32);
        exercices.add(exercice33);
        exercices.add(exercice34);
        exercices.add(exercice35);
        exercices.add(exercice36);
        exercices.add(exercice37);
        exercices.add(exercice38);
        exercices.add(exercice39);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        ExerciceDao exerciceDao = db.exerciceDao();
        exerciceDao.insertExercices(exercices);

    }


    public void gerateTestFile() {  //f??llt listenposition 0
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        ExerciceDao exerciceDao = db.exerciceDao();
        Exercice exercice = new Exercice(0, "Titellol", 12, "010101", 4, 12323, 8432468, "@res/lol", "interessant", "sehr interessant", "Frage", "Antwort1", "Antwort2", "Antwort3", "Antwort4", "Antwort5");
        List<Exercice> exerciceList = new ArrayList<>();
        exerciceList.add(exercice);
        exerciceDao.insertExercices(exerciceList);

    }


    private void showDailyTip() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        DailyTipDao dailyTipDao = db.dailyTipDao();
        List<DailyTip> dailyTips = new ArrayList<>();
        dailyTips = dailyTipDao.getDailytips();
        Random random = new Random();
        int id = random.nextInt(dailyTips.size() - 1);

        DailyTip dailyTip = dailyTips.get(id);


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(dailyTip.getText()) //Strings in Ressourcen auslagern
                .setTitle(dailyTip.getTitle());


        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    protected void onStart() {
        super.onStart();


    }

}