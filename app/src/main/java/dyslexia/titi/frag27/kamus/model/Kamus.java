package dyslexia.titi.frag27.kamus.model;

public class Kamus {
    // inisialisasi
    public long id_word;
    public String word;
    public String type;

    public Kamus(long id_word, String word, String type) {
        this.id_word = id_word;
        this.word = word;
        this.type = type;
    }

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

    @Override
    public String toString() {
        return word;
    }
}