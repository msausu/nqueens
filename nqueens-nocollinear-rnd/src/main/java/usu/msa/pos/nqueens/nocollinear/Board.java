package usu.msa.pos.nqueens.nocollinear;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import usu.msa.pos.nqueens.nocollinear.geo.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import umontreal.ssj.rng.LFSR113;
import umontreal.ssj.rng.RandomPermutation;

/**
 *
 * @author msa
 */
public class Board {

    final int[] array;
    List<Point> board;
    Optional<Boolean> isValid = Optional.empty();
    private final HashFunction hf = Hashing.goodFastHash(64);
    private final LFSR113 rnd = new LFSR113();

    public Board(int n) {
        if (n < 1 || n > 10_000) {
            throw new IllegalArgumentException();
        }
        array = idArray(n);
        randomize();
        board = asPoints(array);
    }

    public Board(int[] array) {
        this.array = new int[array.length];
        System.arraycopy(array, 0, this.array, 0, array.length);
        board = asPoints(this.array);
    }

    public boolean isValid() {
        return !has2DiagonalPoints() && !Point.hasCollinear(board);
    }
    
    public boolean has2DiagonalPoints() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (Math.abs(array[i] - array[j]) == j - i) return true;
            }
        }
        return false;
    }

    public byte[] asBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(array.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(array);
        return byteBuffer.array();
    }

    public int[] asArray() {
        int[] a = new int[array.length];
        System.arraycopy(array, 0, a, 0, array.length);
        return a;
    }


    public List<Point> asPoints() {
        return board;
    }

    public static List<Point> asPoints(int[] a) {
        List<Point> points = new ArrayList<>();
        IntStream.range(0, a.length).forEach(i -> points.add(new Point(i, a[i])));
        return points;
    }

    // todo improve: try pruning/backtracking
    public final void randomize() {
        RandomPermutation.shuffle(array, rnd);
        board = asPoints(array);
        isValid = Optional.empty();
    }

    // Fisher–Yates shuffle
    private void shuffle(int[] a) {
        Random r = ThreadLocalRandom.current();
        IntStream.range(0, a.length - 1).forEach(i -> swap(a, i, i + r.nextInt(a.length - i)));
    }

    void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private int[] idArray(int n) {
        return IntStream.range(0, n).toArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, array.length).forEach(i -> sb.append(array[i]).append(" "));
        return sb.toString().trim();
    }

    public long hash() {
        return hf.hashBytes(asBytes()).asLong();
    }

    @Override
    public Object clone() {
        return new Board(array);
    }
}
