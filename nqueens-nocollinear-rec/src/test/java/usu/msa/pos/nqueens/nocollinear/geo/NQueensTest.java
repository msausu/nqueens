package usu.msa.pos.nqueens.nocollinear.geo;

import org.testng.annotations.Test;
import usu.msa.pos.nqueens.nocollinear.Board;

/**
 *
 * @author msa
 */
public class NQueensTest {

    @Test(
            enabled = true,
            testName = "test-count-nqueens",
            invocationCount = 1,
            threadPoolSize = 5,
            description = "Check the NUMBER of NQueens solutions",
            dataProviderClass = SampleGenerator.class,
            successPercentage = 100,
            dataProvider = SampleGenerator.OEIS_A000170
    )
    void test00CountNQueens(int size, int numSolutionsOEIS) {
        Board board = new Board(size, true);
        board.solve();
        int numSolutions = board.getSolutions().size();
        assert numSolutions == numSolutionsOEIS : "NQueens " + size + " solution count " + numSolutions + " != " + numSolutionsOEIS;
    }

}
