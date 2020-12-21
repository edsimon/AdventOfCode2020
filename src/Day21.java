import Helpers.Parser;
import Helpers.Reader;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {
    public static void main(String[] args) { new Day21().run(); }
    List<String> data = Reader.readLinesToStringArray("day21.txt");
    List<String> allIngredients = new ArrayList<>();
    Map<String, List<String>> allergens = new HashMap<>();

    private void run() {
        createAllergenMapping();
        createOneToOneMapping();

        System.out.println("Task A: " + getAlergenfreeIng().size());
        System.out.println("Task B: " + getSortedList());

    }

    private String getSortedList() {
        StringBuilder sb = new StringBuilder();
        List<String> sortedKeys = allergens.keySet().stream().sorted().collect(Collectors.toList());
        sortedKeys.forEach(a -> sb.append(allergens.get(a).get(0)).append(","));
        return sb.subSequence(0,sb.length()-1).toString();
    }

    private void createOneToOneMapping(){

        while (isKeysNotEqualValues()) {
            for (Map.Entry<String, List<String>> map : allergens.entrySet()) {
                if (map.getValue().size() == 1) {
                    String elem = map.getValue().get(0);
                    for (Map.Entry<String, List<String>> tmap : allergens.entrySet()) {
                        if (tmap.getValue().size() > 1) {
                            allergens.get(tmap.getKey()).remove(elem);
                        }
                    }
                }
            }
        }
    }

    private boolean isKeysNotEqualValues(){
        int values = allergens.values().stream().mapToInt(List::size).sum();
        int keys = allergens.keySet().size();
        return values != keys;
    }

    private void createAllergenMapping(){
        for (String row : data) {
            String[] tmp = row.split("\\(contains");
            List<String> ingredients = Parser.parseString("\\w+", tmp[0]);
            allIngredients.addAll(ingredients);
            for(String al: Parser.parseString("\\w+",tmp[1])){
                if(!allergens.containsKey(al)){
                    allergens.put(al,ingredients);
                }
                else{
                    List<String> t = new ArrayList<>();
                    for (String s : allergens.get(al)) {
                        if(ingredients.contains(s))
                            t.add(s);
                    }
                    allergens.put(al, t);
                }
            }
        }
    }

    private List<String> getAlergenfreeIng(){
        List<String> noAllergerns = new ArrayList<>();
        List<String> tmpAllergens = new ArrayList<>();
        allergens.values().forEach(tmpAllergens::addAll);
        for (String ingred : allIngredients) {
            if(!tmpAllergens.contains(ingred)){
                noAllergerns.add(ingred);
            }
        }
        return noAllergerns;
    }
}
