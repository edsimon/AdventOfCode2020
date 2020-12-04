package Helpers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static ArrayList<String> parseString(String pattern, String searchString){
        ArrayList<String> arr = new ArrayList<>();
        Matcher matcher = Pattern.compile(pattern).matcher(searchString);
        while (matcher.find()) arr.add(matcher.group());
        return arr;
    }
    public static String parseStringFirstWordWithPattern(String pattern, String searchString){
        ArrayList<String> arr = new ArrayList<>();
        Matcher matcher = Pattern.compile(pattern).matcher(searchString);
        while (matcher.find()) arr.add(matcher.group());
        if(arr.size() == 0) return "";
        return arr.get(0);
    }
    public static int parseStringFirstInt(String searchString){
        ArrayList<String> arr = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(searchString);
        while (matcher.find()) arr.add(matcher.group());
        return Integer.parseInt(arr.get(0));
    }
    public static String parseStringFirstWord(String searchString){
        ArrayList<String> arr = new ArrayList<>();
        Matcher matcher = Pattern.compile("[a-zA-Z]+").matcher(searchString);
        while (matcher.find()) arr.add(matcher.group());
        if(arr.size() == 0) return "";
        return arr.get(0);
    }
}
