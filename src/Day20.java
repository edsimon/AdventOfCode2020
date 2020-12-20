import Helpers.Parser;
import Helpers.Reader;

import java.util.ArrayList;
import java.util.List;

public class Day20 {
    public static void main(String[] args) { new Day20().run(); }
    List<String> data = Reader.readLinesToStringArray("day20.txt");
    List<Image> images = new ArrayList<>();

    private void run() {
        parseInput();
        for (Image image : images) {
            for (Image image1 : images) {
                image.checkSides(image1);
            }
        }
        int min = images.stream().mapToInt(Image::getMatchingSides).min().getAsInt();
        System.out.println("Task A: " + images.stream().filter(a -> min == a.getMatchingSides())
                       .mapToLong(Image::getId)
                       .reduce(1,(a,b) -> a * b));
    }

    private void parseInput(){
        int id = 0;
        List<String> tmp = new ArrayList<>();
        for (String row : data) {
            if(row.matches("")){
                images.add(new Image(id, tmp, getSides(tmp)));
                tmp.clear();
            }
            else if(Parser.parseStringFirstWord(row).equals("Tile")){
                id = Parser.parseStringFirstInt(row);
            }
            else{
                tmp.add(row);
            }
        }
        images.add(new Image(id, tmp, getSides(tmp)));
    }

    public List<String> getSides(List<String> allInfo){
        List<String> sides = new ArrayList<>();
        sides.add(allInfo.get(0));
        sides.add(allInfo.get(allInfo.size()-1));

        StringBuilder first = new StringBuilder();
        StringBuilder last = new StringBuilder();
        for (String s : allInfo) {
            first.append(s.charAt(0));
            last.append(s.charAt(s.length()-1));
        }
        sides.add(first.toString());
        sides.add(last.toString());

        List<String> tmp = new ArrayList<>();

        for (String side : sides) {
            tmp.add(side);
            tmp.add(reverse(side));
        }
        return tmp;
    }

    private String reverse(String str){
        StringBuilder sb = new StringBuilder();
        sb.append(str).reverse();
        return sb.toString();
    }

    class Image{
        long id;
        int matchingSides= 0;
        List<String> info;
        List<String> sides;


        public Image(int id, List<String> info, List<String> sides) {
            this.id = id;
            this.info = info;
            this.sides = sides;
        }

        public List<String> getSides(){
            return sides;
        }

        public long getId() {
            return id;
        }

        public int getMatchingSides(){
            return matchingSides;
        }

        public void checkSides(Image im){
            for (String side : sides) {
                for (String imSide : im.getSides()) {
                    if(side.equals(imSide)){
                        matchingSides++;
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "Image{" +
                    "id=" + id +
                    ", matchingSides=" + matchingSides +
                    '}';
        }
    }


}
