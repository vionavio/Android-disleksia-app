package dyslexia.app.ui.tentang;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.app.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TentangActivity extends AppCompatActivity {

    ImageView call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        call = findViewById(R.id.call);

        call.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:087746464946"));
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
