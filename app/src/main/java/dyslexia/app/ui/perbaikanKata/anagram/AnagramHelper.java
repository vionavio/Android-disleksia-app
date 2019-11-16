package dyslexia.app.ui.perbaikanKata.anagram;

import java.util.ArrayList;
import java.util.List;

//class yang berisi method untuk anagram algorithm
public class AnagramHelper {

    //mencari bagian karakter dari char1 di char2
    public static boolean isSubset(char[] charArray1, char[] charArray2) {
        if (charArray1.length > charArray2.length) {
            return false;
        }

        //list memiliki variabel yang menampung karakter
        List<Character> charList1 = charArrayToList(charArray1);
        List<Character> charList2 = charArrayToList(charArray2);

        for (Character character : charList1) {
            if (charList2.contains(character)) //memeriksa apakah karakter dari char1 ada pada char2
            {
                charList2.remove(character); //menghapus nilai pada indeks tertentu
            } else {
                return false;
            }
        }

        return true;
    }


    //mengubah huruf didalam array menjadi daftar(list)
    public static List<Character> charArrayToList(char[] array) {
        if (array == null)
            return null;
        else {
            List<Character> charList = new ArrayList<>();
            for (char character : array) {
                charList.add(character);
            }
            return charList;
        }
    }


    //mengalokasikan array karakter baru, yang panjangnya sesuai dengan panjang string yang ditentukan
    // dan isinya diinisialisasi mengandung urutan karakter diwakili oleh string yang ditentukan.
    private static char[] toCharArray(List<Character> list) {
        List<Character> charList = new ArrayList<>();
        if (list == null || charList.isEmpty()) {
            return new char[0];
        }

        char[] charArray = new char[list.size()];
        for (int index = 0; index < list.size(); index++) {
            charArray[index] = list.get(index);
        }
        return charArray;
    }

    public static char[] getDifference(char[] charArray1, char[] charArray2) {
        List<Character> list1 = charArrayToList(charArray1); //menampung char word1
        List<Character> list2 = charArrayToList(charArray2);
        for (Character character : list2) {
            list1.remove(character);
        }

        return toCharArray(list1);
    }
}