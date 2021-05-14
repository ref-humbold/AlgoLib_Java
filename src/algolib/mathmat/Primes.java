package algolib.mathmat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

/** Algorithms for prime numbers */
public final class Primes
{
    private static final int ATTEMPTS = 17;
    private static final Random random = new Random();

    /**
     * Finds prime numbers inside a range of integers starting from 0.
     * @param maxNumber maximal number in range, exclusive
     * @return collection of prime numbers
     */
    public static Collection<Integer> find(int maxNumber)
    {
        return Primes.find(0, maxNumber);
    }

    /**
     * Finds prime numbers inside a range of integers.
     * @param minNumber minimal number in range, inclusive
     * @param maxNumber maximal number in range, exclusive
     * @return collection of prime numbers
     */
    public static Collection<Integer> find(int minNumber, int maxNumber)
    {
        if(maxNumber <= minNumber || maxNumber <= 2)
            return List.of();

        ArrayList<Integer> primes = new ArrayList<>();
        ArrayList<Boolean> is_prime = new ArrayList<>();
        ArrayList<Boolean> base_primes = new ArrayList<>(
                Collections.nCopies(Double.valueOf(Math.sqrt(maxNumber) / 2.0).intValue(), true));

        for(int i = minNumber; i < maxNumber; ++i)
            is_prime.add(i == 2 || (i > 2 && i % 2 != 0));

        for(int i = 0; i < base_primes.size(); ++i)
            if(base_primes.get(i))
            {
                int p = 2 * i + 3;
                int begin = minNumber < p * p ? p * p - minNumber : (p - minNumber % p) % p;

                for(int j = (p * p - 3) / 2; j < base_primes.size(); j += p)
                    base_primes.set(j, false);

                for(int j = begin; j < is_prime.size(); j += p)
                    is_prime.set(j, false);
            }

        for(int i = 0; i < is_prime.size(); ++i)
            if(is_prime.get(i))
                primes.add(minNumber + i);

        return primes;
    }

    /**
     * Checks whether specified number is prime running Fermat's prime test.
     * @param number number to check
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
     * Checks whether specified number is prime running Miller-Rabin's prime test.
     * @param number number to check
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
}
