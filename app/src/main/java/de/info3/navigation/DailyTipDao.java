package de.info3.navigation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DailyTipDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertDailyTips(List<DailyTip> dailyTips);

    @Query("SELECT *  FROM dailytip")
    public List<DailyTip> getDailytips();
}
