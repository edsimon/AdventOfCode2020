import Helpers.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 {
    public static void main(String[] args) { new Day05().run(); }

    private void run() {
        List<Integer> seats = IntStream.range(0, 128).boxed().collect(Collectors.toList());
        ArrayList<String> passangers = Reader.readLinesToStringArray("day05.txt");
        List<Integer> ids = passangers.stream().map(a -> findSeatId(a, seats, 0 )).collect(Collectors.toList());
        System.out.println("Task A: " + ids.stream().max(Integer::compareTo).get());

        List<Integer> seatIds = IntStream.range(91, 929).boxed().collect(Collectors.toList());

        System.out.println("Task B: " + seatIds.stream()
                .map(a -> {
                    if (ids.contains(a)) return 0;
                    return a; })
                .max(Integer::compareTo).get());
    }

    public int findSeatId(String code, List<Integer> seats, int sum) {
        if(code.length() == 3){
            sum = seats.get(0)*8;
            seats = IntStream.range(0, 8).boxed().collect(Collectors.toList());
        }
        if (code.length() > 0) {
            char letter = code.charAt(0);
            code = code.substring(1);
            if (letter == 'B' || letter == 'R') {
                return findSeatId(code, seats.subList(seats.size() / 2, seats.size()), sum);
            } else if (letter == 'F' || letter == 'L') {
                return findSeatId(code, seats.subList(0, seats.size() / 2), sum);
            }
        }
        return sum + seats.get(0);
    }
}
