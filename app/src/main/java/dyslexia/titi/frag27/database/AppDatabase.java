package dyslexia.titi.frag27.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dyslexia.titi.frag27.database.daos.ScoreDao;
import dyslexia.titi.frag27.database.daos.UserDao;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.database.entities.UserEntity;

import static dyslexia.titi.frag27.utils.Constant.DATABASE_NAME;

@Database(entities = {UserEntity.class, ScoreEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabaseInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabaseInstance == null) {
            appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabaseInstance;
    }

    public abstract UserDao userDao();

    public abstract ScoreDao scoreDao();
}
