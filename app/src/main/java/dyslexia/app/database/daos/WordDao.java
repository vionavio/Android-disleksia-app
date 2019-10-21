package dyslexia.app.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dyslexia.app.database.entities.WordEntity;

@Dao
public interface WordDao {
    @Query("SElECt * FROM words")
    List<WordEntity> getAll();

    @Query("SELECT * FROM words WHERE type = :type")
    List<WordEntity> getByType(String type);

    @Insert
    void insertAll(WordEntity... wordEntities);
}
