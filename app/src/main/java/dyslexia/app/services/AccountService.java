package dyslexia.app.services;

import android.content.Context;
import android.util.Log;

import dyslexia.app.database.AppDatabase;
import dyslexia.app.database.entities.UserEntity;
import dyslexia.app.repositories.SharedPreferenceRepository;

import static dyslexia.app.utils.Constant.TAG;
import static dyslexia.app.utils.Constant.USER_ID;

public class AccountService {
    public static String getName(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        UserEntity userEntity = appDatabase.userDao().getSinglebyId(userId);
        Log.d(TAG, "name: " + userEntity.name);
        return userEntity.name;
    }

    public static UserEntity getMyProfile(Context context) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        UserEntity userEntity = appDatabase.userDao().getSinglebyId(userId);
        return userEntity;
    }

    public static Integer getUserId(Context context) {
        return SharedPreferenceRepository.getInt(context, USER_ID);
    }

    public static Boolean updateMyProfile(Context context, UserEntity newUserEntity) {
        Integer userId = SharedPreferenceRepository.getInt(context, USER_ID);
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        UserEntity userEntity = appDatabase.userDao().getSinglebyId(userId);

        if (!newUserEntity.name.isEmpty()) {
            userEntity.name = newUserEntity.name;
        }
        if (!newUserEntity.gender.isEmpty()) {
            userEntity.gender = newUserEntity.gender;
        }
        if (!newUserEntity.dob.isEmpty()) {
            userEntity.dob = newUserEntity.dob;
        }
        if (!newUserEntity.email.isEmpty()) {
            userEntity.email = newUserEntity.email;
        }
        if (!newUserEntity.password.isEmpty()) {
            userEntity.password = newUserEntity.password;
        }

        Integer updateRow = appDatabase.userDao().update(userEntity);
        return updateRow == 1;
    }
}
