package algolib.maths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// Tests: Algorithms for testing prime numbers.
public class PrimesTestingTest
{
    //region testPrimeFermat

    // 1001 = 7 * 11 * 13 ; 3481 = 59 ^ 2
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 77, 1001, 3481 })
    public void testPrimeFermat_WhenIntNotPrime_ThenFalse(int number)
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(number);

        // then
        Assertions.assertThat(result).isFalse();
    }

    // 41041 = 7 * 11 * 13 * 41 ; 73627 = 17 * 61 * 71
    @ParameterizedTest
    @ValueSource(longs = { 41041L, 73627L })
    public void testPrimeFermat_WhenLongNotPrime_ThenFalse(long number)
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(number);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 107, 1013 })
    public void testPrimeFermat_WhenIntIsPrime_ThenTrue(int number)
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(number);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = { 2131L, 6199L })
    public void testPrimeFermat_WhenLongIsPrime_ThenTrue(long number)
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(number);

        // then
        Assertions.assertThat(result).isTrue();
    }

    //endregion
    //region testPrimeMiller

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 77, 1001, 3481 })
    public void testPrimeMiller_WhenIntNotPrime_ThenFalse(int number)
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(number);

        // then
        Assertions.assertThat(result).isFalse();
    }

    // 41041 = 7 * 11 * 13 * 41 ; 73627 = 17 * 61 * 71
    @ParameterizedTest
    @ValueSource(longs = { 41041L, 73627L })
    public void testPrimeMiller_WhenLongNotPrime_ThenFalse(long number)
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(number);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 107, 1013 })
    public void testPrimeMiller_WhenIntIsPrime_ThenTrue(int number)
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(number);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = { 2131L, 6199L })
    public void testPrimeMiller_WhenLongIsPrime_ThenTrue(long number)
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(number);

        // then
        Assertions.assertThat(result).isTrue();
    }

    //endregion
}
