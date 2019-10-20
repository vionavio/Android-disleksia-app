package dyslexia.titi.frag27.kamus.menu.huruf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.adapter.MyPageAdapter;
import dyslexia.titi.frag27.kamus.menu.huruf.fragment.HurufFragment;
import dyslexia.titi.frag27.kamus.menu.huruf.fragment.KonsonanFragment;
import dyslexia.titi.frag27.kamus.menu.huruf.fragment.VokalFragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;

public class HurufActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

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
        tabLayout.addOnTabSelectedListener(HurufActivity.this);

    }

    private void addPages() {
        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        myPageAdapter.addPage(HurufFragment.newInstance());
        myPageAdapter.addPage(VokalFragment.newInstance());
        myPageAdapter.addPage(KonsonanFragment.newInstance());
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

    @Override
    protected void onDestroy() {


        //Close the Text to Speech Library
        if(textToSpeech != null) {

            textToSpeech.stop();
            textToSpeech.shutdown();
            Log.d("tttttttttt", "TTS Destroyed");
        }
        super.onDestroy();
    }
}
