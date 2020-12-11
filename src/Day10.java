import Helpers.Reader;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) { new Day10().run(); }
    ArrayList<Long> data = Reader.readLinesToLongArray("day10.txt");
    Map<Long,List<Long>> path = new HashMap<>();
    Map<Long, Long> values = new HashMap<>();
    List<Long> diff = Arrays.asList(1L,1L,1L);

    private void run() {
        data.stream().sorted().forEach(this::countJolts);
        System.out.println("Task A: " + diff.stream().reduce(1L, (a, b) -> a * b));
        data.stream().sorted().forEach(n -> path.put(n,countValidJolts(n)));

        data.add(0L);
        data.add(data.stream().max(Long::compareTo).get()+3);
        List<Long> newData = data.stream().sorted((f1, f2) -> Long.compare(f2, f1)).collect(Collectors.toList());
        System.out.println("Task B: " + getNumberOfPaths(newData));
    }
    private void countJolts(long num){
        data.stream().sorted().filter(n -> (n > num)).peek(n -> {
            if (n > num) diff.set((int)(n-num)-1, diff.get((int)(n-num)-1)+1);
            else diff.set(2, diff.get(2)+1);
        }).findFirst();
    }
    private long getNumberOfPaths(List<Long> data){
        values.put(data.get(0), 1L);
        data.subList(1, data.size()).forEach(num -> {
            values.put(num,countValidJolts(num).stream()
                    .map(n -> values.get(n))
                    .reduce(0L, Long::sum)); });
        return values.get(data.get(data.size()-1));
    }
    private List<Long> countValidJolts(long num){
        return data.stream().sorted().filter(n -> (num < n) && (n < num+4)).collect(Collectors.toList());
    }
}