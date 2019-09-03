package dyslexia.titi.frag27.kamus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import dyslexia.titi.frag27.kamus.model.Kamus;

public class DatabaseAdapter extends SQLiteAssetHelper {


    private static final String DB_NAME = "dictionary2.db";
    private static final int DB_VER = 1;
    public Kamus kamus;
    SQLiteDatabase database;

    public DatabaseAdapter(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    /*
    1. INITIALIZE DB HELPER AND PASS IT A CONTEXT
     */


    /*
     1. RETRIEVE SPACECRAFTS FROM DB AND POPULATE ARRAYLIST
     2. RETURN THE LIST
     */
    public List<Kamus> retrieveKamus(String type) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //make sure kolom adalah nama table
        String[] sqlSelect = {"id_word", "word", "type"};
        String tableName = "words";
        qb.setTables(tableName);
        Cursor cursor;

        if (type.equals("all")) {
            cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        } else {
            cursor = qb.query(db, sqlSelect, "type LIKE ?", new String[]{type}, null, null, null);
        }

        List<Kamus> kamuses = new ArrayList<>();

        // Cursor c=db.rawQuery("SELECT * FROM WORDS WHERE TYPE "+type+"'",null);

        if (cursor.moveToFirst()) {
            do {
                Kamus kamus = new Kamus(0, "", "");
                kamus.setId_word(cursor.getLong(cursor.getColumnIndex("id_word")));
                kamus.setWord(cursor.getString(cursor.getColumnIndex("word")));
                kamus.setType(cursor.getString(cursor.getColumnIndex("type")));

                kamuses.add(kamus);
            } while (cursor.moveToNext());
        }
        return kamuses;
    }


    public Kamus getKamus(long id_word) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id_word", "word", "type"};
        String tableName = "words";
        qb.setTables(tableName);



        Cursor cursor = qb.query(db, sqlSelect, "id_word =" + id_word, null, null, null, null);

        List<Kamus> kamuses = new ArrayList<>();

        // Cursor c=db.rawQuery("SELECT * FROM WORDS WHERE TYPE "+type+"'",null);

        if (cursor.moveToFirst()) {
            do {
                Kamus kamus = new Kamus(0, "", "");
                kamus.setId_word(cursor.getLong(cursor.getColumnIndex("id_word")));
                kamus.setWord(cursor.getString(cursor.getColumnIndex("word")));
                kamus.setType(cursor.getString(cursor.getColumnIndex("type")));

                kamuses.add(kamus);
            } while (cursor.moveToNext());
        }
        return kamus;

    }

    public void deleteKamus(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("words","id_word" + " ='" + id + "'",null);

    }

    public void open() {
        database = this.getWritableDatabase();
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

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + "words";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

}
