package usu.msa.pos.nqueens.nocollinear.geo;

import usu.msa.pos.nqueens.nocollinear.num.Rational;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author msa
 */
public record Point(Number x, Number y) implements Comparable<Point> {

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

    // test for collinear vertical or horizontal points
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
        if (hasCollinearVH(points)) return true;
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
