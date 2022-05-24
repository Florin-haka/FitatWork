package de.info3.navigation;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Exercice.class,Profile.class,Setting.class,DailyTip.class, Date.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciceDao exerciceDao();
    public abstract ProfileDao profileDao();
    public abstract SettingDao settingDao();
    public abstract DailyTipDao  dailyTipDao();
    public abstract DateDao dateDao();


}