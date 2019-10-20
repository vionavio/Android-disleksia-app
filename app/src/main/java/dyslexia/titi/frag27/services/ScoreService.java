package dyslexia.titi.frag27.services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import dyslexia.titi.frag27.database.AppDatabase;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.repositories.SharedPreferenceRepository;

import static dyslexia.titi.frag27.utils.Constant.TAG;
import static dyslexia.titi.frag27.utils.Constant.USER_ID;

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
