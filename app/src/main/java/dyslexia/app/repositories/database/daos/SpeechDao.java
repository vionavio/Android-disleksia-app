package dyslexia.app.repositories.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import dyslexia.app.repositories.database.entities.SpeechEntity;

@Dao
public interface SpeechDao {
    @Query("SElECT * FROM speech")
    List<SpeechEntity> getAll();
}
