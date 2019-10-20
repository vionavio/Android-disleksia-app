package dyslexia.titi.frag27.kamus.menu.benda.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.adapter.WordImageAdapter;
import dyslexia.titi.frag27.kamus.database.DatabaseDictionary;
import dyslexia.titi.frag27.kamus.model.Dictionary;

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


public class SekolahFragment extends Fragment {


    ListView listView;
    TextView textView;
    ArrayList<Dictionary> arrayList = new ArrayList<>();
    WordImageAdapter adapter;


    public static SekolahFragment newInstance() {
        return new SekolahFragment();
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
        DatabaseDictionary databaseDictionary = new DatabaseDictionary(getContext());
        ArrayList<Dictionary> imageList = (ArrayList<Dictionary>) databaseDictionary.retrieveKamus("Nomina_se");

        for (Dictionary kamus: imageList){
            arrayList.add(new Dictionary(kamus.id_word, kamus.word, kamus.type));
        }
        adapter = new WordImageAdapter(getContext(), arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public String toString() {
        return "Sekolah";
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
    public void onDetach() {
        super.onDetach();
    }



}
