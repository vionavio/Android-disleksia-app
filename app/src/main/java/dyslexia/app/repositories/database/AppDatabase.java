package dyslexia.app.repositories.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dyslexia.app.repositories.database.daos.ScoreDao;
import dyslexia.app.repositories.database.daos.SpeechDao;
import dyslexia.app.repositories.database.daos.UserDao;
import dyslexia.app.repositories.database.daos.WordDao;
import dyslexia.app.repositories.database.entities.ScoreEntity;
import dyslexia.app.repositories.database.entities.SpeechEntity;
import dyslexia.app.repositories.database.entities.UserEntity;
import dyslexia.app.repositories.database.entities.WordEntity;
import dyslexia.app.repositories.database.prepopulates.UserEntityPopulate;

import static dyslexia.app.utils.Constant.DATABASE_NAME;

@Database(entities = {UserEntity.class, ScoreEntity.class, WordEntity.class, SpeechEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabaseInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabaseInstance == null) {
            appDatabaseInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME
            )
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            AsyncTask.execute(() -> getInstance(context).userDao().insert(UserEntityPopulate.getUser()));
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabaseInstance;
    }

    public abstract UserDao userDao();

    public abstract ScoreDao scoreDao();

    public abstract WordDao wordDao();

    public abstract SpeechDao speechDao();
}
