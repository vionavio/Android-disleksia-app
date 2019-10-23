package dyslexia.app.ui.kamus.menu.konsonan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import dyslexia.app.R;
import dyslexia.app.ui.kamus.adapter.MyPageAdapter;
import dyslexia.app.ui.kamus.menu.konsonan.fragment.KonsonanFragment;
import dyslexia.app.ui.kamus.menu.konsonan.fragment.NgFragment;
import dyslexia.app.ui.kamus.menu.konsonan.fragment.NyFragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;

public class MenuKonsonanActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    int currentPosition = 0;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //VIEWPAGER AND TABS
        viewPager = findViewById(R.id.viewpager);
        addPages();

        //SETUP TAB
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(MenuKonsonanActivity.this);
    }

    private void addPages() {
        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        myPageAdapter.addPage(KonsonanFragment.newInstance());
        myPageAdapter.addPage(NgFragment.newInstance());
        myPageAdapter.addPage(NyFragment.newInstance());
        viewPager.setAdapter(myPageAdapter);
    }

        @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(currentPosition = tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
