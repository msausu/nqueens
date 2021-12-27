package usu.msa.pos.nqueens.nocollinear.num;

import org.apache.commons.math3.fraction.Fraction;

/**
 *
 * @author msa
 */
public class Rational implements Comparable<Rational> {

    public static final Rational ZERO = new Rational(0, 0);
    private final Fraction fraction;

    public Rational(int x, int y) {

        if (x == 0 && y == 0) {
            fraction = Fraction.ZERO;
        } else if (x > 0 && y == 0) {
            fraction = new Fraction(Integer.MAX_VALUE, 1);
        } else if (x < 0 && y == 0) {
            fraction = new Fraction(Integer.MIN_VALUE, 1);
        } else {
            fraction = new Fraction(x, y);
        }
    }

    public Fraction get() {
        return fraction;
    }

    public Rational(float f) {
        this.fraction = new Fraction(f);
    }

    @Override
    public int compareTo(Rational t) {
        return equals(t) ? 0 : fraction.compareTo(t.fraction);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rational)) {
            return false;
        } else {
            return  ((Rational)o).fraction.equals(fraction);
        }
    }

    @Override
    public String toString() {
        return String.format("%+.3f", fraction.doubleValue());
    }

}
