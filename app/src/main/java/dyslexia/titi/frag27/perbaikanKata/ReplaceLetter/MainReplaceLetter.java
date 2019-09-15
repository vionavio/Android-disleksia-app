package dyslexia.titi.frag27.perbaikanKata.ReplaceLetter;

import java.util.ArrayList;
import java.util.Iterator;

public class MainReplaceLetter {
    private static String mainWord;

    // ArrayList = []
    // List      = []
    // String    = "ini string"
    // List<String> = ["ini string", "ini string lagi"]
    // ArrayList<String> = [ "mahru", "mahrn", "sdlfk" ]
    // Similar = { letterKey: "a", letterMask: "e" }
    // ArrayList<Similar> = [ { letterKey: "a", letterMask: "e" }, { letterKey: "m", letterMask: "w" } ]
    // ArrayList<ShuffleWord> = [ {}, {} ]


    private static ArrayList<SimiliarLetter> similarList = new ArrayList<>();
    private static ArrayList<Character> similarKeys = new ArrayList<>();
    private static ArrayList<ShuffleWord> shuffleWordArrayList = new ArrayList<>();
    private static ArrayList<String> shuffledWords = new ArrayList<>();
    private static ArrayList<ArrayList<Boolean>> possibilities = new ArrayList<ArrayList<Boolean>>();

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
//        System.out.println(shuffleWordArrayList);
        generateShuffleWords();
//        System.out.println(shuffleWordArrayList.size());
//        System.out.println(possibilities);
//        removeDuplicates();
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
        for (SimiliarLetter similiar : similarList) {
            if (similiar.getLetterKey().equals(character)) {
                foundCharacter = similiar.getLetterEquation();
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
        //  [false, false, false]  [[...], [...], [...]]
        for (ArrayList<Boolean> possibilityList : possibilities) {
            StringBuilder newWord = new StringBuilder(mainWord);
            Iterator<Boolean> possibilityIterator = possibilityList.iterator();
            Iterator<ShuffleWord> shuffleWordIterator = shuffleWordArrayList.iterator();

            while (possibilityIterator.hasNext() && shuffleWordIterator.hasNext()) {

                // false
                // {index: 0, key: 'm', mask: 'w'}

                Boolean isMask = possibilityIterator.next();
                ShuffleWord shuffleWord = shuffleWordIterator.next();

                if (isMask) {
                    newWord.setCharAt(shuffleWord.getIndex(), shuffleWord.getWord_equation());
                }
            }
            shuffledWords.add(String.valueOf(newWord));
//            System.out.println(shuffledWords);
        }
    }

    private static void removeDuplicates() {
        System.out.println(shuffledWords);
        System.out.println(shuffledWords.size());
//        System.out.println(uniqueShuffledWords);
//        System.out.println(uniqueShuffledWords.size());
    }
}
