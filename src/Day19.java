import Helpers.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
    public static void main(String[] args) { new Day19().run(); }
    final List<String> data = Reader.readLinesToStringArray("day19.txt");
    final Map<Integer, String> rules = new HashMap<>();
    List<String> words = new ArrayList<>();

    public void run(){
        parseRulesAndWords();
        String regularExpression = getRegex();
        System.out.println("Task A: " + words.stream()
                .filter(a -> a.matches(regularExpression))
                .count());

        System.out.println("Task B: " + getNumber());
    }

    private void parseRulesAndWords() {
        data.stream().filter(a -> a.contains(":"))
                .forEach(a -> {
                    String[] parts = a.split(": ");
                    rules.put(Integer.parseInt(parts[0]), parts[1]);
                });
        data.stream().filter(a -> !a.contains(":") && a.length()>1)
                .forEach(words::add);
    }

    private String getRegex() {
        String regex = rules.get(0);
        while (!regex.matches("^[a-z \"\\|()]+$")) {
            regex = getRegular(regex, "\\d+");
        }
        return  regex.replaceAll("[ \"]", "");
    }

    private long getNumber() {
        rules.put(8, "42 | 42 8");
        rules.put(11, "42 31 | 42 11 31");

        String regex = rules.get(0);
        long prev = 0, count = 0;
        while (!(count > 0 && count == prev)) {
            prev = count;
            regex = getRegular(regex, "[0-9]+");
            String pattern = regex.replaceAll("([ \"])|42|31", "");
            count = words.stream().filter(a -> a.matches(pattern)).count();
        }
        return count;
    }

    private String getRegular(String regex, String s) {
        StringBuilder sb = new StringBuilder();
        for (String part : regex.split(" ")) {
            if (part.matches(s)) {
                sb.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
            } else {
                sb.append(part).append(' ');
            }
        }
        return sb.toString();
    }
}