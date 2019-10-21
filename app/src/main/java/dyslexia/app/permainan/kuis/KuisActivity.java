package dyslexia.app.permainan.kuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import dyslexia.app.R;
import dyslexia.app.permainan.PermainanActivity;
import dyslexia.app.permainan.kuis.game.GameKataBendaActivity;
import dyslexia.app.permainan.kuis.game.GameKataKerjaActivity;
import dyslexia.app.permainan.kuis.game.GameKataKeteranganActivity;
import dyslexia.app.permainan.kuis.game.GameKataSifatActivity;
import dyslexia.app.permainan.kuis.game.GameSimbolAngkaActivity;
import dyslexia.app.permainan.kuis.skor.ScoreActivity;

public class KuisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuis);

        Button simbolAngka =  findViewById(R.id.btn_sa);
        simbolAngka.setOnClickListener(view -> {
            startActivity(new Intent(KuisActivity.this, GameSimbolAngkaActivity.class));
        });

        Button kataBenda =  findViewById(R.id.btn_kb);
        kataBenda.setOnClickListener(view -> {
            startActivity(new Intent(KuisActivity.this, GameKataBendaActivity.class));
        });

        Button kataKerja =  findViewById(R.id.btn_kk);
        kataKerja.setOnClickListener(view -> {
            startActivity(new Intent(KuisActivity.this, GameKataKerjaActivity.class));
        });

        Button kataSifat =  findViewById(R.id.btn_ks);
        kataSifat.setOnClickListener(view -> {
            startActivity(new Intent(KuisActivity.this, GameKataSifatActivity.class));
        });

        Button kataKeterangan =  findViewById(R.id.btn_kket);
        kataKeterangan.setOnClickListener(view -> {
            startActivity(new Intent(KuisActivity.this, GameKataKeteranganActivity.class));
        });

        Button skor =  findViewById(R.id.skor);
        skor.setOnClickListener(view -> {
            startActivity(new Intent(KuisActivity.this, ScoreActivity.class));
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), PermainanActivity.class);
        startActivity(intent);
    }
}
