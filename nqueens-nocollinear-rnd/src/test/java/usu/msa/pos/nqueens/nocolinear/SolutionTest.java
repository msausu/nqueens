package usu.msa.pos.nqueens.nocolinear;

import java.util.List;
import org.testng.annotations.Test;
import usu.msa.pos.nqueens.nocollinear.Board;
import usu.msa.pos.nqueens.nocollinear.NoCollinearNQueens;
import usu.msa.pos.nqueens.nocollinear.geo.Point;

/**
 *
 * @author msa
 */
public class SolutionTest {

    @Test(
            enabled = true,
            testName = "solution check",
            invocationCount = 1,
            threadPoolSize = 5,
            dataProviderClass = SolutionGenerator.class,
            dataProvider = "solutions",
            description = "Test solution satisfies",
            successPercentage = 95
    )
    void check(int boardSize, int minSolutions, int trials) {
        NoCollinearNQueens queens = new NoCollinearNQueens(boardSize, trials);
        queens.solve(minSolutions, trials);
        List<Board> solutions = queens.getSolutions();
        boolean invalid = solutions.stream().anyMatch(board -> Point.hasCollinear(board.asPoints()));
        assert solutions.size() > 0 && !invalid :
                "solution check failed:\n" + solutions + "\nshould be ok";
    }

    private static void debug(List<Board> boards) {
        boards.stream().forEach(board -> {
            System.out.println("\n" + board + "\nhas collinear: " + Point.hasCollinear(board.asPoints()));
        });
    }
}
