package dyslexia.titi.frag27.perbaikanKata.ReplaceLetter;

import java.util.ArrayList;
import java.util.Iterator;

public class MainReplaceLetter {
    private static String mainWord;
    private static ArrayList<SimiliarLetter> similarList = new ArrayList<>();
    private static ArrayList<Character> similarKeys = new ArrayList<>();
    private static ArrayList<ShuffleWord> shuffleWordArrayList = new ArrayList<>();
    private static ArrayList<String> shuffledWords = new ArrayList<>();
    private static ArrayList<ArrayList<Boolean>> possibilities = new ArrayList<>();

    public static ArrayList<String> generateWords(String string) {
        mainWord = string;
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
        generateShuffleWords();
        return shuffledWords;
    }

    private static void prepare() {
        similarList.add(new SimiliarLetter('a', 'e'));
        similarList.add(new SimiliarLetter('u', 'n'));
        similarList.add(new SimiliarLetter('m', 'w'));
        similarList.add(new SimiliarLetter('n', 'u'));

        for (SimiliarLetter similiar : similarList) {
            similarKeys.add(similiar.getLetterKey());
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
    }

    private static void generateShuffleWords() {
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
