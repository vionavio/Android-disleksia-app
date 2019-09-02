package dyslexia.titi.frag27.kamus.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
// Created by Arif Ikhsanudin on 9/2/2019.

public class KamusTest {

    private Kamus dictionary;

    private long id = 1;
    private String word = "word";
    private String type = "adverb";

    @Before
    public void setUp() throws Exception {
        dictionary = new Kamus(id, word, type);
    }

    @After
    public void tearDown() throws Exception {
        dictionary = null;
    }

    @Test
    public void getId_word() {
        assertEquals(dictionary.getId_word(), id);
    }

    @Test
    public void setId_word() {
        dictionary.setId_word(id);
        assertEquals(dictionary.getId_word(), id);
    }

    @Test
    public void getWord() {
        assertEquals(dictionary.getWord(), word);
    }

    @Test
    public void setWord() {
        dictionary.setWord(word);
        assertEquals(dictionary.getWord(), word);
    }

    @Test
    public void getType() {
        assertEquals(dictionary.getType(), type);
    }

    @Test
    public void setType() {
        dictionary.setType(type);
        assertEquals(dictionary.getType(), type);
    }

    @Test
    public void toString1() {
        assertNotNull(dictionary.toString());
    }
}