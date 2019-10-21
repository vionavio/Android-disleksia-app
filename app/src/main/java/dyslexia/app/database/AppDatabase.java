package dyslexia.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dyslexia.app.database.daos.ScoreDao;
import dyslexia.app.database.daos.UserDao;
import dyslexia.app.database.daos.WordDao;
import dyslexia.app.database.entities.ScoreEntity;
import dyslexia.app.database.entities.UserEntity;
import dyslexia.app.database.entities.WordEntity;

import static dyslexia.app.utils.Constant.DATABASE_NAME;

@Database(entities = {UserEntity.class, ScoreEntity.class, WordEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabaseInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabaseInstance == null) {
            appDatabaseInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME
            ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabaseInstance;
    }

    public abstract UserDao userDao();

    public abstract ScoreDao scoreDao();

    public abstract WordDao wordDao();
}
