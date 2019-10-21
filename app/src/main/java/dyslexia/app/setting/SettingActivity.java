package dyslexia.app.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dyslexia.app.MainActivity;
import dyslexia.app.R;
import dyslexia.app.services.AuthService;

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
