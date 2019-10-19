package dyslexia.titi.frag27.permainan.kuis.skor;


import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.permainan.kuis.adapter.ScoreAdapter;
import dyslexia.titi.frag27.services.ScoreService;
import dyslexia.titi.frag27.utils.Constant;

public class Skor2Activity extends AppCompatActivity {

    ListView listView;
    List<ScoreEntity> scoreEntityList;
    ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);

        initView();
        loadData();
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
    }

    private void loadData() {
        scoreEntityList = ScoreService.getCurrentScore(this);
        Log.d(Constant.TAG, "loadData: " + scoreEntityList);
        adapter = new ScoreAdapter(this,0,scoreEntityList);
        listView.setAdapter(adapter);

//        DatabaseDictionary databaseDictionary = new DatabaseDictionary(getApplicationContext());
//        ArrayList<Dictionary> imageList =
//                (ArrayList<Dictionary>) databaseDictionary.retrieveKamus("Adjektiva");
//
//        for (Dictionary kamus : imageList) {
//            arrayList.add(new Dictionary(kamus.id_word, kamus.word, kamus.type));
//        }
//        adapter = new WordAdapter(this, arrayList);
//        listView.setAdapter(adapter);


    }


}
