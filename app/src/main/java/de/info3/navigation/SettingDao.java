package de.info3.navigation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SettingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSetting (Setting setting);

    @Query("SELECT * FROM setting")
    public List<Setting> getSettings();
}
