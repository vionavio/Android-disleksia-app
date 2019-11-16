package dyslexia.app.ui.permainan.alphabetSpeech.model;

public class AlphabetSpeech {


    private int id_speech;

    public int getId_speech() {
        return id_speech;
    }

    public void setId_speech(int id_speech) {
        this.id_speech = id_speech;
    }

    private String letter;
    private String transcription;
    private int coef;


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
}
