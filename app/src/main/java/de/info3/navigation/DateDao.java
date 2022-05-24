package de.info3.navigation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertDate(Date date);

    @Query("SELECT * FROM date")
    public List<Date> getDates();

}
