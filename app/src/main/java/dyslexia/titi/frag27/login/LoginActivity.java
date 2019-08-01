package dyslexia.titi.frag27.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import dyslexia.titi.frag27.MenuActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.login.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;

    //Declaration Button
    Button btnLogin;

    //Declaration SqliteHelper
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        initCreateAccountTextView();
        initViews();

        //set click event of login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check user input is correct or not
                if (validate()) {

                    //Get values from EditText fields
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Authenticate user
                    User currentUser = databaseHelper.Authenticate(new User(null, null,null,null, null, Email, Password));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Snackbar.make(btnLogin, "Anda berhasil masuk!", Snackbar.LENGTH_LONG).show();

                        //shared preferences digunakan untuk menyimpan key value dari login yang telah terjadi

                        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        mSettings.edit().putBoolean("isLoggedIn", true).apply();

                        //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        //User Logged in Failed
                        Snackbar.make(btnLogin, "Gagal masuk , silakan coba lagi", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#aaa'>Saya belum punya akun. </font><font color='#0c0099'> Buat Akun</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textInputEmail = (TextInputLayout) findViewById(R.id.textInputEmail);
        textInputPassword = (TextInputLayout) findViewById(R.id.textInputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputEmail.setError("Tolong masukkan email yang benar!");
        } else {
            valid = true;
            textInputEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            textInputPassword.setError("Tolong masukkan password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputPassword.setError(null);
            } else {
                valid = false;
                textInputPassword.setError("Password terlalu pendek!");
            }
        }

        return valid;
    }
}
