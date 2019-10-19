package dyslexia.titi.frag27.services;

import android.content.Context;
import android.util.Log;

import dyslexia.titi.frag27.database.AppDatabase;
import dyslexia.titi.frag27.database.entities.UserEntity;
import dyslexia.titi.frag27.utils.SharedPreferenceRepository;

import static dyslexia.titi.frag27.utils.Constant.TAG;
import static dyslexia.titi.frag27.utils.Constant.USER_ID;

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
