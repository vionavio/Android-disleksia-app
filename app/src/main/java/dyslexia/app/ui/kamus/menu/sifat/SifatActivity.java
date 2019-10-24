package dyslexia.app.ui.kamus.menu.sifat;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.app.R;
//import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.ui.kamus.adapter.WordAdapter;
import dyslexia.app.ui.database.DatabaseDictionary;
import dyslexia.app.ui.database.model.Dictionary;

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

public class SifatActivity extends AppCompatActivity {


    ArrayList<Dictionary> arrayList = new ArrayList<>();
    ListView listView;
    TextView textView;
    WordAdapter adapter;
    TextToSpeech textToSpeech;
    DatabaseDictionary databaseDictionary;

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
        databaseDictionary = new DatabaseDictionary(getApplicationContext());
        ArrayList<Dictionary> imageList =(ArrayList<Dictionary>) databaseDictionary.retrieveKamus("Adjektiva");

        for (Dictionary kamus : imageList) {
            arrayList.add(new Dictionary(kamus.id_word, kamus.word, kamus.type));
        }
        adapter = new WordAdapter(this, arrayList);
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
        return "Kata Sifat";
    }



    @Override
    protected void onStop() {
        super.onStop();
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
