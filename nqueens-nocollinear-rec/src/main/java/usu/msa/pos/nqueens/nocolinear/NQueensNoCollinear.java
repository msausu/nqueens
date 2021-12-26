package usu.msa.pos.nqueens.nocolinear;

import usu.msa.pos.nqueens.nocollinear.geo.Point;

/**
 * @author msa
 *
 * NQueensNoCollinear recursive, simple, approach
 *
 * based on wikipedia's Heap's Algorithm page and Robert Sedgewick algs4
 * (https://algs4.cs.princeton.edu/home/) with additional filtering of collinear
 * points by slope. Segewick hypothesis: NQueens(n) ~ n! / 2.5^n
 *
 * input: boardSize, should be small (NP) output: array encoded by position
 * starting at 0: 2 0 3 1 -> solution tuple ((1, 3), (2, 1), (3, 4), (4, 2))
 */
public class NQueensNoCollinear {

    private final Board board;
    
    public NQueensNoCollinear(int n) {
        board = new Board(n);
        board.solve();
    }

    public static void help() {
        help("""
             java -jar build/libs/nqueens-nocollinear-rec.jar boardSize P?
             boardSize: integer between 1 and %d
             P?: optional board output, if absent numeric otherwise graphic (unicode console)
             """.formatted(Board.MAX_BOARD_SIZE)
        );
    }

    public static void help(String message) {
        System.err.println(message);
        System.exit(1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        board.getSolutions().stream().forEach(b -> {
            for (int x : b) {
                sb.append(x).append(' ');
            }
            sb.append('\n');
        });
        return sb.toString();
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        board.getSolutions().stream().forEach(solution -> sb.append(Board.print(Point.toPoints(solution))).append('\n'));
        return sb.toString();
    }
}
