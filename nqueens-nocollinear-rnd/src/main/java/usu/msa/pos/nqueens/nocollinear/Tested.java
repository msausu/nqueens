
package usu.msa.pos.nqueens.nocollinear;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 *
 * @author msa
 */
public class Tested {
    
    public static final int MAX_TRIALS = 1_000_000_000;
    public static final float COLLISIONS = 0.01f; //0.005f;
    private final BloomFilter<byte[]> filter;
    private int count = 0, skipped = 0;
    
    public Tested() {
        this(MAX_TRIALS, COLLISIONS);
    }  
    
    public Tested(int maxTrials) {
        this(maxTrials, COLLISIONS);
    } 
    
    public Tested(int maxTrials, float factor) {
        filter = BloomFilter.create(Funnels.byteArrayFunnel(), Math.min(maxTrials, MAX_TRIALS), factor);
    }
    
    public void addTest(Board board) {
        filter.put(board.asBytes());
        count++;
    }
    
    public boolean isTested(Board board) {
        boolean ok = filter.mightContain(board.asBytes());
        if (ok) skipped++;
        return ok;
    }
    
    @Override
    public String toString() {
        return "count " + count + " skips " + skipped;
    }
}
