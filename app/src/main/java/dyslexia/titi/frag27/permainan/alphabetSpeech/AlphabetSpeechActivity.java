package dyslexia.titi.frag27.permainan.alphabetSpeech;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseDictionary;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class AlphabetSpeechActivity extends AppCompatActivity implements View.OnClickListener  {

    ArrayList<AlphabetSpeech> list;
    DatabaseDictionary databaseDictionary;

    ImageButton microphoneButton;
    ImageButton nextButton;
    ImageButton playButton;
    TextView letterTextView;
    TextView answerletterTextView;
    private final int SPEECH_RECOGNITION_CODE = 1;

    int position;
    boolean isAnswer = false;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_speech);

        microphoneButton = findViewById(R.id.microphone_image_speech_page);
        nextButton = findViewById(R.id.next_image_button);
        playButton = findViewById(R.id.play_button_speech_page);
        letterTextView = findViewById(R.id.letter_for_speech_text_view);
        answerletterTextView = findViewById(R.id.letter_of_speech_text_view);

        Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/AlteHaasGroteskRegular.ttf");
        answerletterTextView.setTypeface(customfont);

        microphoneButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        playButton.setOnClickListener(this);

        createTTS();
        databaseDictionary = new DatabaseDictionary(getApplicationContext());
        list = (ArrayList<AlphabetSpeech>) databaseDictionary.retrieveSpeech();
        getRandomPosition();
    }

    @SuppressLint("SetTextI18n")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_RECOGNITION_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String text = result.get(0);
                Log.d("EEE", "true " + text);
                String letter = text.substring(0, 1);
                if (letter.equals("à")) {
                    letter = "a";
                    text = "a";
                }
                isAnswer = true;
                if (letter.equals(list.get(position).getLetter().substring(0, 1)) ||
                        letter.equals(list.get(position).getLetter().substring(1, 2))) {
                    answerletterTextView.setText("Benar, Huruf diatas diucapkan" + list.get(position).getLetter().substring(0, 1));
                    Log.d("EEE", "true");
                } else answerletterTextView.setText("Anda salah menyebutkan " + text);

            }
        }
    }



    private void getRandomPosition() {
        Random random = new Random();
        position = random.nextInt(26);
        createTask(position);
    }

    private void createTask(int position) {
        letterTextView.setText(list.get(position).getLetter());
        answerletterTextView.setText("");
    }

    private void createTTS() {

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(new Locale("id", "ID"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.d("EEE", "Bahasa ini tidak didukung");
                }
            } else {
                Log.e("EEE", "Inisialisasi gagal!");
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.microphone_image_speech_page:{
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Ucapkan huruf tersebut...");
                try {
                    startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Maaf! Speech recognition tidak mendukung perangkat anda.",
                            Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.next_image_button:{
                if(isAnswer){
                    isAnswer = false;
                    getRandomPosition();
                }
                break;
            }

            case R.id.play_button_speech_page:{
                tts.speak(list.get(position).getTranscription(), TextToSpeech.QUEUE_FLUSH, null, null);
                break;
            }

        }



    }

    @Override
    protected void onStop() {
        if(tts != null){
            tts.stop();
             tts.shutdown();
        }
        super.onStop();

    }
}
