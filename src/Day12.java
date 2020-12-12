import Helpers.Parser;
import Helpers.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Day12 {
    public static void main(String[] args) {
        ArrayList<String> data = Reader.readLinesToStringArray("day12.txt");
        List<Ship> ships = Arrays.asList(
                new Ship(false),
                new Ship(true));
        ships.forEach(ship -> data.forEach(ship::move));
        ships.forEach(ship -> System.out.println(ship.getTaxiDistance()));
    }

    static class Ship{
        private final int[] cord = {0,0};
        private final int[] waypoint = {10,1};
        private int angle = 0;
        private final boolean isPart2;

        Ship(boolean isPart2){
            this.isPart2 = isPart2;
        }
        public void move(String command){
            String action = Parser.parseStringFirstWord(command);
            int value = Parser.parseStringFirstInt(command);

            BiConsumer<Integer, Integer> takeAction = (a,b) -> {
                if(isPart2) waypoint[a] += b*value;
                else cord[a] += b*value; };
            Consumer<Integer> changeAngle = a -> {
                angle += a*value;
                if(isPart2) rotateRelative(a*Math.toRadians(value)); };

            if(action.equals("N")) takeAction.accept(1,1);
            if(action.equals("E")) takeAction.accept(0,1);
            if(action.equals("S")) takeAction.accept(1,-1);
            if(action.equals("W")) takeAction.accept(0,-1);
            if(action.equals("L")) changeAngle.accept(1);
            if(action.equals("R")) changeAngle.accept(-1);
            if(action.equals("F")) {
                cord[0] += isPart2 ? value*waypoint[0] : value*Math.cos(Math.toRadians(angle));
                cord[1] += isPart2 ? value*waypoint[1] : value*Math.sin(Math.toRadians(angle));
            }
        }
        private void rotateRelative(double angle) {
            int x = waypoint[0];
            int y = waypoint[1];
            waypoint[0] = (int) Math.round(x * Math.cos(angle) - y * Math.sin(angle));
            waypoint[1] = (int) Math.round(x * Math.sin(angle) + y * Math.cos(angle));
        }
        public int getTaxiDistance(){
            return Math.abs(cord[0]) + Math.abs(cord[1]);
        }
    }
}