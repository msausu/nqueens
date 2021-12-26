package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.List;
import org.testng.annotations.Test;

/**
 *
 * @author msa
 */
public class PointTest {

    private static final boolean T3_SLOW = false, T3 = false, T4_SLOW = false, T4 = true;

    @Test(
            enabled = T3_SLOW,
            testName = "test-01-no-three-in-line",
            invocationCount = 1,
            threadPoolSize = 5,
            description = "Test for 3 or more (selected) points are NOT aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_3P_UNALIGNED
    )
    void test01Unligned(List<Point> points) {
        assert !points.isEmpty() && !Point.hasCollinearSlow(points) : "should fail: succeeded for unaligned points";
    }

    @Test(
            enabled = T3,
            testName = "test-02-no-three-in-line",
            invocationCount = 1,
            threadPoolSize = 5,
            description = "Test for 3 or more (selected) points are NOT aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_3P_UNALIGNED
    )
    void test02Unligned(List<Point> points) {
        assert !points.isEmpty() && !Point.hasCollinear(points) : "should fail: succeeded for unaligned points";
    }
    
    @Test(
            enabled = T4_SLOW,
            testName = "test-03-no-four-in-line",
            invocationCount = 1,
            threadPoolSize = 5,
            description = "Test for 4 or more (selected) points are NOT aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_4P_UNALIGNED
    )
    void test03Unligned(List<Point> points) {
        assert !points.isEmpty() && !Point.hasCollinearSlow(points) : "should fail: succeeded for unaligned points";
    }

    @Test(
            enabled = T4,
            testName = "test-04-no-four-in-line",
            invocationCount = 1,
            threadPoolSize = 1,
            description = "Test for 4 or more (selected) points are NOT aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_4P_UNALIGNED
    )
    void test04Unligned(List<Point> points) {
        assert !points.isEmpty() && !Point.hasCollinear(points) : "should fail: succeeded for unaligned points";
    }
    
    @Test(
            enabled = T3_SLOW,
            testName = "test-05-three-in-line",
            invocationCount = 1,
            threadPoolSize = 1,
            description = "Test for 3 or more (selected) points are aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_3P_ALIGNED
    )
    void test01Aligned(List<Point> points) {
        assert !points.isEmpty() && Point.hasCollinearSlow(points) : "point alignment test failed";
    }

    @Test(
            enabled = T3,
            testName = "test-06-three-in-line",
            invocationCount = 1,
            threadPoolSize = 1,
            description = "Test for 3 or more (random) points are aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_3P_ALIGNED
    )
    void test02Aligned(List<Point> points) {
        assert !points.isEmpty() && Point.hasCollinear(points) : "point alignment test failed";
    }
    
    @Test(
            enabled = T4_SLOW,
            testName = "test-07-four-in-line",
            invocationCount = 1,
            threadPoolSize = 1,
            description = "Test for 4 or more (selected) points are aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_4P_ALIGNED
    )
    void test03Aligned(List<Point> points) {
        assert !points.isEmpty() && Point.hasCollinearSlow(points) : "point alignment test failed";
    }

    @Test(
            enabled = T4,
            testName = "test-08-four-in-line",
            invocationCount = 1,
            threadPoolSize = 1,
            description = "Test for 4 or more (random) points are aligned",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.S_5X5_4P_ALIGNED
    )
    void test04Aligned(List<Point> points) {
        assert !points.isEmpty() && Point.hasCollinear(points) : "point alignment test failed";
    }
}
