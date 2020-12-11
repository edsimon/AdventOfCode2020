package Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Reader {

    static public char[][] getCharMatrix(String filepath){
        ArrayList<String> data = readLinesToStringArray(filepath);
        char[][] matrix = new char[data.size()][data.get(0).length()];

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length(); j++){
                matrix[i][j] = data.get(i).charAt(j);
            }
        }
        return matrix;
    }

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
