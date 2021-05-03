package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.List;
import static usu.msa.pos.nqueens.nocollinear.geo.Point.approx;

/**
 *
 * @author msa
 */
public class Segment implements Comparable<Segment> {

    public final Point x, y, mid;
    public final double slope;
    
    // disallow vertical, horizontal or diagonal 
    public Segment(Point x, Point y) {
        if (x == y || x.equals(y) || approx(x.x, y.x) || approx(x.y, y.y)) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
        mid = new Point(Math.min(x.x, y.x) + Math.abs(x.x - y.x) / 2, Math.min(x.y, y.y) + Math.abs(x.y - y.y) / 2);
        this.slope = Point.slope(this.x, this.y);
        if (approx(Math.abs(slope), 1.0)) throw new IllegalArgumentException();
    }

    public Segment(Point mid, double slope, double size) {
        this(
            new Point(
                Math.round(mid.x - k(slope, size) / 2), 
                Math.round(mid.y - slope * k(slope, size) / 2)
            ),
            new Point(
                Math.round(mid.x + k(slope, size) / 2), 
                Math.round(mid.y + slope * k(slope, size) / 2)
            )
        );
    }

    private static double k(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) / (Math.pow(b, 2) + 1.0));
    }
    
    public double getSlope() {
        return slope;
    }
    
    public boolean isCollinear(Segment s) {
        if (slope == Double.POSITIVE_INFINITY && s.slope == Double.POSITIVE_INFINITY && approx(x.x, s.x.x))
            return true;
        if (slope == 0 && s.slope == 0 && approx(x.y, s.x.y))
            return true;
        if (mid == s.mid || mid.equals(s.mid) || y.equals(s.x) || x.equals(s.x) || y.equals(s.y)) {
            return approx(slope, s.slope) && Math.signum(slope) == Math.signum(s.slope);
        }
        try {
            Segment m = new Segment(mid, s.mid);
            return approx(slope, m.slope) && Math.signum(slope) == Math.signum(m.slope);
        } catch (IllegalArgumentException e) { // midpoints are horizontal or vertical 
        }
        return false;
    }

    public static boolean sameXorY(Segment s1, Segment s2) {
        return approx(s1.x.x, s2.x.x) || approx(s1.x.x, s2.y.x) || approx(s1.x.y, s2.y.y) || approx(s1.y.y, s2.y.y);
    }

    /**
     *
     * @param segments List of segments that MUST be ORDERED by SLOPE
     * @return boolean true is no 3 points are colinear, false otherwise
     */
    public static <T extends Segment> boolean noThreeInLine(List<T> segments) {
        for (int i = 0; i < segments.size() - 1; i++) {
            T s1 = segments.get(i), s2 = segments.get(i + 1);
            if (approx(s1.slope, s2.slope) && (Math.signum(s1.slope) * Math.signum(s2.slope) > 0)) {
                try {
                    Segment m = new Segment(s1.mid, s2.mid);
                    if (m.isCollinear(s1)) {
                        return false;
                    }
                } catch (IllegalArgumentException e) {}
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Segment)) {
            return false;
        }
        Segment s = (Segment) o;
        return x.equals(s.x) && y.equals(s.y);
    }

    @Override
    public int compareTo(Segment s) {
        return slope > s.slope ? 1 : (slope < s.slope ? -1 : 0);
    }

    @Override
    public String toString() {
        return "[" + String.format("%f ", slope) + " " + x + " " + y + "]";
    }
}
