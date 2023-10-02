package algolib.maths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for basic mathematical operations.
public class MathsTest
{
    //region gcd

    @Test
    public void gcd_WhenNumbersAreComposite_ThenGreatestCommonDivisor()
    {
        // when
        long result = Maths.gcd(161L, 46L);
        // then
        Assertions.assertThat(result).isEqualTo(23L);
    }

    @Test
    public void gcd_WhenNumbersArePrime_ThenOne()
    {
        // when
        int result = Maths.gcd(127, 41);
        // then
        Assertions.assertThat(result).isOne();
    }

    @Test
    public void gcd_WhenNumbersAreMutuallyPrime_ThenOne()
    {
        // when
        int result = Maths.gcd(119, 57);
        // then
        Assertions.assertThat(result).isOne();
    }

    @Test
    public void gcd_WhenOneOfNumbersIsMultipleOfAnother_ThenLessNumber()
    {
        // given
        int number = 34;
        // when
        int result = Maths.gcd(number, number * 6);
        // then
        Assertions.assertThat(result).isEqualTo(number);
    }

    @Test
    public void gcd_WhenOneOfNumbersIsZero_ThenZero()
    {
        // given
        int number = 96;
        // when
        int result = Maths.gcd(number, 0);
        // then
        Assertions.assertThat(result).isEqualTo(number);
    }

    // endregion
    // region lcm

    @Test
    public void lcm_WhenNumbersAreComposite_ThenLowestCommonMultiple()
    {
        // when
        long result = Maths.lcm(161L, 46L);
        // then
        Assertions.assertThat(result).isEqualTo(322L);
    }

    @Test
    public void lcm_WhenNumbersArePrime_ThenProduct()
    {
        // when
        int result = Maths.lcm(127, 41);
        // then
        Assertions.assertThat(result).isEqualTo(5207);
    }

    @Test
    public void lcm_WhenNumbersAreMutuallyPrime_ThenProduct()
    {
        // when
        int result = Maths.lcm(119, 57);
        // then
        Assertions.assertThat(result).isEqualTo(6783);
    }

    @Test
    public void lcm_WhenOneOfNumbersIsMultipleOfAnother_ThenGreaterNumber()
    {
        // given
        int number = 34;
        // when
        int result = Maths.lcm(number, number * 6);
        // then
        Assertions.assertThat(result).isEqualTo(number * 6);
    }

    @Test
    public void lcm_WhenOneOfNumbersIsZero_ThenZero()
    {
        // when
        int result = Maths.lcm(96, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    // endregion
    // region multiply

    @Test
    public void multiply_WhenFirstFactorIsZero_ThenZero()
    {
        // when
        int result = Maths.multiply(0, 14);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void multiply_WhenSecondFactorIsZero_ThenZero()
    {
        // when
        int result = Maths.multiply(14, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void multiply_WhenFactorsAreZero_ThenZero()
    {
        // when
        int result = Maths.multiply(0, 0);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void multiply_WhenFactorsArePositive_ThenResultIsPositive()
    {
        // when
        long result = Maths.multiply(3, 10);
        // then
        Assertions.assertThat(result).isEqualTo(30);
    }

    @Test
    public void multiply_WhenFirstFactorIsNegativeAndSecondFactorIsPositive_ThenResultIsNegative()
    {
        // when
        int result = Maths.multiply(-3, 10);
        // then
        Assertions.assertThat(result).isEqualTo(-30);
    }

    @Test
    public void multiply_WhenFirstFactorIsPositiveAndSecondFactorIsNegative_ThenResultIsNegative()
    {
        // when
        int result = Maths.multiply(3, -10);
        // then
        Assertions.assertThat(result).isEqualTo(-30);
    }

    @Test
    public void multiply_WhenFactorsAreNegative_ThenResultIsPositive()
    {
        // when
        long result = Maths.multiply(-3L, -10L);
        // then
        Assertions.assertThat(result).isEqualTo(30L);
    }

    @Test
    public void multiply_WhenModuloAndFactorsArePositive()
    {
        // when
        int result = Maths.multiply(547, 312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(664);
    }

    @Test
    public void multiply_WhenModuloIsPositiveAndFirstFactorIsNegative()
    {
        // when
        int result = Maths.multiply(-547, 312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(9336);
    }

    @Test
    public void multiply_WhenModuloIsPositiveAndSecondFactorIsNegative()
    {
        // when
        int result = Maths.multiply(547, -312, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(9336);
    }

    @Test
    public void multiply_WhenModuloIsPositiveAndFactorsAreNegative()
    {
        // when
        long result = Maths.multiply(-547L, -312L, 10000L);
        // then
        Assertions.assertThat(result).isEqualTo(664L);
    }

    @Test
    public void multiply_WhenModuloIsNegative_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.multiply(547, 312, -10000));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    // endregion
    // region power

    @Test
    public void power_WhenBaseIsZero_ThenZero()
    {
        // when
        int result = Maths.power(0, 14);
        // then
        Assertions.assertThat(result).isZero();
    }

    @Test
    public void power_WhenExponentIsZero_ThenOne()
    {
        // when
        int result = Maths.power(14, 0);
        // then
        Assertions.assertThat(result).isOne();
    }

    @Test
    public void power_WhenBaseAndExponentAreZero_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.power(0, 0));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void power_WhenBaseAndExponentArePositive_ThenResultIsPositive()
    {
        // when
        int result = Maths.power(3, 10);
        // then
        Assertions.assertThat(result).isEqualTo(59049);
    }

    @Test
    public void power_WhenBaseIsNegativeAndExponentIsEven_ThenResultIsPositive()
    {
        // when
        int result = Maths.power(-3, 10);
        // then
        Assertions.assertThat(result).isEqualTo(59049);
    }

    @Test
    public void power_WhenBaseIsNegativeAndExponentIsOdd_ThenResultIsNegative()
    {
        // when
        long result = Maths.power(-3L, 9L);
        // then
        Assertions.assertThat(result).isEqualTo(-19683L);
    }

    @Test
    public void power_WhenExponentIsNegative_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.power(3, -10));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void power_WhenModuloAndBaseArePositive()
    {
        // when
        int result = Maths.power(5, 11, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(8125);
    }

    @Test
    public void power_WhenModuloIsPositiveAndBaseIsNegativeAndExponentIsOdd()
    {
        // when
        int result = Maths.power(-5, 11, 10000);
        // then
        Assertions.assertThat(result).isEqualTo(1875);
    }

    @Test
    public void power_WhenModuloIsPositiveAndBaseIsNegativeAndExponentIsEven()
    {
        // when
        long result = Maths.power(-5L, 12L, 10000L);
        // then
        Assertions.assertThat(result).isEqualTo(625L);
    }

    @Test
    public void power_WhenModuloIsNegative_ThenArithmeticException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Maths.power(5, 11, -10000));
        // then
        Assertions.assertThat(throwable).isInstanceOf(ArithmeticException.class);
    }

    // endregion
}
