package dyslexia.app.permainan.kuis;

import java.util.ArrayList;
import java.util.List;

public class WordShuffler {

    // wordShuffler metod menerima string sebagai input
    public String shuffle(String input){

        //buat objek arraylist untuk menyimpan characters dari string
        List<Character> inputArray = new ArrayList<Character>();

        //loop(acak) melalui karakter dalam string dan menambahkannya ke daftar array
        for(char character:input.toCharArray()){
            inputArray.add(character);
        }

        //StringBuilder digunakan untuk membentuk atau melakukan operasi terhadap objek string
        //buat onbject untuk membuat kata dengan huruf yang acak (our random string from)
        StringBuilder output = new StringBuilder(input.length());

        //chek ketika masih ada karakter yang belum digunakan secara acak
        // check that there are still characters unused to randomly choose
        while(inputArray.size()!=0){
            //pilih secara acak huruf dari list, dan hapus setelahnya.
            //randomly choose a letter from the list, and remove it after.
            int randomPick = (int)(Math.random()*inputArray.size());
            //menambah larik karakter pada buffer string
            output.append(inputArray.remove(randomPick));
        }

        //return the scrambled word
        String scambled = output.toString();
        return scambled;
    }

}
