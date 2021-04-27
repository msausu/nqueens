package usu.msa.pos.nqueens.nocolinear;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static usu.msa.pos.nqueens.nocolinear.NQueensNoCollinear.help;
import usu.msa.pos.nqueens.nocollinear.geo.Point;

/**
 *
 * @author msa
 */
public class Board {

    public static final int MAX_SOLUTIONS = 100_000_000, MAX_BOARD_SIZE = 10_000; // testing
    private final List<int[]> solutions = new ArrayList<>();
    private final int n;
    private final int[] array;

    public Board(int n) {
        if (n < 1 || n > MAX_BOARD_SIZE) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.array = new int[n];
    }

    public static int boardSize(String size) {
        try {
            int n = Integer.parseInt(size);
            if (n < 1 || n > 25) { // 25! 27 digits
                throw new IllegalArgumentException();
            }
            return n;
        } catch (NullPointerException | IllegalArgumentException e) {
            help();
        }
        throw new IllegalStateException();
    }

    public void solve() {
        id();
        permutations(0);
    }

    public List<int[]> getSolutions() {
        return solutions;
    }

    // prune if queens in diagonal
    private boolean hasBacktracking(int i) {
        return IntStream.range(0, i).anyMatch(j -> Math.abs(array[i] - array[j]) == i - j);
    }

    // board permutations
    private void permutations(int i) {
        if (i == n) {
            if (n < 3 || Point.hasCollinear(Point.toPoints(array))) {
                return;
            }
            int[] solution = new int[array.length];
            System.arraycopy(array, 0, solution, 0, array.length);
            if (solutions.size() < MAX_SOLUTIONS) {
                solutions.add(solution);
            } else {
                help("Maximum number of solutions exceeded (needs reconfiguration)");
            }
        } else {
            IntStream.range(i, n).forEach(j -> {
                swap(i, j);
                if (!hasBacktracking(i)) {
                    permutations(i + 1);
                }
                swap(j, i);
            });
        }
    }

    private void swap(int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    private void id() {
        IntStream.range(0, n).forEach(i -> array[i] = i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, array.length).forEach(i -> sb.append(array[i]).append(" "));
        return sb.toString().trim();
    }
}
