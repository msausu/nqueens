package usu.msa.pos.nqueens.nocolinear;

import java.util.List;
import usu.msa.pos.nqueens.nocollinear.geo.Point;
import org.testng.annotations.Test;

/**
 *
 * @author msa
 */
public class PointTest {
      
    @Test(
        enabled = false,
        testName = "test-no-three-in-line-01",
        invocationCount = 1,
        threadPoolSize = 5,
        description = "Test for 3 or more (selected) points are NOT aligned",
        dataProviderClass = PointGenerator.class,
        successPercentage = 100,
        dataProvider = PointGenerator.PARALLEL
    )
    void test01Unligned(List<Point> points) {
        assert !Point.hasCollinear(points) : "should fail: succeeded for unaligned points";
    }
    
    @Test(
        enabled = false,
        testName = "test-no-three-in-line-02",
        invocationCount = 1,
        threadPoolSize = 5,
        description = "Test for 3 or more (random) points are NOT aligned",
        dataProviderClass = PointGenerator.class,
        successPercentage = 100,
        dataProvider = PointGenerator.RND_DIFFERENT_SLOPE
    )
    void test02Unligned(List<Point> points) {
        assert !Point.hasCollinear(points) : "should fail: succeeded for unaligned points \n" + Point.print(points);
    }
    
    @Test(
        enabled = true,
        testName = "test-three-in-line-01",
        invocationCount = 1,
        threadPoolSize = 1,
        description = "Test for 3 or more (selected) points are aligned",
        dataProviderClass = PointGenerator.class,
        successPercentage = 100,
        dataProvider = PointGenerator.ALIGNED
    )
    void test01Aligned(List<Point> points) {
        assert Point.hasCollinear(points) : "point alignment test failed";
    }
    
    @Test(
        enabled = true,
        testName = "test-three-in-line-02",
        invocationCount = 1,
        threadPoolSize = 1,
        description = "Test for 3 or more (random) points are aligned",
        dataProviderClass = PointGenerator.class,
        successPercentage = 100,
        dataProvider = PointGenerator.RND_ALIGNED
    )
    void test02Aligned(List<Point> points) {
        boolean tst = Point.hasCollinear(points);
        if (!tst) {
            Point.print(points);
        }
        assert Point.hasCollinear(points) : "point alignment test failed";
    }    
}
