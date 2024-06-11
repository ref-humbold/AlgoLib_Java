package algolib.maths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for testing prime numbers.
public class PrimesTestingTest
{
    //region testPrimeFermat

    @Test
    public void testPrimeFermat_WhenZero_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(0);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeFermat_WhenOne_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(1);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeFermat_WhenTwo_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(2);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeFermat_WhenPrime_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(1013);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeFermat_WhenPrimeLong1_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(2131L);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeFermat_WhenPrimeLong2_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(6199L);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeFermat_WhenComposite_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(1001); // 1001 = 7 * 11 * 13
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeFermat_WhenCompositeLong1_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(41041L); // 41041 = 7 * 11 * 13 * 41
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeFermat_WhenCompositeLong2_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(73627L); // 73627 = 17 * 61 * 71
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeFermat_WhenCompositeSquareOfPrime_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeFermat(3481); // 3481 = 59 ^ 2
        // then
        Assertions.assertThat(result).isFalse();
    }

    //endregion
    //region testPrimeMiller

    @Test
    public void testPrimeMiller_WhenZero_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(0);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeMiller_WhenOne_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(1);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeMiller_WhenTwo_ThenTrue_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(2);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeMiller_WhenPrime_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(1013);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeMiller_WhenPrimeLong1_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(2131L);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeMiller_WhenPrimeLong2_ThenTrue()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(6199L);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testPrimeMiller_WhenComposite_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(1001);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeMiller_WhenCompositeLong1_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(41041L);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeMiller_WhenCompositeLong2_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(73627L);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testPrimeMiller_WhenCompositeSquareOfPrime_ThenFalse()
    {
        // when
        boolean result = PrimesTesting.testPrimeMiller(3481);

        // then
        Assertions.assertThat(result).isFalse();
    }

    //endregion
}
