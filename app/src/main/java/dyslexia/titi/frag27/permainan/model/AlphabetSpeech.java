package dyslexia.titi.frag27.permainan.model;

public class AlphabetSpeech {

    private int id_alphabetspeech;
    private String letter;
    private String transcription;
    private int coef;

    public AlphabetSpeech(int id_alphabetspeech, String letter, String transcription, int coef) {
        this.id_alphabetspeech = id_alphabetspeech;
        this.letter = letter;
        this.transcription = transcription;
        this.coef = coef;
    }

    public int getId_alphabetspeech() {
        return id_alphabetspeech;
    }

    public void setId_alphabetspeech(int id_alphabetspeech) {
        this.id_alphabetspeech = id_alphabetspeech;
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
}
