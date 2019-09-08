package dyslexia.titi.frag27.kamus.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.adapter.MyPageAdapter;
import dyslexia.titi.frag27.kamus.crud.DetailKamusActivity;
import dyslexia.titi.frag27.kamus.crud.EditKamusActivity;
import dyslexia.titi.frag27.kamus.crud.TambahKamusActivity;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdjektivaFragment extends Fragment  {

    ListView lv;
    TextView tv;
    ArrayAdapter<Kamus> adapter;
    List<Kamus> kamuses = new ArrayList<>();
    TextToSpeech t1;

    MyPageAdapter pagerAdapter;
    private Button tambahButton;
    private Button editButton;
    private Button detailButton;
    private Button delButton;

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
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        adapter = new ArrayAdapter<Kamus>(getActivity(), R.layout.text_view_kamus, db.retrieveKamus("adjektiva"));
        lv.setAdapter(adapter);

        t1 = new TextToSpeech(getActivity().getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                t1.setLanguage(new Locale("id", "ID"));
            }
        });

        lv.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Kamus selectedFromList = (Kamus) lv.getItemAtPosition(position);
            Toast.makeText(getActivity(), " " + selectedFromList, Toast.LENGTH_LONG).show();
            t1.speak(String.valueOf(selectedFromList), TextToSpeech.QUEUE_FLUSH, null);
        });

        lv.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_view_kata);
            dialog.show();

            editButton = dialog.findViewById(R.id.button_edit_data);
            delButton = dialog.findViewById(R.id.button_delete_data);

            Kamus selectedFromList = (Kamus) lv.getItemAtPosition(position);



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
                        DatabaseAdapter databaseAdapter = new DatabaseAdapter(getActivity());
                        databaseAdapter.deleteKamus(selectedFromList.getId_word());
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

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    //method untuk get single data
    private void switchToEdit(long id_word) {

        DatabaseAdapter databaseAdapter =new DatabaseAdapter(getActivity());
        Kamus selectedFromList = (Kamus) databaseAdapter.getKamus(id_word);
        Intent intent = new Intent(getActivity(), EditKamusActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id_word", selectedFromList.getId_word());
        bundle.putString("word", selectedFromList.getWord());
        bundle.putString("type",selectedFromList.getType());
        intent.putExtras(bundle);
        databaseAdapter.close();
        startActivity(intent);
    }









}
