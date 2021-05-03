
package usu.msa.pos.nqueens.nocollinear;

import java.util.List;
import static usu.msa.pos.nqueens.nocollinear.Solutions.MAX_TRIALS;
import usu.msa.pos.nqueens.nocollinear.geo.Segment;
/**
 *
 * @author msa
 *
 * NoCollinearNQueens randomized approach
 *
 * an experiment to test/check larger boards
 * generate random boards and check if constraints are satisfied
 * store tested boards in a bloom filter and solutions in a map
 * 
 * input: boardSize
 * output: array encoded by position starting at 0: 2 0 3 1 -> solution tuple
 * ((1, 3), (2, 1), (3, 4), (4, 2))
 * 
 */

public class NoCollinearNQueens {

    public final Solutions solutions; 
    public final Board board;

    public NoCollinearNQueens(int n, int trials) {
        solutions = new Solutions(trials);
        board = new Board<Segment>(n);
    }

    public void solve() {
        solve(1);
    }
    
    public void solve(int minSolutions) {
        solve(minSolutions, MAX_TRIALS);
    }
    
    public void solve(int minSolutions, int maxTrials) {
        board.randomize();
        for (int i = 0; i < maxTrials; i++) {
            if (!solutions.isTested(board)) {
                boolean isValid = board.isValid();
                solutions.addTest(board, isValid);
                if (solutions.numSolutions() >= minSolutions) 
                    return;
            }
            board.randomize();
        }
        System.err.println("Limit " + maxTrials + " reached");
    }
    
    public List<Board> getSolutions() {
        return solutions.get();
    }
}
