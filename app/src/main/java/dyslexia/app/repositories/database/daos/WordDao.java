package dyslexia.app.repositories.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dyslexia.app.repositories.database.entities.WordEntity;

@Dao
public interface WordDao {

    @Query("SElECt * FROM words")
    List<WordEntity> getAll();

    @Query("SELECT * FROM words WHERE type = :type")
    List<WordEntity> getByType(String type);

    @Query("SELECT * FROM words WHERE word LIKE  :word")
    List<WordEntity> getWord(String word);

    @Insert
    void insertAll(WordEntity... wordEntities);
}
