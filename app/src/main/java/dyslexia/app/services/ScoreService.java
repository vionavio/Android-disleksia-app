package dyslexia.app.services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.entities.ScoreEntity;
import dyslexia.app.repositories.SharedPreferenceRepository;

import static dyslexia.app.utils.Constant.TAG;
import static dyslexia.app.utils.Constant.USER_ID;

public class ScoreService {
    public static List<ScoreEntity> getCurrentScoreAngka(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);

        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getScoreAngka(userId);
        Log.d(TAG, "currentScore: " + scoreEntityList);
        return scoreEntityList;
    }

    public static List<ScoreEntity> getCurrentScoreBenda(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);

        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getScoreBenda(userId);
        Log.d(TAG, "currentScore: " + scoreEntityList);
        return scoreEntityList;
    }

    public static List<ScoreEntity> getCurrentScoreKerja(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);

        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getScoreKerja(userId);
        Log.d(TAG, "currentScore: " + scoreEntityList);
        return scoreEntityList;
    }

    public static List<ScoreEntity> getCurrentScoreSifat(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);

        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getScoreSifat(userId);
        Log.d(TAG, "currentScore: " + scoreEntityList);
        return scoreEntityList;
    }

    public static List<ScoreEntity> getCurrentScoreKeterangan(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);

        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getScoreKeterangan(userId);
        Log.d(TAG, "currentScore: " + scoreEntityList);
        return scoreEntityList;
    }
}
