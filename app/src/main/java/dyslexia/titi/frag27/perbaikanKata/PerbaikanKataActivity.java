package dyslexia.titi.frag27.perbaikanKata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import dyslexia.titi.frag27.R;

import dyslexia.titi.frag27.R;

public class PerbaikanKataActivity extends AppCompatActivity {

    TextToSpeech t1;
    EditText ed_kataAwal;
    Button btn_proseskata;
    TextView tv_hasil;
    ImageView iv_suara;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbaikan_kata);

        ed_kataAwal = findViewById(R.id.ed_kataAwal);
        btn_proseskata = findViewById(R.id.btnProsesKata);
        tv_hasil = findViewById(R.id.tv_hasil);
        iv_suara = findViewById(R.id.iv_suara);
        jaroWinklerDistance();
        loadSuara();
    }

    private void jaroWinklerDistance() {

    }

    private void loadSuara() {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    t1.setLanguage(new Locale("id","ID"));
                }
            }
        });

        iv_suara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String toSpeak = tv_hasil.getText().toString();
                Toast.makeText(getApplicationContext(),toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak , TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }
}
