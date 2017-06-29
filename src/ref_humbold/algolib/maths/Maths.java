// ALGORYTMY MATEMATYCZNE
package ref_humbold.algolib.maths;

public class Maths
{
    /**
     * Największy wspólny dzielnik dwóch liczb.
     * @param number1 pierwsza liczba
     * @param number2 druga liczba
     * @return największy wspólny dzielnik
     */
    public static long gcd(long number1, long number2)
    {
        long min_number = Math.min(number1, number2);
        long max_number = Math.max(number1, number2);

        while(min_number > 0)
        {
            long rem = max_number % min_number;

            max_number = min_number;
            min_number = rem;
        }

        return max_number;
    }

    /**
     * Najmniejsza wspólna wielokrotność dwóch liczb.
     * @param number1 pierwsza liczba
     * @param number2 druga liczba
     * @return najmniejsza wspólna wielokrotność
     */
    public static long lcm(long number1, long number2)
    {
        long min_number = Math.min(number1, number2);
        long max_number = Math.max(number1, number2);

        return max_number / gcd(number1, number2) * min_number;
    }

    /**
     * Szybkie potęgowanie binarne modulowane.
     * @param base podstawa
     * @param exponent wykładnik
     * @param modulo modulo
     * @return wynik potęgowania wzięty modulo
     */
    public static long powerI(long base, long exponent, long modulo)
    {
        long result = 1;

        if(exponent < 0)
            throw new ArithmeticException("Negative exponent.");

        if(base == 0 && exponent == 0)
            throw new ArithmeticException("Zero to the power of zero is NaN.");

        while(exponent > 0)
        {
            if((exponent & 1) == 1)
                result = multMod(result, base, modulo);

            base = multMod(base, base, modulo);
            exponent >>= 1;
        }

        return result;
    }

    /**
     * Mnożenie binarne modulowane.
     * @param factor1 pierwszy czynnik
     * @param factor2 drugi czynnik
     * @param modulo modulo
     * @return wynik mnożenia wzięty modulo
     */
    public static long multMod(long factor1, long factor2, long modulo)
    {
        long result = 0;

        if(factor2 < 0)
            return modulo - multMod(factor1, -factor2, modulo);

        while(factor2 > 0)
        {
            if((factor2 & 1) == 1)
                result = (result + factor1) % modulo;

            factor2 >>= 1;
            factor1 = (factor1 + factor1) % modulo;
        }

        return result;
    }
}