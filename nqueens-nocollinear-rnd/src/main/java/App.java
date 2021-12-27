
import usu.msa.pos.nqueens.nocollinear.NoCollinearNQueens;

/**
 *
 * @author msa
 */
public class App {

    public static void main(String[] args) {
        try {
            int boardSize = Integer.parseInt(args.length > 0 ? args[0] : "");
            int minSolutions = Integer.parseInt(args.length > 1 ? args[1] : "1");
            int maxTrials = Integer.parseInt(args.length > 2 ? args[2] : "1000000");
            NoCollinearNQueens queens = new NoCollinearNQueens(boardSize, maxTrials);
            queens.solve(minSolutions, maxTrials);
            queens.getSolutions().forEach(board -> System.out.println(print(board)));
        } catch (NumberFormatException e) {
            System.out.println("arguments: boardSize minSolutions? maxTrials?\ndefaults: minSolutions=1 maxTrials=1000000");
        }
    }
    
    private static String print(int[] board) {
        StringBuilder sb = new StringBuilder();
        for (int v : board) sb.append(v).append(' ');
        return sb.toString();
    }
}
