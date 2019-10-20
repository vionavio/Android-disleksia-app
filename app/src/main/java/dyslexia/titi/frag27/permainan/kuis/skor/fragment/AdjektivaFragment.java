package dyslexia.titi.frag27.permainan.kuis.skor.fragment;


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

import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.kamus.database.DatabaseDictionary;
import dyslexia.titi.frag27.kamus.model.Dictionary;
import dyslexia.titi.frag27.permainan.kuis.adapter.ScoreAdapter;
import dyslexia.titi.frag27.services.ScoreService;
import dyslexia.titi.frag27.utils.Constant;


public class AdjektivaFragment extends Fragment  {

    ListView listView;
    TextView textView;
    List<ScoreEntity> scoreEntityList;
    ScoreAdapter adapter;

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
        View rootView = inflater.inflate(R.layout.fragment_score, null);
        initializeViews(rootView);
        loadData();
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initializeViews(View rootView) {
        listView =rootView.findViewById(R.id.list);
        textView = rootView.findViewById(R.id.title_name);
        textView.setText(toString());
    }

    private void loadData() {
        scoreEntityList = ScoreService.getCurrentScoreSifat(getContext());
        Log.d(Constant.TAG, "loadData: " + scoreEntityList);
        adapter = new ScoreAdapter(getContext(),0,scoreEntityList);
        listView.setAdapter(adapter);
    }


    @Override
    public String toString() {
        return "Kata Sifat";
    }


}
