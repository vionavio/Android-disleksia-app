package dyslexia.titi.frag27.kamus.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.crud.EditKamusActivity;
import dyslexia.titi.frag27.kamus.database.DatabaseDictionary;
import dyslexia.titi.frag27.kamus.model.Dictionary;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    ListView lv;
    TextView tv;
    ArrayAdapter<Dictionary> adapter;
    List<Dictionary> dictionaries = new ArrayList<>();
    TextToSpeech t1;

    private Button editButton;
    private Button delButton;

    public static Fragment newInstance() {
        return new AllFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, null);
        initializeViews(rootView);
        loadData();
        return rootView;
    }

    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        DatabaseDictionary db = new DatabaseDictionary(getActivity());
        adapter = new ArrayAdapter<Dictionary>(getActivity(), R.layout.text_view_kamus, db.retrieveKamus("all"));

        lv.setAdapter(adapter);

        t1 = new TextToSpeech(getActivity().getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                t1.setLanguage(new Locale("id", "ID"));
            }
        });

        lv.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Dictionary selectedFromList = (Dictionary) lv.getItemAtPosition(position);
            Toast.makeText(getActivity(), " " + selectedFromList, Toast.LENGTH_LONG).show();
            t1.speak(String.valueOf(selectedFromList), TextToSpeech.QUEUE_FLUSH, null);
        });

        lv.setOnItemLongClickListener((adapterView, view, position, id) -> {
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_view_kata);
                    dialog.show();

                    editButton = dialog.findViewById(R.id.button_edit_data);
                    delButton = dialog.findViewById(R.id.button_delete_data);

                    Dictionary selectedFromList = (Dictionary) lv.getItemAtPosition(position);

                    //apabila tombol edit diklik
                    editButton.setOnClickListener(
                            v -> {
                                //  Auto-generated method stub
                                switchToEdit(selectedFromList.getId_word());
                                dialog.dismiss();
                            }
                    );

                    delButton.setOnClickListener(
                            v -> {
                                // Delete kata dari db
                                DatabaseDictionary databaseDictionary = new DatabaseDictionary(getActivity());
                                databaseDictionary.deleteKamus(selectedFromList.getId_word());
                                getActivity().finish();
                            }
                    );
                    return true;
                }
        );
    }

    private void initializeViews(View rootView) {
        lv = rootView.findViewById(R.id.list);
        tv = rootView.findViewById(R.id.title_name);
        tv.setText(toString());
    }

    @Override
    public String toString() {
        return "Kamus Kata";
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

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    //method untuk get single data
    private void switchToEdit(long id_word) {

        DatabaseDictionary databaseDictionary =new DatabaseDictionary(getActivity());
        Dictionary selectedFromList = (Dictionary) databaseDictionary.getKamus(id_word);
        Intent intent = new Intent(getActivity(), EditKamusActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id_word", selectedFromList.getId_word());
        bundle.putString("word", selectedFromList.getWord());
        bundle.putString("type",selectedFromList.getType());
        intent.putExtras(bundle);
        databaseDictionary.close();
        startActivity(intent);
    }


}
