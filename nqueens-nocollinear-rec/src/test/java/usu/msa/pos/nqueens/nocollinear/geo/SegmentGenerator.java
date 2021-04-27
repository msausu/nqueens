package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import usu.msa.pos.nqueens.nocolinear.Board;
import static usu.msa.pos.nqueens.nocollinear.geo.Point.approx;

public final class SegmentGenerator extends SampleGenerator {

    @DataProvider(name = ILLEGAL, parallel = true)
    public static Object[][] illegal() {
        return new Object[][]{
            new Object[]{new Point(0, 0), new Point(0, 0)},
            new Object[]{new Point(1, 1), new Point(1, 4)},
            new Object[]{new Point(3, 4), new Point(4, 4)},
            new Object[]{new Point(Board.MAX_BOARD_SIZE, 4), new Point(4, 4)}
        };
    }

    @DataProvider(name = ALIGNED, parallel = false)
    public static Object[][] aligned() {
        return new Object[][]{
            new Object[]{new Segment(new Point(0, 0), new Point(1, 1)), new Segment(new Point(2, 2), new Point(3, 3))},
            new Object[]{new Segment(new Point(0, 3), new Point(1, 2)), new Segment(new Point(2, 1), new Point(3, 0))}
        };
    }

    @DataProvider(name = PARALLEL, parallel = true)
    public static Object[][] parallel() {
        Segment[] segments = new Segment[]{
            new Segment(new Point(0, 0), new Point(1, 1)),
            new Segment(new Point(2, 1), new Point(3, 2)),
            new Segment(new Point(1, 0), new Point(2, 1)),
            new Segment(new Point(0, 1), new Point(1, 2)),
            new Segment(new Point(0, 2), new Point(1, 3))
        };
        List<Object[]> objs = new ArrayList<>();
        for (int i = 0; i < segments.length; i++) {
            for (int j = i + 1; j < segments.length; j++) {
                Segment s1 = segments[i], s2 = segments[j];
                if (i == j || s1.x.equals(s2.y) || s1.y.equals(s2.x) || !approx(s1.slope, s2.slope)) {
                    continue;
                }
                objs.add(new Object[]{s1, s2});
            }
        }
        return objs.toArray(new Object[][]{});
    }

    @DataProvider(name = RND_ALIGNED, parallel = true)
    public static Object[][] randomAligned() {
        Object[][] objs = new Object[SAMPLE_SIZE][2];
        int i = 0;
        while (i < SAMPLE_SIZE) {
            try {
                Segment s1 = randomSegment(), s2 = makeSegment(s1, Type.COLLINEAR);
                Segment m = new Segment(s1.mid, s2.mid);
                if (!approx(s1.slope, s2.slope) || !approx(s1.slope, m.slope) || Math.signum(s1.slope) != Math.signum(m.slope)) {
                    continue;
                }
                objs[i++] = new Object[]{s1, s2};
            } catch (IllegalArgumentException e) { // midpoints are vertical or horizontal
            }
        }
        return objs;
    }

    @DataProvider(name = RND_PARALLEL, parallel = false)
    public static Object[][] randomParallel() {
        Object[][] objs = new Object[SAMPLE_SIZE][2];
        int i = 0;
        while (i < SAMPLE_SIZE) {
            try {
                Segment s1 = randomSegment(), s2 = makeSegment(s1, Type.PARALLEL);
                Segment m = new Segment(s1.mid, s2.mid);
                if (Segment.sameXorY(s1, s2) || !approx(s1.slope, s2.slope) || approx(s1.slope, m.slope)) {
                    continue;
                }
                objs[i++] = new Object[]{s1, s2};
            } catch (IllegalArgumentException e) { 
            }
        }
        return objs;
    }

    @DataProvider(name = RND_DIFFERENT_SLOPE, parallel = false)
    public static Object[][] randomDifferentSlope() {
        Object[][] objs = new Object[SAMPLE_SIZE][2];
        int i = 0;
        while (i < SAMPLE_SIZE) {
            try {
                Segment s1 = randomSegment(), s2 = makeSegment(s1, Type.DIFFERENT_SLOPE);
                Segment m = new Segment(s1.mid, s2.mid);
                if (Segment.sameXorY(s1, s2) || approx(s1.slope, s2.slope) || approx(s1.slope, m.slope)) {
                    continue;
                }
                objs[i++] = new Object[]{s1, s2};
            } catch (IllegalArgumentException e) {
            }
        }
        return objs;
    }

}
