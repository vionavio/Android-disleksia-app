package dyslexia.app.perbaikanKata.ReplaceLetter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class ReplaceLetter {
    //ArrayList digunakan untuk membuat array dinamis yang berisi objek.
    // ArrayList yang menampung SimilarLetter
    //

    private static ArrayList<SimiliarLetter> similarList = new ArrayList<>();  // mengistansiasi objek dengan menyiapkan tempat[]
    private static ArrayList<Character> similarKeys = new ArrayList<>();
    private static ArrayList<ShuffleWord> shuffleWordArrayList = new ArrayList<>();
    private static ArrayList<String> shuffledWords = new ArrayList<>();

    //
    private static ArrayList<ArrayList<Boolean>> possibilities = new ArrayList<>();
    private static String TAG = "ReplaceLetter";

    public static ArrayList<String> generateWords(String mainWord) {
        shuffleWordArrayList.clear();
        shuffledWords.clear();
        possibilities.clear();
        Log.e(TAG, "shuffleWordArrayList: " + shuffleWordArrayList);
        Log.e(TAG, "shuffledWords: " + shuffledWords);
        prepare();

        // mengambil informasi huruf yang diganti berdasarkan index keberapa dan huruf penggantinya apa
        // berdasarkan huruf yang termasuk ke dalam similarKeys
        //length menghiting jumlah text
        for (int i = 0; i < mainWord.length(); i++) {
            //mendeteksi huruf yang termasuk similiar key
            if (similarKeys.contains(mainWord.charAt(i))) {
            //memasukkan sebuah value kedalam objek suffleWordArrayList
                //menyimpan index, huruf awal dan huruf akhir

                ShuffleWord shuffleWord = new ShuffleWord( i, mainWord.charAt(i), getEqualCharacterSimilar(mainWord.charAt(i)));
                shuffleWordArrayList.add(shuffleWord);

                // [ {1, u , n}, {1, m , w} ]
            }
        }

        // membuat kombinasi kemungkinan false true dari huruf yang tersedia
        generatePossibilities();

        generateShuffleWords(mainWord);
        Log.d(TAG, "generateWords: " + shuffledWords);
        return shuffledWords;
    }

    private static void prepare() {
        similarList.add(new SimiliarLetter('a', 'e'));
        similarList.add(new SimiliarLetter('e', 'a'));
        similarList.add(new SimiliarLetter('u', 'n'));
        similarList.add(new SimiliarLetter('m', 'w'));
        //similarList.add(new SimiliarLetter('m', 'n'));
        similarList.add(new SimiliarLetter('w', 'm'));
        similarList.add(new SimiliarLetter('n', 'u'));
        //similarList.add(new SimiliarLetter('n', 'm'));
        similarList.add(new SimiliarLetter('b', 'd'));
        similarList.add(new SimiliarLetter('d', 'b'));
        similarList.add(new SimiliarLetter('p', 'q'));
        similarList.add(new SimiliarLetter('q', 'p'));

        //variabel similiar keys mengambil letter key dari similar list

        for (SimiliarLetter similar : similarList) {
            similarKeys.add(similar.getLetterKey());
        }
    }

    private static Character getEqualCharacterSimilar(Character character) {
        Character foundCharacter = ' ';
        for (SimiliarLetter similar : similarList) {
            if (similar.getLetterKey().equals(character)) {
                foundCharacter = similar.getLetterEquation();
            }
        }
        return foundCharacter;
    }

    //permutasi , penyusunan kembali suatu objek dalam urutan yang berbeda dari urutan semula
    private static void generatePossibilities() {
        final int n = shuffleWordArrayList.size();
        for (int i = 0; i < Math.pow(2, n); i++) {
            // 2 ^ 3=
            StringBuilder binary = new StringBuilder(Integer.toBinaryString(i));
                   // nilai binary dari 1-7
            while (binary.length() < n)
                //nilai yang kurang dari 3 ditambah 0 dibelakangnya
                //menambah huruf didepanya
                binary.insert(0, "0");
            // char[] karakter di dalam array
            //   misal 7=111 menjadi ['1','1','1']
            char[] chars = binary.toString().toCharArray();

            //instansiasi yang panjangnya n = 0,1,2 = 3
//            boolean[] boolArray = new boolean[n];
//
//            for (int j = 0; j < chars.length; j++) {
//                               //  jika chars[0] isinya 0 berarti false, jika 1 berarti true
//                boolArray[j] = chars[j] == '1';
//            }

            //membuat variabel baru bertipe arraylist yang isinya boolean
//            ArrayList<Boolean> boolArray2 = new ArrayList<>();
//            for (Boolean value : boolArray) {
//                boolArray2.add(value);
//            }

            //membuat variabel boolarray dengan tipe arraylist yang isinya boolean, kemudian diinisalisasi dengan panjang n
            // membuat arraylist yang lebar/wadah 3 [ , , ] , membuat objek boolArray
            ArrayList<Boolean> boolArray = new ArrayList<>(n);
            for(int j = 0; j < chars.length; j++)
            {
                boolArray.add(j, chars[j] == '1');
            }

            possibilities.add(boolArray);
        }
        Log.d(TAG, "generatePossibilities: " + possibilities);
    }

    // mengkombinaasikan kemungkinan permutasi dengan objek suffleWord
    // untuk membuat kata baru berdasarkan huruf yang dianggap sama
    // jika kondisi permutasi adalah true, maka huruf dengan index dari sufflleWord akan diganti dengan letter equal
    // jika kondisi permutasi adalah false, maka huruf tersebut tidak diubah
    // selanjutnya, kata dari hasil kombinasi dan permutasi tersebut, dikumpulkan dalam satu array
    // yang kemudian array tersebut dikembalikan keluar kelas. menurut baris kode yang memanggilnya
    // dengan type array list of string
    private static void generateShuffleWords(String mainWord) {
        for (ArrayList<Boolean> possibilityList : possibilities) {
            StringBuilder newWord = new StringBuilder(mainWord);
                                                    // ArrayList<Boolean>
            Iterator<Boolean> possibilityIterator = possibilityList.iterator();
                                                    // ArrayLIst<ShuffleWord>
            Iterator<ShuffleWord> shuffleWordIterator = shuffleWordArrayList.iterator();
                    // false | true                 {index, key, equation}
            while (possibilityIterator.hasNext() && shuffleWordIterator.hasNext()) {
                Boolean isEqual = possibilityIterator.next();
                ShuffleWord shuffleWord = shuffleWordIterator.next();
                if (isEqual) {
                    newWord.setCharAt(shuffleWord.getIndex(), shuffleWord.getWord_equation());
                }
            }
            shuffledWords.add(String.valueOf(newWord));
        }
    }
}
