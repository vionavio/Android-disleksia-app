package dyslexia.titi.frag27.perbaikanKata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dyslexia.titi.frag27.R;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;

public class PerbaikanKataActivity extends AppCompatActivity {

    TextToSpeech t1;
    EditText ed_kataAwal;
    Button btn_proseskata;
    TextView tv_hasil;
    ListView lv_hasil;
    //ImageView iv_suara;
    DatabaseAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbaikan_kata);

        ed_kataAwal = findViewById(R.id.ed_kataAwal);
        btn_proseskata = findViewById(R.id.btnProsesKata);
        tv_hasil = findViewById(R.id.tv_hasil);
        lv_hasil = findViewById(R.id.lv_hasil);
        //iv_suara = findViewById(R.id.iv_suara);
        btn_proseskata.setOnClickListener(view -> {
            jaroWinklerDistance();
        });
       // loadSuara();
    }

    private void jaroWinklerDistance() {

        List<Kamus> listKata;
        db = new DatabaseAdapter(getApplicationContext());
        listKata = db.retrieveKamus("all");

        String hasil = "";
        double similarityMax = 0;
        for (Kamus kamus : listKata) {
            double similarity = new JaroWinkler().getSimilarity(kamus.getWord(), ed_kataAwal.getText().toString());
            if (similarity > similarityMax && similarity > 0.7) {
                similarityMax = similarity;
                hasil = kamus.getWord();
            }
        }
        final ArrayAdapter<Kamus> adapter = new ArrayAdapter<Kamus>(this,
                android.R.layout.simple_list_item_1, R.id.tv_hasil, listKata);
        lv_hasil.setAdapter(adapter);
        tv_hasil.setText(hasil);

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

//    private void loadSuara() {
//        t1 = new TextToSpeech(getApplicationContext(), status -> {
//            if (status != TextToSpeech.ERROR) {
//                t1.setLanguage(new Locale("id", "ID"));
//            }
//        });
//
//        iv_suara.setOnClickListener(view -> {
//            String toSpeak = tv_hasil.getText().toString();
//            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
//            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//        });
//    }
}
