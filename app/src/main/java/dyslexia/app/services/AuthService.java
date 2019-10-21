package dyslexia.app.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dyslexia.app.repositories.SharedPreferenceRepository;

import static dyslexia.app.utils.Constant.IS_LOGGED_IN;
import static dyslexia.app.utils.Constant.USER_ID;

public class AuthService {
    public static void saveLoginInfo(Context context, Integer userId) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(context);
        mSettings.edit().putBoolean(IS_LOGGED_IN, true).apply();
        mSettings.edit().putInt(USER_ID, userId).apply();
    }

    public static Boolean isLoggedIn(Context context) {
        return SharedPreferenceRepository.getBoolean(context, IS_LOGGED_IN);
    }

    public static void logout(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }

    public static void removeLoginInfo(Context context) {
    }
}
