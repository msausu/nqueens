package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.ArrayList;
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
