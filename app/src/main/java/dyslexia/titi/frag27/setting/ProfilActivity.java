package dyslexia.titi.frag27.setting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.database.entities.UserEntity;
import dyslexia.titi.frag27.services.AccountService;
import dyslexia.titi.frag27.utils.AlertUtil;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button updateButton = findViewById(R.id.btnUpdate);
        EditText name = findViewById(R.id.editTextName);
        RadioGroup gender = findViewById(R.id.radioJK);
        EditText dob = findViewById(R.id.editTextTL);
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);

        int selectedIdGender = gender.getCheckedRadioButtonId();
        RadioButton radioButtonGender = findViewById(selectedIdGender);

//        RadioButton radioButton1 = findViewById(R.id.radioL);
//        RadioButton radioButton2 = findViewById(R.id.radioP);

        UserEntity userEntity = AccountService.getMyProfile(this);
        name.setText(userEntity.name);
        radioButtonGender.setText(userEntity.gender);
        dob.setText(userEntity.dob);
        email.setText(userEntity.email);
        password.setText(userEntity.password);

        updateButton.setOnClickListener(view -> {

            Boolean updating = AccountService.updateMyProfile(this, new UserEntity(
                            name.getText().toString(),
                    radioButtonGender.getText().toString(),
                            dob.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString()

                    )
            );

            if (updating) {
                AlertUtil.showSnackbar(view, "Profil Anda Berhasil diubah");
            }

        });
    }
}
