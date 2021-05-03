package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author msa
 *
 */
public class Point implements Comparable<Point> {

    public static final Point ORIGIN = new Point(0, 0);
    public static final double MAX_COORD = 10_000; // may need to change to BigDecimal for large x, y
    public static final double DISTANCE_PRECISION = 1e-6;
    public final double x, y, slope;

    public Point(Number x, Number y) {
        if (x.doubleValue() < 0 || y.doubleValue() < 0 || x.doubleValue() > MAX_COORD
                || y.doubleValue() > MAX_COORD) {
            throw new IllegalArgumentException();
        }
        this.x = x.doubleValue();
        this.y = y.doubleValue();
        this.slope = slope();
    }

    public final double slope() {
        if (x == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return y / x;
    }

    public double slope(Point p) {
        return !this.equals(ORIGIN) ? slope(this, p) : p.slope;
    }

    public static double slope(Point p1, Point p2) {
        if (p1.x == p2.x) {
            return Double.POSITIVE_INFINITY;
        }
        return (p1.y - p2.y) / (p1.x - p2.x);
    }

    public static List<Point> asList(int[] a) {
        List<Point> points = new ArrayList<>();
        IntStream.range(0, a.length).forEach(i -> points.add(new Point(i, a[i])));
        return points;
    }

    public boolean isSameXorY(Point p) {
        return isSameXorY(this, p);
    }

    public static boolean isSameXorY(Point p1, Point p2) {
        return approx(p1.x, p2.x) || approx(p1.y, p2.y);
    }

    public static boolean approx(double x, double y) {
        return (x == Double.POSITIVE_INFINITY && y == Double.POSITIVE_INFINITY) || Math.abs(x - y) < DISTANCE_PRECISION;
    }

    // collinear points have ccw == 0
    public static int ccw(Point a, Point b, Point c) {
        double area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        return area < 0 ? -1 : (area > 0 ? 1 : 0);
    }

    // points in diagonal invalidate test
    public static boolean hasCollinear(List<Point> points) {
        if (points == null || points.size() < 3) {
            throw new IllegalArgumentException();
        }
        List<Point> ordered = points.stream().sorted().collect(Collectors.toList());
        for (Point p : points) {
            int j = p.equals(ordered.get(0)) ? 1 : 0;
            double last = p.slope(ordered.get(j++));
            if (approx(Math.abs(last), 1.0)) {
                // System.out.println("** wrong slope: " + last + " " + p + " " + ordered.get(j - 1));
                return true;
            }
            for (int i = j; i < ordered.size(); i++) {
                double slope = p.slope(ordered.get(i));
                if (approx(slope, last) || approx(Math.abs(slope), 1.0)) {
                    return true;
                }
                last = slope;
            }
        }
        return false;
    }
    
    public static String print(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        IntStream.range(0, points.size()).forEach(i -> sb.append(points.get(i)).append(" "));
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
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

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        if (this == p) {
            return true;
        }
        return (x == p.x && y == p.y) || approx(x, p.x) && approx(y, p.y);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        if (slope != Double.POSITIVE_INFINITY) {
            hash = 29 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
            hash = 29 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
            hash = 29 * hash + (int) (Double.doubleToLongBits(this.slope) ^ (Double.doubleToLongBits(this.slope) >>> 32));
        } else {
            hash = 67 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
            hash = 67 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        }
        return hash;
    }

}
