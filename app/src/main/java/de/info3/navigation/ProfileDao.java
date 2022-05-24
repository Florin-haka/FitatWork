package de.info3.navigation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertProfile(Profile profile);

    @Query("SELECT * FROM profile")
    public List<Profile> getProfile();
}
