package algolib.mathmat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class FractionTest
{
    @Test
    public void constructor_WhenNumeratorAndDenominatorAreDivisible_ThenNormalized()
    {
        // when
        Fraction result = new Fraction(32, 104);
        // then
        Assertions.assertEquals(Fraction.of(4, 13), result);
    }

    @Test
    public void constructor_WhenOnlyNumerator_ThenDenominatorEqualsOne()
    {
        // given
        int value = 29;
        // when
        Fraction result = new Fraction(value);
        // then
        Assertions.assertEquals(0, result.compareTo(value));
    }

    @Test
    public void constructor_WhenDenominatorIsZero_ThenArithmeticException()
    {
        // when
        Executable executable = () -> new Fraction(1, 0);
        // then
        Assertions.assertThrows(ArithmeticException.class, executable);
    }

    @Test
    public void constructor_WhenNumeratorIsNegative_ThenNegativeFraction()
    {
        // when
        Fraction result = new Fraction(-4, 11);
        // then
        Assertions.assertEquals(-1, result.compareTo(0L));
    }

    @Test
    public void constructor_WhenDenominatorIsNegative_ThenNegativeFraction()
    {
        // when
        Fraction result = new Fraction(4, -11);
        // then
        Assertions.assertEquals(-1, result.compareTo(0L));
    }

    @Test
    public void constructor_WhenNumeratorAndDenominatorAreNegative_ThenPositiveFraction()
    {
        // when
        Fraction result = new Fraction(-4, -11);
        // then
        Assertions.assertEquals(1, result.compareTo(0L));
    }

    @Test
    public void add_WhenFraction_ThenDenominatorEqualsLCM()
    {
        // given
        Fraction fraction = Fraction.of(1, 2);
        // when
        Fraction result = fraction.add(Fraction.of(5, 7));
        // then
        Assertions.assertEquals(Fraction.of(17, 14), result);
    }

    @Test
    public void subtract_WhenFraction_ThenNormalized()
    {
        // given
        Fraction fraction = Fraction.of(1, 2);
        // when
        Fraction result = fraction.subtract(Fraction.of(3, 10));
        // then
        Assertions.assertEquals(Fraction.of(1, 5), result);
    }

    @Test
    public void multiply_WhenFraction_ThenNormalized()
    {
        // given
        Fraction fraction = Fraction.of(3, 7);
        // when
        Fraction result = fraction.multiply(Fraction.of(5, 12));
        // then
        Assertions.assertEquals(Fraction.of(5, 28), result);
    }

    @Test
    public void divide_WhenFraction_ThenNormalized()
    {
        // given
        Fraction fraction = Fraction.of(9, 14);
        // when
        Fraction result = fraction.divide(Fraction.of(2, 5));
        // then
        Assertions.assertEquals(Fraction.of(45, 28), result);
    }

    @Test
    public void invert_WhenProperFraction_ThenInverted()
    {
        // given
        Fraction fraction = Fraction.of(23, 18);
        // when
        Fraction result = fraction.invert();
        // then
        Assertions.assertEquals(Fraction.of(18, 23), result);
    }

    @Test
    public void invert_WhenZero_ThenArithmeticException()
    {
        // given
        Fraction fraction = new Fraction(0);
        // when
        Executable executable = () -> fraction.invert();
        // then
        Assertions.assertThrows(ArithmeticException.class, executable);
    }
}
