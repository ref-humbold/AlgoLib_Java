package algolib.mathmat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Structure of fraction
public class FractionTest
{
    // region of

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

    // endregion
    // region cast functions

    @Test
    public void intValue_ThenIntegerValueRoundedTowardsZero()
    {    // when
        int result = Fraction.of(-129, 20).intValue();
        // then
        Assertions.assertThat(result).isEqualTo(-6);
    }

    @Test
    public void doubleValue_ThenDoubleValue()
    {
        // when
        double result = Fraction.of(-129, 20).doubleValue();
        // then
        Assertions.assertThat(result).isEqualTo(-6.45);
    }

    // endregion
    // region unary functions

    @Test
    public void negate_WhenPositive_ThenNegative()
    {
        // when
        Fraction result = Fraction.of(3, 10).negate();
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(-3, 10));
    }

    @Test
    public void negate_WhenNegative_ThenPositive()
    {
        // when
        Fraction result = Fraction.of(-3, 10).negate();
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(3, 10));
    }

    @Test
    public void negate_WhenZero_ThenZero()
    {
        // when
        Fraction result = Fraction.of(-3, 10).negate();
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(3, 10));
    }

    @Test
    public void invert_ThenInverted()
    {
        // when
        Fraction result = Fraction.of(23, 18).invert();
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(18, 23));
    }

    @Test
    public void invert_WhenZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(Fraction.of(0)::invert);
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    // endregion
    // region binary functions

    @Test
    public void add_ThenDenominatorEqualsLCM()
    {
        // when
        Fraction result = Fraction.of(1, 2).add(Fraction.of(5, 7));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(17, 14));
    }

    @Test
    public void add_WhenInteger_ThenAdded()
    {
        // when
        Fraction result = Fraction.of(7, 3).add(4);
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(19, 3));
    }

    @Test
    public void subtract_ThenNormalized()
    {
        // when
        Fraction result = Fraction.of(1, 2).subtract(Fraction.of(3, 10));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(1, 5));
    }

    @Test
    public void subtract_WhenInteger_ThenSubtracted()
    {
        // when
        Fraction result = Fraction.of(7, 3).subtract(4);
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(-5, 3));
    }

    @Test
    public void multiply_ThenNormalized()
    {
        // when
        Fraction result = Fraction.of(3, 7).multiply(Fraction.of(5, 12));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(5, 28));
    }

    @Test
    public void multiplied_WhenInteger_ThenMultiplied()
    {
        // when
        Fraction result = Fraction.of(7, 3).multiply(4);
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(28, 3));
    }

    @Test
    public void divide_ThenNormalized()
    {
        // when
        Fraction result = Fraction.of(9, 14).divide(Fraction.of(2, 5));
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(45, 28));
    }

    @Test
    public void divide_WhenZero_ThenArithmeticException()
    {
        // when
        Throwable throwable =
                Assertions.catchThrowable(() -> Fraction.of(9, 14).divide(Fraction.of(0)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void divide_WhenInteger_ThenDivided()
    {
        // when
        Fraction result = Fraction.of(7, 3).divide(4);
        // then
        Assertions.assertThat(result).isEqualTo(Fraction.of(7, 12));
    }

    // endregion
    // region comparisons

    @Test
    public void compareTo_WhenSameDenominatorAndGreaterNumerator_ThenNegative()
    {
        // when
        int result = Fraction.of(9, 14).compareTo(Fraction.of(17, 14));
        // then
        Assertions.assertThat(result).isLessThan(0);
    }

    @Test
    public void compareTo_WhenSameNumeratorAndGreaterDenominator_ThenPositive()
    {
        // when
        int result = Fraction.of(9, 14).compareTo(Fraction.of(9, 26));
        // then
        Assertions.assertThat(result).isGreaterThan(0);
    }

    @Test
    public void compareTo_WhenDifferentFraction_ThenCompareFractions()
    {
        // when
        int result = Fraction.of(9, 14).compareTo(Fraction.of(3, 5));
        // then
        Assertions.assertThat(result).isGreaterThan(0);
    }

    @Test
    public void compareTo_WhenSameNormalizedFraction_ThenZero()
    {
        // when
        int result = Fraction.of(9, 15).compareTo(Fraction.of(3, 5));
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void compareTo_WhenDouble_ThenCompareAsDoubles()
    {
        // when
        int result = Fraction.of(11, 3).compareTo(2.67);
        // then
        Assertions.assertThat(result).isGreaterThan(0);
    }

    @Test
    public void compareTo_WhenEqualToDouble_ThenZero()
    {
        // when
        int result = Fraction.of(-13, 8).compareTo(-1.625);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void compareTo_WhenInt_ThenCompareAsDoubles()
    {
        // when
        int result = Fraction.of(-31, 6).compareTo(-4);
        // then
        Assertions.assertThat(result).isLessThan(0);
    }

    @Test
    public void compareTo_WhenEqualToInt_ThenZero()
    {
        // when
        int result = Fraction.of(125, 5).compareTo(25);
        // then
        Assertions.assertThat(result).isZero();
    }

    // endregion

    @Test
    public void toString_ThenStringRepresentation()
    {
        // when
        String result = Fraction.of(129, -20).toString();
        // then
        Assertions.assertThat(result).isEqualTo("-129/20");
    }
}
