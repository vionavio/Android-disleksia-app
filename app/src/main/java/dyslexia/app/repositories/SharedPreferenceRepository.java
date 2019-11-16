package dyslexia.app.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//penyimpanan sementara
public class SharedPreferenceRepository {
    private static SharedPreferences sharedPreferences;

    public static void setInt(Context context, String key, Integer value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static Integer getInt(Context context, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static Boolean getBoolean(Context context, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key, Boolean value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void removeInt(Context context, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
