import Helpers.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class Day18{
    public static void main(String[] args) { new Day18().run(); }
    List<String> data = Reader.readLinesToStringArray("day18.txt");
    private Stack<Equation> stack;

    private void run() {
        stack = new Stack<>();
        System.out.println("Task A: "+ data.stream().map(s -> parseEq(s, false))
                .mapToLong(a -> a.evaluate(false))
                .sum());

        stack = new Stack<>();
        System.out.println("Task B: "+ data.stream().map(s -> parseEq(s, true))
                .mapToLong(a -> a.evaluate(true))
                .sum());
    }
    private Equation parseEq(String input, boolean partTwo) {
        stack.push(new Equation());
        Arrays.stream(input.split(" ")).forEach(row -> parseWord(row, partTwo));
        return stack.pop();
    }

    private void parseWord(String word, boolean partTwo) {
        String open = word.replaceAll("\\(", "");
        String close = word.replaceAll("\\)", "");
        String bracket = open.replaceAll("\\)", "");

        IntStream.range(0, word.length()-open.length())
                .forEach(a -> stack.push(new Equation()));
        stack.peek().add(bracket);
        IntStream.range(0, word.length()-close.length())
                .mapToLong(a -> stack.pop().evaluate(partTwo))
                .forEach(a -> stack.peek().add(a));
    }
    class Equation {
        private List<String> sequence = new ArrayList<>();
        public long evaluate(boolean partTwo) { return partTwo ? evaluatePartTwo() : evaluatePartOne(); }
        public void add(String inputString) { sequence.add(inputString); }
        public void add(long result) { sequence.add("" + result); }

        private long evaluatePartOne() {
            long result = 0L;
            for (int x=0; x<sequence.size(); x=x+2) {
                int operand = Integer.parseInt(sequence.get(x));
                char operator = (x==0) ? '+' : sequence.get(x-1).charAt(0);
                if (operator == '+') result += operand;
                else if (operator == '*') result *= operand;
            }
            return result;
        }

        private long evaluatePartTwo() {
            List<String> ints;

            while (sequence.contains("+")) {
                ints = new ArrayList<>();
                for (int x = 0; x < sequence.size(); x++) {
                    String current = sequence.get(x);
                    if (current.equals("*")) {
                        ints.add(sequence.get(x - 1));
                    } else if (current.equals("+")) {
                        String previous = sequence.get(x-1);
                        String next = x == sequence.size() - 1 ? "0" : sequence.get(x + 1);
                        ints.add("" + (Long.parseLong(previous) + Long.parseLong(next)));
                        if (x<sequence.size()-2) {
                            ints.addAll(sequence.subList(x+2, sequence.size()));
                        }
                        break;
                    } else {
                        String next = sequence.get(x+1);
                        if (!next.equals("*") && !next.equals("+")) {
                            ints.add(current);
                        }
                    }
                }
                sequence = ints;
            }
            return sequence.stream().filter(s -> !s.equals("*"))
                    .mapToLong(Long::parseLong)
                    .reduce(1L, (a, b) -> a*b);
        }
    }
}
