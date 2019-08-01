package dyslexia.titi.frag27.login.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dyslexia.titi.frag27.login.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "disleksia.db";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;


    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String TABLE_WORDS = "words";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";
    public static final String KEY_ID_WORD = "id_word";

    //COLUMN kamus
    public static final String KEY_WORD = "word";
    public static final String KEY_TYPE = "type";


    //COLUMN user name
    public static final String KEY_NAME = "name";
    //COLUMN user name
    public static final String KEY_JK = "jenis_kelamin";
    //COLUMN user name
    public static final String KEY_TL = "tanggal_lahir";
    //COLUMN user name
    public static final String KEY_USER_NAME = "username";
    //COLUMN email
    public static final String KEY_EMAIL = "email";
    //COLUMN password
    public static final String KEY_PASSWORD = "password";


    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_JK + " TEXT, "
            + KEY_TL + " DATE, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    //SQL for creating word table
    public static final String SQL_TABLE_WORDS = " CREATE TABLE " + TABLE_WORDS
            + " ( "
            + KEY_ID_WORD + " INTEGER PRIMARY KEY , "
            + KEY_WORD + " TEXT, "
            + KEY_TYPE + " TEXT "
            + " ) ";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_WORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_WORDS);
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values to insert
        ContentValues values = new ContentValues();
        //Put name in  @values
        values.put(KEY_NAME, user.name);
        //Put jk in  @values
        values.put(KEY_JK, user.jk);
        //Put tl in  @values
        values.put(KEY_TL, user.tanggaLahir);
        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);
        //Put email in  @values
        values.put(KEY_EMAIL, user.email);
        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);
        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }


    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_NAME, KEY_JK, KEY_TL, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),  cursor.getString(5), cursor.getString(6));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_NAME, KEY_JK, KEY_TL, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
}
