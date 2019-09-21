package dyslexia.titi.frag27.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {


    //untuk merapikan dan meringkas kode dalam activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        populateView();
    }

    protected abstract int getLayoutId();

    public void initView() {
    }

    public void populateView() {
    }
}
