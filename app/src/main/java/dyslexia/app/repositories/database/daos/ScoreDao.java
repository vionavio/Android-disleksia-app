package dyslexia.app.repositories.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dyslexia.app.repositories.database.entities.ScoreEntity;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM scores")
    List<ScoreEntity> getAll();

    @Query("SELECT * FROM scores WHERE user_id = :id_score AND quiz_type ='gameAngka' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreAngka(Integer id_score);

    @Query("SELECT * FROM scores WHERE user_id = :id_score AND quiz_type ='gameBenda' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreBenda(Integer id_score);

    @Query("SELECT * FROM scores WHERE user_id = :id_score AND quiz_type ='gameKerja' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreKerja(Integer id_score);

    @Query("SELECT * FROM scores WHERE user_id = :id_score AND quiz_type ='gameSifat' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreSifat(Integer id_score);

    @Query("SELECT * FROM scores WHERE user_id = :id_score AND quiz_type ='gameKeterangan' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreKeterangan(Integer id_score);


    @Query("SELECT * FROM scores WHERE id_score = :id_score")
    ScoreEntity getOne(Integer id_score);

    @Insert
    void insert(ScoreEntity scoreEntity);

    @Insert
    void insertAll(ScoreEntity... scoreEntities);

    @Delete
    void delete(ScoreEntity scoreEntity);
}
