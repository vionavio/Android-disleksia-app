package dyslexia.app.ui.kamus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import dyslexia.app.ui.kamus.model.Dictionary;
import dyslexia.app.ui.permainan.alphabetSpeech.model.AlphabetSpeech;

public class DatabaseDictionary extends SQLiteAssetHelper {


    private static final String DB_NAME = "dictionary2.db";
    private static final int DB_VER = 1;
    public Dictionary dictionary;
    SQLiteDatabase database;



    public DatabaseDictionary(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    public List<Dictionary> retrieveKamus(String type) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //make sure kolom adalah nama table
        String[] sqlSelect = {"id_word", "LOWER (word)", "type"};
        String tableName = "words";
        qb.setTables(tableName);
        Cursor cursor;

        if (type.equals("all")) {
            cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        } else {
            cursor = qb.query(db, sqlSelect, "type LIKE ?", new String[]{type}, null, null, null);
        }

        List<Dictionary> dictionaries = new ArrayList<>();

        // Cursor c=db.rawQuery("SELECT * FROM WORDS WHERE TYPE "+type+"'",null);

        if (cursor.moveToFirst()) {
            do {
                Dictionary dictionary = new Dictionary(0, "", "");
                dictionary.setId_word(cursor.getLong(cursor.getColumnIndex("id_word")));
                dictionary.setWord(cursor.getString(cursor.getColumnIndex("LOWER (word)")));
                dictionary.setType(cursor.getString(cursor.getColumnIndex("type")));

                dictionaries.add(dictionary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dictionaries;
    }


    public long insert(String word, String type) {

        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("type", type);
        long id = database.insert("words", null, contentValues);

        // close db connection
        database.close();

        // return newly inserted row id

        return id;
    }

    public List<Dictionary> getWord(String word){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //make sure kolom adalah nama table
        String[] sqlSelect = {"id_word", "LOWER (word)", "type"};
        String tableName = "words";
        qb.setTables(tableName);
        Cursor cursor;

        cursor = qb.query(db, sqlSelect, "word LIKE ?", new String[]{word}, null, null, null);

        List<Dictionary> dictionaries = new ArrayList<>();

        // Cursor c=db.rawQuery("SELECT * FROM WORDS WHERE TYPE "+type+"'",null);

        if (cursor.moveToFirst()) {
            do {
                Dictionary dictionary = new Dictionary(0, "", "");
                dictionary.setId_word(cursor.getLong(cursor.getColumnIndex("id_word")));
                dictionary.setWord(cursor.getString(cursor.getColumnIndex("LOWER (word)")));

                dictionaries.add(dictionary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dictionaries;
    }

    public List<AlphabetSpeech> retrieveSpeech() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //make sure kolom adalah nama table
        String[] sqlSelect = {"id_speech", "letter", "transcription", " coef"};
        String tableName = "speech";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        List<AlphabetSpeech> speeches = new ArrayList<>();

        // Cursor c=db.rawQuery("SELECT * FROM WORDS WHERE TYPE "+type+"'",null);

        if (cursor.moveToFirst()) {
            do {
                AlphabetSpeech alphabetSpeech = new AlphabetSpeech();
                alphabetSpeech.setLetter(cursor.getString(cursor.getColumnIndex("letter")));
                alphabetSpeech.setTranscription(cursor.getString(cursor.getColumnIndex("transcription")));
                alphabetSpeech.setCoef(cursor.getInt(cursor.getColumnIndex("coef")));
                speeches.add(alphabetSpeech);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return speeches;
    }


}
