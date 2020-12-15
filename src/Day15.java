import java.util.*;

public class Day15 {
    public static void main(String[] args) { new Day15().run(); }

    private void run() {
        List<Integer> startingNumbers = Arrays.asList(14,1,17,0,3,20);
        System.out.println("Task A: " + getLastSpokenAtIndex(startingNumbers, 2020));
        System.out.println("Task B: " + getLastSpokenAtIndex(startingNumbers, 30000000));
    }

    private int getLastSpokenAtIndex(List<Integer> startingNumbers, int index) {
        Map<Integer, List<Integer>> spoken = new HashMap<>();
        int lastSpoken = 0;

        for(int i = 1; i <= index; i++){
            if(startingNumbers.size() >= i) lastSpoken = startingNumbers.get(i-1);
            else if(spoken.get(lastSpoken).size() == 1) lastSpoken = 0;
            else{
                List<Integer> li = spoken.get(lastSpoken);
                lastSpoken = li.get(li.size()-1) - li.get(li.size()-2);
            }
            if (!spoken.containsKey(lastSpoken)) spoken.put(lastSpoken, new ArrayList<>());
            spoken.get(lastSpoken).add(i);
        }
        return lastSpoken;
    }
}
