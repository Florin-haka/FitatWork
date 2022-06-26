package de.info3.navigation;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Definiert Room Database

@Database(entities = {Exercice.class,Profile.class,Setting.class,DailyTip.class, Date.class, Notiz.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciceDao exerciceDao();
    public abstract ProfileDao profileDao();
    public abstract SettingDao settingDao();
    public abstract DailyTipDao  dailyTipDao();
    public abstract DateDao dateDao();
    public abstract NotizDao notizDao();


}