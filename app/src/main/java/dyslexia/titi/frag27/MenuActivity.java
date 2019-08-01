package dyslexia.titi.frag27;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.titi.frag27.kamus.KamusActivity;

public class MenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CardView kamus = (CardView) findViewById(R.id.kamus);
        kamus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent kamusIntent = new Intent(MenuActivity.this, KamusActivity.class);
                startActivity(kamusIntent);
            }
        });

//        CardView perbaikanKata = (CardView) findViewById(R.id.perbaikankata);
//        perbaikanKata.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent perbaikanKataIntent = new Intent(MenuActivity.this, PerbaikanKataActivity.class);
//                startActivity(perbaikanKataIntent);
//            }
//        });
//
//        CardView permainan = (CardView) findViewById(R.id.permainan);
//        permainan.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent permainanIntent = new Intent(MenuActivity.this, KamusActivity.class);
//                startActivity(permainanIntent);
//            }
//        });
//
//        CardView bantuan = (CardView) findViewById(R.id.bantuan);
//        bantuan.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent bantuanIntent = new Intent(MenuActivity.this, BantuanActivity.class);
//                startActivity(bantuanIntent);
//            }
//        });
//
//        CardView tentang = (CardView) findViewById(R.id.tentang);
//        tentang.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent tentangIntent = new Intent(MenuActivity.this, TentangActivity.class);
//                startActivity(tentangIntent);
//            }
//        });

//        final AppDatabase db = RoomAsset.databaseBuilder(
//                getApplicationContext(), AppDatabase.class, "dictionary.sqlite").build();
//        db.wordDao().getWords().observe(this, new Observer<List<WordEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<WordEntity> wordEntities) {
//                assert wordEntities != null;
//                Log.d("Word entity = ", wordEntities.get(1).getWord());
//            }
//        });

        CardView settingCardView = findViewById(R.id.cv_setting);
        settingCardView.setOnClickListener(view -> {
            Intent settingIntent = new Intent(MenuActivity.this, SettingActivity.class);
            startActivity(settingIntent);
        });
    }

    // tombol kembali langsung keluar
    @Override
    public void onBackPressed() {
        // TODO: Use declarative variable name
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
