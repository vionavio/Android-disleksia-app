package dyslexia.app.database.daos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import dyslexia.app.database.entities.SpeechEntity;

@Dao
public interface SpeechDao {

    @Query("SElECT * FROM speech")
    List<SpeechEntity> getAll();

}
