package dyslexia.titi.frag27.kamus.crud;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TambahKamusActivity extends AppCompatActivity {

    //inisilisasi elemen-elemen pada layout
    private Button buttonSubmit;
    private EditText edWord;
    private EditText edType;
    //inisialisasi kontroller/Data Source
    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kamus);
//
//        buttonSubmit = (Button) findViewById(R.id.buttom_submit);
//        buttonSubmit.setOnClickListener((View.OnClickListener) this);
//        edWord = (EditText) findViewById(R.id.et_word);
//        edType = (EditText) findViewById(R.id.et_type);

        // instanstiasi kelas DBDataSource
//        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
//
//        //membuat sambungan baru ke database
//        databaseAdapter.open();

    }
//    public void onClik(View v){
//        String word;
//        String type;
//        Kamus kamus = null;
//
//        if(edWord.getText()==null && edType.getText()==null )
//        {
//            Toast.makeText(this, "Data Kosong\n" , Toast.LENGTH_LONG).show();
//        }
//        else if(edWord.getText()!=null && edType.getText()!=null )
//        {
//            /* jika field nama, merk, dan harga tidak kosong
//             * maka masukkan ke dalam data barang*/
//            word = edWord.getText().toString().trim();
//            type = edType.getText().toString().trim();
//            switch(v.getId())
//            {
//                case R.id.buttom_submit:
//                    // insert data barang baru
//                    kamus =databaseAdapter.insert(word, type);
//
//                    //konfirmasi kesuksesan
//                    Toast.makeText(this, "masuk KATA\n" +
//                            "KATA" + kamus.getWord() +
//                            "TIPE" + kamus.getType(), Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
//
//
//
//
//    }
}
