package ref_humbold.algolib.maths;

import java.util.Random;

import ref_humbold.algolib.structures.Pair;

public class PrimeChecking
{
    public static boolean fermatPrime(long number)
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

    public static boolean millerPrime(long number)
    {
        if(number == 2 || number == 3)
            return true;

        if(number < 2 || number % 2 == 0 || number % 3 == 0)
            return false;

        Pair<Long, Long> distribution = distribute(number - 1);
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
        Long power = 2L;
        Long exponent = 1L;

        while(number % power == 0)
        {
            ++exponent;
            power <<= 1;
        }

        --exponent;

        return Pair.make(exponent, number / (1L << exponent));
    }
}
