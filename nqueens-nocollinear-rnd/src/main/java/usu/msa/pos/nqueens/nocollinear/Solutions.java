
package usu.msa.pos.nqueens.nocollinear;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author msa
 */
public class Solutions {
    
    public static final int MAX_TRIALS = 1_000_000_000;
    public static final float COLLISIONS = 0.01f; //0.005f;
    private final BloomFilter<byte[]> filter;
    private final Map<Long, Board> solutions = new HashMap<>();
    private int count = 0, skipped = 0, stores = 0;
    
    public Solutions() {
        this(MAX_TRIALS, COLLISIONS);
    }  
    
    public Solutions(int maxTrials) {
        this(maxTrials, COLLISIONS);
    } 
    
    public Solutions(int maxTrials, float factor) {
        filter = BloomFilter.create(Funnels.byteArrayFunnel(), Math.min(maxTrials, MAX_TRIALS), factor);
    }
    
    public void addTest(Board board, boolean result) {
        filter.put(board.asBytes());
        if (result) {
            stores++;
            solutions.put(board.hash(), (Board)board.clone());
        }
        count++;
    }
    
    public boolean isTested(Board board) {
        boolean ok = filter.mightContain(board.asBytes());
        if (ok) skipped++;
        return ok;
    }
    
    public int numSolutions() {
        return solutions.size();
    }
    
    public List<Board> get() {
        System.err.println("test count: " + count + " stores " + stores + " skips " + skipped);
        return solutions.values().stream().collect(Collectors.toList());
    } 
}
