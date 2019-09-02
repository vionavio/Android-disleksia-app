package dyslexia.titi.frag27.perbaikanKata;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;
import dyslexia.titi.frag27.kamus.model.KamusSimilar;

public class PerbaikanKataActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText ed_kataAwal;
    Button btn_proseskata;
    ListView listViewSimiliarWords;
    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbaikan_kata);

        ed_kataAwal = findViewById(R.id.ed_kataAwal);
        btn_proseskata = findViewById(R.id.btnProsesKata);
        listViewSimiliarWords = findViewById(R.id.lv_similar_words);
        btn_proseskata.setOnClickListener(view -> jaroWinklerDistance());
        loadSuara();
    }

    private void jaroWinklerDistance() {
        List<Kamus> kamusList;
        List<KamusSimilar> kamusSimilarList = new ArrayList<>();
        List<KamusSimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();

        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        kamusList = databaseAdapter.retrieveKamus("all");

        for (Kamus kamus : kamusList) {
            kamusSimilarList.add(new KamusSimilar(kamus.getId_word(), kamus.getWord(), kamus.getType(), getSimilarScore(kamus.getWord())));
        }
        for (KamusSimilar kamusSimilar : kamusSimilarList) {
            if (kamusSimilar.getSimilarScore() > 0.7) {
                kamusSimilarFilteredList.add(kamusSimilar);
            }
        }
        Collections.sort(kamusSimilarFilteredList, (kamusSimilar, kamusSimilar2) -> Double.compare(kamusSimilar2.getSimilarScore(), kamusSimilar.getSimilarScore()));
        Log.d("aaaaaa", "jaroWinklerDistance: " + kamusSimilarFilteredList.toString());
        for (KamusSimilar kamusSimilar : kamusSimilarFilteredList) {
            finalWords.add(kamusSimilar.getWord());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, finalWords);
        listViewSimiliarWords.setAdapter(arrayAdapter);
        inputMethodManager();
    }

    private void inputMethodManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private double getSimilarScore(String word) {
        String inputWord = ed_kataAwal.getText().toString();
        return new JaroWinkler().getSimilarity(word, inputWord);
    }

    private void loadSuara() {
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });

//        iv_suara.setOnClickListener(view -> {
//            String toSpeak = tv_hasil.getText().toString();
//            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//        });
    }
}
