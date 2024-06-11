package algolib.maths;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for searching for prime numbers.
public class PrimesSearchingTest
{

    @Test
    public void findPrimes_WhenMinGreaterThanMax_ThenEmpty()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(100, 30);

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findPrimes_WhenSingleArgument_ThenMinIsZero()
    {
        // when
        Collection<Integer> result1 = PrimesSearching.findPrimes(100);
        Collection<Integer> result2 = PrimesSearching.findPrimes(0, 100);

        // then
        Assertions.assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void findPrimes_WhenMaxIsComposite_ThenAllPrimes()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(100);

        // then
        Assertions.assertThat(result)
                  .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
                                   61, 67, 71, 73, 79, 83, 89, 97);
    }

    @Test
    public void findPrimes_WhenMaxIsPrime_ThenMaxExclusive()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(67);

        // then
        Assertions.assertThat(result)
                  .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
                                   61);
    }

    @Test
    public void findPrimes_WhenMaxIsTwo_ThenEmpty()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(2);

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findPrimes_WhenMaxIsThree_ThenSingleElement()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(3);

        // then
        Assertions.assertThat(result).containsExactly(2);
    }

    @Test
    public void findPrimes_WhenMaxIsFour_ThenAllPrimes()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(4);

        // then
        Assertions.assertThat(result).containsExactly(2, 3);
    }

    @Test
    public void findPrimes_WhenRange_ThenPrimesBetween()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(30, 200);

        // then
        Assertions.assertThat(result)
                  .containsExactly(31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
                                   103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,
                                   173, 179, 181, 191, 193, 197, 199);
    }

    @Test
    public void findPrimes_WhenMinIsTwo_ThenTwoIncluded()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(2, 30);

        // then
        Assertions.assertThat(result).containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    }

    @Test
    public void findPrimes_WhenMinIsThree_ThenTwoNotIncluded()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(3, 30);

        // then
        Assertions.assertThat(result).containsExactly(3, 5, 7, 11, 13, 17, 19, 23, 29);
    }

    @Test
    public void findPrimes_WhenMaxIsFourthPowerOfPrime_ThenAllPrimesBetween()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(9, 81);

        // then
        Assertions.assertThat(result)
                  .containsExactly(11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                                   73, 79);
    }

    @Test
    public void findPrimes_WhenMinIsLessThanSquareRootOfMax_ThenPrimesBetween()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(5, 150);

        // then
        Assertions.assertThat(result)
                  .containsExactly(5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
                                   71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137,
                                   139, 149);
    }

    @Test
    public void findPrimes_WhenMinAndMaxArePrimes_ThenMinInclusiveAndMaxExclusive()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(137, 317);

        // then
        Assertions.assertThat(result)
                  .containsExactly(137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197,
                                   199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271,
                                   277, 281, 283, 293, 307, 311, 313);
    }

    @Test
    public void findPrimes_WhenMinEqualsMaxAndPrime_ThenEmpty()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(41, 41);

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void findPrimes_WhenMinEqualsMaxAndComposite_ThenEmpty()
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(91, 91);

        // then
        Assertions.assertThat(result).isEmpty();
    }
}
