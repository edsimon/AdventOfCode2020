import Helpers.Reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day08 {
    public static void main(String[] args) { new Day08().run(); }
    ArrayList<String> data = Reader.readLinesToStringArray("day08.txt");

    private void run() {
        System.out.println("Task A: "+getAccumulator(false));

        System.out.println("Task B: " +
                getAllAccumulators().stream()
                        .max(Integer::compareTo).get());
    }

    private ArrayList<Integer> getAllAccumulators() {
        ArrayList<Integer> endings = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            String row = data.get(i);
            changeOneCode(i, row);
            endings.add(getAccumulator(true));
            data.set(i, row);
        }
        return endings;
    }

    private void changeOneCode(int i, String row) {
        String code = row.split(" ")[0];
        int change = Integer.parseInt(row.split(" ")[1]);

        if(code.equals("nop")){
            data.set(i, "jmp " + change);
        }else if(code.equals("jmp")){
            data.set(i, "nop " + change);
        }
    }

    private int getAccumulator(boolean normalTermination) {
        Set<String> actionsTaken = new HashSet<>();
        String lastAction = "", row;
        int pointer = 0, acc = 0;

        while(true) {
            actionsTaken.add(lastAction);
            if(data.size() == pointer) return acc;

            row = data.get(pointer);
            String code = row.split(" ")[0];
            lastAction = pointer + " " + row;

            if(actionsTaken.contains(lastAction)) break;

            int change = Integer.parseInt(row.split(" ")[1]);
            if(code.equals("acc")){
                acc+= change;
                pointer++;
            }else if(code.equals("jmp")){
                pointer += change;
            }
            else{
                pointer++;
            }
        }
        if(normalTermination) return 0;
        else return acc;
    }
}
