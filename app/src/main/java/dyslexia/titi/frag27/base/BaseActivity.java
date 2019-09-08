package dyslexia.titi.frag27.base;
// Created by Arif Ikhsanudin on 9/8/2019.

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

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
