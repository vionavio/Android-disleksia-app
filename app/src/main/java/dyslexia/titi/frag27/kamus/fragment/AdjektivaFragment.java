package dyslexia.titi.frag27.kamus.fragment;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;

import static android.R.layout.simple_list_item_1;
import static dyslexia.titi.frag27.R.id.lv_word;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdjektivaFragment extends Fragment{

    ListView lv;
    public TextView tv;
    ArrayAdapter<Kamus> adapter;
    TextToSpeech t1,t2;

    public static AdjektivaFragment newInstance() {
        return new AdjektivaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, null);
        initializeViews(rootView);
        loadData();

//        t1 = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status != TextToSpeech.ERROR){
//                    t1.setLanguage(new Locale("id","ID"));
//                }
//            }
//        });


        return rootView;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        adapter = new ArrayAdapter<Kamus>(getActivity(), simple_list_item_1, db.retrieveKamus("adjektiva"));
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String selectedFromList = (String) lv.getItemAtPosition(position);
                String selectedFromList = (String) lv.getItemAtPosition(position);

               Toast.makeText(getActivity(),"  ListItem : " +selectedFromList , Toast.LENGTH_LONG).show();

                //Toast.makeText(getActivity(),"ListItem : ", Toast.LENGTH_LONG).show();
                //Log.v("TAG", "CLICKED row number: " + position);
            }

        });


    }

    private void initializeViews(View rootView) {
        lv = rootView.findViewById(R.id.list);
        tv = rootView.findViewById(R.id.title_name);
        tv.setText(toString());




    }

    @Override
    public String toString() {
        return "Kata Sifat";
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
    public void onPauseEn(){
        if (t1 != null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }






}
