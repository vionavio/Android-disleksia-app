package dyslexia.titi.frag27;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dyslexia.titi.frag27.login.LoginActivity;
import dyslexia.titi.frag27.login.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (mSettings.getBoolean("isLoggedIn", false)) {
            Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(menuIntent);
        }
        setContentView(R.layout.activity_main);
    }

    public void Masuk(View view) {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void Register(View view) {
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
