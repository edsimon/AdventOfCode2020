import Helpers.Parser;
import Helpers.Reader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public class Day13 {
    public static void main(String[] args) { new Day13().run(); }
    ArrayList<String> data = Reader.readLinesToStringArray("day13.txt");
    int time = Parser.parseStringFirstInt(data.get(0));
    List<Bus> busses = new ArrayList<>();

    private void run() {
        setup();
        System.out.println("Task A: " + getFirstDeparture());

        long[] n = busses.stream().mapToLong(bus -> bus.id).toArray();
        long[] a = busses.stream().mapToLong(bus -> (bus.id - bus.position) % bus.id).toArray();
        System.out.println("Task B: " + chineseRemainder(n,a));
    }

    public long getFirstDeparture() {
        for (int i = time; i < Integer.MAX_VALUE; i++) {
            for (Bus b : busses) {
                if (i % b.id == 0) return  (i - time) * b.id;
            }
        }return 0;
    }

    public void setup() {
        final String[] split = data.get(1).split(",");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                busses.add(new Bus(Integer.parseInt(split[i]), i));
            }
        }
        System.out.println(busses);
    }
    public static class Bus {
        int id;
        int position;
        public Bus(int id, int position) {
            this.id = id;
            this.position = position;
        }
    }

    /*
    The following code is taken from the implementation of chinese reminder formula
    from the following website. Did not have the energy to implement it :P
    https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
     */
    public static long chineseRemainder(long[] n, long[] a) {
        long prod = stream(n).reduce(1, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * mulInv(p, n[i]) * p;
        }
        return sm % prod;
    }
    private static long mulInv(long a, long b) {
        long b0 = b;
        long x0 = 0;
        long x1 = 1;

        if (b == 1)
            return 1;

        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0)
            x1 += b0;

        return x1;
    }
}