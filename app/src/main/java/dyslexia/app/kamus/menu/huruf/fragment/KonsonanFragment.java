package dyslexia.app.kamus.menu.huruf.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dyslexia.app.R;
import dyslexia.app.database.AppDatabase2;
import dyslexia.app.database.entities.WordEntity;
import dyslexia.app.kamus.adapter.ImageAdapter;
//import dyslexia.app.kamus.database.DatabaseDictionary;
//import dyslexia.app.kamus.model.Dictionary;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class KonsonanFragment extends Fragment {

    ListView listView;
    TextView textView;
    ArrayList<WordEntity> arrayList = new ArrayList<>();
    ImageAdapter adapter;
    AppDatabase2 appDatabase;

    public static KonsonanFragment newInstance() {
        return new KonsonanFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_huruf, null);
        initializeViews(rootView);
        loadData();
        return rootView;
    }

    private void initializeViews(View rootView) {
        listView = rootView.findViewById(R.id.list_view);
        textView = rootView.findViewById(R.id.title_name);
        textView.setText(toString());
    }

    private void loadData() {
        //DatabaseDictionary databaseDictionary = new DatabaseDictionary(getContext());
        //ArrayList<Dictionary> imageList = (ArrayList<Dictionary>) databaseDictionary.retrieveKamus("Konsonan_h");
        ArrayList<WordEntity> imageList = (ArrayList<WordEntity>) appDatabase.wordDao().getByType("Konsonan_h");

        for (WordEntity kamus: imageList){
            arrayList.add(new WordEntity(kamus.id_word, kamus.word, kamus.type));
        }
        adapter = new ImageAdapter(getContext(), arrayList);
        listView.setAdapter(adapter);

    }

    @Override
    public String toString() {
        return "Huruf Konsonan";
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {
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


