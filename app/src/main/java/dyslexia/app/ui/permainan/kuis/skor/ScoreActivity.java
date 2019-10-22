package dyslexia.app.ui.permainan.kuis.skor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import dyslexia.app.R;
import dyslexia.app.ui.kamus.adapter.MyPageAdapter;
import dyslexia.app.ui.permainan.kuis.skor.fragment.AdjektivaFragment;
import dyslexia.app.ui.permainan.kuis.skor.fragment.AdverbiaFragment;
import dyslexia.app.ui.permainan.kuis.skor.fragment.NominaFragment;
import dyslexia.app.ui.permainan.kuis.skor.fragment.NumeralFragment;
import dyslexia.app.ui.permainan.kuis.skor.fragment.VerbaFragment;

public class ScoreActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tab;
    private ViewPager vp;
    int currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //VIEWPAGER AND TABS
        vp = findViewById(R.id.viewpager);
        addPages();

        //SETUP TAB
        tab = findViewById(R.id.tabs);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setupWithViewPager(vp);
        tab.addOnTabSelectedListener(this);

        //
    }

    //FILL TAB PAGES
    private void addPages() {
        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        myPageAdapter.addPage(NumeralFragment.newInstance());
        myPageAdapter.addPage(NominaFragment.newInstance());
        myPageAdapter.addPage(VerbaFragment.newInstance());
        myPageAdapter.addPage(AdjektivaFragment.newInstance());
        myPageAdapter.addPage(AdverbiaFragment.newInstance());
        vp.setAdapter(myPageAdapter);
    }

    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(currentPos = tab.getPosition());
    }

    public void onTabUnselected(TabLayout.Tab tab) {

    }

    public void onTabReselected(TabLayout.Tab tab) {

    }

}