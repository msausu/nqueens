package usu.msa.pos.nqueens.nocolinear;

import usu.msa.pos.nqueens.nocollinear.geo.Point;
import usu.msa.pos.nqueens.nocollinear.geo.Segment;
import org.testng.annotations.Test;

/**
 *
 * @author msa
 */
public class SegmentTest {

     @Test(
        enabled = true,
        testName = "test-illegal-segment",
        invocationCount = 1,
        threadPoolSize = 5,
        description = "Test invalid segments are disallowed",
        dataProviderClass = SegmentGenerator.class,
        successPercentage = 100,
        dataProvider = SegmentGenerator.ILLEGAL
    )
    void testIllegal(Point x, Point y) {
        boolean isInvalid = false;
        try {
            new Segment(x, y);
        } catch (IllegalArgumentException e) {
            isInvalid = true;
        }
        assert isInvalid : "illegal: points: " + x + " " + y  + " should be NOT de accepted as a valid";
    }   
    
    @Test(
        enabled = false,
        testName = "test-aligned-segments-01",
        invocationCount = 1,
        threadPoolSize = 5,
        description = "Test segments in same line",
        dataProviderClass = SegmentGenerator.class,
        successPercentage = 100,
        dataProvider = SegmentGenerator.ALIGNED
    )
    void test01Aligned(Segment s1, Segment s2) {
        assert s1.isCollinear(s2) : "alignment: segments " + s1 + " " + s2 + " should be accepted as colinear";
    }
    
    @Test(
        enabled = false,
        testName = "test-aligned-segments-02",
        invocationCount = 1,
        threadPoolSize = 1,
        description = "Test segments in same line",
        dataProviderClass = SegmentGenerator.class,
        successPercentage = 100,
        dataProvider = SegmentGenerator.RND_ALIGNED
    )
    void test02Aligned(Segment s1, Segment s2) {
        assert s1.isCollinear(s2) : "alignment: segments " + s1 + " " + s2 + " should be accepted as colinear";
    }

     @Test(
        enabled = true,
        testName = "test-parallel-segments-01",
        invocationCount = 1,
        threadPoolSize = 5,
        description = "Test segments with same slope but not aligned",
        dataProviderClass = SegmentGenerator.class,
        successPercentage = 100,
        dataProvider = SegmentGenerator.PARALLEL
    )
    void test01parallel(Segment s1, Segment s2) {
        assert !s1.isCollinear(s2) : "alignment: segments " + s1 + " " + s2 + " should be accepted as NOT colinear";
    }
    
    @Test(
        enabled = true,
        testName = "test-parallel-segments-02",
        invocationCount = 1,
        threadPoolSize = 1,
        description = "Test segments with same slope but not aligned",
        dataProviderClass = SegmentGenerator.class,
        successPercentage = 100,
        dataProvider = SegmentGenerator.RND_PARALLEL
    )
    void test02parallel(Segment s1, Segment s2) {
        assert !s1.isCollinear(s2) : "alignment: segments " + s1 + " " + s2 + " should be accepted as NOT colinear";
    }
    
    @Test(
        enabled = true,
        testName = "test-different-slope-segments",
        invocationCount = 1,
        threadPoolSize = 1,
        description = "Test segments with different slopes",
        dataProviderClass = SegmentGenerator.class,
        successPercentage = 100,
        dataProvider = SegmentGenerator.RND_DIFFERENT_SLOPE
    )
    void testNonColinear(Segment s1, Segment s2) {
        assert !s1.isCollinear(s2) : "alignment: segments " + s1 + " " + s2 + " should be accepted as NOT colinear";
    }
}
