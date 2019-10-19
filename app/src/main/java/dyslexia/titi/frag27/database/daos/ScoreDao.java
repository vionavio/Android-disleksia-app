package dyslexia.titi.frag27.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dyslexia.titi.frag27.database.entities.ScoreEntity;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM scores")
    List<ScoreEntity> getAll();

    @Query("SELECT * FROM scores WHERE user_id = :id ORDER BY created_at DESC")
    List<ScoreEntity> getCurrentScores(Integer id);

    @Query("SELECT * FROM scores WHERE id = :id")
    ScoreEntity getOne(Integer id);

    @Insert
    void insert(ScoreEntity scoreEntity);

    @Insert
    void insertAll(ScoreEntity... scoreEntities);

    @Delete
    void delete(ScoreEntity scoreEntity);
}
