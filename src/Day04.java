import Helpers.Parser;
import Helpers.Reader;

import java.util.*;

public class Day04 {
    static int counter = 0;
    static List<String> validEyeColor = Arrays.asList("amb","blu","brn","gry","grn","hzl","oth");
    public static void main(String[] args) { new Day04().run(); }

    private void run() {
        List<Passport> passports = fillPassportWithData();
        System.out.println("Number of valid passports: " +
                passports.stream()
                        .filter(Passport::isValid)
                        .count());

        System.out.println("Number of supervalid passports: " +
                passports.stream()
                        .filter(Passport::isValid)
                        .filter(Passport::isFieldsValid)
                        .count());
    }

    private List<Passport> fillPassportWithData(){
        ArrayList<String> data = Reader.readLinesToStringArray("day04.txt");
        List<Passport> passports = new ArrayList<>();
        Passport pass = new Passport(1);
        int id = 2;
        for (String row : data) {
            if(row.length() == 0){
                passports.add(pass);
                pass = new Passport(id);
                id++;
            }else {
                for (String info : row.split(" ")) {
                    pass.setInfo(info);
                }
            }
        }
        passports.add(pass);
        return passports;
    }


    class Passport{
        private Map<String, String> info = new HashMap<>();
        private int id;

        Passport(int id){
            this.id = id;
        }

        public void setInfo(String information) {
            String[] arr = information.split(":");
            info.put(arr[0], arr[1]);
        }

        @Override
        public String toString() {
            return "Passport{" + "id=" + id + " info=" + info + '}';
        }

        public boolean isValid(){
            if(info.size() == 8) return true;
            else if(info.size() == 7 && !info.containsKey("cid")) return true;
            else return false;
        }
        public boolean isFieldsValid(){
            // byr (Birth Year) - four digits; at least 1920 and at most 2002.
            if (info.containsKey("byr")) {
                int year = Integer.parseInt(info.get("byr"));
                if(1920 > year || year > 2002) return false;
            }

            // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            if(info.containsKey("iyr")){
                int year = Integer.parseInt(info.get("iyr"));
                if(2010 > year || year > 2020) return false;
            }

            // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
            if(info.containsKey("eyr")){
                int year = Integer.parseInt(info.get("eyr"));
                if(2020 > year || year > 2030)return false;
            }
            // hgt (Height) - a number followed by either cm or in:
            if(info.containsKey("hgt")){
                int height = Parser.parseStringFirstInt(info.get("hgt"));
                String scale = Parser.parseStringFirstWord(info.get("hgt"));

                if(!scale.equals("cm") && !scale.equals("in")) return false;
                // If cm, the number must be at least 150 and at most 193.
                if(scale.equals("cm"))
                    if( 150 > height || height > 193) return false;

                // If in, the number must be at least 59 and at most 76.
                if(scale.equals("in")){
                    if(59 > height || height > 76) return false;
                }
            }

            // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            if (info.containsKey("hcl")) {
                String characters = Parser.parseStringFirstWordWithPattern("[0-9a-f]+",info.get("hcl"));
                if(info.get("hcl").charAt(0) != '#' || characters.length() != 6)
                    return false;
            }

            //ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            if(info.containsKey("ecl")){
                if(!validEyeColor.contains(info.get("ecl"))) return false;
            }
            //pid (Passport ID) - a nine-digit number, including leading zeroes.
            if(info.containsKey("pid")){
                String passNumber = info.get("pid");
                if(passNumber.contains("[\\D]+") || passNumber.length() != 9) return false;
            }

            return true;
        }
    }
}
