package usu.msa.pos.nqueens.nocolinear;

import java.util.ArrayList;
import usu.msa.pos.nqueens.nocollinear.geo.Point;
import usu.msa.pos.nqueens.nocollinear.geo.Segment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.testng.annotations.DataProvider;
import static usu.msa.pos.nqueens.nocollinear.geo.Point.approx;
import static usu.msa.pos.nqueens.nocollinear.geo.Point.ccw;

public final class PointGenerator extends SampleGenerator {

    @DataProvider(name = PARALLEL, parallel = true)
    public static Object[][] unaligned() {
        return new Object[][]{
            new Object[]{ Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(3, 2)) },
            new Object[]{ Arrays.asList(new Point(0, 3), new Point(4, 4), new Point(1, 2)) }
        };
    }

    @DataProvider(name = ALIGNED, parallel = false)
    public static Object[][] aligned() {
        return new Object[][]{
            new Object[]{ Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(3, 3)) },
            new Object[]{ Arrays.asList(new Point(0, 3), new Point(1, 2), new Point(3, 0)) }
        };
    }

    @DataProvider(name = RND_ALIGNED, parallel = true)
    public static Object[][] randomAligned() {
        Object[][] objs = new Object[SAMPLE_SIZE][];
        int i = 0;
        while (i < SAMPLE_SIZE) {
            try {
                Segment s1 = randomSegment(), s2 = makeSegment(s1, Type.COLLINEAR);
                Segment m = new Segment(s1.mid, s2.mid);
                if (!approx(s1.slope, s2.slope) || !approx(s1.slope, m.slope) || Math.signum(s1.slope) != Math.signum(m.slope)) {
                    continue;
                }
                if (s1.equals(s2) || s1.x.isSameXorY(s2.x) || s1.x.isSameXorY(s2.y)) continue;
                if (ccw(s1.x, s1.y, s2.x) == 0) {
                    Set<Point> points = new HashSet<>() {
                        {
                            add(s1.x); add(s1.y); add(s2.x);
                        }
                    };
                    if (points.size() < 3) continue;
                    objs[i++] = new Object[]{ new ArrayList(points) };
                }
                if (i < SAMPLE_SIZE && ccw(s1.x, s1.y, s2.y) == 0) {
                    Set<Point> points = new HashSet<>() {
                        {
                            add(s1.x); add(s1.y); add(s2.y);
                        }
                    };
                    if (points.size() < 3) continue;
                    objs[i++] = new Object[]{ new ArrayList(points) };
                }
            } catch (IllegalArgumentException e) { // midpoints are vertical or horizontal
            }
        }
        return objs;
    }
    
    @DataProvider(name = RND_DIFFERENT_SLOPE, parallel = true)
    public static Object[][] randomUnaligned() {
        Object[][] objs = new Object[SAMPLE_SIZE][];
        int i = 0;
        while (i < SAMPLE_SIZE) {
            try {
                Segment s1 = randomSegment(), s2 = makeSegment(s1, Type.DIFFERENT_SLOPE);
                Segment m = new Segment(s1.mid, s2.mid);
                if (approx(s1.slope, s2.slope) || approx(s1.slope, m.slope)) {
                    continue;
                }
                if (s1.equals(s2) || s1.x.isSameXorY(s2.x) || s1.x.isSameXorY(s2.y)) continue;
                if (ccw(s1.x, s1.y, s2.x) != 0) {
                    Set<Point> points = new HashSet<>() {
                        {
                            add(s1.x); add(s1.y); add(s2.x);
                        }
                    };
                    if (points.size() < 3) continue;
                    objs[i++] = new Object[]{ new ArrayList(points) };
                }
                if (i < SAMPLE_SIZE && ccw(s1.x, s1.y, s2.y) != 0) {
                    Set<Point> points = new HashSet<>() {
                        {
                            add(s1.x); add(s1.y); add(s2.y);
                        }
                    };
                    if (points.size() < 3) continue;
                    objs[i++] = new Object[]{ new ArrayList(points) };
                }
            } catch (IllegalArgumentException e) { // midpoints are vertical or horizontal
            }
        }
        return objs;
    }

}
