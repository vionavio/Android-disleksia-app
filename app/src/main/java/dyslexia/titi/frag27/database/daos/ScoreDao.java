package dyslexia.titi.frag27.database.daos;
// Created by Arif Ikhsanudin on 10/3/2019.

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

    @Query("SELECT * FROM scores WHERE id = :id")
    ScoreEntity getOne(int id);

    @Insert
    void insert(ScoreEntity scoreEntity);

    @Insert
    void insertAll(ScoreEntity... scoreEntities);

    @Delete
    void delete(ScoreEntity scoreEntity);
}
