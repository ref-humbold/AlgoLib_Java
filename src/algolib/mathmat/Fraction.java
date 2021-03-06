package algolib.mathmat;

import java.util.Objects;

/** Structure of fraction */
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

        if(!(obj instanceof Fraction))
            return false;

        Fraction other = (Fraction)obj;

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
        return String.format("%d/%d", numerator, denominator);
    }

    public Fraction add(Fraction f)
    {
        return Fraction.of(numerator * f.denominator + f.numerator * denominator,
                           denominator * f.denominator);
    }

    public Fraction subtract(Fraction f)
    {
        return Fraction.of(numerator * f.denominator - f.numerator * denominator,
                           denominator * f.denominator);
    }

    public Fraction multiply(Fraction f)
    {
        return Fraction.of(numerator * f.numerator, denominator * f.denominator);
    }

    public Fraction divide(Fraction f)
    {
        if(f.numerator == 0)
            throw new ArithmeticException("Division by zero");

        return Fraction.of(numerator * f.denominator, denominator * f.numerator);
    }

    public Fraction invert()
    {
        if(numerator == 0)
            throw new ArithmeticException("Value of zero cannot be inverted");

        return Fraction.of(denominator, numerator);
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
