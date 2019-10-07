package dyslexia.titi.frag27.permainan.kuis.skor.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import dyslexia.titi.frag27.R;


public class NumeralFragment extends Fragment {

    ListView lv;
    TextView tv;

    public static NumeralFragment newInstance() {
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
        View rootView = inflater.inflate(R.layout.fragment, null);
        initializeViews(rootView);
        return rootView;
    }
    private void initializeViews(View rootView) {
        lv = rootView.findViewById(R.id.list);
        tv = rootView.findViewById(R.id.title_name);
        tv.setText(toString());
    }

    @Override
    public String toString() {
        return "Simbol Angka";
    }


}
