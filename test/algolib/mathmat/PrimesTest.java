// Tests: Algorithms for prime numbers
package algolib.mathmat;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimesTest
{
    //region testFindPrimes

    @Test
    public void findPrimes_WhenTwoArgsDescending()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Primes.find(100, 30));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findPrimesOneArgIsTwoArgsWithZeroAsMin()
    {
        // when
        Collection<Integer> result1 = Primes.find(100);
        Collection<Integer> result2 = Primes.find(0, 100);
        // then
        Assertions.assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void findPrimesOneArg()
    {
        // when
        Collection<Integer> result = Primes.find(100);
        // then
        Assertions.assertThat(result)
                  .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
                                   61, 67, 71, 73, 79, 83, 89, 97);
    }

    @Test
    public void findPrimesOneArg_WhenMaxIsPrime()
    {
        // when
        Collection<Integer> result = Primes.find(67);
        // then
        Assertions.assertThat(result)
                  .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
                                   61, 67);
    }

    @Test
    public void findPrimesOneArg_WhenLessThanTwo()
    {
        // when
        Collection<Integer> result = Primes.find(1);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findPrimesTwoArgs()
    {
        // when
        Collection<Integer> result = Primes.find(30, 200);
        // then
        Assertions.assertThat(result)
                  .containsExactly(31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
                                   103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,
                                   173, 179, 181, 191, 193, 197, 199);
    }

    @Test
    public void findPrimesTwoArgs_WhenMinLessThanSqrtOfMax()
    {
        // when
        Collection<Integer> result = Primes.find(5, 150);
        // then
        Assertions.assertThat(result)
                  .containsExactly(5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
                                   71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137,
                                   139, 149);
    }

    @Test
    public void findPrimesTwoArgs_WhenMinAndMaxAreFindPrimes()
    {
        // when
        Collection<Integer> result = Primes.find(137, 317);
        // then
        Assertions.assertThat(result)
                  .containsExactly(137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197,
                                   199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271,
                                   277, 281, 283, 293, 307, 311, 313, 317);
    }

    @Test
    public void findPrimesTwoArgs_WhenMinEqualsMaxAndPrime()
    {
        // when
        Collection<Integer> result = Primes.find(41, 41);
        // then
        Assertions.assertThat(result).containsExactly(41);
    }

    @Test
    public void findPrimesTwoArgs_WhenMinEqualsMaxAndComposite()
    {
        // when
        Collection<Integer> result = Primes.find(91, 91);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    //endregion
    //region testTestFermat

    @Test
    public void testFermat_WhenZero_ThenFalse()
    {
        // when
        boolean result = Primes.testFermat(0);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testFermat_WhenOne_ThenFalse()
    {
        // when
        boolean result = Primes.testFermat(1);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testFermat_WhenTwo_ThenTrue()
    {
        // when
        boolean result = Primes.testFermat(2);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testFermat_WhenPrime_ThenTrue()
    {
        // when
        boolean result = Primes.testFermat(1013);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testFermat_WhenComposite_ThenFalse()
    {
        // when
        boolean result = Primes.testFermat(1001);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testFermat_WhenCarmichaelNumber_ThenFalse()
    {
        // when
        boolean result = Primes.testFermat(1105);  // 1105 = 5 * 13 * 17 is a Carmichael number
        // then
        Assertions.assertThat(result).isFalse();
    }

    //endregion
    //region testTestMiller

    @Test
    public void testMiller_WhenZero_ThenFalse()
    {
        // when
        boolean result = Primes.testMiller(0);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testMiller_WhenOne_ThenFalse()
    {
        // when
        boolean result = Primes.testMiller(1);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testMiller_WhenTwo_ThenTrue_ThenFalse()
    {
        // when
        boolean result = Primes.testMiller(2);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testMiller_WhenPrime_ThenTrue()
    {
        // when
        boolean result = Primes.testMiller(1013);
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testMiller_WhenComposite1_ThenFalse()
    {
        // when
        boolean result = Primes.testMiller(1001);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testMiller_WhenComposite2_ThenFalse()
    {
        // when
        boolean result = Primes.testMiller(1105);
        // then
        Assertions.assertThat(result).isFalse();
    }

    //endregion
}
