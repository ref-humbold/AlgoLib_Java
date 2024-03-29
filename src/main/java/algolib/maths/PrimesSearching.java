package algolib.maths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Algorithms for searching for prime numbers. */
public final class PrimesSearching
{
    /**
     * Searches for prime numbers less than given number.
     * @param maximum the maximal number, exclusive
     * @return the prime numbers
     */
    public static Collection<Integer> findPrimes(int maximum)
    {
        return PrimesSearching.findPrimes(0, maximum);
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
