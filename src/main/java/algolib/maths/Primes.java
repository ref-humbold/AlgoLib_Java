package algolib.maths;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/** Algorithms for prime numbers. */
public final class Primes
{
    private static final int ATTEMPTS = 17;
    private static final Random random = new Random();

    /**
     * Searches for prime numbers less than given number.
     * @param maximum the maximal number, exclusive
     * @return the prime numbers
     */
    public static Collection<Integer> findPrimes(int maximum)
    {
        return Primes.findPrimes(0, maximum);
    }

    /**
     * Searches for prime numbers inside given range of numbers.
     * @param minimum the minimal number, inclusive
     * @param maximum the maximal number, exclusive
     * @return the prime numbers
     */
    public static Collection<Integer> findPrimes(int minimum, int maximum)
    {
        if(maximum <= minimum || maximum <= 2)
            return Collections.emptyList();

        int segmentSize = Double.valueOf(Math.sqrt(maximum)).intValue();
        int[] basePrimes = getBasePrimes(segmentSize);
        List<Integer> primes = new ArrayList<>();

        if(minimum < segmentSize)
            primes.addAll(IntStream.concat(IntStream.of(2), Arrays.stream(basePrimes))
                                   .filter(p -> p >= minimum)
                                   .boxed()
                                   .toList());

        for(int i = Math.max(minimum, segmentSize); i < maximum; i += segmentSize)
        {
            int[] segmentPrimes =
                    getSegmentPrimes(i, Math.min(i + segmentSize, maximum), basePrimes);

            primes.addAll(Arrays.stream(segmentPrimes).boxed().toList());
        }

        return primes;
    }

    /**
     * Checks whether given number is prime using Fermat prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testFermat(int number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            int witness = 2 + Math.abs(random.nextInt()) % (number - 2);

            if(Maths.gcd(witness, number) > 1 || Maths.power(witness, number - 1, number) != 1)
                return false;
        }

        return true;
    }

    /**
     * Checks whether given number is prime using Fermat prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testFermat(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            long witness = 2L + Math.abs(random.nextLong()) % (number - 2);

            if(Maths.gcd(witness, number) > 1 || Maths.power(witness, number - 1, number) != 1)
                return false;
        }

        return true;
    }

    /**
     * Checks whether given number is prime using Miller-Rabin prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testMiller(int number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        int multiplicand = number - 1;

        while(multiplicand % 2 == 0)
            multiplicand /= 2;

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            int witness = 1 + Math.abs(random.nextInt()) % (number - 1);

            if(Maths.power(witness, multiplicand, number) != 1)
            {
                if(IntStream.iterate(multiplicand, d -> d <= number / 2, d -> d * 2)
                            .boxed()
                            .allMatch(d -> Maths.power(witness, d, number) != number - 1))
                    return false;
            }
        }

        return true;
    }

    /**
     * Checks whether given number is prime using Miller-Rabin prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testMiller(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        long multiplicand = number - 1;

        while(multiplicand % 2 == 0)
            multiplicand /= 2;

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            long witness = 1L + Math.abs(random.nextLong()) % (number - 1);

            if(Maths.power(witness, multiplicand, number) != 1)
            {
                if(LongStream.iterate(multiplicand, d -> d <= number / 2, d -> d * 2)
                             .boxed()
                             .allMatch(d -> Maths.power(witness, d, number) != number - 1))
                    return false;
            }
        }

        return true;
    }

    // Extracts prime numbers between zero and given maximum value.
    private static int[] getBasePrimes(int baseMaximum)
    {
        List<Boolean> isPrime = new ArrayList<>(Collections.nCopies((baseMaximum - 1) / 2, true));

        for(int i = 0; i < Double.valueOf(Math.sqrt(baseMaximum) / 2).intValue(); ++i)
            if(isPrime.get(i))
            {
                int primeValue = 2 * i + 3;

                for(int j = primeValue * primeValue; j < baseMaximum; j += 2 * primeValue)
                    isPrime.set((j - 3) / 2, false);
            }

        return IntStream.range(0, isPrime.size())
                        .filter(isPrime::get)
                        .map(index -> 2 * index + 3)
                        .toArray();
    }

    // Extracts prime numbers from given range using given basic prime numbers.
    private static int[] getSegmentPrimes(int segmentStart, int segmentEnd, int[] basePrimes)
    {
        int segmentBegin = segmentStart + 1 - segmentStart % 2;
        List<Boolean> isPrime = IntStream.range(segmentBegin, segmentEnd)
                                         .filter(i -> i % 2 == 1)
                                         .mapToObj(i -> i > 2)
                                         .collect(Collectors.toList());

        for(int p : basePrimes)
        {
            int primeMultiple = (segmentBegin + p - 1) / p * p;
            int multipleStart = primeMultiple % 2 == 0 ? primeMultiple + p : primeMultiple;

            for(int i = multipleStart; i < segmentEnd; i += 2 * p)
                isPrime.set((i - segmentBegin) / 2, false);
        }

        return IntStream.range(0, isPrime.size())
                        .filter(isPrime::get)
                        .map(index -> segmentBegin + 2 * index)
                        .toArray();
    }
}
