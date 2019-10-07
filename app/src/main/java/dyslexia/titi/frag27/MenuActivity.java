package dyslexia.titi.frag27;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.titi.frag27.bantuan.BantuanActivity;
import dyslexia.titi.frag27.kamus.DictionaryActivity;
import dyslexia.titi.frag27.perbaikanKata.WordRepairActivity;
import dyslexia.titi.frag27.permainan.PermainanActivity;
import dyslexia.titi.frag27.setting.SettingActivity;
import dyslexia.titi.frag27.tentang.TentangActivity;

public class MenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CardView kamus =  findViewById(R.id.kamus);
        kamus.setOnClickListener(view -> {
            Intent kamusIntent = new Intent(MenuActivity.this, DictionaryActivity.class);
            startActivity(kamusIntent);
        });

        CardView settingCardView =  findViewById(R.id.cv_setting);
        settingCardView.setOnClickListener(view -> {
            Intent perbaikanKataIntent = new Intent(MenuActivity.this, SettingActivity.class);
            startActivity(perbaikanKataIntent);
        });

        CardView permainan =  findViewById(R.id.permainan);
        permainan.setOnClickListener((View view) -> {
            Intent permainanIntent = new Intent(MenuActivity.this, PermainanActivity.class);
            startActivity(permainanIntent);
        });

        CardView bantuan =  findViewById(R.id.bantuan);
        bantuan.setOnClickListener(view -> {
            Intent bantuanIntent = new Intent(MenuActivity.this, BantuanActivity.class);
            startActivity(bantuanIntent);
        });

        CardView tentang = findViewById(R.id.tentang);
        tentang.setOnClickListener(view -> {
            Intent tentangIntent = new Intent(MenuActivity.this, TentangActivity.class);
            startActivity(tentangIntent);
        });

        CardView perbaikanKata = findViewById(R.id.perbaikankata);
        perbaikanKata.setOnClickListener(view -> {
            Intent perbaikanKataIntent = new Intent(MenuActivity.this, WordRepairActivity.class);
            startActivity(perbaikanKataIntent);
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
}
