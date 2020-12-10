import Helpers.Reader;

import java.util.ArrayList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) { new Day09().run(); }
    ArrayList<Long> data = Reader.readLinesToLongArray("day09.txt");
    int preamble = 25;
    int index = preamble-1;

    private void run() {
        long number = data.subList(preamble+1,data.size()).stream()
            .filter(this::isNotASum)
            .findFirst().get();

        System.out.println("Task A: " + number);
        System.out.println("Task B: " + getContiguousSum(number));
    }

    public boolean isNotASum(long number){
        List<Long> subData = data.subList((++index)-preamble,index+1);
        for(long num1 : subData){
            for(long num2 : subData){
                if((num1 + num2) == number) return false;
            }
        } return true;
    }

    public long getContiguousSum(long number){
        for(int i = 0; i < data.size(); i++){
            int sum = 0;
            for(int j = i+1; j < data.size(); j++){
                sum += data.get(j);
                if(sum > number) break;
                if(sum == number) {
                    long min = data.subList(i+1,j+1).stream().min(Long::compareTo).get();
                    long max = data.subList(i+1,j+1).stream().max(Long::compareTo).get();
                    return min + max;
                }
            }
        }return 0;
    }
}