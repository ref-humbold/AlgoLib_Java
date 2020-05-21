// Tests: Algorithms for basic mathematical computations
package algolib.mathmat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathsTest
{
    @Test
    public void gcd_WhenNumbersAreComposite()
    {
        // when
        long result = Maths.gcd(161, 46);
        // then
        Assertions.assertThat(result).isEqualTo(23);
    }

    @Test
    public void gcd_WhenNumbersArePrime()
    {
        // when
        long result = Maths.gcd(127, 41);
        // then
        Assertions.assertThat(result).isOne();
    }

    @Test
    public void gcd_WhenNumbersAreMutuallyPrime()
    {
        // when
        long result = Maths.gcd(119, 57);
        // then
        Assertions.assertThat(result).isOne();
    }

    @Test
    public void gcd_WhenOneOfNumbersIsMultipleOfAnother()
    {
        // given
        long number = 34;
        // when
        long result = Maths.gcd(number, number * 6);
        // then
        Assertions.assertThat(result).isEqualTo(number);
    }

    @Test
    public void gcd_WhenOneOfNumbersIsZero()
    {
        // given
        long number = 96;
        // when
        long result = Maths.gcd(number, 0);
        // then
        Assertions.assertThat(result).isEqualTo(number);
    }

    @Test
    public void lcm_WhenNumbersAreComposite()
    {
        // when
        long result = Maths.lcm(161, 46);
        // then
        Assertions.assertThat(result).isEqualTo(322);
    }

    @Test
    public void lcm_WhenNumbersArePrime()
    {
        // when
        long result = Maths.lcm(127, 41);
        // then
        Assertions.assertThat(result).isEqualTo(5207);
    }

    @Test
    public void lcm_WhenNumbersAreMutuallyPrime()
    {
        // when
        long result = Maths.lcm(119, 57);
        // then
        Assertions.assertThat(result).isEqualTo(6783);
    }

    @Test
    public void lcm_WhenOneOfNumbersIsMultipleOfAnother()
    {
        // given
        long number = 34;
        // when
        long result = Maths.lcm(number, number * 6);
        // then
        Assertions.assertThat(result).isEqualTo(number * 6);
    }

    @Test
    public void lcm_WhenOneOfNumbersIsZero_ThenZero()
    {
        // when
        long result = Maths.lcm(96, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void powerMod_WhenBaseIsZero_ThenZero()
    {
        // when
        long result = Maths.powerMod(0, 14, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void powerMod_WhenExponentIsZero_ThenOne()
    {
        // when
        long result = Maths.powerMod(14, 0, 0);
        // then
        Assertions.assertThat(result).isOne();
    }

    @Test
    public void powerMod_WhenBaseAndExponentAreZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.powerMod(0, 0, 0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void powerMod_WhenBaseAndExponentArePositive()
    {
        // when
        long result = Maths.powerMod(3, 10, 0);
        // then
        Assertions.assertThat(result).isEqualTo(59049);
    }

    @Test
    public void powerMod_WhenBaseIsNegativeAndExponentIsEven()
    {
        // when
        long result = Maths.powerMod(-3, 10, 0);
        // then
        Assertions.assertThat(result).isEqualTo(59049);
    }

    @Test
    public void powerMod_WhenBaseIsNegativeAndExponentIsOdd()
    {
        // when
        long result = Maths.powerMod(-3, 9, 0);
        // then
        Assertions.assertThat(result).isEqualTo(-19683);
    }

    @Test
    public void powerMod_WhenExponentIsNegative_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.powerMod(3, -10, 0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void powerMod_WhenModuloAndBaseArePositive()
    {
        // when
        long result = Maths.powerMod(5, 11, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(8125);
    }

    @Test
    public void powerMod_WhenModuloIsPositiveAndBaseIsNegative()
    {
        // when
        long result = Maths.powerMod(-5, 11, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(1875);
    }

    @Test
    public void powerMod_WhenModuloIsNegative_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.powerMod(5, 11, -10000));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void multMod_WhenFactor1IsZero()
    {
        // when
        long result = Maths.multMod(0, 14, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void multMod_WhenFactor2IsZero()
    {
        // when
        long result = Maths.multMod(14, 0, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void multMod_WhenFactorsAreZero()
    {
        // when
        long result = Maths.multMod(0, 0, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void multMod_WhenFactor1IsNegativeAndFactor2IsPositive()
    {
        // when
        long result = Maths.multMod(-3, 10, 0);
        // then
        Assertions.assertThat(result).isEqualTo(-30);
    }

    @Test
    public void multMod_WhenFactor1IsPositiveAndFactor2IsNegative()
    {
        // when
        long result = Maths.multMod(3, -10, 0);
        // then
        Assertions.assertThat(result).isEqualTo(-30);
    }

    @Test
    public void multMod_WhenFactorsAreNegative()
    {
        // when
        long result = Maths.multMod(-3, -10, 0);
        // then
        Assertions.assertThat(result).isEqualTo(30);
    }

    @Test
    public void multMod_WhenModuloAndFactorsArePositive()
    {
        // when
        long result = Maths.multMod(547, 312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(664);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactor1IsNegative()
    {
        // when
        long result = Maths.multMod(-547, 312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(9336);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactor2IsNegative()
    {
        // when
        long result = Maths.multMod(547, -312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(9336);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactorsAreNegative()
    {
        // when
        long result = Maths.multMod(-547, -312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(664);
    }

    @Test
    public void multMod_WhenModuloIsNegative_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.multMod(547, 312, -10000));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }
}
