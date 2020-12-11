import Helpers.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day03 {
    private ArrayList<String> map = Reader.readLinesToStringArray("day03.txt");
    public static void main(String[] args) {
        new Day03().run();
    }

    private void run() {
        List<Skier> skiers = Arrays.asList(
                new Skier(1,1,map),
                new Skier(3,1,map),
                new Skier(5,1,map),
                new Skier(7,1,map),
                new Skier(1,2,map)
        );
        
        int product = 1;
        for (Skier skier : skiers) {
            product *= findNumberOfTrees(skier);
        }
        System.out.println(product);
    }

    private int findNumberOfTrees(Skier skier){
        int numOfTrees = 0;
        for(int i = 0; i < map.size(); i++){
            if( skier.getyPos() == i){
                if(skier.hitTree()){
                    numOfTrees++;
                }
                skier.takeStep();
            }
        }
        return numOfTrees;
    }

    class Skier{
        private int xPos;
        private int yPos;
        private final int rightStep;
        private final int downStep;
        private final ArrayList<String> map;

        public Skier(int rightStep, int downStep, ArrayList<String> map) {
            this.rightStep = rightStep;
            this.downStep = downStep;
            this.map = map;
        }
        public boolean hitTree(){
            return map.get(yPos).charAt(xPos%map.get(0).length()) == '#';
        }
        public void takeStep(){
            xPos += rightStep;
            yPos += downStep;
        }
        public int getyPos() {
            return yPos;
        }
    }
}
