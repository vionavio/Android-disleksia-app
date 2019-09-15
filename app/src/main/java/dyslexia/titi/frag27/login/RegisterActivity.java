package dyslexia.titi.frag27.login;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.login.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextName;
    EditText editTextTL;
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputName;
    TextInputLayout textInputJK;
    TextInputLayout textInputTL;
    TextInputLayout textInputUserName;
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;

    //declaration radioGroup
    RadioGroup radioJK;

    private RadioButton mRadioButton;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //Declaration Button
    Button buttonRegister;

    //Declaration SqliteHelper
    DatabaseHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDisplayDate = findViewById(R.id.editTextTL);

        mDisplayDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener, year, month, day);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = day + "-" + month + "-" + year;
            mDisplayDate.setText(date);
        };

        sqliteHelper = new DatabaseHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(view -> {
            if (validate()) {

                int selectedIdJK = radioJK.getCheckedRadioButtonId();

                //find the radiobutton by returened id
                mRadioButton = findViewById(selectedIdJK);

                String Name = editTextName.getText().toString();
                String JK = mRadioButton.getText().toString();
                String TL = editTextTL.getText().toString();
                String UserName = editTextUserName.getText().toString();
                String Email = editTextEmail.getText().toString();
                String Password = editTextPassword.getText().toString();
                //Check in the database is there any user associated with  this email
                if (!sqliteHelper.isEmailExists(Email)) {
                    //Email does not exist now add new user to database
                    sqliteHelper.addUser(new User(null, Name, JK, TL, UserName, Email, Password));
                    Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> finish(), Snackbar.LENGTH_LONG);
                } else {
                    //Email exists with email input provided so show error user already exist
                    Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(view -> finish());
    }

    //    this method is used to connect XML views to its Objects
    private void initViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextTL = findViewById(R.id.editTextTL);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);

        radioJK = findViewById(R.id.radioJK);

        textInputName = findViewById(R.id.textInputName);
        textInputTL = findViewById(R.id.textInputTL);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputUserName = findViewById(R.id.textInputUserName);
        buttonRegister = findViewById(R.id.btnRegister);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        //  Redundant
        boolean valid = false;

        //Get values from EditText fields
        String Name = editTextName.getText().toString();
        String TL = editTextTL.getText().toString();
        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Name field
        if (Name.isEmpty()) {
            valid = false;
            textInputName.setError("Tolong diisi nama!");
        }



        //Handling validation for TL field
        if (TL.isEmpty()) {
            valid = false;
            textInputTL.setError("Tolong isi Tanggal Lahir!");
        } else


            //Handling validation for UserName field
            //  Avoid nested if
            if (UserName.isEmpty()) {
                valid = false;
                textInputUserName.setError("Tolong isi username!");
            } else {
                if (UserName.length() > 5) {
                    valid = true;
                    textInputUserName.setError(null);
                } else {
                    valid = false;
                    textInputUserName.setError("Username terlalu pendek!");
                }
            }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputEmail.setError("Tolong isi email yang valid!");
        } else {
            valid = true;
            textInputEmail.setError(null);
        }

        //Handling validation for Password field
        //  Avoid nested if
        if (Password.isEmpty()) {
            valid = false;
            textInputPassword.setError("Tolong masukkan password yang benar!");
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
