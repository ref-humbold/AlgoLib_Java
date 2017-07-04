package ref_humbold.algolib.maths;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Sieve
{
    public static Collection<Integer> findPrimes(int maxNumber)
    {
        return findPrimes(0, maxNumber);
    }

    public static Collection<Integer> findPrimes(int minNumber, int maxNumber)
    {
        if(maxNumber < minNumber)
            throw new IllegalArgumentException(
                "Second argument must be grater or equal to the first argument.");

        ArrayList<Integer> primes = new ArrayList<>();
        ArrayList<Boolean> is_prime = new ArrayList<>();
        ArrayList<Boolean> base_primes = new ArrayList<>(
            Collections.nCopies((int)(Math.sqrt(maxNumber) / 2), true));

        for(int i = minNumber; i <= maxNumber; ++i)
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
}
