package algolib.maths;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

// Tests: Algorithms for searching for prime numbers.
public class PrimesSearchingTest
{
    private static final int[] Primes =
            { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79,
              83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
              179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269,
              271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373,
              379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467,
              479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593,
              599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691,
              701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821,
              823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937,
              941, 947, 953, 967, 971, 977, 983, 991, 997 };

    @Test
    public void findPrimes_WhenSingleArgument_ThenMinIsZero()
    {
        // when
        Collection<Integer> result1 = PrimesSearching.findPrimes(100);
        Collection<Integer> result2 = PrimesSearching.findPrimes(0, 100);

        // then
        Assertions.assertThat(result1).isEqualTo(result2);
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 3, 4, 67, 100, 155, 400, 499, 701, 911 })
    public void findPrimes_WhenMaximalNumber_ThenMaxExclusive(int number)
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(number);

        // then
        Collection<Integer> expected =
                Arrays.stream(Primes).filter(p -> p < number).boxed().toList();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("findPrimes_WhenRange_ThenMinInclusiveAndMaxExclusive_Params")
    public void findPrimes_WhenRange_ThenMinInclusiveAndMaxExclusive(int minimum, int maximum)
    {
        // when
        Collection<Integer> result = PrimesSearching.findPrimes(minimum, maximum);

        // then
        Collection<Integer> expected =
                Arrays.stream(Primes).filter(p -> p >= minimum && p < maximum).boxed().toList();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> findPrimes_WhenRange_ThenMinInclusiveAndMaxExclusive_Params()
    {
        int[] minimums = { 2, 3, 8, 25, 54, 71, 101, 243 };
        int[] maximums = { 54, 150, 243, 481, 625, 827, 1000 };

        return Arrays.stream(minimums)
                     .boxed()
                     .flatMap(min -> Arrays.stream(maximums)
                                           .mapToObj(max -> Arguments.of(min, max)));
    }
}
