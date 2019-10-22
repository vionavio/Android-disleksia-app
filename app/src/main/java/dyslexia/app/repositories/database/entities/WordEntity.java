package dyslexia.app.repositories.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class WordEntity {
    @PrimaryKey(autoGenerate = true)
    public long id_word;

    @ColumnInfo(name = "word")
    public String word;

    @ColumnInfo(name = "type")
    public String type;

    public long getId_word() {
        return id_word;
    }

    public void setId_word(long id_word) {
        this.id_word = id_word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WordEntity(long id_word, String word, String type) {
        this.id_word = id_word;
        this.word = word;
        this.type = type;
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                "id_word=" + id_word +
                ", word='" + word + '\'' +
                ", type='" + type + '\'' +
                '}' + '\n';
    }
}
