package algolib.maths;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/** Algorithms for testing prime numbers. */
public final class PrimesTesting
{
    private static final int ATTEMPTS = 17;
    private static final Random random = new Random();

    //region testPrimeFermat

    /**
     * Checks whether given number is prime using Fermat prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testPrimeFermat(int number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            int witness = 2 + Math.abs(random.nextInt()) % (number - 2);

            if(Integers.gcd(witness, number) > 1
                    || Integers.power(witness, number - 1, number) != 1)
                return false;
        }

        return true;
    }

    /**
     * Checks whether given number is prime using Fermat prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testPrimeFermat(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        for(int i = 0; i < ATTEMPTS; ++i)
        {
            long witness = 2L + Math.abs(random.nextLong()) % (number - 2);

            if(Integers.gcd(witness, number) > 1
                    || Integers.power(witness, number - 1, number) != 1)
                return false;
        }

        return true;
    }

    //endregion
    //region testPrimeMiller

    /**
     * Checks whether given number is prime using Miller-Rabin prime test.
     * @param number the number
     * @return {@code true} if the number is probably prime, otherwise {@code false}
     */
    public static boolean testPrimeMiller(int number)
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

            if(Integers.power(witness, multiplicand, number) != 1)
            {
                if(IntStream.iterate(multiplicand, d -> d <= number / 2, d -> d * 2)
                            .boxed()
                            .allMatch(d -> Integers.power(witness, d, number) != number - 1))
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
    public static boolean testPrimeMiller(long number)
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

            if(Integers.power(witness, multiplicand, number) != 1)
            {
                if(LongStream.iterate(multiplicand, d -> d <= number / 2, d -> d * 2)
                             .boxed()
                             .allMatch(d -> Integers.power(witness, d, number) != number - 1))
                    return false;
            }
        }

        return true;
    }

    //endregion
}
