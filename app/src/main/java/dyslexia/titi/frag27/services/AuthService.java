package dyslexia.titi.frag27.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static dyslexia.titi.frag27.utils.Constant.IS_LOGGED_IN;
import static dyslexia.titi.frag27.utils.Constant.TAG;
import static dyslexia.titi.frag27.utils.Constant.USER_ID;

public class AuthService {
    public static void saveLoginInfo(Context context, Integer userId) {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(context);
        mSettings.edit().putBoolean(IS_LOGGED_IN, true).apply();
        mSettings.edit().putInt(USER_ID, userId).apply();
        Log.d(TAG, "saveLoginInfo: " + userId);
    }

    public static void removeLoginInfo(Context context) {
    }
}
