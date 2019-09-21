package dyslexia.titi.frag27.kamus.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseDictionary;
import dyslexia.titi.frag27.kamus.model.Dictionary;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumeralFragment extends Fragment {

    ListView lv;
    TextView tv;
    ArrayAdapter<Dictionary> adapter;
    TextToSpeech t1;

    public static NumeralFragment newInstance()
    {
        return new NumeralFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment,null);
        initializeViews(rootView);
        loadData();
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        DatabaseDictionary db=new DatabaseDictionary(getActivity());
        adapter = new ArrayAdapter<Dictionary>(getActivity(), R.layout.text_view_kamus, db.retrieveKamus("numeralia"));
        lv.setAdapter(adapter);
        t1 = new TextToSpeech(getActivity().getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                t1.setLanguage(new Locale("id", "ID"));
            }
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Dictionary selectedFromList = (Dictionary) lv.getItemAtPosition(position);
            Toast.makeText(getActivity(), " " + selectedFromList, Toast.LENGTH_LONG).show();
            t1.speak(String.valueOf(selectedFromList), TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void initializeViews(View rootView) {
        lv=  rootView.findViewById(R.id.list);
        tv = rootView.findViewById(R.id.title_name);
        tv.setText(toString());
    }
    @Override
    public String toString() {
        return "Simbol Angka";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        loadData();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String word) {

                adapter.getFilter().filter(word);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onPauseID() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
