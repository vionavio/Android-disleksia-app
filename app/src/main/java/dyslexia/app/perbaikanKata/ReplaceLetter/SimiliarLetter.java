package dyslexia.app.perbaikanKata.ReplaceLetter;

public class SimiliarLetter {
    // model yg menyimpan karakter sebelum dan sesudah

    //letterKey = huruf yang masuk daftar untuk terdeteksi
    // "aasds"
    // 'a'
    private Character letterKey;
    private Character letterEquation;

    public SimiliarLetter(Character letterKey, Character letterEquation) {
        this.letterKey = letterKey;
        this.letterEquation = letterEquation;
    }

    public Character getLetterKey() {
        return letterKey;
    }

    public void setLetterKey(Character letterKey) {
        this.letterKey = letterKey;
    }

    public Character getLetterEquation() {
        return letterEquation;
    }

    public void setLetterEquation(Character letterEquation) {
        this.letterEquation = letterEquation;
    }
}
