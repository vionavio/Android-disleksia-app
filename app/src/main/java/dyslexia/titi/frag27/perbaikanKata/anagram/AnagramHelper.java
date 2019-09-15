package dyslexia.titi.frag27.perbaikanKata.anagram;

import java.util.ArrayList;
import java.util.List;

public class AnagramHelper {
    public static boolean isSubset(char[] charArray1, char[] charArray2) {
        if (charArray1.length > charArray2.length) {
            return false;
        }
        List<Character> charList1 = charArrayToList(charArray1);
        List<Character> charList2 = charArrayToList(charArray2);

        for (Character ch : charList1) {
            if (charList2.contains(ch)) {
                charList2.remove(ch);
            } else {
                return false;
            }
        }
        return true;
    }


    public static List<Character> charArrayToList(char[] array) {
        if (array == null)
            return null;
        else {
            List<Character> charList = new ArrayList<>();
            for (char ch : array) {
                charList.add(ch);
            }
            return charList;
        }
    }


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
        List<Character> list1 = charArrayToList(charArray1);
        List<Character> list2 = charArrayToList(charArray2);
        for (Character ch : list2) {
            list1.remove(ch);
        }
        return toCharArray(list1);
    }
}