package dyslexia.titi.frag27.permainan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import dyslexia.titi.frag27.MenuActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.permainan.alphabetSpeech.AlphabetSpeechActivity;
import dyslexia.titi.frag27.permainan.kuis.KuisActivity;
import dyslexia.titi.frag27.permainan.susunAlfabet.UppercaseAlfabet;

public class PermainanActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permainan);

        CardView alfabet = findViewById(R.id.susunabjad);
        alfabet.setOnClickListener(view -> {
            startActivity(new Intent(PermainanActivity.this, UppercaseAlfabet.class));
        });

        CardView kuis = findViewById(R.id.kuis);
        kuis.setOnClickListener(view -> {
            startActivity(new Intent(PermainanActivity.this, KuisActivity.class));
        });

        CardView speech = findViewById(R.id.speechAlphabet);
        speech.setOnClickListener(view -> {
            startActivity(new Intent(PermainanActivity.this, AlphabetSpeechActivity.class));
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
