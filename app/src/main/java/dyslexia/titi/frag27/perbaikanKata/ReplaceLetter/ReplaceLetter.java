package dyslexia.titi.frag27.perbaikanKata.ReplaceLetter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class ReplaceLetter {
    private static ArrayList<SimiliarLetter> similarList = new ArrayList<>();
    private static ArrayList<Character> similarKeys = new ArrayList<>();
    private static ArrayList<ShuffleWord> shuffleWordArrayList = new ArrayList<>();
    private static ArrayList<String> shuffledWords = new ArrayList<>();
    private static ArrayList<ArrayList<Boolean>> possibilities = new ArrayList<>();
    private static String TAG = "ReplaceLetter";

    public static ArrayList<String> generateWords(String mainWord) {
        shuffleWordArrayList.clear();
        shuffledWords.clear();
        possibilities.clear();
        Log.e(TAG, "shuffleWordArrayList: " + shuffleWordArrayList);
        Log.e(TAG, "shuffledWords: " + shuffledWords);
        prepare();
        for (int i = 0; i < mainWord.length(); i++) {
            if (similarKeys.contains(mainWord.charAt(i))) {
                shuffleWordArrayList.add(new ShuffleWord(
                        i,
                        mainWord.charAt(i),
                        getMaskCharacterFromSimilar(mainWord.charAt(i))
                ));
            }
        }
        generatePossibilities();
        generateShuffleWords(mainWord);
        Log.d(TAG, "generateWords: " + shuffledWords);
        return shuffledWords;
    }

    private static void prepare() {
        similarList.add(new SimiliarLetter('a', 'e'));
        similarList.add(new SimiliarLetter('u', 'n'));
        similarList.add(new SimiliarLetter('m', 'w'));
        similarList.add(new SimiliarLetter('n', 'u'));

        for (SimiliarLetter similar : similarList) {
            similarKeys.add(similar.getLetterKey());
        }
    }

    private static Character getMaskCharacterFromSimilar(Character character) {
        Character foundCharacter = ' ';
        for (SimiliarLetter similar : similarList) {
            if (similar.getLetterKey().equals(character)) {
                foundCharacter = similar.getLetterEquation();
            }
        }
        return foundCharacter;
    }

    private static void generatePossibilities() {
        final int n = shuffleWordArrayList.size();
        for (int i = 0; i < Math.pow(2, n); i++) {
            StringBuilder bin = new StringBuilder(Integer.toBinaryString(i));
            while (bin.length() < n)
                bin.insert(0, "0");
            char[] chars = bin.toString().toCharArray();

            boolean[] boolArray = new boolean[n];
            for (int j = 0; j < chars.length; j++) {
                boolArray[j] = chars[j] == '1';
            }
            ArrayList<Boolean> boolArray2 = new ArrayList<>();
            for (Boolean value : boolArray) {
                boolArray2.add(value);
            }
            possibilities.add(boolArray2);
        }
        Log.d(TAG, "generatePossibilities: " + possibilities);
    }

    private static void generateShuffleWords(String mainWord) {
        for (ArrayList<Boolean> possibilityList : possibilities) {
            StringBuilder newWord = new StringBuilder(mainWord);
            Iterator<Boolean> possibilityIterator = possibilityList.iterator();
            Iterator<ShuffleWord> shuffleWordIterator = shuffleWordArrayList.iterator();
            while (possibilityIterator.hasNext() && shuffleWordIterator.hasNext()) {
                Boolean isMask = possibilityIterator.next();
                ShuffleWord shuffleWord = shuffleWordIterator.next();
                if (isMask) {
                    newWord.setCharAt(shuffleWord.getIndex(), shuffleWord.getWord_equation());
                }
            }
            shuffledWords.add(String.valueOf(newWord));
        }
    }
}
