package com.epam.rd.autotasks.words;

import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if ((words == null)||(words.length == 0)||(sample == null)||(sample.trim().length()) == 0){
            return 0;
        }
        String pattern = sample.trim();
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        int counter = 0;
            for (int i = 0; i < words.length; i++) {
                String text = words[i];
                String t = text.trim();
                Matcher m = p.matcher(t);
                if (m.matches()) {
                    counter++;
                }
            }
         return counter;
    }

    public static String[] splitWords(String text) {
        if (null==text|| text.isEmpty()) {
            return null;
        }
        String reg = "[,.;:?! ]+";
        List<String> strings= List.of(text.split(reg));
        if(strings.isEmpty()) {
            return null;
        }
        strings = strings.stream().filter(value->!(",.;:?! ".contains(value)
        )).collect(Collectors.toList());
        return strings.toArray(new String[0]);
    }

    public static String convertPath(String path, boolean toWin) {
        if ((path == null)||(path.isEmpty())) {return null;}
        String pattern = "((.+/.+\\\\.+)|" +
                "(.+\\\\.+/.+)|(.+~.+)|(C:/.+)|" +
                "(~\\\\.+)|(~/~)|(~{2,})|(.+C:\\\\)|" +
                "(C:\\\\.+C:\\\\))";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        if (m.matches()) {return null;}
        String pattern2 = "(C:\\\\.+)";
        Pattern p_2 = Pattern.compile(pattern2);
        Matcher m_2 = p_2.matcher(path);
        if ((m_2.matches())&&(toWin)) {return path;}
        String pattern3 = "(~.+)";
        Pattern p_3 = Pattern.compile(pattern3);
        Matcher m_3 = p_3.matcher(path);
        if ((m_3.matches())&&(!toWin)) {return path;}
        if ((path.startsWith("/"))&&(toWin)){
            path = "C:" + path.replace("/","\\");
            return path;
        }
        if (((path.startsWith("/"))||(path.startsWith("~")))&&(!toWin)){
            return path;
        }
        if (((path.startsWith("C:\\"))||(path == "file.txt"))&&(toWin)){
            return path;
        }
        if ((!toWin)){
            path = path.replace("C:\\User","~");
            path = path.replace("C:\\User\\","~/");
            path = path.replace("C:\\","/");
            path = path.replace(".",".");
            path = path.replace("\\","/");
            path = path.replace("..","..");
            return path;
        }
        if ((toWin)){
            path = path.replace("~","C:\\User");
            path = path.replace(".",".");
            path = path.replace("/","\\");
            path = path.replace("..","..");
            return path;
        }
    return "path";
    }
    public static String joinWords(String[] words) {
        String result = "[";
        String elementToBeDeleted = "";
        String[] words2 = null;
        int counter = 0;
        if (words == null){return null;}
        else if (words.length == 0){return null;}
        for (int q = 0; q < words.length; q++){
            if (words[q] == ""){counter++;}
        }
        if (counter == words.length){return null;}
        if (counter == 0){
            for (int t = 0; t < words.length-1; t++){
                result = result + words[t] + ", ";
            }
            result = result + words[words.length-1] + "]";
        }
        if (counter != 0){
            if (words[words.length-1] == ""){
                for (int r = 0; r < words.length-2; r++){
                    if (words[r] != ""){
                        result = result + words[r] + ", ";}
                }
                result = result + words[words.length-2] + "]";
            }
            else {
            for (int r = 0; r < words.length-1; r++){
                if (words[r] != ""){
                    result = result + words[r] + ", ";}
            }
            result = result + words[words.length-1] + "]";
        }}
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}
