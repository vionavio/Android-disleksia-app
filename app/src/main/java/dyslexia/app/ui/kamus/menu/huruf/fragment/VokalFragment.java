package dyslexia.app.ui.kamus.menu.huruf.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dyslexia.app.R;
import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.entities.WordEntity;
import dyslexia.app.ui.kamus.adapter.ImageAdapter;
//import dyslexia.app.ui.kamus.database.DatabaseDictionary;
//import dyslexia.app.ui.kamus.model.Dictionary;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VokalFragment extends Fragment {

    ListView listView;
    TextView textView;
    ArrayList<WordEntity> arrayList = new ArrayList<>();
    ImageAdapter adapter;
    TextToSpeech textToSpeech;
    AppDatabase appDatabase;

    public static VokalFragment newInstance() {
        return new VokalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_huruf,null);
        initializeViews(rootView);
        loadData();
        return rootView;
    }

    private void initializeViews(View rootView) {
        listView=  rootView.findViewById(R.id.list_view);
        textView = rootView.findViewById(R.id.title_name);
        textView.setText(toString());
    }

    private void loadData() {
        //DatabaseDictionary databaseDictionary = new DatabaseDictionary(getContext());
        //ArrayList<Dictionary> imageList = (ArrayList<Dictionary>) databaseDictionary.retrieveKamus("Vokal");
        appDatabase = AppDatabase.getInstance(getContext());
        ArrayList<WordEntity> imageList = (ArrayList<WordEntity>) appDatabase.wordDao().getByType("Vokal");

        for (WordEntity kamus: imageList){
            arrayList.add(new WordEntity(kamus.id_word, kamus.word, kamus.type));
        }
        Log.d("lllllll", "loadData: "+ arrayList);
        adapter = new ImageAdapter(getContext(), arrayList);
        listView.setAdapter(adapter);

        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            List<WordEntity> list = new ArrayList<>();
            //final Dictionary currentKamus = list.get(position);

            WordEntity selectedFromList = (WordEntity) listView.getItemAtPosition(position);
            Log.d("mmmmmm", "loadData: "+selectedFromList.word);
            Toast.makeText(getActivity(), " " + selectedFromList.word, Toast.LENGTH_LONG).show();
            textToSpeech.speak(String.valueOf(selectedFromList.word), TextToSpeech.QUEUE_FLUSH, null);
        });

    }

    @Override
    public String toString() {
        return "Huruf Vokal";
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
