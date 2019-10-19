package dyslexia.titi.frag27.services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import dyslexia.titi.frag27.database.AppDatabase;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.utils.SharedPreferenceRepository;

import static dyslexia.titi.frag27.utils.Constant.TAG;
import static dyslexia.titi.frag27.utils.Constant.USER_ID;

public class ScoreService {
    public static List<ScoreEntity> getCurrentScore(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);

        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getCurrentScores(userId);
        Log.d(TAG, "currentScore: " + scoreEntityList);
        return scoreEntityList;
    }
}
