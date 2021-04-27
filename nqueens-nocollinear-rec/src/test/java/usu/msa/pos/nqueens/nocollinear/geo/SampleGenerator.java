package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.Random;
import static usu.msa.pos.nqueens.nocollinear.geo.Point.approx;

/**
 *
 * @author msa
 */
public class SampleGenerator {

    public enum Type {
        PARALLEL, COLLINEAR, DIFFERENT_SLOPE
    };
    public static final String ILLEGAL = "illegal",
            PARALLEL = "fixed_parallel",
            RND_PARALLEL = "rnd_parallel",
            ALIGNED = "fixed_aligned",
            RND_ALIGNED = "rnd_aligned",
            RND_DIFFERENT_SLOPE = "rnd_different_slope";
    static final int SAMPLE_SIZE = 100,
            MAX_COORD = 1_000,
            MAX_DISTANCE = (int) Math.sqrt(MAX_COORD);
    static final Random RND = new Random();

    static Segment randomSegment() {
        return new Segment(
           new Point(RND.nextInt(MAX_COORD), RND.nextInt(MAX_COORD)),
           new Point(RND.nextInt(MAX_COORD), RND.nextInt(MAX_COORD))
        );
    }

    static Segment makeSegment(Segment s, Type type) {
        double slope = s.slope, dist = 1 + RND.nextInt(MAX_DISTANCE), size = 1 + RND.nextInt((int) dist);
        double sign = slope > 0 ? 1.0 : -1.0;
        double dx = Math.sqrt(Math.pow(dist, 2) / (1.0 + Math.pow(slope, 2)));
        double dy = slope * dx;
        switch (type) {
            case COLLINEAR: {
                return new Segment(new Point((long)(s.mid.x + dx), (long)(s.mid.y + sign * dy)), slope, size);
            }
            case PARALLEL: {
                int delta = 1 + RND.nextInt((int) size);
                boolean dirx = RND.nextBoolean();
                return new Segment(new Point((long)(s.mid.x + dx + (dirx ? delta : 0)), (long)(s.mid.y + sign * (dy + (dirx ? 0 : delta)))), slope, size);
            }
            case DIFFERENT_SLOPE: {
                for (int i = 0; i < 100_000; i++) {
                    Segment s2 = randomSegment();
                    if (!approx(s.slope, s2.slope)) {
                        return s2;
                    }
                }
            }
        }
        throw new IllegalStateException();
    }
}
