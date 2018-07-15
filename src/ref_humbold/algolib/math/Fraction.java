// UŁAMKI
package ref_humbold.algolib.math;

import java.util.Objects;

public class Fraction
    extends Number
{
    private static final long serialVersionUID = 330776497365163091L;
    private long numerator;
    private long denominator;

    public Fraction(long numerator)
    {
        this(numerator, 1);
    }

    public Fraction(long numerator, long denominator)
    {
        super();

        if(denominator == 0)
            throw new ArithmeticException("Denominator equals zero.");

        this.numerator = numerator;
        this.denominator = denominator;
        this.normalize();
    }

    public static Fraction fromInt(int value)
    {
        return fromLong(value);
    }

    public static Fraction fromLong(long value)
    {
        return new Fraction(value);
    }

    public static Fraction from(long numerator, long denominator)
    {
        return new Fraction(numerator, denominator);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || !(obj instanceof Fraction))
            return false;

        Fraction other = (Fraction)obj;

        return Objects.equals(this.numerator, other.numerator) && Objects.equals(this.denominator,
                                                                                 other.denominator);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(numerator, denominator);
    }

    public Fraction add(Fraction f)
    {
        return new Fraction(this.numerator * f.denominator + f.numerator * this.denominator,
                            this.denominator * f.denominator);
    }

    public Fraction add(long d)
    {
        return this.add(Fraction.fromLong(d));
    }

    public Fraction sub(Fraction f)
    {
        return new Fraction(this.numerator * f.denominator - f.numerator * this.denominator,
                            this.denominator * f.denominator);
    }

    public Fraction sub(long d)
    {
        return this.sub(Fraction.fromLong(d));
    }

    public Fraction mult(Fraction f)
    {
        return new Fraction(this.numerator * f.numerator, this.denominator * f.denominator);
    }

    public Fraction mult(long d)
    {
        return this.mult(Fraction.fromLong(d));
    }

    public Fraction div(Fraction f)
    {
        if(f.numerator == 0)
            throw new ArithmeticException("Division by zero.");

        return new Fraction(this.numerator * f.denominator, this.denominator * f.numerator);
    }

    public Fraction div(long d)
    {
        return this.div(Fraction.fromLong(d));
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

    private void normalize()
    {
        long gcd = Maths.gcd(numerator, denominator);

        numerator /= gcd;
        denominator /= gcd;

        if(denominator < 0)
        {
            numerator = -numerator;
            denominator = -denominator;
        }
    }
}
