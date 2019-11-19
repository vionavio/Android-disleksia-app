package dyslexia.app.utils;

import java.util.ArrayList;

public class StringUtil {
    public static ArrayList<Character> stringToArrayList(String before) {
        ArrayList<Character> characterArrayList = new ArrayList<>();
        for (char c : before.toCharArray()) {
            characterArrayList.add(c);
        }
        return characterArrayList;
    }
}
