package algolib.mathmat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Structure of fraction
public class FractionTest
{
    @Test
    public void of_WhenNumeratorAndDenominatorAreDivisible_ThenNormalized()
    {
        // when
        Fraction result = Fraction.of(32, 104);
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(4, 13));
    }

    @Test
    public void of_WhenOnlyNumerator_ThenDenominatorEqualsOne()
    {
        // given
        int value = 29;
        // when
        Fraction result = Fraction.of(value);
        // then
        Assertions.assertThat(result.compareTo(value)).isEqualTo(0);
    }

    @Test
    public void of_WhenDenominatorIsZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Fraction.of(1, 0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void of_WhenNumeratorIsNegative_ThenNegativeFraction()
    {
        // when
        Fraction result = Fraction.of(-4, 11);
        // then
        Assertions.assertThat(result.compareTo(0L)).isEqualTo(-1);
    }

    @Test
    public void of_WhenDenominatorIsNegative_ThenNegativeFraction()
    {
        // when
        Fraction result = Fraction.of(4, -11);
        // then
        Assertions.assertThat(result.compareTo(0L)).isEqualTo(-1);
    }

    @Test
    public void of_WhenNumeratorAndDenominatorAreNegative_ThenPositiveFraction()
    {
        // when
        Fraction result = Fraction.of(-4, -11);
        // then
        Assertions.assertThat(result.compareTo(0L)).isEqualTo(1);
    }

    @Test
    public void add_WhenFraction_ThenDenominatorEqualsLCM()
    {
        // given
        Fraction fraction = Fraction.of(1, 2);
        // when
        Fraction result = fraction.add(Fraction.of(5, 7));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(17, 14));
    }

    @Test
    public void subtract_WhenFraction_ThenNormalized()
    {
        // given
        Fraction fraction = Fraction.of(1, 2);
        // when
        Fraction result = fraction.subtract(Fraction.of(3, 10));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(1, 5));
    }

    @Test
    public void multiply_WhenFraction_ThenNormalized()
    {
        // given
        Fraction fraction = Fraction.of(3, 7);
        // when
        Fraction result = fraction.multiply(Fraction.of(5, 12));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(5, 28));
    }

    @Test
    public void divide_WhenFraction_ThenNormalized()
    {
        // given
        Fraction fraction = Fraction.of(9, 14);
        // when
        Fraction result = fraction.divide(Fraction.of(2, 5));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(45, 28));
    }

    @Test
    public void invert_WhenProperFraction_ThenInverted()
    {
        // given
        Fraction fraction = Fraction.of(23, 18);
        // when
        Fraction result = fraction.invert();
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(18, 23));
    }

    @Test
    public void invert_WhenZero_ThenArithmeticException()
    {
        // given
        Fraction fraction = Fraction.of(0);
        // when
        Throwable throwable = Assertions.catchThrowable(fraction::invert);
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }
}
