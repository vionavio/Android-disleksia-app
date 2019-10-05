package dyslexia.titi.frag27.kamus.fragment;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseDictionary;
import dyslexia.titi.frag27.kamus.model.Dictionary;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlphabetFragment extends Fragment {

    String alphabet[] = {
            "a", "b", "c", "d", "e", "f", "g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
    };

    // daftar gambar
    int list_gambar[] = {
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
            R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
            R.drawable.m, R.drawable.n,
            R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r,
            R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v,
            R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z


    };
    AdapterAlphabet adapter;
    ListView lv;
    TextView tv;
    TextToSpeech t1;

    public static AlphabetFragment newInstance()
    {
        return new AlphabetFragment();
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
        adapter = new AdapterAlphabet(getContext(), alphabet, list_gambar);
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
                String adapterAlphabet = alphabet[position];
                Toast.makeText(AlphabetFragment.this.getActivity(), " " + adapterAlphabet, Toast.LENGTH_LONG).show();
                t1.speak(String.valueOf(adapterAlphabet), TextToSpeech.QUEUE_FLUSH, null);
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
        return "Simbol Huruf";
    }


    // class di dalam class
    private class AdapterAlphabet extends ArrayAdapter {
        String alphabet[];
        int list_gambar[];

        public AdapterAlphabet(Context context, String[] alphabet, int[] list_gambar) {
            super(context, R.layout.text_view_kamus2, alphabet);
            this.list_gambar = list_gambar;
            this.alphabet = alphabet;
        }
        //menthode yang digunakan untuk memanggil layout list_buah dan mengenalkan widgetnya
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // panggil layout list
            LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
            View v = inflater.inflate(R.layout.text_view_kamus2, null);
            // kenalkan widget yang ada pada list buah
            ImageView gambar;
            TextView nama;

            //casting widget id
            gambar =  v.findViewById(R.id.IvGambar);
            nama =  v.findViewById(R.id.TxtNama);

            // set data kedalam image
            gambar.setImageResource(list_gambar[position]);
            nama.setText(alphabet[position]);

            return v;
        }
    }

    public void onPauseID() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
