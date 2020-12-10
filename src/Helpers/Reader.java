package Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Reader {

    static public ArrayList<String> readLinesToStringArray(String filepath){
        ArrayList<String> arr = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get("data/" + filepath));
            while (br.ready()) arr.add(br.readLine());
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public ArrayList<Integer> readLinesToIntArray(String filepath){
        ArrayList<Integer> arr = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get("data/" + filepath));
            while (br.ready()){
                arr.add(Integer.parseInt(br.readLine()));
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public ArrayList<Long> readLinesToLongArray(String filepath){
        ArrayList<Long> arr = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get("data/" + filepath));
            while (br.ready()){
                arr.add(Long.parseLong((br.readLine())));
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
