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

        if((settingDao.getSettings().get(0).isShowDailyTip()& intent.hasCategory(Intent.CATEGORY_LAUNCHER)|| Objects.equals(intent.getStringExtra("origin"), "firststart"))){ //püft ob activity von auserhalb gestartet wurd (launbcher activity) und ob dailytips in einstellungen aktiviert sind wenn ja werden daly tips aufgerufen
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

        } catch (IndexOutOfBoundsException outOfBoundsException) {          //wenn es kein setting gibt wird catch kalmmer ausgeführt  erzeug default setting und profil lädt übungen u. dalytips und startet first activity
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

        DailyTip dailyTip1 = new DailyTip(1, "Yoga Übungen sind auch gut für dein Immunsystem", "Laut einer Norwegischen Studie regt Yoga die Zirkulation der weißen Blutkörperchen an, die für unsere Immunabwehr verantwortlich sind.");
        DailyTip dailyTip2 = new DailyTip(2, "Yoga am Arbeitsplatz kann die Produktivität erhöhen", "Yoga Übungen steigern deine Konzentration und reduzieren das Risiko von Rückenschmerzen.");

        List<DailyTip> dailyTips = new ArrayList<>();
        dailyTips.add(dailyTip1);
        dailyTips.add(dailyTip2);
        dailyTipDao.insertDailyTips(dailyTips);

    }

    private void loadHardCodedExercices() {
        Exercice exercice1 = new Exercice(1, "Viparita-Karani Level 3", 180000, "00111", 3, 10, 5, "viparita_karani", "Mache eine Übung.", "Lege dich auf dem Rücken vor einer Wand ab. Strecke nun die Beine senkrecht nach oben und stütze sie an der Wand ab. Lasse los und bleibe einige Minuten in der Asana (in der ruhenden Körperhaltung).", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");//aktuell müsste Antwort 1 bitte die beste sein
        Exercice exercice2 = new Exercice(2, "Viparita-Karani Level 2", 180000, "00111", 2, 10, 5, "viparita_karani", "Mache eine Übung.", "Lege dich auf dem Rücken vor einer Wand ab. Strecke nun die Beine senkrecht nach oben und stütze sie an der Wand ab. Lasse los und bleibe einige Minuten in der Asana (in der ruhenden Körperhaltung).", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice3 = new Exercice(3, "Viparita-Karani Level 1", 180000, "00111", 1, 10, 5, "viparita_karani", "Mache eine Übung.", "Lege dich auf dem Rücken vor einer Wand ab. Strecke nun die Beine senkrecht nach oben und stütze sie an der Wand ab. Lasse los und bleibe einige Minuten in der Asana (in der ruhenden Körperhaltung).", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice4 = new Exercice(4, "Vierfüsslerstand Level 3", 180000, "10111", 3, 10, 5, "vierfuesslerstand", "Mache eine Übung.", "Für diese Übung musst Du Dich in die kniende Position begeben. Generell ist es ratsam, eine Trainingsmatte für die Übungen zu nutzen. Die Knie sollten für diese Übung unter der Hüfte sein und die Arme sollten sich unter den Schultern befinden. Anschließend solltest Du den linken Arm heben, bis der Arm auf Schulterhöhe ist. Gleichzeitig musst Du das rechte Bein auf Hüfthöhe heben. \nPo und Bauch müssen bei dieser Übung angespannt werden. Der Kopf darf nicht nach unten zeigen. Diese Übung sollte 40-mal wiederholt werden. Nach den Wiederholungen muss die Seite gewechselt werden.", "Wieviele Durchläufe hast du geschafft?", "0-8", "8-16", "16-24", "24-32", "32-40");
        Exercice exercice5 = new Exercice(5, "Vierfüsslerstand Level 2", 180000, "10111", 2, 10, 5, "vierfuesslerstand", "Mache eine Übung.", "Für diese Übung musst Du Dich in die kniende Position begeben. Generell ist es ratsam, eine Trainingsmatte für die Übungen zu nutzen. Die Knie sollten für diese Übung unter der Hüfte sein und die Arme sollten sich unter den Schultern befinden. Anschließend solltest Du den linken Arm heben, bis der Arm auf Schulterhöhe ist. Gleichzeitig musst Du das rechte Bein auf Hüfthöhe heben. \nPo und Bauch müssen bei dieser Übung angespannt werden. Der Kopf darf nicht nach unten zeigen. Diese Übung sollte 40-mal wiederholt werden. Nach den Wiederholungen muss die Seite gewechselt werden.", "Wieviele Durchläufe hast du geschafft?", "0-8", "8-16", "16-24", "24-32", "32-40");
        Exercice exercice6 = new Exercice(6, "Vierfüsslerstand Level 1", 180000, "10111", 1, 10, 5, "vierfuesslerstand", "Mache eine Übung.", "Für diese Übung musst Du Dich in die kniende Position begeben. Generell ist es ratsam, eine Trainingsmatte für die Übungen zu nutzen. Die Knie sollten für diese Übung unter der Hüfte sein und die Arme sollten sich unter den Schultern befinden. Anschließend solltest Du den linken Arm heben, bis der Arm auf Schulterhöhe ist. Gleichzeitig musst Du das rechte Bein auf Hüfthöhe heben. \nPo und Bauch müssen bei dieser Übung angespannt werden. Der Kopf darf nicht nach unten zeigen. Diese Übung sollte 40-mal wiederholt werden. Nach den Wiederholungen muss die Seite gewechselt werden.", "Wieviele Durchläufe hast du geschafft?", "0-8", "8-16", "16-24", "24-32", "32-40");
        Exercice exercice7 = new Exercice(7, "seitlicher Plank Level 1", 180000, "00010", 1, 10, 5, "seitlicherplank", "Mache eine Übung", "Seitliche Planks trainieren die seitlichen Bauchmuskeln. Dazu legst Du Dich zunächst seitlich auf Deine Trainingsmatte. Dann stellst Du den rechten Ellbogen unter Deine Schulter. Anschließend hebst Du Deinen Körper vom Boden ab und streckst die Beine durch. Dein Körper muss von Kopf bis zu den Füßen eine Linie bilden. Der Bauch sollte bei der Übung angespannt sein. Die Position sollte 30 Sekunden lang gehalten werden.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice8 = new Exercice(8, "seitlicher Plank Level 2", 180000, "00010", 2, 10, 5, "seitlicherplank", "Mache eine Übung", "Seitliche Planks trainieren die seitlichen Bauchmuskeln. Dazu legst Du Dich zunächst seitlich auf Deine Trainingsmatte. Dann stellst Du den rechten Ellbogen unter Deine Schulter. Anschließend hebst Du Deinen Körper vom Boden ab und streckst die Beine durch. Dein Körper muss von Kopf bis zu den Füßen eine Linie bilden. Der Bauch sollte bei der Übung angespannt sein. Die Position sollte 30 Sekunden lang gehalten werden.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice9 = new Exercice(9, "seitlicher Plank Level 3", 300000, "00010", 3, 10, 5, "seitlicherplank", "Mache eine Übung", "Seitliche Planks trainieren die seitlichen Bauchmuskeln. Dazu legst Du Dich zunächst seitlich auf Deine Trainingsmatte. Dann stellst Du den rechten Ellbogen unter Deine Schulter. Anschließend hebst Du Deinen Körper vom Boden ab und streckst die Beine durch. Dein Körper muss von Kopf bis zu den Füßen eine Linie bilden. Der Bauch sollte bei der Übung angespannt sein. Die Position sollte 30 Sekunden lang gehalten werden.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice10 = new Exercice(10, "Balasana Level 1", 180000, "00110", 1, 10, 5, "balasana", "Mache eine Übung", "Lege aus dem Fersensitz deinen Oberkörper auf den Oberschenkel ab. Bringe die Stirn vor den Knien auf die Matte. Lege deine Arme mit nach oben zeigenden Handflächen neben deinem Körper ab. Lass deinen Schultern nach unten sinken und bringe dein Gesäß tiefer in Richtung Füße. Bleibe für einige Atemzüge in Balasana und entspanne dich.\n" +
                "Wiederhole einige Male.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice11 = new Exercice(11, "Balasana Level 2", 240000, "00110", 2, 10, 5, "balasana", "Mache eine Übung", "Lege aus dem Fersensitz deinen Oberkörper auf den Oberschenkel ab. Bringe die Stirn vor den Knien auf die Matte. Lege deine Arme mit nach oben zeigenden Handflächen neben deinem Körper ab. Lass deinen Schultern nach unten sinken und bringe dein Gesäß tiefer in Richtung Füße. Bleibe für einige Atemzüge in Balasana und entspanne dich.\n" +
                "Wiederhole einige Male.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice12 = new Exercice(12, "Balasana Level 3", 240000, "00110", 3, 10, 5, "balasana", "Mache eine Übung", "Lege aus dem Fersensitz deinen Oberkörper auf den Oberschenkel ab. Bringe die Stirn vor den Knien auf die Matte. Lege deine Arme mit nach oben zeigenden Handflächen neben deinem Körper ab. Lass deinen Schultern nach unten sinken und bringe dein Gesäß tiefer in Richtung Füße. Bleibe für einige Atemzüge in Balasana und entspanne dich.\n" +
                "Wiederhole einige Male.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice13 = new Exercice(13, "Bergsteigen Level 1", 180000, "11111", 1, 15, 5, "bergsteiger", "Mache eine Übung", "Liegestütz Ausgangsposition. Abwechselnd ein Bein vom Boden abheben und das Knie zur Brust ziehen. In Ausgangsposition zurückbewegen.\n Wiederhole 8x", "Wieviele Durchläufe hast du geschafft?", "0-1", "2-3", "4-5", "6-7", "8+");
        Exercice exercice14 = new Exercice(14, "Bergsteigen Level 2", 300000, "11111", 2, 15, 5, "bergsteiger", "Mache eine Übung", "Liegestütz Ausgangsposition. Abwechselnd ein Bein vom Boden abheben und das Knie zur Brust ziehen. In Ausgangsposition zurückbewegen.\n Wiederhole 8x", "Wieviele Durchläufe hast du geschafft?", "0-1", "2-3", "4-5", "6-7", "8+");
        Exercice exercice15 = new Exercice(15, "Bergsteigen Level 3", 360000, "11111", 3, 15, 5, "bergsteiger", "Mache eine Übung", "Liegestütz Ausgangsposition. Abwechselnd ein Bein vom Boden abheben und das Knie zur Brust ziehen. In Ausgangsposition zurückbewegen.\n Wiederhole 8x", "Wieviele Durchläufe hast du geschafft?", "0-1", "2-3", "4-5", "6-7", "8+");
        Exercice exercice16 = new Exercice(16, "Brücke Level 1", 300000, "10111", 1, 10, 5, "bruecke", "Mache eine Übung", "Begib Dich in Rückenlage. Die Beine solltest Du anschließend anwinkeln. Dann hebst Du Dein Becken vom Boden ab. Dein Körper sollte eine gerade Linie bilden. Bei der Übung musst Du Beine und Po anspannen. Sobald Dein Körper die gerade Linie bildet und Du Po und Beine angespannt hast, kannst Du die Übungsposition zwei Sekunden lang halten. Anschließend kehrst Du wieder in die Rückenlage auf den Boden zurück. Die Übungen kannst Du 10-mal hintereinander durchführen.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice17 = new Exercice(17, "Brücke Level 2", 360000, "10111", 2, 10, 5, "bruecke", "Mache eine Übung", "Begib Dich in Rückenlage. Die Beine solltest Du anschließend anwinkeln. Dann hebst Du Dein Becken vom Boden ab. Dein Körper sollte eine gerade Linie bilden. Bei der Übung musst Du Beine und Po anspannen. Sobald Dein Körper die gerade Linie bildet und Du Po und Beine angespannt hast, kannst Du die Übungsposition zwei Sekunden lang halten. Anschließend kehrst Du wieder in die Rückenlage auf den Boden zurück. Die Übungen kannst Du 10-mal hintereinander durchführen.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice18 = new Exercice(18, "Brücke Level 3", 420000, "10111", 3, 10, 5, "bruecke", "Mache eine Übung", "Begib Dich in Rückenlage. Die Beine solltest Du anschließend anwinkeln. Dann hebst Du Dein Becken vom Boden ab. Dein Körper sollte eine gerade Linie bilden. Bei der Übung musst Du Beine und Po anspannen. Sobald Dein Körper die gerade Linie bildet und Du Po und Beine angespannt hast, kannst Du die Übungsposition zwei Sekunden lang halten. Anschließend kehrst Du wieder in die Rückenlage auf den Boden zurück. Die Übungen kannst Du 10-mal hintereinander durchführen.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice19 = new Exercice(19, "einseitige Brücke Level 1", 180000, "10111", 1, 10, 5, "einseitigebruecke", "Mache eine Übung", "Für die Übung musst Du Dich in Rückenlage begeben und die Beine anwinkeln. Das rechte Bein hebst Du angewinkelt in die Luft. Während der Übung legst Du Deine Hände auf den Hinterkopf. Anschließend musst Du Deine Hüfte langsam heben und senken. Der Po darf bei dieser Übung nicht die Matte berühren. Im Anschluss führst du die Übung auf der linken Seite durch.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice20 = new Exercice(20, "einseitige Brücke Level 2", 300000, "10111", 2, 10, 5, "einseitigebruecke", "Mache eine Übung", "Für die Übung musst Du Dich in Rückenlage begeben und die Beine anwinkeln. Das rechte Bein hebst Du angewinkelt in die Luft. Während der Übung legst Du Deine Hände auf den Hinterkopf. Anschließend musst Du Deine Hüfte langsam heben und senken. Der Po darf bei dieser Übung nicht die Matte berühren. Im Anschluss führst du die Übung auf der linken Seite durch.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice21 = new Exercice(21, "einseitige Brücke Level 3", 360000, "10111", 3, 10, 5, "einseitigebruecke", "Mache eine Übung", "Für die Übung musst Du Dich in Rückenlage begeben und die Beine anwinkeln. Das rechte Bein hebst Du angewinkelt in die Luft. Während der Übung legst Du Deine Hände auf den Hinterkopf. Anschließend musst Du Deine Hüfte langsam heben und senken. Der Po darf bei dieser Übung nicht die Matte berühren. Im Anschluss führst du die Übung auf der linken Seite durch.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice22 = new Exercice(22, "Katze-Kuh Level 1", 180000, "01001", 1, 10, 5, "katze_kuh", "Mache eine Übung", "Komme in den Vierfüßlerstand und mache den gesamten Rücken rund wie einen Katzenbuckel. Komme anschließend in ein geführtes Hohlkreuz. Achte darauf, den Nacken nicht zu überstrecken. Wiederhole diesen Ablauf 15 Mal.", "Wieviele Durchläufe hast du geschafft?", "0-3", "3-6", "6-9", "9-12", "12-15");
        Exercice exercice23 = new Exercice(23, "Katze-Kuh Level 2", 180000, "01001", 2, 10, 5, "katze_kuh", "Mache eine Übung", "Komme in den Vierfüßlerstand und mache den gesamten Rücken rund wie einen Katzenbuckel. Komme anschließend in ein geführtes Hohlkreuz. Achte darauf, den Nacken nicht zu überstrecken. Wiederhole diesen Ablauf 15 Mal.", "Wieviele Durchläufe hast du geschafft?", "0-3", "3-6", "6-9", "9-12", "12-15");
        Exercice exercice24 = new Exercice(24, "Katze-Kuh Level 3", 240000, "01001", 3, 10, 5, "katze_kuh", "Mache eine Übung", "Komme in den Vierfüßlerstand und mache den gesamten Rücken rund wie einen Katzenbuckel. Komme anschließend in ein geführtes Hohlkreuz. Achte darauf, den Nacken nicht zu überstrecken. Wiederhole diesen Ablauf 15 Mal.", "Wieviele Durchläufe hast du geschafft?", "0-3", "3-6", "6-9", "9-12", "12-15");
        Exercice exercice25 = new Exercice(25, "Liegestütz asynchron Level 1", 180000, "11100", 1, 15, 5, "liegestuetzasynchron", "Mache eine Übung", "Liegestütz Ausgangsposition mit einem Arm vorne und einem Arm hinten. Oberkörper durch Beugen der Arme senken. In Ausgangsposition zurückbewegen. Einfacher: Mit Knien am Boden abstützen. Mache mindestens 2 Durchläufe pro Seite.", "Wieviele Durchläufe hast du geschafft?", "0-2", "2-4", "4-6", "6-8", "10-12");
        Exercice exercice26 = new Exercice(26, "Liegestütz asynchron Level 2", 300000, "11100", 2, 15, 5, "liegestuetzasynchron", "Mache eine Übung", "Liegestütz Ausgangsposition mit einem Arm vorne und einem Arm hinten. Oberkörper durch Beugen der Arme senken. In Ausgangsposition zurückbewegen. Einfacher: Mit Knien am Boden abstützen. Mache mindestens 2 Durchläufe pro Seite.", "Wieviele Durchläufe hast du geschafft?", "0-2", "2-4", "4-6", "6-8", "10-12");
        Exercice exercice27 = new Exercice(27, "Liegestütz asynchron Level 3", 540000, "11100", 3, 15, 5, "liegestuetzasynchron", "Mache eine Übung", "Liegestütz Ausgangsposition mit einem Arm vorne und einem Arm hinten. Oberkörper durch Beugen der Arme senken. In Ausgangsposition zurückbewegen. Einfacher: Mit Knien am Boden abstützen. Mache mindestens 2 Durchläufe pro Seite.", "Wieviele Durchläufe hast du geschafft?", "0-2", "2-4", "4-6", "6-8", "10-12");
        Exercice exercice28 = new Exercice(28, "Makarasana Level 1", 180000, "10110", 1, 10, 5, "makarasana", "Mache eine Übung", "Das Krokodil ist eine sanfte Twist-Übung, die in der Yogastunde meistens direkt vor der Endentspannung geübt wird. Aber auch ohne vorherige längere Praxis ist sie ganz einfach auszuführen und wartet mit ähnlich positiven Effekten wie intensivere Drehhaltungen auf: Sie wirkt stark entgiftend auf den Verdauungstrakt, löst Stress auf und kann gleichzeitig Spannungen in der Wirbelsäule – besonders der Lendenwirbelsäule – auflösen. Schultern und Nacken erfahren eine sanfte Dehnung.\n" +
                "Komme in die Rückenlage und lege die Arme ausgestreckt ab. Hebe das Gesäß an und lege es ein Stückchen weiter rechts ab. Winkele nun die Knie an und lege sie übereinander nach links ab. Schaue nach rechts. Bleibe einige Atemzüge in der Asana und übe sie dann auf der anderen Seite. \n" +
                "Wiederhole 2 Mal.", "Wieviele Durchläufe hast du geschafft?", "1", "2", "3", "-", "-");
        Exercice exercice29 = new Exercice(29, "Makarasana Level 2", 240000, "10110", 2, 10, 5, "makarasana", "Mache eine Übung", "Das Krokodil ist eine sanfte Twist-Übung, die in der Yogastunde meistens direkt vor der Endentspannung geübt wird. Aber auch ohne vorherige längere Praxis ist sie ganz einfach auszuführen und wartet mit ähnlich positiven Effekten wie intensivere Drehhaltungen auf: Sie wirkt stark entgiftend auf den Verdauungstrakt, löst Stress auf und kann gleichzeitig Spannungen in der Wirbelsäule – besonders der Lendenwirbelsäule – auflösen. Schultern und Nacken erfahren eine sanfte Dehnung.\n" +
                "Komme in die Rückenlage und lege die Arme ausgestreckt ab. Hebe das Gesäß an und lege es ein Stückchen weiter rechts ab. Winkele nun die Knie an und lege sie übereinander nach links ab. Schaue nach rechts. Bleibe einige Atemzüge in der Asana und übe sie dann auf der anderen Seite. \n" +
                "Wiederhole 2 Mal.", "Wieviele Durchläufe hast du geschafft?", "1", "2", "3", "-", "-");
        Exercice exercice30 = new Exercice(30, "Makarasana Level 3", 300000, "10110", 3, 10, 5, "makarasana", "Mache eine Übung", "Das Krokodil ist eine sanfte Twist-Übung, die in der Yogastunde meistens direkt vor der Endentspannung geübt wird. Aber auch ohne vorherige längere Praxis ist sie ganz einfach auszuführen und wartet mit ähnlich positiven Effekten wie intensivere Drehhaltungen auf: Sie wirkt stark entgiftend auf den Verdauungstrakt, löst Stress auf und kann gleichzeitig Spannungen in der Wirbelsäule – besonders der Lendenwirbelsäule – auflösen. Schultern und Nacken erfahren eine sanfte Dehnung.\n" +
                "Komme in die Rückenlage und lege die Arme ausgestreckt ab. Hebe das Gesäß an und lege es ein Stückchen weiter rechts ab. Winkele nun die Knie an und lege sie übereinander nach links ab. Schaue nach rechts. Bleibe einige Atemzüge in der Asana und übe sie dann auf der anderen Seite. \n" +
                "Wiederhole 2 Mal.", "Wieviele Durchläufe hast du geschafft?", "1", "2", "3", "-", "-");
        Exercice exercice31 = new Exercice(31, "Strecksprung Level 1", 180000, "10101", 1, 15, 5, "strecksprung", "Mache eine Übung", "Schulterbreite Standposition. In die Hocke gehen und mit Händen den Boden berühren. Arme seitlich nach oben führen und mit Schwung nach oben springen.Mache mindestens 10 Wiederholungen.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice32 = new Exercice(32, "Strecksprung Level 2", 240000, "10101", 2, 15, 5, "strecksprung", "Mache eine Übung", "Schulterbreite Standposition. In die Hocke gehen und mit Händen den Boden berühren. Arme seitlich nach oben führen und mit Schwung nach oben springen.Mache mindestens 10 Wiederholungen.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice33 = new Exercice(33, "Strecksprung Level 3", 240000, "10101", 3, 15, 5, "strecksprung", "Mache eine Übung", "Schulterbreite Standposition. In die Hocke gehen und mit Händen den Boden berühren. Arme seitlich nach oben führen und mit Schwung nach oben springen.Mache mindestens 10 Wiederholungen.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice34 = new Exercice(34, "Superman Level 1", 180000, "11111", 1, 10, 5, "superman", "Mache eine Übung", "Die aus dem Englischen stammende Übungsbezeichnung ist für Po und Rückenstrecker gut. Leg Dich für diese Übung auf den Bauch. Anschließend hebst Du Deine Arme sowie Deinen Kopf vom Boden. Der Oberkörper muss, soweit wie möglich, vom Boden abgehoben werden. Anschließend werden die Beine angehoben. Es reicht, die Beine so hoch, wie Du es schaffst, zu halten. Du kannst dabei Schwimmbewegungen machen, ohne abzulegen. Der Kopf darf bei der Übung weder überstreckt werden, noch nach unten hängen. Es reicht, die Position 20 Sekunden zu halten und 15 Sekunden Pause zu machen. Wiederhole die Übung 6 Mal.", "Wieviele Durchläufe hast du geschafft?", "1", "2", "3", "4", "5-6");
        Exercice exercice35 = new Exercice(35, "Superman Level 2", 180000, "11111", 2, 10, 5, "superman", "Mache eine Übung", "Die aus dem Englischen stammende Übungsbezeichnung ist für Po und Rückenstrecker gut. Leg Dich für diese Übung auf den Bauch. Anschließend hebst Du Deine Arme sowie Deinen Kopf vom Boden. Der Oberkörper muss, soweit wie möglich, vom Boden abgehoben werden. Anschließend werden die Beine angehoben. Es reicht, die Beine so hoch, wie Du es schaffst, zu halten. Du kannst dabei Schwimmbewegungen machen, ohne abzulegen. Der Kopf darf bei der Übung weder überstreckt werden, noch nach unten hängen. Es reicht, die Position 20 Sekunden zu halten und 15 Sekunden Pause zu machen. Wiederhole die Übung 6 Mal.", "Wieviele Durchläufe hast du geschafft?", "1", "2", "3", "4", "5-6");
        Exercice exercice36 = new Exercice(36, "Superman Level 3", 300000, "11111", 3, 10, 5, "superman", "Mache eine Übung", "Die aus dem Englischen stammende Übungsbezeichnung ist für Po und Rückenstrecker gut. Leg Dich für diese Übung auf den Bauch. Anschließend hebst Du Deine Arme sowie Deinen Kopf vom Boden. Der Oberkörper muss, soweit wie möglich, vom Boden abgehoben werden. Anschließend werden die Beine angehoben. Es reicht, die Beine so hoch, wie Du es schaffst, zu halten. Du kannst dabei Schwimmbewegungen machen, ohne abzulegen. Der Kopf darf bei der Übung weder überstreckt werden, noch nach unten hängen. Es reicht, die Position 20 Sekunden zu halten und 15 Sekunden Pause zu machen. Wiederhole die Übung 6 Mal.", "Wieviele Durchläufe hast du geschafft?", "1", "2", "3", "4", "5-6");
        Exercice exercice37 = new Exercice(37, "Totenstellung Level 1", 300000, "11111", 1, 10, 5, "totenstellung", "Mache eine Übung", "Komme in die Rückenlage und lege Arme und Beine entspannt ab. Lasse die Füße nach außen fallen und lege die Handrücken auf den Boden, damit sich die Schultern noch mehr lösen können. Entspanne deinen gesamten Körper und Geist für mindestens 3 Minuten.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice38 = new Exercice(38, "Totenstellung Level 2", 300000, "11111", 2, 10, 5, "totenstellung", "Mache eine Übung", "Komme in die Rückenlage und lege Arme und Beine entspannt ab. Lasse die Füße nach außen fallen und lege die Handrücken auf den Boden, damit sich die Schultern noch mehr lösen können. Entspanne deinen gesamten Körper und Geist für mindestens 3 Minuten.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");
        Exercice exercice39 = new Exercice(39, "Totenstellung Level 3", 300000, "11111", 3, 10, 5, "totenstellung", "Mache eine Übung", "Komme in die Rückenlage und lege Arme und Beine entspannt ab. Lasse die Füße nach außen fallen und lege die Handrücken auf den Boden, damit sich die Schultern noch mehr lösen können. Entspanne deinen gesamten Körper und Geist für mindestens 3 Minuten.", "Wieviele Durchläufe hast du geschafft?", "0-2", "3-4", "5-6", "7-8", "9-10");






        List<Exercice> exercices = new ArrayList<>();
        exercices.add(exercice1); //evtl. durch for-schleife machen später
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


    public void gerateTestFile() {  //füllt listenposition 0
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