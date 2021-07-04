package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author msa
 */
public class Point implements Comparable<Point> {
    
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
    // worst case O((n^2) * log(n)) 
    public static boolean hasCollinear(List<Point> points) {
        if (points == null || points.size() < 3) {
            throw new IllegalArgumentException();
        }
        for (Point p: points) {
            Comparator<Point> cmp = (Point t, Point t1) -> {
                double s1 = p.slope(t), s2 = p.slope(t1);
                return s1 > s2 ? 1 : (s1 < s2 ? -1 : 0);
            };
            List<Point> ordered = points.stream().filter(x -> !p.equals(x)).sorted(cmp).collect(Collectors.toList());
            double last = p.slope(ordered.get(0));
            for (int i = 1; i < ordered.size(); i++) {
                double slope = p.slope(ordered.get(i));
                if (last == slope) 
                    return true;
                else 
                    last = slope;
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
                if (i == j) continue;
                for (int k = j + 1; k < points.size(); k++) {
                    if (i == k || j == k) continue;
                        if (ccw(points.get(i), points.get(j), points.get(k)) == 0) return true;
                }
            }
        }
        return false;
    }
    
    public boolean isSameXorY(Point p) {
        return isSameXorY(this, p);
    }
    
    public static boolean isSameXorY(Point p1, Point p2) {
        return approx(p1.x, p2.x) || approx(p1.y, p2.y);
    }

    // collinear points have ccw == 0
    public static int ccw(Point a, Point b, Point c) {
        double area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        return area < 0 ? -1 : (area > 0 ? 1 : 0);
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
