package dyslexia.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dyslexia.app.login.LoginActivity;
import dyslexia.app.login.RegisterActivity;
import dyslexia.app.services.AuthService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AuthService.isLoggedIn(this)) {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        }
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void register(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
