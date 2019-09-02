package dyslexia.titi.frag27.kamus.model;
// Created by Arif Ikhsanudin on 9/2/2019.

public class KamusSimilar extends Kamus {
    private double similarScore;

    public KamusSimilar(long id_word, String word, String type, double similarScore) {
        super(id_word, word, type);
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
        return "KamusSimilar{" +
                "word=" + getWord() + " " +
                "similarScore=" + similarScore +
                '}';
    }
}
