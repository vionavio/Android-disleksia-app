package dyslexia.titi.frag27.kamus.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdverbiaFragment extends Fragment {

    ListView lv;
    TextView tv,tvt;
    ArrayAdapter<Kamus> adapter;

    public static AdverbiaFragment newInstance()
    {
        return new AdverbiaFragment();
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
        DatabaseAdapter db=new DatabaseAdapter(getActivity());

        adapter=new ArrayAdapter<Kamus>(getActivity(),android.R.layout.simple_list_item_1,db.retrieveKamus("adverbia"));
        lv.setAdapter(adapter);
    }

    private void initializeViews(View rootView) {
        lv=  rootView.findViewById(R.id.list);

        tv = rootView.findViewById(R.id.title_name);
        tv.setText(toString());
    }
    @Override
    public String toString() {
        return "Kata Keterangan";
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


}
