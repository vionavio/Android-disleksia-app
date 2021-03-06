package dyslexia.app.ui.permainan.kuis.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dyslexia.app.R;
import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.entities.ScoreEntity;
import dyslexia.app.ui.permainan.kuis.WordShuffler;
import dyslexia.app.services.AccountService;
import dyslexia.app.utils.StringUtil;

import static dyslexia.app.utils.Constant.GAME_ANGKA;

public class GameSimbolAngkaActivity extends AppCompatActivity {

    public static final long COUNTDOWN_IN_MILLIS = 30000; // timer countdown counter

    private String[] names = new String[]{"nol", "satu", "dua", "tiga", "empat", "lima", "enam",
            "tujuh", "delapan", "sembilan"};

    private String word;
    private String answer;
    private Boolean answered;
    private String scrambled, normal;
    private ImageView pic;
    private TextView tvScore, tvQuestionCount, tvCountdown;
    private Button btn_check;
    private CountDownTimer countDownTimer;
    private RecyclerView recyclerViewLetter;
    private EditText editTextAnswer;
    private int previousChoice;
    private long timeLeftInMillis;
    private int imageResource;

    int score = 0;
    int question = 0;
    int chances = 10;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_simbol_angka);

        TextView normi = findViewById(R.id.normalletters);
        editTextAnswer = findViewById(R.id.answer);
        editTextAnswer.setInputType(InputType.TYPE_NULL);


        btn_check = findViewById(R.id.check);
        tvScore = findViewById(R.id.tvScore);
        tvQuestionCount = findViewById(R.id.question_count);
        tvCountdown = findViewById(R.id.tvCountdown);
        pic = findViewById(R.id.imageView1);
        recyclerViewLetter = findViewById(R.id.rv_letters);

        tvScore.setText("SCORE: " + score);
        tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);

        if (savedInstanceState != null) {
            word = savedInstanceState.getString("word");
            answer = savedInstanceState.getString("answer");
            previousChoice = savedInstanceState.getInt("previousChoice");
            normal = savedInstanceState.getString("normal");

            scrambled = savedInstanceState.getString("scrambled");
            imageResource = savedInstanceState.getInt("image");
            answered = savedInstanceState.getBoolean("answered");
            timeLeftInMillis = savedInstanceState.getLong("millisLeft");

            //restore text

            Drawable res = getResources().getDrawable(imageResource);
            pic.setImageDrawable(res);
            TextView norm = findViewById(R.id.normalletters);
            norm.setText(normal);

//            Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/AlteHaasGroteskRegular.ttf");
//            scram.setTypeface(customfont);

        } else {

            setImage();
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });

        pic.setOnClickListener(view -> {
            String toSpeak = normi.getText().toString().trim();
            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });

        normi.setOnClickListener(view -> {
            String toSpeak = normi.getText().toString().trim();
            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });

        btn_check.setOnClickListener(view -> {
            answered = true;
            if (answered) {
                checkAnswer();
            } else {
                setImage();
            }
//            EditText input = findViewById(R.id.answer);
//            answer = input.getText().toString().toLowerCase().trim();
//            if (question < chances) {
//                if (answer.equals(word)) {
//                    question++;
//                    tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);
//                    score++;
//                    tvScore.setText("SCORE: " + score);
//                    setImage();
//                    Toast.makeText(GameSimbolAngkaActivity.this, "Benar", Toast.LENGTH_SHORT).show();
//
//                } else if (!(answer.equals(word))) {
//
//                    question++;
//                    tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);
//                    setImage();
//                    Toast.makeText(GameSimbolAngkaActivity.this, "Salah", Toast.LENGTH_SHORT).show();
//
//                }
//            } else {
//                finishQuiz();
//            }
        });
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();

            }
        }.start();

    }

    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel(); // when we pick up the answer we want to stop the timer

        EditText input = findViewById(R.id.answer);
        answer = input.getText().toString().toLowerCase();


        if (answer.equals(word)) {
            question++;
            tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);
            score++;
            tvScore.setText("SCORE: " + score);
            Toast.makeText(GameSimbolAngkaActivity.this, "Benar", Toast.LENGTH_SHORT).show();
        } else if (!(answer.equals(word))) {
            question++;
            tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);
            Toast.makeText(GameSimbolAngkaActivity.this, "Salah", Toast.LENGTH_SHORT).show();
        }
        setImage();

    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // to get what is left after / by 60

        String timeFormatted = String.format(Locale.getDefault(), "% 02d:%02d", minutes, seconds);
        tvCountdown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            tvCountdown.setTextColor(Color.RED);
        } else {
            tvCountdown.setTextColor(Color.GREEN);
        }
    }

    private void finishQuiz() {
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastScore", score);
        editor.apply();
        saveScore(score);

        Intent intent = new Intent(getApplicationContext(), ScoreAkhirActivity.class);
        startActivity(intent);
        finish();
    }

    protected void setImage() {
        EditText input = findViewById(R.id.answer);
//        Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/AlteHaasGroteskRegular.ttf");
//        input.setTypeface(customfont);
        answer = input.getText().toString().toLowerCase().trim();
        if (question < chances) {
            WordShuffler shuffler = new WordShuffler();
            Random rand = new Random();
            //use this code block to make sure don't show the same picture back to back
            int whichpic = previousChoice;
            while (whichpic == previousChoice) {
                whichpic = rand.nextInt(10);
            }
            previousChoice = whichpic;

            //here we get a little creative randomly choosing an image
            String image = "@drawable/" + names[whichpic];
            imageResource = getResources().getIdentifier(image, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            pic.setImageDrawable(res);

            TextView normi = findViewById(R.id.normalletters);
            word = names[whichpic];
            normal = word;
            normi.setText(normal);

            //set the new word value and scramble up the new letters! reset the views
            word = names[whichpic];
            scrambled = shuffler.shuffle(word);

            recyclerViewLetter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            GameLetterAdapter gameLetterAdapter = new GameLetterAdapter(StringUtil.stringToArrayList(scrambled), clickedLetter -> {
                Toast.makeText(this, clickedLetter.toString(), Toast.LENGTH_SHORT).show();
                editTextAnswer.append(clickedLetter.toString());
            });
            recyclerViewLetter.setAdapter(gameLetterAdapter);


            //scram.setTypeface(customfont);
            EditText answer5 = findViewById(R.id.answer);
            answer5.setText("");
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("word", word);
        savedInstanceState.putString("answer", answer);
        savedInstanceState.putString("normal", normal);
        savedInstanceState.putInt("previousChoice", previousChoice);
        savedInstanceState.putStringArray("names", names);
        savedInstanceState.putString("scrambled", scrambled);
        savedInstanceState.putString("normal", normal);
        savedInstanceState.putInt("image", imageResource);
        savedInstanceState.putBoolean("answered", answered);
        savedInstanceState.putLong("millisLeft", timeLeftInMillis);
    }

    public void saveScore(Integer score) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss' 'dd-MM-yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());

        AppDatabase appDatabase = AppDatabase.getInstance(this);
        appDatabase.scoreDao().insert(new ScoreEntity(
                AccountService.getUserId(this),
                GAME_ANGKA,
                score,
                currentDateandTime
        ));

        Log.d("aaaaaaaaa", "saveScore: " + appDatabase.scoreDao().getAll());
    }

    public void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
