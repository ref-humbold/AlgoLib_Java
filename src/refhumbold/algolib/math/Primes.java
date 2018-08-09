// ALGORYTMY DLA LICZB PIERWSZYCH
package refhumbold.algolib.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import refhumbold.algolib.tuples.Pair;

public class Primes
{
    public static Collection<Integer> find(int maxNumber)
    {
        return Primes.find(0, maxNumber);
    }

    public static Collection<Integer> find(int minNumber, int maxNumber)
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

    public static boolean testFermat(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        Random rd = new Random();

        for(int i = 0; i < 12; ++i)
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

        Pair<Long, Long> distribution = Primes.distribute(number - 1);
        Random rd = new Random();

        for(int i = 0; i < 12; ++i)
        {
            long rdv = 1L + Math.abs(rd.nextLong()) % (number - 1);

            if(Maths.powerMod(rdv, distribution.getSecond(), number) != 1)
            {
                boolean isComposite = true;

                for(long j = 0L; j < distribution.getFirst(); ++j)
                {
                    long pwm = Maths.powerMod(rdv, (1L << j) * distribution.getSecond(), number);

                    isComposite = isComposite && pwm != number - 1;
                }

                if(isComposite)
                    return false;
            }
        }

        return true;
    }

    private static Pair<Long, Long> distribute(long number)
    {
        long power = 2L, exponent = 1L;

        while(number % power == 0)
        {
            ++exponent;
            power <<= 1;
        }

        --exponent;

        return Pair.make(exponent, number / (1L << exponent));
    }
}
