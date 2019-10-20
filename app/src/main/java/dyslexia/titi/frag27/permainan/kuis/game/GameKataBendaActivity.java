package dyslexia.titi.frag27.permainan.kuis.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.database.AppDatabase;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.permainan.kuis.WordShuffler;
import dyslexia.titi.frag27.services.AccountService;
import dyslexia.titi.frag27.utils.Constant;

import static dyslexia.titi.frag27.utils.Constant.GAME_BENDA;

public class GameKataBendaActivity extends AppCompatActivity {

    public static final long COUNTDOWN_IN_MILLIS = 30000; // timer countdown counter


    private String[] names = new String[]{"sepatu", "tas", "buku", "pensil", "baju",
            "celana", "meja", "kursi", "bola", "mobil", "sepeda",
            "pesawat", "apel", "topi", "rumah", "pintu",
            "jendela", "burung", "televisi", "jam"};

    private String word;
    private String answer;
    private Boolean answered;
    private String scrambled;
    private ImageView pic;
    private TextView tvScore, tvQuestionCount, tvCountdown;
    private Button btn_check;
    private CountDownTimer countDownTimer;
    private int previousChoice;
    private long timeLeftInMillis;
    private int imageResource;
    int score = 0;
    int question = 0;
    int chances = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_kata_benda);

        btn_check = findViewById(R.id.check);
        tvScore = findViewById(R.id.tvScore);
        tvQuestionCount = findViewById(R.id.question_count);
        tvCountdown = findViewById(R.id.tvCountdown);
        pic = findViewById(R.id.imageView1);

        tvScore.setText("SCORE: " + score);
        tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);

        if (savedInstanceState != null) {
            word = savedInstanceState.getString("word");
            answer = savedInstanceState.getString("answer");
            previousChoice = savedInstanceState.getInt("previousChoice");
            scrambled = savedInstanceState.getString("scrambled");
            imageResource = savedInstanceState.getInt("image");
            answered = savedInstanceState.getBoolean("answered");
            timeLeftInMillis = savedInstanceState.getLong("millisLeft");

            //restore text

            Drawable resources = getResources().getDrawable(imageResource);
            pic.setImageDrawable(resources);
            TextView scram = findViewById(R.id.scrambledletters);
            scram.setText(scrambled);
            Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/AlteHaasGroteskRegular.ttf");
            scram.setTypeface(customfont);

        } else {

            setImage();
        }

        btn_check.setOnClickListener(view -> {
            answered = true;
            if (answered) {
                checkAnswer();
            } else {
                setImage();
            }
        });
        Log.d(Constant.TAG, "onCreate: " + AccountService.getUserId(this));
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
            Toast.makeText(GameKataBendaActivity.this, "Benar", Toast.LENGTH_SHORT).show();
        } else if (!(answer.equals(word))) {
            question++;
            tvQuestionCount.setText("Pertanyaan: " + question + "/" + 10);
            Toast.makeText(GameKataBendaActivity.this, "Salah", Toast.LENGTH_SHORT).show();
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

        startActivity(new Intent(getApplicationContext(), ScoreAkhirActivity.class));
        finish();
    }

    public void saveScore(Integer score) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss' 'dd-MM-yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());

        AppDatabase appDatabase = AppDatabase.getInstance(this);
        appDatabase.scoreDao().insert(new ScoreEntity(
                AccountService.getUserId(this),
                GAME_BENDA,
                score,
                currentDateandTime
        ));

        Log.d("aaaaaaaaa", "saveScore: " + appDatabase.scoreDao().getAll());
    }

    protected void setImage() {
        EditText input = findViewById(R.id.answer);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/AlteHaasGroteskRegular.ttf");
        input.setTypeface(customfont);
        answer = input.getText().toString().toLowerCase().trim();
        if (question < chances) {
            WordShuffler shuffler = new WordShuffler();
            Random random = new Random();
            //use this code block to make sure don't show the same picture back to back
            int whichpic = previousChoice;
            while (whichpic == previousChoice) {
                whichpic = random.nextInt(20);
            }
            previousChoice = whichpic;

            //here we get a little creative randomly choosing an image
            String image = "@drawable/" + names[whichpic];
            imageResource = getResources().getIdentifier(image, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            pic.setImageDrawable(res);

            //set the new word value and scramble up the new letters! reset the views
            word = names[whichpic];
            scrambled = shuffler.shuffle(word);
            TextView scram = findViewById(R.id.scrambledletters);
            scram.setText(scrambled);
            scram.setTypeface(customfont);
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
        savedInstanceState.putInt("previousChoice", previousChoice);
        savedInstanceState.putStringArray("names", names);
        savedInstanceState.putString("scrambled", scrambled);
        savedInstanceState.putInt("image", imageResource);
        savedInstanceState.putBoolean("answered", answered);
        savedInstanceState.putLong("millisLeft", timeLeftInMillis);

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
