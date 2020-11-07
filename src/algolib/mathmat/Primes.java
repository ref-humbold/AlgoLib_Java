package algolib.mathmat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Algorithms for prime numbers */
public final class Primes
{
    private static final int ATTEMPTS = 16;

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

    public static boolean testFermat(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        Random rd = new Random();

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            long rdv = 1L + Math.abs(rd.nextLong()) % (number - 1);

            if(Maths.gcd(rdv, number) > 1 || Maths.powerMod(rdv, number - 1, number) != 1)
                return false;
        }

        return true;
    }

    public static boolean testMiller(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        long multip = number - 1;

        while(multip % 2 == 0)
            multip >>= 1;

        Random rd = new Random();

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            long rdv = 1L + Math.abs(rd.nextLong()) % (number - 1);

            if(Maths.powerMod(rdv, multip, number) != 1)
            {
                boolean isComposite = true;

                for(long d = multip; d <= number / 2; d <<= 1)
                {
                    long pwm = Maths.powerMod(rdv, d, number);

                    isComposite = isComposite && pwm != number - 1;
                }

                if(isComposite)
                    return false;
            }
        }

        return true;
    }
}
