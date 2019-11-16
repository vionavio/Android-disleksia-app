package dyslexia.app.ui.feature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.app.R;
import dyslexia.app.ui.bantuan.BantuanActivity;
import dyslexia.app.ui.kamus.DictionaryActivity;
import dyslexia.app.ui.perbaikanKata.WordRepairActivity;
import dyslexia.app.ui.permainan.PermainanActivity;
import dyslexia.app.ui.setting.SettingActivity;
import dyslexia.app.ui.tentang.TentangActivity;

public class MenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CardView kamus =  findViewById(R.id.kamus);
        kamus.setOnClickListener(view -> {
            startActivity(new Intent(MenuActivity.this, DictionaryActivity.class));
        });

        CardView settingCardView =  findViewById(R.id.cv_setting);
        settingCardView.setOnClickListener(view -> {
            startActivity( new Intent(MenuActivity.this, SettingActivity.class));
        });

        CardView permainan =  findViewById(R.id.permainan);
        permainan.setOnClickListener((View view) -> {
            startActivity(new Intent(MenuActivity.this, PermainanActivity.class));
        });

        CardView bantuan =  findViewById(R.id.bantuan);
        bantuan.setOnClickListener(view -> {
            startActivity(new Intent(MenuActivity.this, BantuanActivity.class));
        });

        CardView tentang = findViewById(R.id.tentang);
        tentang.setOnClickListener(view -> {
            startActivity(new Intent(MenuActivity.this, TentangActivity.class));
        });

        CardView perbaikanKata = findViewById(R.id.perbaikankata);
        perbaikanKata.setOnClickListener(view -> {
            startActivity(new Intent(MenuActivity.this, WordRepairActivity.class));
        });
    }

    // tombol kembali langsung keluar
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
