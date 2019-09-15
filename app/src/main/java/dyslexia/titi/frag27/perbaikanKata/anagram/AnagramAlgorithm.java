package dyslexia.titi.frag27.perbaikanKata.anagram;

import android.content.Context;

import java.util.ArrayList;

import dyslexia.titi.frag27.kamus.database.DatabaseAdapter;
import dyslexia.titi.frag27.kamus.model.Dictionary;

public class AnagramAlgorithm {

    private ArrayList<Dictionary> sortedDictionary;

    private Context mContext;
    private DatabaseAdapter databaseAdapter;
    private ArrayList<String> stringArrayList = new ArrayList<>();


    public AnagramAlgorithm(Context context){
        mContext = context;
    }

    private void loadDatabase(){
        databaseAdapter = new DatabaseAdapter(mContext);
        sortedDictionary = (ArrayList<Dictionary>) databaseAdapter.retrieveKamus("all");
    }

    public ArrayList<String> getAnagrams(String inputWord){
        stringArrayList.clear();
        loadDatabase();

        int wordSize = inputWord.length();
        // "rumah" > ["r","u", ...]
        char[] givenChar = inputWord.trim().toCharArray();
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

        Dictionary availableWord = sortedDictionary.get(index);
        char[] searchWordChars = availableWord.getWord().toCharArray();

        if (AnagramHelper.isSubset(searchWordChars, unresolvedInputWordChar)){
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
