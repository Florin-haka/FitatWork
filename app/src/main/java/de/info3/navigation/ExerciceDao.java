package de.info3.navigation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertExercices(List<Exercice> exercices);

    @Insert
    public void insertOneExecice(Exercice exercic);

    @Query("SELECT * FROM exercice WHERE id = :id")
    public Exercice getExcerciseById(int id);

    @Query("SELECT * FROM exercice")
    public List<Exercice> getAllExercices();

    @Query("SELECT * FROM exercice WHERE level = :selectLevel")
    public List<Exercice> getExercicesWithDifficulty(int selectLevel);

    @Query("SELECT * FROM exercice WHERE bodyPart = :selectBodyPart")
    public List<Exercice> getExercicesWithBodyPart(String selectBodyPart);

}
