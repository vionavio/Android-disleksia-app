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

    @Query("SELECT * FROM scores WHERE user_id = :id AND quiz_type ='gameAngka' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreAngka(Integer id);

    @Query("SELECT * FROM scores WHERE user_id = :id AND quiz_type ='gameBenda' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreBenda(Integer id);

    @Query("SELECT * FROM scores WHERE user_id = :id AND quiz_type ='gameKerja' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreKerja(Integer id);

    @Query("SELECT * FROM scores WHERE user_id = :id AND quiz_type ='gameSifat' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreSifat(Integer id);

    @Query("SELECT * FROM scores WHERE user_id = :id AND quiz_type ='gameKeterangan' ORDER BY created_at DESC")
    List<ScoreEntity> getScoreKeterangan(Integer id);


    @Query("SELECT * FROM scores WHERE id = :id")
    ScoreEntity getOne(Integer id);

    @Insert
    void insert(ScoreEntity scoreEntity);

    @Insert
    void insertAll(ScoreEntity... scoreEntities);

    @Delete
    void delete(ScoreEntity scoreEntity);
}
