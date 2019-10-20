package dyslexia.titi.frag27.permainan.kuis.skor.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.database.entities.ScoreEntity;
import dyslexia.titi.frag27.permainan.kuis.adapter.ScoreAdapter;
import dyslexia.titi.frag27.services.ScoreService;
import dyslexia.titi.frag27.utils.Constant;


public class NumeralFragment extends Fragment {

    ListView listView;
    TextView textView;
    List<ScoreEntity> scoreEntityList;
    ScoreAdapter adapter;

    public static NumeralFragment newInstance() {
        return new NumeralFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_score, null);
        initializeViews(rootView);
        loadData();
        return rootView;
    }

    private void initializeViews(View rootView) {
        listView =rootView.findViewById(R.id.list);
        textView = rootView.findViewById(R.id.title_name);
        textView.setText(toString());
    }

    private void loadData() {
        scoreEntityList = ScoreService.getCurrentScoreAngka(getContext());
        Log.d(Constant.TAG, "loadData: " + scoreEntityList);
        adapter = new ScoreAdapter(getContext(),0,scoreEntityList);
        listView.setAdapter(adapter);
    }

    @Override
    public String toString() {
        return "Simbol Angka";
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
}
