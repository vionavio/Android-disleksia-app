package dyslexia.app.ui.perbaikanKata.JaroWinkler;

import dyslexia.app.ui.kamus.model.Dictionary;


//kelas baru yang mewarisi dictionary untuk menampilkan nilai jaro
public class DictionarySimilar extends Dictionary {
    private double similarScore;

    public DictionarySimilar(long id_word, String word, String type, double similarScore) {
        super((int) id_word, word, type);
        this.similarScore = similarScore;
    }

    public double getSimilarScore() {
        return similarScore;
    }

    public void setSimilarScore(double similarScore) {
        this.similarScore = similarScore;
    }

    @Override
    public String toString() {
        return "DictionarySimilar{" +
                "word=" + getWord() + " " +
                "similarScore=" + similarScore +
                "}\n";
    }
}
