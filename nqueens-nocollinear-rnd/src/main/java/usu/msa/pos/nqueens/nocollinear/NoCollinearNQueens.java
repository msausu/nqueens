
package usu.msa.pos.nqueens.nocollinear;

import java.util.ArrayList;
import java.util.List;
import static usu.msa.pos.nqueens.nocollinear.Tested.MAX_TRIALS;
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

    public final Tested tested;
    public final Board board;
    public final List<int[]> solutions = new ArrayList<>();

    public NoCollinearNQueens(int n, int trials) {
        tested = new Tested(trials);
        board = new Board(n);
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
            if (!tested.isTested(board)) {
                if (board.isValid()) solutions.add(board.asArray());
                tested.addTest(board);
                if (solutions.size() >= minSolutions) 
                    return;
            }
            board.randomize();
        }
        System.err.println("Limit " + maxTrials + " reached");
    }
    
    public List<int[]> getSolutions() {
        return solutions;
    }
}
