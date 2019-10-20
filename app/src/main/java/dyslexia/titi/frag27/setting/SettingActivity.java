package dyslexia.titi.frag27.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import dyslexia.titi.frag27.MainActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.services.AccountService;
import dyslexia.titi.frag27.services.AuthService;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button btnLogout = findViewById(R.id.btn_logout);
        Button btnProfil = findViewById(R.id.btn_profil);


        btnProfil.setOnClickListener(view -> {
            startActivity(new Intent(this, ProfilActivity.class));
        });

        btnLogout.setOnClickListener(view -> {
            AuthService.logout(this);
            startActivity(new Intent(this, MainActivity.class));
        });
 }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
