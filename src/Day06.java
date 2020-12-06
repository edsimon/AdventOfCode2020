import Helpers.Reader;
import java.util.*;

public class Day06 {
    public static void main(String[] args) { new Day06().run(); }

    ArrayList<String> data = Reader.readLinesToStringArray("day06.txt");
    private void run() {
        List<List<String>> groups = getGroups(data);

        System.out.println("Task A: " + groups.stream()
                .map(Day06::getAnswers)
                .map(Set::size)
                .mapToInt(Integer::intValue)
                .sum());

        System.out.println("Task B: " + groups.stream()
                .map(Day06::getNumberOfSameAnswers)
                .mapToInt(Integer::intValue)
                .sum());
    }

    public static Set<String> getAnswers(List<String> group){
        Set<String> answers = new HashSet<>();
        group.forEach(a -> Collections.addAll(answers, a.split("")));
        return answers;
    }

    public static int getNumberOfSameAnswers(List<String> group){
        return (int)getAnswers(group).stream()
                .map(ans -> group.stream()
                        .filter(b -> b.contains(ans))
                        .count())
                .filter(num -> num == group.size())
                .count();
    }
    private List<List<String>> getGroups(ArrayList<String> data){
        List<List<String>> groups = new ArrayList<>();
        List<String> group = new ArrayList<>();
        for (String answers : data) {
            if(answers.equals("")){
                groups.add(group);
                group = new ArrayList<>();
            }else{
                group.add(answers);
            }
        }
        groups.add(group);
        return groups;
    }
}