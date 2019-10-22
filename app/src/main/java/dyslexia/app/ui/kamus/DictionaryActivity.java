package dyslexia.app.ui.kamus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.app.R;
import dyslexia.app.ui.kamus.menu.angka.AngkaActivity;
import dyslexia.app.ui.kamus.menu.benda.BendaActivity;
import dyslexia.app.ui.kamus.menu.huruf.HurufActivity;
import dyslexia.app.ui.kamus.menu.kerja.KerjaActivity;
import dyslexia.app.ui.kamus.menu.keterangan.KeteranganActivity;
import dyslexia.app.ui.kamus.menu.konsonan.MenuKonsonanActivity;
import dyslexia.app.ui.kamus.menu.sifat.SifatActivity;

import static dyslexia.app.R.layout.activity_dictionary;

public class DictionaryActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_dictionary);

        CardView huruf =  findViewById(R.id.simbol_huruf);
        huruf.setOnClickListener(view -> {
            startActivity(new Intent(this, HurufActivity.class));
        });

        CardView angka =  findViewById(R.id.simbol_angka);
        angka.setOnClickListener(view -> {
            startActivity(new Intent(this, AngkaActivity.class));
        });

        CardView menuKonsonan =  findViewById(R.id.konsonan);
        menuKonsonan.setOnClickListener(view -> {
            startActivity(new Intent(this, MenuKonsonanActivity.class));
        });

        CardView kataBenda =  findViewById(R.id.kata_benda);
        kataBenda.setOnClickListener(view -> {
            startActivity(new Intent(this, BendaActivity.class));
        });

        CardView kataKerja =  findViewById(R.id.kata_kerja);
        kataKerja.setOnClickListener(view -> {
            startActivity(new Intent(this, KerjaActivity.class));
        });

        CardView kataSifat =  findViewById(R.id.kata_sifat);
        kataSifat.setOnClickListener(view -> {
            startActivity(new Intent(this, SifatActivity.class));
        });

        CardView kataKeterangan =  findViewById(R.id.kata_keterangan);
        kataKeterangan.setOnClickListener(view -> {
            startActivity(new Intent(this, KeteranganActivity.class));
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}