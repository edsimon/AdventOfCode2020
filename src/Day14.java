import Helpers.Parser;
import Helpers.Reader;

import java.util.*;

public class Day14 {
    public static void main(String[] args) { new Day14().run(); }
    ArrayList<String> data = Reader.readLinesToStringArray("day14.txt");
    Map<Long,Long> memory;

    private void run() {
        updateMemory(false);
        System.out.println("Task A: " + memory.values().stream().mapToLong(Long::longValue).sum());

        updateMemory(true);
        System.out.println("Task B: " + memory.values().stream().mapToLong(Long::longValue).sum());
    }

    private void updateMemory(boolean isV2) {
        memory = new HashMap<>();
        String mask = "";
        for (String row : data) {
            String firstWord = Parser.parseStringFirstWord(row);
            if(firstWord.equals("mask")) mask = row.split(" = ")[1];
            else {
                long address = Parser.parseIntegers(row).get(0);
                int value = Parser.parseIntegers(row).get(1);

                if(isV2) addMask(address,value, mask,true);
                else addMask(address, value, mask, false);
            }
        }
    }

    private void addMask(long key, long value, String mask, boolean isV2){
        StringBuilder binary = new StringBuilder(Long.toBinaryString(value));
        while (binary.length() < mask.length()) binary.insert(0, "0");
        String binaryValue = binary.toString();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mask.length(); i++) {
            if(isV2){
                if(mask.charAt(i) == 'X') sb.append('X');
                if(mask.charAt(i) == '1') sb.append('1');
                else sb.append(binaryValue.charAt(i));
            }
            else {
                if(mask.charAt(i) == 'X') sb.append(binaryValue.charAt(i));
                else sb.append(mask.charAt(i));
            }
        }
        if(isV2){
            List<String> b = new ArrayList<>();
            b.add(sb.toString());
            List<String> tmp = createAllAddresses(b);
            for (String s : tmp) memory.put(Long.parseLong(s, 2), value);
        }
        else {
            memory.put(key, Long.parseLong(sb.toString(),2));
        }
    }

    private List<String> createAllAddresses(List<String> binaries){
        for (int i = 0; i < binaries.size(); i++) {
            for (int i1 = 0; i1 < binaries.get(i).toCharArray().length; i1++) {
                if(binaries.get(i).charAt(i1) == 'X'){
                    binaries.add(binaries.get(i).replaceFirst("X","0"));
                    binaries.add(binaries.get(i).replaceFirst("X","1"));
                    binaries.remove(binaries.get(i));
                    createAllAddresses(binaries);
                }
            }
        }
        return binaries;
    }
}