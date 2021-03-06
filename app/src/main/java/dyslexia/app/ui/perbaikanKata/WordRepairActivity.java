package dyslexia.app.ui.perbaikanKata;

import android.app.Activity;
import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import dyslexia.app.R;
import dyslexia.app.base.BaseActivity;
import dyslexia.app.ui.database.DatabaseDictionary;
import dyslexia.app.ui.database.model.Dictionary;
import dyslexia.app.ui.perbaikanKata.JaroWinkler.DictionarySimilar;
import dyslexia.app.ui.perbaikanKata.JaroWinkler.JaroWinklerDistance;
import dyslexia.app.ui.perbaikanKata.ReplaceLetter.ReplaceLetter;
import dyslexia.app.ui.perbaikanKata.anagram.AnagramAlgorithm;

public class WordRepairActivity extends BaseActivity {

    TextToSpeech textToSpeech;
    EditText ed_inputWord;
    Button btn_proseskata, clear;
    ListView listViewSimilarWords;
    DatabaseDictionary databaseDictionary;
    ImageView iv_suara;
    TextView tv_resultWord;
    ArrayList<Dictionary> wordRepairResultList = new ArrayList<>();

    //anagram
    ArrayList<String> resultAnagramSearchList;
    ArrayList<String> generatedWordsFromSimilarCase = new ArrayList<>();

    private Button editButton;
    private Button deleteButton;
    AnagramAlgorithm anagramAlgorithm;

    List<Dictionary> dictionaryWords = new ArrayList<>();
    String inputWord;
    final String TAG = "WordRepairActivity";
    WordRepairAdapter wordRepairAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perbaikan_kata;
    }

    @Override
    public void initView() {
        tv_resultWord = findViewById(R.id.tv_hasil);
        iv_suara = findViewById(R.id.iv_suara);
        ed_inputWord = findViewById(R.id.ed_kataAwal);
        btn_proseskata = findViewById(R.id.btnProsesKata);
        clear = findViewById(R.id.clear);
        listViewSimilarWords = findViewById(R.id.lv_similar_words);
        databaseDictionary = new DatabaseDictionary(getApplicationContext());
        anagramAlgorithm = new AnagramAlgorithm(WordRepairActivity.this);
    }

    @Override
    public void populateView() {
        clear.setOnClickListener((View view) -> reset());
        btn_proseskata.setOnClickListener((View view) -> anagramSearch());
        loadVoice();
        loadVoice2();
        loadVoice3();
        loadMenu();
    }

    private void reset() {
        tv_resultWord.setText(" ");
        ed_inputWord.setText("");
        listViewSimilarWords.setAdapter(null);
    }

    private void anagramSearch() {
        inputWord = ed_inputWord.getText().toString().trim();

        wordRepairResultList.clear();
        resultAnagramSearchList = anagramAlgorithm.getAnagrams(inputWord);
        for (String resultAnagramSearchWord : resultAnagramSearchList) {
            Dictionary dictionary = new Dictionary(0, resultAnagramSearchWord, "");
            wordRepairResultList.add(dictionary);
        }

        if (!(wordRepairResultList.isEmpty())) {
            jaroWinklerDistanceAnagram(wordRepairResultList);
        } else {
            Log.d(TAG, "anagramSearch: tidak ketemu di anagramSearch 1");
            generateWordsFromAbnormalityCondition();
        }
        inputMethodManager();
    }

    private void generateWordsFromAbnormalityCondition() {
        // ArrayList<String> generatedWordsFromSimilarCase = new ArrayList<>();
        generatedWordsFromSimilarCase.clear();
        inputWord = ed_inputWord.getText().toString().trim();
        Log.d(TAG, "generateWordsFromAbnormalityCondition: " + inputWord);
        tv_resultWord.setText(" ");
        generatedWordsFromSimilarCase = ReplaceLetter.generateWords(inputWord);
        Log.d(TAG, "generateWordsFromAbnormalityCondition 2: " + generatedWordsFromSimilarCase.toString());

        //cari lagi di anagram
        anagramSearch2();
    }

    private void anagramSearch2() {

        Log.d(TAG, "generateWordsFromAbnormalityCondition 3: " + generatedWordsFromSimilarCase.toString());

        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Dictionary> replacedWordsAnagram = new ArrayList<>();

        stringArrayList = anagramAlgorithm.getAnagrams(generatedWordsFromSimilarCase);

        for (String string : stringArrayList) {
            replacedWordsAnagram.add(new Dictionary(0, string, ""));
        }
        Log.d(TAG, "cariLagiDiAnagram: " + replacedWordsAnagram);

        ArrayAdapter<Dictionary> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, replacedWordsAnagram);
        if (!(replacedWordsAnagram.isEmpty())) {
            jaroWinklerDistanceAnagram(replacedWordsAnagram);
//            tv_resultWord.setText(" ");
//            listViewSimilarWords.setAdapter(adapter);
        } else {
            jaroWinklerDistance();
        }
        inputMethodManager();
    }

    private void jaroWinklerDistanceAnagram(ArrayList<Dictionary> wordResultAnagram) {
        inputWord = ed_inputWord.getText().toString().trim();
        List<DictionarySimilar> kamusSimilarList = new ArrayList<>();
        List<DictionarySimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();

        for (Dictionary dictionary : wordResultAnagram) {
            //menginstansiasi dictionarySimilar dari kelas DictionarySimilar untuk menampung score
            //menyimpan value ke dalam kamusSimilarList
            DictionarySimilar dictionarySimilar = new DictionarySimilar(dictionary.getId_word(), dictionary.getWord(), dictionary.getType(), getSimilarScore(dictionary.getWord()));
            kamusSimilarList.add(dictionarySimilar);
        }

        //kelas baru yang mewarisi kelas dictionary untuk mengaluarkan nilai jaro
        for (DictionarySimilar kamusSimilar : kamusSimilarList) {
            kamusSimilarFilteredList.add(kamusSimilar);
        }

        Collections.sort(kamusSimilarFilteredList, (kamusSimilar, kamusSimilar2) -> Double.compare(kamusSimilar2.getSimilarScore(), kamusSimilar.getSimilarScore()));
        for (DictionarySimilar kamusSimilar : kamusSimilarFilteredList) {
            finalWords.add(kamusSimilar.getWord());
        }

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, finalWords);
        wordRepairAdapter = new WordRepairAdapter(this, R.layout.word_repair_item, finalWords);

        if (!(finalWords.isEmpty())) {
            tv_resultWord.setText(" ");
            listViewSimilarWords.setAdapter(wordRepairAdapter);
        } else {
            jaroWinklerDistance();
        }
        inputMethodManager();
    }

    private void jaroWinklerDistance() {
        dictionaryWords = databaseDictionary.retrieveKamus("all");

        inputWord = ed_inputWord.getText().toString().trim();
        List<DictionarySimilar> kamusSimilarList = new ArrayList<>();
        List<DictionarySimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();

        for (Dictionary dictionary : dictionaryWords) {
            //menginstansiasi dictionarySimilar dari kelas DictionarySimilar untuk menampung score
            //menyimpan value ke dalam kamusSimilarList
            DictionarySimilar dictionarySimilar = new DictionarySimilar(dictionary.getId_word(), dictionary.getWord(), dictionary.getType(), getSimilarScore(dictionary.getWord()));
            kamusSimilarList.add(dictionarySimilar);
        }

        //kelas baru yang mewarisi kelas dictionary untuk mengaluarkan nilai jaro
        for (DictionarySimilar kamusSimilar : kamusSimilarList) {
            if (kamusSimilar.getSimilarScore() > 0.92) {
                kamusSimilarFilteredList.add(kamusSimilar);
            }
        }
        Collections.sort(kamusSimilarFilteredList, (kamusSimilar, kamusSimilar2) -> Double.compare(kamusSimilar2.getSimilarScore(), kamusSimilar.getSimilarScore()));
        for (DictionarySimilar kamusSimilar : kamusSimilarFilteredList) {
            finalWords.add(kamusSimilar.getWord());
        }

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, finalWords);
        wordRepairAdapter = new WordRepairAdapter(this, R.layout.word_repair_item, finalWords);
        if (!(finalWords.isEmpty())) {
            tv_resultWord.setText(" ");
            listViewSimilarWords.setAdapter(wordRepairAdapter);
        } else {
            tv_resultWord.setText(R.string.hasil_kosong);
            listViewSimilarWords.setAdapter(null);
        }
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
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private double getSimilarScore(String word) {
        String inputWord = ed_inputWord.getText().toString().trim();
        return new JaroWinklerDistance().getSimilarity(inputWord, word);
    }

    private void loadVoice() {
        textToSpeech();
        listViewSimilarWords.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            String selectedItem = String.valueOf(adapterView.getItemAtPosition(position));
            Toast.makeText(getApplicationContext(), "" + selectedItem, Toast.LENGTH_LONG).show();
            textToSpeech.speak(selectedItem, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void loadVoice2() {
        textToSpeech();
        iv_suara.setOnClickListener(view -> {
            String toSpeak = ed_inputWord.getText().toString().trim();
            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void loadVoice3() {
        textToSpeech();
        tv_resultWord.setOnClickListener(view -> {
            String toSpeak = tv_resultWord.getText().toString().trim();
            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void textToSpeech() {
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });
    }

    private void loadMenu() {
        listViewSimilarWords.setOnItemLongClickListener((adapterView, view, position, l) -> {
            Dialog dialog = new Dialog(WordRepairActivity.this);
            dialog.setContentView(R.layout.dialog_view_kata);
            dialog.show();

            editButton = dialog.findViewById(R.id.button_edit_data);
            deleteButton = dialog.findViewById(R.id.button_delete_data);

            //String selectedFromList = (String) listViewSimilarWords.getItemAtPosition(position);

            Dictionary selectedFromList = (Dictionary) listViewSimilarWords.getItemAtPosition(position);

            //apabila tombol edit diklik
            editButton.setOnClickListener(
                    v -> {
                        //  Auto-generated method stub
                        //switchToEdit(selectedFromList.getId_word());
//                        Intent tambahIntent = new Intent(this, TambahKamusActivity.class);
//                        startActivity(tambahIntent);
                    }
            );

            deleteButton.setOnClickListener(
                    v -> {
                        // Delete kata dari db

                        //Dictionary selectedItem = (Dictionary) adapterView.getItemAtPosition(position);
                        // DatabaseDictionary databaseDictionary = new DatabaseDictionary(getApplicationContext());
                        //databaseDictionary.deleteKamus(selectedItem.getId_word());
                        // finish();
                        //startActivity(getIntent());


                        AlertDialog.Builder builder = new AlertDialog.Builder(WordRepairActivity.this);
                        builder.setTitle("Delete process");
                        builder.setMessage("Are you sure to delete : ");

                        builder.setPositiveButton("YES", (dialogInterface, i) -> {

//                                databaseDictionary.deleteKamus(id.getText().toString());
//                                name.setText("");
//                                tel.setText("");
                            Toast.makeText(WordRepairActivity.this, "A contact is deleted ",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }).setNegativeButton("NO", (dialogInterface, i) -> {
                            dialogInterface.cancel();
                            dialog.dismiss();
                        });

                        builder.create().show();

                    }
            );


            return false;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tambahKata) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
