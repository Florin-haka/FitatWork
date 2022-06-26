package de.info3.navigation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotizDao {
    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //public void insertNotiz(List<String> notizen);

    @Insert
    void insertAll(Notiz...notizen);

    //@Insert
    //void inserttoID(String notiztext, int keycode);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void inserttoID(Notiz notiz);

    @Query("SELECT *  FROM notiz")
    List<Notiz> getAll();

    @Query("SELECT * FROM notiz WHERE keycode = :number")
    List<Notiz> loadNotizonkeycode(int number);
}
