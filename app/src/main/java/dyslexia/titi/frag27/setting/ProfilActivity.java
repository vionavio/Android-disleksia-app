package dyslexia.titi.frag27.setting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        UserEntity userEntity = AccountService.getMyProfile(this);
        name.setText(userEntity.name);

        updateButton.setOnClickListener(view -> {
            Boolean updating = AccountService.updateMyProfile(this, new UserEntity(
                            name.getText().toString(),
                            "",
                            "",
                            "",
                            ""
                    )
            );

            if (updating) {
                AlertUtil.showSnackbar(view, "Berhasil dirubah");
            }
        });
    }
}
