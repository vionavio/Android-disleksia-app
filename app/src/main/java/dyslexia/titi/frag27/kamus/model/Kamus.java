package dyslexia.titi.frag27.kamus.model;

public class Kamus {
    //inisialisasi
    private long id_word;
    private String word;
    private String type;


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
