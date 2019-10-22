package dyslexia.app.ui.perbaikanKata.JaroWinkler;

import static java.lang.Double.isNaN;

public class JaroWinklerDistance {
    private String wordString1;
    private String wordString2;

    private String matchString1 = "";
    private String matchString2 = "";
    private int mRange =0   ;


    public double getSimilarity(String s1, String s2) {
        wordString1 = s1;
        wordString2 = s2;

        //range = Nilai dj akan dianggap benar bila sama dan tidak melebihi batas yang dinyatakan dalam persamaan
        mRange = Math.max(wordString1.length(), wordString2.length()) / 2 - 1;
        //Log.d("dddddddd", "getRange: " + mRange);
        double dw ;

        //jumlah karakter yang sama persis
        int m = getMatch(wordString1,wordString2);
        int t = 0;
        if (getMissMatch(wordString2, wordString1) > 0) {
            t = ((getMissMatch(wordString1, wordString2) / getMissMatch(wordString2, wordString1))/2);

        }

        int lengthString1 = wordString1.length();
        int lengthString2 = wordString2.length();

        double f = 0.3333;
        double mt = (double) (m - t) / m;
        if (isNaN(mt)) mt = 0.0;

        double p = 0.1;
        
        //rumus jaro untuk menghitung jarak
        double dj = f * ((double) m / lengthString1 + (double) m / lengthString2 + (double) mt);

        dw = dj + (getCommonPrefix(wordString1, wordString2) * (p * (1.0 - dj)));

        return dw;
    }

    //mencari jumlah karakter yang sama persis
    private int getMatch(String matchA, String matchB) {

        matchString1 = "";
        matchString2 = "";

        int matches = 0;

        //? : = menugaskan suatu nilai kedalam variabel berdasarkan kondisi true atau false
        //jika panjang wordString1 kurang dari wordString2 maka keluar wordstring1 jika tidak keluar wordString2
        int length = wordString1.length() < wordString2.length() ? wordString1.length() : wordString2.length();

        for (int i = 0; i < length; i++) {
            //Look backward
            int counter = 0; //mRange
            while (counter <= mRange && i >= 0 && counter <= i) {
                //charAt digunakan untuk mengambil karakter pada sebuah String sesuai index yang diinginkan.
                if (wordString1.charAt(i) == wordString2.charAt(i - counter)) {
                    matches++;
                    matchString1 = matchString1 + wordString1.charAt(i);
                    matchString2 = matchString2 + wordString2.charAt(i);
                }
                counter++;
            }

            //Look forward
            counter = 1;  //mRange
            while (counter <= mRange && i < wordString2.length() && counter + i < wordString2.length()) {
                if (wordString1.charAt(i) == wordString2.charAt(i + counter)) {
                    matches++;
                    matchString1 = matchString1 + wordString1.charAt(i);
                    matchString2 = matchString2 + wordString2.charAt(i);
                }
                counter++;
            }
        }
        return matches;
    }

    //jika terdapat transpose
    private int getMissMatch(String s1, String s2) {
        int transPositions = 0;

        for (int i = 0; i < matchString1.length(); i++) {
            //Look Backward
            int counter = 0; //mRange
            while (counter <= mRange && i >= 0 && counter <= i) {
                if (matchString1.charAt(i) == matchString2.charAt(i - counter) && counter > 0) {
                    transPositions++;
                }
                counter++;
            }

            //Look forward
            counter = 1; //mRange
            while (counter <= mRange && i < matchString2.length() && (counter + i) < matchString2.length()) {
                if (matchString1.charAt(i) == matchString2.charAt(i + counter) && counter > 0) {
                    transPositions++;
                }
                counter++;
            }
        }
        return transPositions;
    }

    // L = panjang awalan yaitu panjang karakter yang sama dari string yang dibandingkan sampai ditemukannya ketidaksamaan
    private int getCommonPrefix(String wordString1, String wordString2) {

        //
        int length = wordString1.length() < wordString2.length() ? wordString1.length() : wordString2.length();

        int length2 = 0;
        if (length>4){
            length = 4;
             length2 = length;
        }
        int commonPrefix = 0;
        for (int i = 0; i < length2; i++) {
            if (wordString1.charAt(i) == wordString2.charAt(i)) commonPrefix++;
        }
        return commonPrefix;
    }
}
