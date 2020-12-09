import Helpers.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 {
    public static void main(String[] args) { new Day07().run(); }

    private static Map<String, List<Bag>> map;
    private void run() {
        initializeBagMap();

        System.out.println("Task A: " + map.entrySet()
                .stream()
                .map((entry) -> entry.getValue())
                .filter((value) -> (containsBag("shiny gold", value)))
                .count());

        System.out.println("Task B: " + countBags(map.get("shiny gold")));
    }

    static boolean containsBag(String bag, List<Bag> lst) {
        if (lst.isEmpty()) {
            return false;
        }
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).name.contains(bag)) {
                return true;
            }
        }
        for (int i = 0; i < lst.size(); i++) {
            boolean contains = containsBag(bag, map.get(lst.get(i).name));
            if (contains) {
                return true;
            }
        }

        return false;
    }

    static int countBags(List<Bag> lst) {
        int count = 0;
        for (int i = 0; i < lst.size(); i++) {
            Bag bag = lst.get(i);
            count += bag.amount * (1 + countBags(map.get(bag.name)));
        }
        return count;
    }

    static void initializeBagMap() {
        ArrayList<String> data = Reader.readLinesToStringArray("day07.txt");
        map = new HashMap<>();

        for (String st : data) {
            String[] s = st.split("\\b bags contain \\b");
            String key = s[0];
            List<Bag> lst = new ArrayList<>();
            String[] values = s[1].split(",");
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (value.endsWith(".")) {
                    value = value.substring(0, value.length() - 1);
                }
                if (value.endsWith("s")) {
                    value = value.substring(0, value.length() - 1);
                }
                value = value.substring(0, value.length() - 4).trim();
                if (!value.equals("no other")) {
                    int index = value.indexOf(" ");
                    int amount = Integer.parseInt(value.substring(0, index));
                    value = value.substring(index + 1);

                    lst.add(new Bag(amount, value));
                }
            }
            map.put(key, lst);
        }
    }
}

class Bag {

    int amount;
    String name;

    public Bag(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

}