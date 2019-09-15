package dyslexia.titi.frag27.perbaikanKata;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

import dyslexia.titi.frag27.R;
import dyslexia.titi.frag27.base.BaseActivity;
import dyslexia.titi.frag27.kamus.crud.TambahKamusActivity;
import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Dictionary;
import dyslexia.titi.frag27.kamus.model.DictionarySimilar;
import dyslexia.titi.frag27.perbaikanKata.ReplaceLetter.ReplaceLetter;
import dyslexia.titi.frag27.perbaikanKata.anagram.AnagramAlgorithm;

public class WordRepairActivity extends BaseActivity {

    TextToSpeech textToSpeech;
    EditText ed_inputWord;
    Button btn_proseskata;
    ListView listViewSimiliarWords;
    DatabaseAdapter databaseAdapter;
    ImageView iv_suara;
    TextView tv_resultWord;
    ArrayList<String> replacedWords;
    ArrayList<Dictionary> replacedWordsAnagram = new ArrayList<>();
    ArrayList<Dictionary> wordRepairResultList = new ArrayList<>();


    //anagram
    ArrayList<String> resultAnagramSearchList;

    ArrayList<Dictionary> a = new ArrayList<>();

    private Button editButton;
    private Button deleteButton;
    AnagramAlgorithm anagramAlgorithm;

    List<Dictionary> dictionaryWords;
    String inputWord;
    final String TAG = "WordRepairActivity";


    //MENYAMAKAN HURUF


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
        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        anagramAlgorithm = new AnagramAlgorithm(WordRepairActivity.this);
    }

    @Override
    public void populateView() {
        btn_proseskata.setOnClickListener((View view) -> searchInDictionary());
        dictionaryWords = databaseAdapter.retrieveKamus("all");
        loadSuara();
        loadSuara2();
        loadSuara3();
        loadMenu();
    }

    public void searchInDictionary() {
        String wordRepairResultSingle = "";
        inputWord = ed_inputWord.getText().toString().trim();

        for (Dictionary dictionary : dictionaryWords) {
            if (inputWord.equals(dictionary.getWord())) {
                wordRepairResultSingle = dictionary.getWord();
                break;
            }
        }

        if (!wordRepairResultSingle.isEmpty()) {
            setSingleResult(wordRepairResultSingle);
        } else {
            anagramSearch();
            Log.d(TAG, "anagramSearch 1");
        }
    }

    private void setSingleResult(String word) {
        tv_resultWord.setText(word);
        listViewSimiliarWords.setAdapter(null);
        Log.d(TAG, "single result: " + word);
    }

    private void setMultiResult(ArrayList<Dictionary> dictionaryArrayList) {
        ArrayAdapter<Dictionary> dictionaryArrayAdapter;
        dictionaryArrayAdapter = new ArrayAdapter<>(this, R.layout.kamus_list_row, dictionaryArrayList);
        tv_resultWord.setVisibility(View.GONE);
        listViewSimiliarWords.setAdapter(dictionaryArrayAdapter);
        Log.d(TAG, "multi result: " + dictionaryArrayList);
    }

    private void anagramSearch() {
        wordRepairResultList = new ArrayList<>();
        resultAnagramSearchList = anagramAlgorithm.getAnagrams(inputWord);
        Log.d(TAG, "wordRepairResultList: " + wordRepairResultList );
        Log.d(TAG, "resultAnagramSearchList: " + resultAnagramSearchList);
        for (String resultAnagramSearchWord : resultAnagramSearchList) {
            wordRepairResultList.add(new Dictionary(0, resultAnagramSearchWord, ""));
        }

        if (!(wordRepairResultList.isEmpty())) {
            setMultiResult(wordRepairResultList);
        } else {
            Log.d(TAG, "anagramSearch: tidak ketemu di anagramSearch 1");
            buatKataDariPersamaanHuruf();
        }
    }

    private void buatKataDariPersamaanHuruf() {
        Log.d("yyy", "cariLagiDiAnagram: " + replacedWordsAnagram);
        inputWord = ed_inputWord.getText().toString().trim();
        replacedWords = ReplaceLetter.generateWords(inputWord);
        Log.d("bbbbbbb", "buatKataDariPersamaanHuruf: " + replacedWords.toString());

        //cari lagi di anagram
        cariLagiDiAnagram();
    }


    private void cariLagiDiAnagram() {

        ArrayList<String> stringArrayList = new ArrayList<>();

        for (String replacedWord : replacedWords) {
            stringArrayList = anagramAlgorithm.getAnagrams(replacedWord);
            Log.d("pppppp", "buatKataDariPersamaanHuruf: " + stringArrayList);
        }
        for (String string : stringArrayList) {
            replacedWordsAnagram.add(new Dictionary(0, string, ""));
        }
        Log.d("llllll", "cariLagiDiAnagram: " + replacedWordsAnagram);
        setMultiResult(replacedWordsAnagram);


        Log.d("nnnnnn", "cariLagiDiAnagram: " + replacedWordsAnagram);
        Log.d("mmmmmm", "cariLagiDiAnagram: " + replacedWords);
        replacedWords.clear();


    }

    private void jaroWinklerDistance() {

        List<DictionarySimilar> kamusSimilarList = new ArrayList<>();
        List<DictionarySimilar> kamusSimilarFilteredList = new ArrayList<>();
        List<String> finalWords = new ArrayList<>();

        for (Dictionary dictionary : dictionaryWords) {
            kamusSimilarList.add(new DictionarySimilar(dictionary.getId_word(), dictionary.getWord(), dictionary.getType(), getSimilarScore(dictionary.getWord())));
        }
        for (DictionarySimilar kamusSimilar : kamusSimilarList) {
            if (kamusSimilar.getSimilarScore() > 0.85) {
                kamusSimilarFilteredList.add(kamusSimilar);
            }

        }
        Collections.sort(kamusSimilarFilteredList, (kamusSimilar, kamusSimilar2) -> Double.compare(kamusSimilar2.getSimilarScore(), kamusSimilar.getSimilarScore()));
        Log.d("aaaaaa", "jaroWinklerDistance: " + kamusSimilarFilteredList.toString());
        for (DictionarySimilar kamusSimilar : kamusSimilarFilteredList) {
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
        String inputWord = ed_inputWord.getText().toString().trim();
        return new JaroWinkler().getSimilarity(word, inputWord);
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

            Dictionary selectedFromList = (Dictionary) listViewSimiliarWords.getItemAtPosition(position);


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

                        //Dictionary selectedItem = (Dictionary) adapterView.getItemAtPosition(position);
                        // DatabaseAdapter databaseAdapter = new DatabaseAdapter(getApplicationContext());
                        //databaseAdapter.deleteKamus(selectedItem.getId_word());
                        // finish();
                        //startActivity(getIntent());


                        AlertDialog.Builder builder = new AlertDialog.Builder(WordRepairActivity.this);
                        builder.setTitle("Delete process");
                        builder.setMessage("Are you sure to delete : ");

                        builder.setPositiveButton("YES", (dialogInterface, i) -> {

//                                databaseAdapter.deleteKamus(id.getText().toString());
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
                Intent tambahIntent = new Intent(this, TambahKamusActivity.class);
                startActivity(tambahIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
