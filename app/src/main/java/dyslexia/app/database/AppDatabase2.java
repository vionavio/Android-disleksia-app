package dyslexia.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dyslexia.app.database.daos.ScoreDao;
import dyslexia.app.database.daos.SpeechDao;
import dyslexia.app.database.daos.UserDao;
import dyslexia.app.database.daos.WordDao;
import dyslexia.app.database.entities.ScoreEntity;
import dyslexia.app.database.entities.SpeechEntity;
import dyslexia.app.database.entities.UserEntity;
import dyslexia.app.database.entities.WordEntity;

import static dyslexia.app.utils.Constant.DATABASE_NAME;

@Database(entities = { WordEntity.class, SpeechEntity.class}, version = 1)
public abstract class AppDatabase2 extends RoomDatabase {
    private static AppDatabase2 appDatabaseInstance;

    private static final Object sLock = new Object();

    public static synchronized AppDatabase2 getInstance(Context context) {
        synchronized (sLock) {

            if (appDatabaseInstance == null) {
                appDatabaseInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase2.class, "dictionary2.db"
                ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
            return appDatabaseInstance;


        }
    }

    public abstract WordDao wordDao();

    public abstract SpeechDao speechDao();

}
