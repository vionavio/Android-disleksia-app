package dyslexia.titi.frag27.permainan.kuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.permainan.kuis.kataBenda.GameKataBendaActivity;
import dyslexia.titi.frag27.permainan.kuis.kataKerja.GameKataKerjaActivity;
import dyslexia.titi.frag27.permainan.kuis.kataKeterangan.GameKataKeteranganActivity;
import dyslexia.titi.frag27.permainan.kuis.kataSifat.GameKataSifatActivity;
import dyslexia.titi.frag27.permainan.kuis.simbolAngka.GameSimbolAngkaActivity;

public class KuisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuis);

        Button simbolAngka =  findViewById(R.id.btn_sa);
        simbolAngka.setOnClickListener(view -> {
            Intent simbolAngkaIntent = new Intent(KuisActivity.this, GameSimbolAngkaActivity.class);
            startActivity(simbolAngkaIntent);
        });

        Button kataBenda =  findViewById(R.id.btn_kb);
        kataBenda.setOnClickListener(view -> {
            Intent kataBendaIntent = new Intent(KuisActivity.this, GameKataBendaActivity.class);
            startActivity(kataBendaIntent);
        });

        Button kataKerja =  findViewById(R.id.btn_kk);
        kataKerja.setOnClickListener(view -> {
            Intent kataKerjaIntent = new Intent(KuisActivity.this, GameKataKerjaActivity.class);
            startActivity(kataKerjaIntent);
        });

        Button kataSifat =  findViewById(R.id.btn_ks);
        kataSifat.setOnClickListener(view -> {
            Intent kataSifatIntent = new Intent(KuisActivity.this, GameKataSifatActivity.class);
            startActivity(kataSifatIntent);
        });

        Button kataKeterangan =  findViewById(R.id.btn_kket);
        kataKeterangan.setOnClickListener(view -> {
            Intent kataKeteranganIntent = new Intent(KuisActivity.this, GameKataKeteranganActivity.class);
            startActivity(kataKeteranganIntent);
        });
    }
}
