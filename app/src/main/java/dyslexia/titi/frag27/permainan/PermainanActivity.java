package dyslexia.titi.frag27.permainan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.titi.frag27.MenuActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.KamusActivity;
import dyslexia.titi.frag27.permainan.kuis.KuisActivity;
import dyslexia.titi.frag27.permainan.susunAlfabet.SusunAlfabet;
import dyslexia.titi.frag27.setting.SettingActivity;

public class PermainanActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permainan);

        CardView alfabet = findViewById(R.id.susunabjad);
        alfabet.setOnClickListener(view -> {
            Intent alfabetIntent = new Intent(PermainanActivity.this, SusunAlfabet.class);
            startActivity(alfabetIntent);
        });

        CardView kuis = findViewById(R.id.kuis);
        kuis.setOnClickListener(view -> {
            Intent kuisIntent = new Intent(PermainanActivity.this, KuisActivity.class);
            startActivity(kuisIntent);
        });
    }
}
