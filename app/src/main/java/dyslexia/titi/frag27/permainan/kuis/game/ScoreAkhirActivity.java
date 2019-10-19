package dyslexia.titi.frag27.permainan.kuis.game;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.database.AppDatabase;
import dyslexia.titi.frag27.permainan.kuis.KuisActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class ScoreAkhirActivity extends AppCompatActivity {

    TextView tvScore;
    RatingBar ratingBar;
    int lastScore;
   // double best1, best2, best3;
    AppDatabase appDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_akhir);
        tvScore = findViewById(R.id.tvScore);
        ratingBar = findViewById(R.id.rating);

        appDatabase = AppDatabase.getInstance(this);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        lastScore = preferences.getInt("lastScore", 0);

//        best1 = preferences.getInt("best1", 0);
//        best2 = preferences.getInt("best2", 0);
//        best3 = preferences.getInt("best3", 0);
//
//        if (lastScore > best3){
//            best3 = lastScore;
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putInt("best3", best3);
//            editor.apply();
//        }
//        if (lastScore > best2){
//            int temp = best2;
//            best2 = lastScore;
//            best3 = temp;
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putInt("best3", best3);
//            editor.putInt("best2", best2);
//            editor.apply();
//        }
//        if (lastScore > best1){
//            int temp = best1;
//            best1 = lastScore;
//            best2 = temp;
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putInt("best2", best2);
//            editor.putInt("best1", best1);
//            editor.apply();
//        }

        // TODO: implementasi pengambilan nilai dari Room

//        List<ScoreEntity> scoreEntityList = appDatabase.scoreDao().getBestScores();
//        for (ScoreEntity scoreEntity: scoreEntityList) {
//            //
//        }

//        tvScore.setText("NILAI ANDA: " + lastScore + "\n" +
//                "Nilai Terbaik 1 : " + best1 + "\n" +
//                "Nilai Terbaik 2  : " + best2 + "\n" +
//                "Nilai Terbaik 3  : " + best3 );
//        tvScore.setTextSize(28);

        tvScore.setText("SKOR ANDA: " + lastScore);
        tvScore.setTextSize(28);

        if(lastScore == 0) {ratingBar.setRating(0);}

        else {ratingBar.setRating((float) ((float) lastScore / 2.0));}
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), KuisActivity.class);
        startActivity(intent);
        finish();
    }
}
