package dyslexia.titi.frag27.perbaikanKata.anagram;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Kamus;

public class AnagramAlgoritm {

    private ArrayList<Kamus> sortedDictionary;

    private ArrayList<String> kamus = new ArrayList<>();
    private int wordSize;
    private Context mContext;
    private DatabaseAdapter databaseAdapter;

    public AnagramAlgoritm(Context context){
        mContext = context;
    }

    public  void loadDatabase(){
        databaseAdapter = new DatabaseAdapter(mContext);
        sortedDictionary = (ArrayList<Kamus>) databaseAdapter.retrieveKamus("all");

    }

    public ArrayList<String> getAnagrams(String inputWord){
        loadDatabase();

        wordSize = inputWord.length();
        // "rumah" > ["r","u", ...]
        char[] givenChar = inputWord.trim().toCharArray();
        for (int i = 0 ; i < sortedDictionary.size(); i++){
            mainAlgoritm(i, givenChar);
        }
        return kamus;
    }

    private void mainAlgoritm(int index , char[] unresolvedInputWordChar) {

        Kamus availableWord = sortedDictionary.get(index);
        char[] searchWordChars = availableWord.getWord().toCharArray();

        if (AnagramHelper.isSubset(searchWordChars, unresolvedInputWordChar)){
            char[] newChar = AnagramHelper.getDifference(unresolvedInputWordChar,searchWordChars);
            if (newChar.length == 0){
                //                Log.d("bbb", "mainAlgorithm: " + availableWord.getWord());
                if (availableWord.getWord().length() == wordSize){
                    kamus.add(availableWord.getWord());
                }
            }
        }
    }
}
