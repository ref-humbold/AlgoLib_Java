package algolib.maths;

import java.util.Objects;

/** Structure of fraction. */
public final class Fraction
        extends Number
        implements Comparable<Number>
{
    private static final long serialVersionUID = 330776497365163091L;

    private final long numerator;
    private final long denominator;

    private Fraction(long numerator, long denominator)
    {
        super();

        if(denominator == 0)
            throw new ArithmeticException("Denominator is zero");

        if(denominator < 0)
        {
            numerator = -numerator;
            denominator = -denominator;
        }

        long gcd = Maths.gcd(numerator, denominator);

        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public static Fraction of(long numerator)
    {
        return Fraction.of(numerator, 1);
    }

    public static Fraction of(long numerator, long denominator)
    {
        return new Fraction(numerator, denominator);
    }

    public static Fraction fromInt(int value)
    {
        return Fraction.fromLong(value);
    }

    public static Fraction fromLong(long value)
    {
        return Fraction.of(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Fraction other))
            return false;

        return Objects.equals(numerator, other.numerator) && Objects.equals(denominator,
                                                                            other.denominator);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public String toString()
    {
        return "%d/%d".formatted(numerator, denominator);
    }

    /**
     * Negates this fraction.
     * @return the negated fraction
     */
    public Fraction negate()
    {
        return Fraction.of(-numerator, denominator);
    }

    /**
     * Inverts this fraction.
     * @return the inverted fraction
     * @throws ArithmeticException if this fraction is equal to zero
     */
    public Fraction invert()
    {
        if(numerator == 0)
            throw new ArithmeticException("Value of zero cannot be inverted");

        return Fraction.of(denominator, numerator);
    }

    /**
     * Adds given fraction to this fraction.
     * @param f the fraction
     * @return the result of addition
     */
    public Fraction add(Fraction f)
    {
        return Fraction.of(numerator * f.denominator + f.numerator * denominator,
                           denominator * f.denominator);
    }

    /**
     * Adds given number to this fraction.
     * @param i the number
     * @return the result of addition
     */
    public Fraction add(int i)
    {
        return add(fromInt(i));
    }

    /**
     * Adds given number to this fraction.
     * @param i the number
     * @return the result of addition
     */
    public Fraction add(long i)
    {
        return add(fromLong(i));
    }

    /**
     * Subtracts given fraction from this fraction.
     * @param f the fraction
     * @return the result of subtraction
     */
    public Fraction subtract(Fraction f)
    {
        return Fraction.of(numerator * f.denominator - f.numerator * denominator,
                           denominator * f.denominator);
    }

    /**
     * Subtracts given number from this fraction.
     * @param i the number
     * @return the result of subtraction
     */
    public Fraction subtract(int i)
    {
        return subtract(fromInt(i));
    }

    /**
     * Subtracts given number from this fraction.
     * @param i the number
     * @return the result of subtraction
     */
    public Fraction subtract(long i)
    {
        return subtract(fromLong(i));
    }

    /**
     * Multiplies this fraction by given fraction.
     * @param f the fraction
     * @return the result of multiplication
     */
    public Fraction multiply(Fraction f)
    {
        return Fraction.of(numerator * f.numerator, denominator * f.denominator);
    }

    /**
     * Multiplies this fraction by given number.
     * @param i the number
     * @return the result of multiplication
     */
    public Fraction multiply(int i)
    {
        return multiply(fromInt(i));
    }

    /**
     * Multiplies this fraction by given number.
     * @param i the number
     * @return the result of multiplication
     */
    public Fraction multiply(long i)
    {
        return multiply(fromLong(i));
    }

    /**
     * Divides this fraction by given fraction.
     * @param f the fraction
     * @return the result of division
     * @throws ArithmeticException if divisor is equal to zero
     */
    public Fraction divide(Fraction f)
    {
        if(f.numerator == 0)
            throw new ArithmeticException("Division by zero");

        return Fraction.of(numerator * f.denominator, denominator * f.numerator);
    }

    /**
     * Divides this fraction by given number.
     * @param i the number
     * @return the result of division
     * @throws ArithmeticException if divisor is equal to zero
     */
    public Fraction divide(int i)
    {
        return divide(fromInt(i));
    }

    /**
     * Divides this fraction by given number.
     * @param i the number
     * @return the result of division
     * @throws ArithmeticException if divisor is equal to zero
     */
    public Fraction divide(long i)
    {
        return divide(fromLong(i));
    }

    @Override
    public int intValue()
    {
        return (int)(numerator / denominator);
    }

    @Override
    public long longValue()
    {
        return numerator / denominator;
    }

    @Override
    public float floatValue()
    {
        return (1.0f * numerator) / denominator;
    }

    @Override
    public double doubleValue()
    {
        return (1.0 * numerator) / denominator;
    }

    @Override
    public int compareTo(Number number)
    {
        if(number instanceof Fraction)
            return compare((Fraction)number);

        return Double.compare(doubleValue(), number.doubleValue());
    }

    private int compare(Fraction f)
    {
        long commonDenominator = Maths.lcm(denominator, f.denominator);

        long thisNumerator = commonDenominator / denominator * numerator;
        long otherNumerator = commonDenominator / f.denominator * f.numerator;

        return Long.compare(thisNumerator, otherNumerator);
    }
}
