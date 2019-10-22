package dyslexia.app.ui.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.app.ui.feature.MenuActivity;
import dyslexia.app.R;
import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.entities.UserEntity;
//import dyslexia.app.ui.authentication.login.database.DatabaseUser;
import dyslexia.app.services.AuthService;
import dyslexia.app.ui.authentication.register.RegisterActivity;
import dyslexia.app.utils.AlertUtil;

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
    //DatabaseUser databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
     //   databaseUser = new DatabaseUser(this);
        initCreateAccountTextView();
        initViews();

        //set click event of login button
        btnLogin.setOnClickListener(view -> {

            //Check user input is correct or not
            if (validate()) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                //Check Authentication is successful or not
                //      if (currentUser != null) {
                if (authenticate(email, password)) {
                    Snackbar.make(btnLogin, "Anda berhasil masuk!", Snackbar.LENGTH_LONG).show();
                    //User Logged in Successfully Launch You home screen activity
                    startActivity(new Intent(this, MenuActivity.class));
                    finish();
                } else {
                    //User Logged in Failed
                    AlertUtil.showSnackbar(btnLogin, "Gagal masuk , silakan coba lagi");
                }

//                User currentUser = databaseUser.Authenticate(new User(null, null,null,null,  email, password));
//
//                //Check Authentication is successful or not
//                if (currentUser != null) {
//                    Snackbar.make(btnLogin, "Anda berhasil masuk!", Snackbar.LENGTH_LONG).show();
//
//                    //shared preferences digunakan untuk menyimpan key value dari login yang telah terjadi
//
//                    SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    mSettings.edit().putBoolean("isLoggedIn", true).apply();
//
//                    //User Logged in Successfully Launch You home screen activity
////                        Intent intent=new Intent(LoginActivity.this, MenuActivity.class);
////                        startActivity(intent);
//                    finish();
//
//                } else {
//
//                    //User Logged in Failed
//                    Snackbar.make(btnLogin, "Gagal masuk , silakan coba lagi", Snackbar.LENGTH_LONG).show();
//
                //               }
            }
        });
    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#aaa'>Saya belum punya akun. </font><font color='#0c0099'> Buat Akun</font>"));
        textViewCreateAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        btnLogin = findViewById(R.id.btnLogin);

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

    private Boolean authenticate(String email, String password) {
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        UserEntity userEntity = appDatabase.userDao().getSingle(email);
        Log.d("auth", "authenticate: " + appDatabase.userDao().getAll());
        Log.d("auth", "authenticate: " + userEntity);
//        Log.d("auth", "authenticate: " + userEntity.password.equals(password));
        if (userEntity != null && userEntity.password.equals(password)) {
            AuthService.saveLoginInfo(this, userEntity.id_user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
