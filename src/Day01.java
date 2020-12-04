import Helpers.Reader;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Day01 {
    private static ArrayList<Integer> lines = Reader.readLinesToIntArray("day01.txt");

    public static void main(String[] args) {
        new Day01().run();
    }

    private void run() {
        Predicate<Integer> sumOfTwo = a -> lines.stream().anyMatch( b-> a+b == 2020 );
        Predicate<Integer> sumOfThree = a -> lines.stream().anyMatch( b-> lines.stream().anyMatch( c -> a+b+c ==2020));

        System.out.println("First task gives: " +
                lines.stream()
                     .filter(sumOfTwo)
                     .reduce(1, (a,b) -> a*b));

        System.out.println("Second task gives: " +
                lines.stream()
                     .filter(sumOfThree)
                     .reduce(1, (a,b) -> a*b));
    }
}