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

    public Fraction(long numerator)
    {
        this(numerator, 1);
    }

    public Fraction(long numerator, long denominator)
    {
        super();

        if(denominator == 0)
            throw new ArithmeticException("Denominator cannot be equal to zero");

        if(denominator < 0)
        {
            numerator = -numerator;
            denominator = -denominator;
        }

        long gcd = Maths.gcd(numerator, denominator);

        numerator /= gcd;
        denominator /= gcd;

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fraction fromInt(int value)
    {
        return Fraction.fromLong(value);
    }

    public static Fraction fromLong(long value)
    {
        return new Fraction(value);
    }

    public static Fraction of(long numerator, long denominator)
    {
        return new Fraction(numerator, denominator);
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
        return new Fraction(numerator * f.denominator + f.numerator * denominator,
                            denominator * f.denominator);
    }

    public Fraction add(long d)
    {
        return add(Fraction.fromLong(d));
    }

    public Fraction subtract(Fraction f)
    {
        return new Fraction(numerator * f.denominator - f.numerator * denominator,
                            denominator * f.denominator);
    }

    public Fraction subtract(long d)
    {
        return subtract(Fraction.fromLong(d));
    }

    public Fraction multiply(Fraction f)
    {
        return new Fraction(numerator * f.numerator, denominator * f.denominator);
    }

    public Fraction multiply(long d)
    {
        return multiply(Fraction.fromLong(d));
    }

    public Fraction divide(Fraction f)
    {
        if(f.numerator == 0)
            throw new ArithmeticException("Division by zero");

        return new Fraction(numerator * f.denominator, denominator * f.numerator);
    }

    public Fraction divide(long d)
    {
        return divide(Fraction.fromLong(d));
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
        long lcm = Maths.lcm(denominator, f.denominator);

        long thisNumerator = lcm / denominator * numerator;
        long otherNumerator = lcm / f.denominator * f.numerator;

        return Long.compare(thisNumerator, otherNumerator);
    }
}
