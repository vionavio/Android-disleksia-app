package dyslexia.titi.frag27.kamus.fragment;


import android.content.Context;
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
import android.widget.ImageView;
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

    String number[] = {
            "satu", "dua","tiga","empat","lima","enam","tujuh","delapan","sembilan","nol"
    };

    int pic_number[]= {
            R.drawable.a_satu,
            R.drawable.a_dua,
            R.drawable.a_tiga,
            R.drawable.a_empat,
            R.drawable.a_lima,
            R.drawable.a_enam,
            R.drawable.a_tujuh,
            R.drawable.a_delapan,
            R.drawable.a_sembilan,R.drawable.a_nol

    };

    AdapterNumbers adapter;

    ListView lv;
    TextView tv;
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
        adapter = new AdapterNumbers(getContext(), number, pic_number);
        lv.setAdapter(adapter);

        t1 = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(new Locale("id", "ID"));
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = number[position];
                Toast.makeText(NumeralFragment.this.getActivity(), " " + selectedFromList, Toast.LENGTH_LONG).show();
                t1.speak(String.valueOf(selectedFromList), TextToSpeech.QUEUE_FLUSH, null);
            }
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

    private class AdapterNumbers extends ArrayAdapter {

        String number2[];
        int pic_number2[];

        public AdapterNumbers(Context context, String[] number, int[] pic_number) {
            super(context, R.layout.text_view_kamus2, number);
            this.number2 = number;
            this.pic_number2 = pic_number;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // panggil layout list
            LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
            View view = inflater.inflate(R.layout.text_view_kamus2, null);
            // kenalkan widget yang ada pada list buah
            ImageView pic_number;
            TextView number;

            //casting widget id
            pic_number = view.findViewById(R.id.IvGambar);
            number = view.findViewById(R.id.TxtNama);

            // set data kedalam image
            pic_number.setImageResource(pic_number2[position]);
            return view;
        }

    }

}
