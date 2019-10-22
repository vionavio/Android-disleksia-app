package dyslexia.app.repositories.database.daos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import dyslexia.app.repositories.database.entities.SpeechEntity;

@Dao
public interface SpeechDao {

    @Query("SElECT * FROM speech")
    List<SpeechEntity> getAll();

}
