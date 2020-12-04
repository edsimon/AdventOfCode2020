import Helpers.Parser;
import Helpers.Reader;

import java.util.ArrayList;

public class Day02 {
    public static void main(String[] args) {
        ArrayList<String> lines = Reader.readLinesToStringArray("day02.txt");
        String pattern = "\\d+|\\w+";

        System.out.println("Number of valid passwords is: " +
                lines.stream()
                        .map(a -> Parser.parseString(pattern, a))
                        .filter(Day02::passwordsPolicy)
                        .count());

        System.out.println("Number of valid official passwords is: " +
                lines.stream()
                        .map(a -> Parser.parseString(pattern, a))
                        .filter(Day02::officialPasswordsPolicy)
                        .count());
    }

    private static boolean passwordsPolicy(ArrayList<String> line){
        int low = Integer.parseInt(line.get(0));
        int high = Integer.parseInt(line.get(1));
        char controllCharacter = line.get(2).charAt(0);
        String password = line.get(3);
        long numCharsInPass = password.codePoints().filter(ch -> ch == controllCharacter).count();
        return ( (low <= numCharsInPass) && (numCharsInPass <= high) );
    }

    private static boolean officialPasswordsPolicy(ArrayList<String> line){
        int low = Integer.parseInt(line.get(0)) - 1;
        int high = Integer.parseInt(line.get(1)) - 1;
        char controllCharacter = line.get(2).charAt(0);
        String password = line.get(3);
        return (controllCharacter == password.charAt(low)) ^ (controllCharacter == password.charAt(high));
    }
}
