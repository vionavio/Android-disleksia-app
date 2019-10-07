package dyslexia.titi.frag27.permainan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.titi.frag27.MenuActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.permainan.kuis.KuisActivity;
import dyslexia.titi.frag27.permainan.susunAlfabet.UppercaseAlfabet;

public class PermainanActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permainan);

        CardView alfabet = findViewById(R.id.susunabjad);
        alfabet.setOnClickListener(view -> {
            Intent alfabetIntent = new Intent(PermainanActivity.this, UppercaseAlfabet.class);
            startActivity(alfabetIntent);
        });

        CardView kuis = findViewById(R.id.kuis);
        kuis.setOnClickListener(view -> {
            Intent kuisIntent = new Intent(PermainanActivity.this, KuisActivity.class);
            startActivity(kuisIntent);
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}
