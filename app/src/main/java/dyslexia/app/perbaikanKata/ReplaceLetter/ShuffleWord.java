package dyslexia.app.perbaikanKata.ReplaceLetter;

public class ShuffleWord {
    //model kelas untuk menyimpan index, word key dan word equation

    private int index;
    private Character word_key;
    private Character word_equation;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Character getWord_key() {
        return word_key;
    }

    public void setWord_key(Character word_key) {
        this.word_key = word_key;
    }

    public Character getWord_equation() {
        return word_equation;
    }

    public void setWord_equation(Character word_equation) {
        this.word_equation = word_equation;
    }

    public ShuffleWord(int index, Character word_key, Character word_equation) {
        this.index = index;
        this.word_key = word_key;
        this.word_equation = word_equation;
    }
}
