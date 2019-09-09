package dyslexia.titi.frag27.kamus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.adapter.MyPageAdapter;
import dyslexia.titi.frag27.kamus.fragment.AdjektivaFragment;
import dyslexia.titi.frag27.kamus.fragment.AdverbiaFragment;
import dyslexia.titi.frag27.kamus.fragment.AllFragment;
import dyslexia.titi.frag27.kamus.fragment.AlphabetFragment;
import dyslexia.titi.frag27.kamus.fragment.NominaFragment;
import dyslexia.titi.frag27.kamus.fragment.NumeralFragment;
import dyslexia.titi.frag27.kamus.fragment.VerbaFragment;

public class KamusActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener  {
    private TabLayout tab;
    private ViewPager vp;
    int currentPos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //VIEWPAGER AND TABS
        vp = findViewById(R.id.viewpager);
        addPages();

        //SETUP TAB
        tab =findViewById(R.id.tabs);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setupWithViewPager(vp);
        tab.addOnTabSelectedListener(this);

        //
    }

    //FILL TAB PAGES
    private void addPages()
    {
        MyPageAdapter myPageAdapter=new MyPageAdapter(getSupportFragmentManager());
        myPageAdapter.addPage(AlphabetFragment.newInstance());
        myPageAdapter.addPage(NumeralFragment.newInstance());
        myPageAdapter.addPage(NominaFragment.newInstance());
        myPageAdapter.addPage(VerbaFragment.newInstance());
        myPageAdapter.addPage(AdjektivaFragment.newInstance());
        myPageAdapter.addPage(AdverbiaFragment.newInstance());
        myPageAdapter.addPage(AllFragment.newInstance());
        vp.setAdapter(myPageAdapter);
    }

    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(currentPos=tab.getPosition());
    }

    public void onTabUnselected(TabLayout.Tab tab) {

    }

    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}