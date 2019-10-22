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
//import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.entities.WordEntity;
//import dyslexia.app.ui.kamus.database.DatabaseDictionary;
//import dyslexia.app.ui.kamus.model.Dictionary;
import dyslexia.app.ui.perbaikanKata.JaroWinkler.DictionarySimilar;
import dyslexia.app.ui.perbaikanKata.JaroWinkler.JaroWinklerDistance;
import dyslexia.app.ui.perbaikanKata.ReplaceLetter.ReplaceLetter;
import dyslexia.app.ui.perbaikanKata.anagram.AnagramAlgorithm;

public class WordRepairActivity extends BaseActivity {

    TextToSpeech textToSpeech;
    EditText ed_inputWord;
    Button btn_proseskata;
    ListView listViewSimiliarWords;
    //DatabaseDictionary databaseDictionary;
    ImageView iv_suara;
    TextView tv_resultWord;
    ArrayList<WordEntity> wordRepairResultList = new ArrayList<>();
    AppDatabase appDatabase;


    //anagram
    ArrayList<String> resultAnagramSearchList;
    ArrayList<String> generatedWordsFromSimilarCase = new ArrayList<>();

    private Button editButton;
    private Button deleteButton;
    AnagramAlgorithm anagramAlgorithm;

    List<WordEntity> dictionaryWords = new ArrayList<>();
    String inputWord;
    final String TAG = "WordRepairActivity";

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
        listViewSimiliarWords = findViewById(R.id.lv_similar_words);
        //databaseDictionary = new DatabaseDictionary(getApplicationContext());
        anagramAlgorithm = new AnagramAlgorithm(WordRepairActivity.this);
    }

    @Override
    public void populateView() {
        btn_proseskata.setOnClickListener((View view) -> anagramSearch());
        loadSuara();
        loadSuara2();
        loadSuara3();
        loadMenu();
    }

    private void anagramSearch() {
        inputWord = ed_inputWord.getText().toString().trim();

        wordRepairResultList.clear();
        resultAnagramSearchList = anagramAlgorithm.getAnagrams(inputWord);
        //Log.d(TAG, "wordRepairResultList: " + wordRepairResultList);
        //Log.d(TAG, "resultAnagramSearchList: " + resultAnagramSearchList);
        for (String resultAnagramSearchWord : resultAnagramSearchList) {
            WordEntity dictionary = new WordEntity(0,resultAnagramSearchWord,"");
            wordRepairResultList.add(dictionary);
        }
        ArrayAdapter<WordEntity> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, wordRepairResultList);
        Log.d(TAG, "anagramSearch: "+ wordRepairResultList);


        if (!(wordRepairResultList.isEmpty())) {
            jaroWinklerDistance2(wordRepairResultList);
//            tv_resultWord.setText(" ");
//            listViewSimiliarWords.setAdapter(adapter);

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
        ArrayList<WordEntity> replacedWordsAnagram = new ArrayList<>();

        stringArrayList = anagramAlgorithm.getAnagrams(generatedWordsFromSimilarCase);

        for (String string : stringArrayList) {
            replacedWordsAnagram.add(new WordEntity(0, string, ""));
        }
        Log.d(TAG, "cariLagiDiAnagram: " + replacedWordsAnagram);

        ArrayAdapter<WordEntity> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, replacedWordsAnagram);
        if (!(replacedWordsAnagram.isEmpty())) {
            jaroWinklerDistance2(replacedWordsAnagram);
//            tv_resultWord.setText(" ");
//            listViewSimiliarWords.setAdapter(adapter);
        }
        else
        {
            jaroWinklerDistance();
        }
        inputMethodManager();
    }

    private void jaroWinklerDistance2(ArrayList<WordEntity> wordResultAnagram) {

        Log.d("wwwwwwwwww", "jaroWinklerDistance: " + wordResultAnagram);
        Log.d("ooooooooooooooo", "jaroWinklerDistance: " + inputWord);
        inputWord = ed_inputWord.getText().toString().trim();
        List<DictionarySimilar> kamusSimilarList = new ArrayList<>();
        List<DictionarySimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();



        for (WordEntity dictionary : wordResultAnagram) {
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
        Log.d("aaaaaa", "jaroWinklerDistance: " + kamusSimilarFilteredList.toString());
        for (DictionarySimilar kamusSimilar : kamusSimilarFilteredList) {
            finalWords.add(kamusSimilar.getWord());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, finalWords);
        if (!(finalWords.isEmpty())) {
            tv_resultWord.setText(" ");
            listViewSimiliarWords.setAdapter(arrayAdapter);
        }
        else
        {
           jaroWinklerDistance();   }
        inputMethodManager();
    }

    private void jaroWinklerDistance() {


        //dictionaryWords = databaseDictionary.retrieveKamus("all");
        dictionaryWords = appDatabase.wordDao().getAll();

        Log.d("ooooooooooooooo", "jaroWinklerDistance: " + inputWord);
        inputWord = ed_inputWord.getText().toString().trim();
        List<DictionarySimilar> kamusSimilarList = new ArrayList<>();
        List<DictionarySimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();


        for (WordEntity dictionary : dictionaryWords) {
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
        Log.d("aaaaaa", "jaroWinklerDistance: " + kamusSimilarFilteredList.toString());
        for (DictionarySimilar kamusSimilar : kamusSimilarFilteredList) {
            finalWords.add(kamusSimilar.getWord());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, finalWords);
        if (!(finalWords.isEmpty())) {
            tv_resultWord.setText(" ");
            listViewSimiliarWords.setAdapter(arrayAdapter);
        }
        else
        {
            tv_resultWord.setText(R.string.hasil_kosong);
            listViewSimiliarWords.setAdapter(null);
            Log.d("eeeeee", "jaroWinklerDistance: " + kamusSimilarFilteredList.toString());
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
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private double getSimilarScore(String word) {
        String inputWord = ed_inputWord.getText().toString().trim();
        return new JaroWinklerDistance().getSimilarity(inputWord, word);
    }

    private void loadSuara() {
        textToSpeech();
        listViewSimiliarWords.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            String selectedItem = String.valueOf(adapterView.getItemAtPosition(position));
            Toast.makeText(getApplicationContext(), "" + selectedItem, Toast.LENGTH_LONG).show();
            textToSpeech.speak(selectedItem, TextToSpeech.QUEUE_FLUSH, null);
        });

    }

    private void loadSuara2() {
        textToSpeech();
        iv_suara.setOnClickListener(view -> {
            String toSpeak = ed_inputWord.getText().toString().trim();
            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        });

    }

    private void loadSuara3() {
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
        listViewSimiliarWords.setOnItemLongClickListener((adapterView, view, position, l) -> {
            Dialog dialog = new Dialog(WordRepairActivity.this);
            dialog.setContentView(R.layout.dialog_view_kata);
            dialog.show();

            editButton = dialog.findViewById(R.id.button_edit_data);
            deleteButton = dialog.findViewById(R.id.button_delete_data);

            //String selectedFromList = (String) listViewSimiliarWords.getItemAtPosition(position);

            WordEntity selectedFromList = (WordEntity) listViewSimiliarWords.getItemAtPosition(position);


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
        switch (item.getItemId()) {
            case R.id.tambahKata:
//                Intent tambahIntent = new Intent(this, TambahKamusActivity.class);
//                startActivity(tambahIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
