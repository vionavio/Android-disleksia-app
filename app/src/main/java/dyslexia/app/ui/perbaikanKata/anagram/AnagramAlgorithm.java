package dyslexia.app.ui.perbaikanKata.anagram;

import android.content.Context;

import java.util.ArrayList;

//import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.AppDatabase;
import dyslexia.app.repositories.database.entities.WordEntity;
//import dyslexia.app.ui.kamus.database.DatabaseDictionary;
//import dyslexia.app.ui.kamus.model.Dictionary;

public class AnagramAlgorithm {

    private ArrayList<WordEntity> sortedDictionary;

    private Context mContext;
    //private DatabaseDictionary databaseDictionary;
    AppDatabase appDatabase;
    private ArrayList<String> stringArrayList = new ArrayList<>();


    public AnagramAlgorithm(Context context){
        mContext = context;
    }

    private void loadDatabase(){
        //databaseDictionary = new DatabaseDictionary(mContext);
        //appDatabase = new AppDatabase(get);
        sortedDictionary = (ArrayList<WordEntity>) appDatabase.wordDao().getAll();
    }

    public ArrayList<String> getAnagrams(String inputWord){
        stringArrayList.clear();
        loadDatabase();

        int wordSize = inputWord.length();
        // "rumah" > ["r","u", ...]
        //to Char Array mengalokasikan array karakter baru dari inputWord menjadi karakter,
        //mengandung urutan karakter diwakili oleh string yang ditentukan.
        char[] givenChar = inputWord.trim().toCharArray();

        //.size() = menghitung panjang array
        for (int i = 0 ; i < sortedDictionary.size(); i++){
            mainAlgorithm(i, givenChar, wordSize);
        }

        return stringArrayList;
    }

    public ArrayList<String> getAnagrams(ArrayList<String> inputWords) {
        stringArrayList.clear();
        loadDatabase();
        for (String inputWord: inputWords) {
            int wordSize = inputWord.length();
            char[] givenChar = inputWord.trim().toCharArray();
            for (int i = 0 ; i < sortedDictionary.size(); i++){
                mainAlgorithm(i, givenChar, wordSize);
            }
        }
        return stringArrayList;
    }

    private void mainAlgorithm(int index , char[] unresolvedInputWordChar, int wordSize) {

        WordEntity availableWord = sortedDictionary.get(index);
        char[] searchWordChars = availableWord.getWord().toCharArray();

       //Mengembalikan True jika set lain berisi set ini
        if (AnagramHelper.isSubset(unresolvedInputWordChar, searchWordChars)){
            char[] newChar = AnagramHelper.getDifference(unresolvedInputWordChar,searchWordChars);
            if (newChar.length == 0){
                //                Log.d("bbb", "mainAlgorithm: " + availableWord.getWord());
                if (availableWord.getWord().length() == wordSize){
                    stringArrayList.add(availableWord.getWord());
                }
            }
        }
    }
}
