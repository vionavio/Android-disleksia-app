package dyslexia.app.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "speech")
public class SpeechEntity {

    @PrimaryKey(autoGenerate = true)
    public int id_alphabetSpeech;

    @ColumnInfo(name = "letter")
    private String letter;

    @ColumnInfo(name = "transcription")
    private String transcription;

    @ColumnInfo(name = "coef")
    private int coef;

    public int getId_alphabetSpeech() {
        return id_alphabetSpeech;
    }

    public void setId_alphabetSpeech(int id_alphabetSpeech) {
        this.id_alphabetSpeech = id_alphabetSpeech;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public SpeechEntity(int id_alphabetSpeech, String letter, String transcription, int coef) {
        this.id_alphabetSpeech = id_alphabetSpeech;
        this.letter = letter;
        this.transcription = transcription;
        this.coef = coef;
    }

    @Override
    public String toString() {
        return "SpeechEntity{" +
                "id_alphabetSpeech=" + id_alphabetSpeech +
                ", letter='" + letter + '\'' +
                ", transcription='" + transcription + '\'' +
                ", coef=" + coef +
                '}';
    }
}
