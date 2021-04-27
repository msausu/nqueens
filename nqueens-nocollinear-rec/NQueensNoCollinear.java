package usu.msa.pos.nqueens.nocolinear;

import static usu.msa.pos.nqueens.nocolinear.NQueensNoCollinear.Point.approx;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static class Point implements Comparable<Point> {

        public static final double DISTANCE_PRECISION = 1e-6;
        public final long x, y;
        public final double slope;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
            slope = x == 0 ? Double.MAX_VALUE : y / x;
        }

        public static boolean approx(double x, double y) {
            return Math.abs(x - y) < DISTANCE_PRECISION;
        }

        public double slope(Point o) {
            return slope(this, o);
        }

        public static double slope(Point p1, Point p2) {
            if (p1.x == p2.x) {
                return Double.POSITIVE_INFINITY;
            }
            double p1x = p1.x, p1y = p1.y, p2x = p2.x, p2y = p2.y;
            return (p1y - p2y) / (p1x - p2x);
        }

        public static List<Point> toPoints(int[] a) {
            List<Point> points = new ArrayList<>();
            IntStream.range(0, a.length).forEach(i -> points.add(new Point(i, a[i])));
            return points;
        }

        // test for colinear points when there are 3 or more points with same slope
        // worst case O(n^4) 
        public static boolean hasCollinear(List<Point> points) {
            if (points == null || points.size() < 3) {
                throw new IllegalArgumentException();
            }
            List<Point> ordered = points.stream().sorted().collect(Collectors.toList());
            for (Point p : points) {
                int j = p.equals(ordered.get(0)) ? 1 : 0;
                double last = p.slope(ordered.get(j++));
                for (int i = j; i < ordered.size(); i++) {
                    double slope = p.slope(ordered.get(i));
                    if (approx(slope, last)) {
                        return true;
                    }
                    last = slope;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "(" + x + " " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Point)) {
                return false;
            }
            return (x == ((Point) o).x && y == ((Point) o).y) || (x == ((Point) o).x && y == ((Point) o).y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Point p) {
            if (y < p.y) {
                return -1;
            }
            if (y > p.y) {
                return 1;
            }
            if (x < p.x) {
                return -1;
            }
            if (x > p.x) {
                return 1;
            }
            return 0;
        }
    }

    private static final int MAX_BOARD_SIZE = 100_000;
    private final Consumer<NQueensNoCollinear> ret;
    private final int n;
    private final int[] q;

    // prune if queens in diagonal
    private boolean hasBacktracking(int i) {
        return IntStream.range(0, i).anyMatch(j -> Math.abs(q[i] - q[j]) == i - j);
    }

    // board permutations
    private void permutations(int i) {
        if (i == n) {
            if (n < 3 || Point.hasCollinear(Point.toPoints(q))) {
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

    public static void help() {
        System.err.println("java: version greater than 11\nuse: java NQueensNoCollinear.java boardSize\nboardSize: integer between 1 and " + MAX_BOARD_SIZE);
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            help();
        }
        StringBuilder sb = new StringBuilder();
        Consumer<NQueensNoCollinear> use = solution -> sb.append(solution).append('\n');
        new NQueensNoCollinear(boardSize(args[0]), use);
        System.out.print(sb.toString());
    }
}
