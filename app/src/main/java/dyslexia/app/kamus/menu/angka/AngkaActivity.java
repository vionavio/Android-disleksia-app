package dyslexia.app.kamus.menu.angka;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.app.R;
import dyslexia.app.database.AppDatabase;
import dyslexia.app.database.entities.WordEntity;
import dyslexia.app.kamus.adapter.WordImageAdapter;
//import dyslexia.app.kamus.database.DatabaseDictionary;
//import dyslexia.app.kamus.model.Dictionary;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class AngkaActivity extends AppCompatActivity  {


    ArrayList<WordEntity> arrayList = new ArrayList<>();
    ListView listView;
    TextView textView;
    WordImageAdapter adapter;
    TextToSpeech textToSpeech;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_huruf);
        initView();
        loadData();
    }

    private void initView() {
        listView= findViewById(R.id.list_view);
        textView = findViewById(R.id.title_name);
        textView.setText(toString());
    }

    private void loadData() {
        //DatabaseDictionary databaseDictionary = new DatabaseDictionary(getApplicationContext());
        appDatabase = AppDatabase.getInstance(this);
        ArrayList<WordEntity> imageList =
                (ArrayList<WordEntity>) appDatabase.wordDao().getByType("Numeral");
                    //    databaseDictionary.retrieveKamus("Numeral");

        for (WordEntity kamus : imageList) {
            arrayList.add(new WordEntity(kamus.id_word, kamus.word, kamus.type));
        }
        adapter = new WordImageAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String word) {

                if(TextUtils.isEmpty(word))
                {
                    listView.clearTextFilter();
                    adapter.filter("");
                }else {
                    adapter.filter(word);
                }
                return true;
            }
        });
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public String toString() {
        return "Angka";
    }




    @Override
    protected void onDestroy() {


        //Close the Text to Speech Library
        if(textToSpeech != null) {

            textToSpeech.stop();
            textToSpeech.shutdown();
            Log.d("tttttttttt", "TTS Destroyed");
        }
        super.onDestroy();
    }
}
