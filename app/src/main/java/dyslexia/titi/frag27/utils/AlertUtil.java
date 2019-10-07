package dyslexia.titi.frag27.utils;
// Created by Arif Ikhsanudin on 10/7/2019.

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class AlertUtil {
    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
