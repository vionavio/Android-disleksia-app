package dyslexia.titi.frag27.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import dyslexia.titi.frag27.repositories.SharedPreferenceRepository;

import static dyslexia.titi.frag27.utils.Constant.IS_LOGGED_IN;
import static dyslexia.titi.frag27.utils.Constant.TAG;
import static dyslexia.titi.frag27.utils.Constant.USER_ID;

public class AuthService {
    public static void saveLoginInfo(Context context, Integer userId) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(context);
        mSettings.edit().putBoolean(IS_LOGGED_IN, true).apply();
        mSettings.edit().putInt(USER_ID, userId).apply();
    }

    public static Boolean isLoggedIn(Context context) {
        return SharedPreferenceRepository.getBoolean(context, IS_LOGGED_IN);
    }

    public static void removeLoginInfo(Context context) {
    }
}
