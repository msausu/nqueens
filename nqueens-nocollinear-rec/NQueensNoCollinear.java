package usu.msa.pos.nqueens.nocolinear;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.math3.fraction.Fraction;

/**
 * @author msa
 *
 * NQueensNoCollinear recursive, simple, approach
 *
 * based on wikipedia's Heap's Algorithm page and Robert Sedgewick algs4
 * (https://algs4.cs.princeton.edu/home/) with additional filtering of collinear
 * points by slope. Segewick hypothesis: NQueens(n) ~ n! / 2.5^n
 *
 * input: boardSize, should be small (NP) output: array encoded by position
 * starting at 0: 2 0 3 1 -> solution tuple ((1, 3), (2, 1), (3, 4), (4, 2))
 */
public class NQueensNoCollinear {

    public static class Rational implements Comparable<Rational> {

        public static final Rational ZERO = new Rational(0, 0);
        private final Fraction fraction;

        public Rational(int x, int y) {

            if (x == 0 && y == 0) {
                fraction = Fraction.ZERO;
            } else if (x > 0 && y == 0) {
                fraction = new Fraction(Integer.MAX_VALUE, 1);
            } else if (x < 0 && y == 0) {
                fraction = new Fraction(Integer.MIN_VALUE, 1);
            } else {
                fraction = new Fraction(x, y);
            }
        }

        public Fraction get() {
            return fraction;
        }

        public Rational(float f) {
            this.fraction = new Fraction(f);
        }

        @Override
        public int compareTo(Rational t) {
            return equals(t) ? 0 : fraction.compareTo(t.fraction);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Rational)) {
                return false;
            } else {
                return ((Rational) o).fraction.equals(fraction);
            }
        }

        @Override
        public String toString() {
            return String.format("%+.3f", fraction.doubleValue());
        }

    }

    public static record Point(Number x, Number y) implements Comparable<Point> {

        public static final Point ORIGIN = new Point(0, 0);

        public Rational slopeOrigin() {
            return slope(ORIGIN, this);
        }

        public static Rational slope(Point p1, Point p2) {
            return isDecimal(p1.x, p1.y, p2.x, p2.y)
                    ? new Rational((p1.y.floatValue() - p2.y.floatValue()) / (p1.x.floatValue() - p2.x.floatValue()))
                    : new Rational(p1.y.intValue() - p2.y.intValue(), p1.x.intValue() - p2.x.intValue());
        }

        public static List<Point> toPoints(int[] a) {
            List<Point> points = new ArrayList<>();
            IntStream.range(0, a.length).forEach(i -> points.add(new Point(i, a[i])));
            return points;
        }

        // test for colinear points when there are 3 or more points with same slope
        public static boolean hasCollinearVH(List<Point> points) {
            Map<Integer, Long> vpoints = points.stream().map(p -> p.x.intValue()).collect(Collectors.groupingBy(v -> v, Collectors.counting()));
            Map<Integer, Long> hpoints = points.stream().map(p -> p.y.intValue()).collect(Collectors.groupingBy(v -> v, Collectors.counting()));
            return vpoints.values().stream().mapToLong(v -> v).anyMatch(v -> v > 2) || hpoints.values().stream().mapToLong(v -> v).anyMatch(v -> v > 2);
        }

        // test for colinear points when there are 3 or more points with same slope
        public static boolean hasCollinear(List<Point> points) {
            if (points == null || points.size() < 3) {
                throw new IllegalArgumentException();
            }
            if (hasCollinearVH(points)) {
                return true;
            }
            for (int i = 0; i < points.size(); i++) {
                Point p = points.get(i);
                Comparator<Point> cmp = (Point a, Point b) -> slope(p, a).compareTo(slope(p, b));
                List<Point> ordered = points.stream().filter(x -> !x.equals(p)).sorted(cmp).collect(Collectors.toList());
                Rational last = slope(p, ordered.get(0));
                for (int j = 1; j < ordered.size(); j++) {
                    Rational slope = slope(p, ordered.get(j));
                    if (last.equals(slope)) {
                        return true;
                    } else {
                        last = slope;
                    }
                }
            }
            return false;
        }

        public static boolean hasCollinearSlow(List<Point> points) {
            if (points == null || points.size() < 3) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    for (int k = j + 1; k < points.size(); k++) {
                        if (i == k || j == k) {
                            continue;
                        }
                        if (area(points.get(i), points.get(j), points.get(k)) == 0) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        // collinear points have area == 0
        public static int area(Point a, Point b, Point c) {
            double area = (b.x.doubleValue() - a.x.doubleValue()) * (c.y.doubleValue() - a.y.doubleValue())
                    - (b.y.doubleValue() - a.y.doubleValue()) * (c.x.doubleValue() - a.x.doubleValue());
            return area < 0 ? -1 : (area > 0 ? 1 : 0);
        }

        @Override
        public int compareTo(Point p) {
            if (y.doubleValue() < p.y.doubleValue()) {
                return -1;
            }
            if (y.doubleValue() > p.y.doubleValue()) {
                return 1;
            }
            if (x.doubleValue() < p.x.doubleValue()) {
                return -1;
            }
            if (x.doubleValue() > p.x.doubleValue()) {
                return 1;
            }
            return 0;
        }

        public static boolean isDecimal(Number x) {
            return x.doubleValue() - x.intValue() > 0;
        }

        public static boolean isDecimal(Number... numbers) {
            for (Number number : numbers) {
                if (!isDecimal(number)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "(" + x + " " + y + " " + slopeOrigin() + ")";
        }

    }

    private static final int MAX_BOARD_SIZE = 25;
    private final Consumer<NQueensNoCollinear> ret;
    private final int n;
    private final int[] q;

    // prune if queens in diagonal
    private boolean hasBacktracking(int i) {
        return IntStream.range(0, i).anyMatch(j -> Math.abs(q[i] - q[j]) == i - j);
    }

    // Testing just NQUEENS == true ignoring collinear queens
    private static final boolean JUST_NQUEENS = false;
    
    // board permutations
    private void permutations(int i) {
        if (i == n) {
            if (n < 3 || JUST_NQUEENS || Point.hasCollinearSlow(Point.toPoints(q))) {
                return;
            }
            ret.accept(this);
        } else {
            IntStream.range(i, n).forEach(j -> {
                swap(i, j);
                if (!hasBacktracking(i)) {
                    permutations(i + 1);
                }
                swap(j, i);
            });
        }
    }

    private void swap(int i, int j) {
        int t = q[i];
        q[i] = q[j];
        q[j] = t;
    }

    private void id() {
        IntStream.range(0, n).forEach(i -> q[i] = i);
    }

    public NQueensNoCollinear(int n, Consumer<NQueensNoCollinear> ret) {
        this.n = n;
        this.ret = ret;
        this.q = new int[n];
        id();
        permutations(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, n).forEach(i -> sb.append(q[i]).append(" "));
        return sb.toString().trim();
    }

    public static int boardSize(String size) {
        try {
            int n = Integer.parseInt(size);
            if (n < 1 || n > MAX_BOARD_SIZE) {
                throw new IllegalArgumentException();
            }
            return n;
        } catch (NullPointerException | IllegalArgumentException e) {
            help();
        }
        throw new IllegalStateException();
    }

    static final PrintStream out = System.out;

    public static void help() {
        out.print("""
                  java: version greater than 15
                  use: java -cp commons-math3-3.6.1.jar NQueensNoCollinear.java boardSize
                  boardSize: integer between 1 and \s """ + MAX_BOARD_SIZE + "\n"
        );
        System.exit(2);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            help();
        }
        StringBuilder sb = new StringBuilder();
        Consumer<NQueensNoCollinear> use = solution -> sb.append(solution).append('\n');
        new NQueensNoCollinear(boardSize(args[0]), use);
        out.print(sb.toString());
    }
}
