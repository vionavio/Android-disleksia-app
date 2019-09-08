package dyslexia.titi.frag27.perbaikanKata;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.kamus.crud.TambahKamusActivity;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;
import dyslexia.titi.frag27.kamus.model.KamusSimilar;

public class PerbaikanKataActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText ed_kataAwal;
    Button btn_proseskata;
    ListView listViewSimiliarWords;
    DatabaseAdapter databaseAdapter;
    ImageView iv_suara;

    private Button editButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbaikan_kata);

        iv_suara = findViewById(R.id.iv_suara);
        ed_kataAwal = findViewById(R.id.ed_kataAwal);
        btn_proseskata = findViewById(R.id.btnProsesKata);
        listViewSimiliarWords = findViewById(R.id.lv_similar_words);
        btn_proseskata.setOnClickListener(view -> {
            jaroWinklerDistance();
        });
        loadSuara();
        loadSuara2();
        loadMenu();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambahKata:
                Intent tambahIntent = new Intent(this, TambahKamusActivity.class);
                startActivity(tambahIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void jaroWinklerDistance() {
        List<Kamus> kamusList;
        List<KamusSimilar> kamusSimilarList = new ArrayList<>();
        List<KamusSimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();

        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        kamusList = databaseAdapter.retrieveKamus("all");

        for (Kamus kamus : kamusList) {
            kamusSimilarList.add(new KamusSimilar(kamus.getId_word(), kamus.getWord(), kamus.getType(), getSimilarScore(kamus.getWord())));
        }
        for (KamusSimilar kamusSimilar : kamusSimilarList) {

            if((kamusSimilar.getSimilarScore() > 0.99 )&&(kamusSimilar.getSimilarScore() <= 1.03 )) {
                kamusSimilarFilteredList.add(kamusSimilar);
            }
           else if ((kamusSimilar.getSimilarScore() > 0.92 )&&(kamusSimilar.getSimilarScore() < 0.98 ) ) {
                kamusSimilarFilteredList.add(kamusSimilar);
            }
        }
        Collections.sort(kamusSimilarFilteredList, (kamusSimilar, kamusSimilar2) -> Double.compare(kamusSimilar2.getSimilarScore(), kamusSimilar.getSimilarScore()));
        Log.d("aaaaaa", "jaroWinklerDistance: " + kamusSimilarFilteredList.toString());
        for (KamusSimilar kamusSimilar : kamusSimilarFilteredList) {
            finalWords.add(kamusSimilar.getWord());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, finalWords);
        listViewSimiliarWords.setAdapter(arrayAdapter);
        inputMethodManager();
    }

    private void inputMethodManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private double getSimilarScore(String word) {
        String inputWord = ed_kataAwal.getText().toString().trim();
        return new JaroWinkler().getSimilarity(word, inputWord);
    }

    private void loadSuara() {
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });

        listViewSimiliarWords.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            String selectedItem = String.valueOf(adapterView.getItemAtPosition(position));
            Toast.makeText(getApplicationContext(),""+ selectedItem,Toast.LENGTH_LONG).show();
            textToSpeech.speak(selectedItem,TextToSpeech.QUEUE_FLUSH,null);
        });

    }

    private void loadSuara2() {

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });

        iv_suara.setOnClickListener(view -> {
            String toSpeak = ed_kataAwal.getText().toString().trim();
            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });

    }

    private void loadMenu() {
        listViewSimiliarWords.setOnItemLongClickListener((adapterView, view, position, l) -> {
            Dialog dialog = new Dialog(PerbaikanKataActivity.this);
            dialog.setContentView(R.layout.dialog_view_kata);
            dialog.show();

            editButton = dialog.findViewById(R.id.button_edit_data);
            deleteButton = dialog.findViewById(R.id.button_delete_data);

             //String selectedFromList = (String) listViewSimiliarWords.getItemAtPosition(position);

            Kamus selectedFromList = (Kamus) listViewSimiliarWords.getItemAtPosition(position);


            //apabila tombol edit diklik
            editButton.setOnClickListener(
                    v -> {
                        //  Auto-generated method stub
                        //switchToEdit(selectedFromList.getId_word());
                        Intent tambahIntent = new Intent(this, TambahKamusActivity.class);
                        startActivity(tambahIntent);
                    }
            );

            deleteButton.setOnClickListener(
                    v -> {
                        // Delete kata dari db

                        //Kamus selectedItem = (Kamus) adapterView.getItemAtPosition(position);
                       // DatabaseAdapter databaseAdapter = new DatabaseAdapter(getApplicationContext());
                        //databaseAdapter.deleteKamus(selectedItem.getId_word());
                       // finish();
                        //startActivity(getIntent());


                        AlertDialog.Builder builder = new AlertDialog.Builder(PerbaikanKataActivity.this);
                        builder.setTitle("Delete process");
                        builder.setMessage("Are you sure to delete : ");

                        builder.setPositiveButton("YES", (dialogInterface, i) -> {

//                                databaseAdapter.deleteKamus(id.getText().toString());
//                                name.setText("");
//                                tel.setText("");
                            Toast.makeText(PerbaikanKataActivity.this, "A contact is deleted ",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }).setNegativeButton("NO", (dialogInterface, i) -> {
                            dialogInterface.cancel();
                            dialog.dismiss();});

                        builder.create().show();

                    }
            );


            return false;
        });
    }
}