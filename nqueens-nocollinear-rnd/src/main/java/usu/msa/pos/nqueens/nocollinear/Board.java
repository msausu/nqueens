package usu.msa.pos.nqueens.nocollinear;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import usu.msa.pos.nqueens.nocollinear.geo.Point;
import usu.msa.pos.nqueens.nocollinear.geo.Segment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import umontreal.ssj.rng.LFSR113;
import umontreal.ssj.rng.RandomPermutation;

/**
 *
 * @author msa
 * @param <T> type of Segment (generating segments, ordering)
 */
public class Board<T extends Segment> {

    final int[] array;
    List<Point> board;
    List<T> segments;
    Optional<Boolean> isValid = Optional.empty();
    private final HashFunction hf = Hashing.goodFastHash(64);
    private final LFSR113 rnd = new LFSR113();

    public Board(int n) {
        if (n < 1 || n > 10_000) {
            throw new IllegalArgumentException();
        }
        array = randomArray(n);
        board = asPoints(array);
    }

    public Board(int[] array) {
        this.array = new int[array.length];
        System.arraycopy(array, 0, this.array, 0, array.length);
        board = asPoints(this.array);
    }

    public boolean isValid() {
        return !Point.hasCollinear(board);
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

    public List<T> asSegments() {
        return segments;
    }

    List<T> segments(List<Point> points, Class<T> _class) {
        final List<T> segments = new ArrayList<>();
        try {
            Constructor t = _class.getConstructor(Point.class, Point.class);
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    if (i == j || ((Point)points.get(i)).isSameXorY((Point)points.get(j))) {
                        continue;
                    }
                    try {
                        segments.add((T) t.newInstance(points.get(i), points.get(j)));
                    } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                    }
                }
            }
            Collections.sort((List) segments);
            return segments;
        } catch (NoSuchMethodException e) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, e);
        }
        return segments;
    }

    public List<Point> asPoints() {
        return board;
    }

    public static List<Point> asPoints(int[] a) {
        List<Point> points = new ArrayList<>();
        IntStream.range(0, a.length).forEach(i -> points.add(new Point(i, a[i])));
        return points;
    }

    public void randomize() {
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

    private void idArray() {
        IntStream.range(0, array.length).forEach(i -> array[i] = i);
    }

    private int[] idArray(int n) {
        int[] id = new int[n];
        IntStream.range(0, n).forEach(i -> id[i] = i);
        return id;
    }

    final int[] randomArray(int n) {
        int[] a = idArray(n);
        shuffle(a);
        return a;
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
