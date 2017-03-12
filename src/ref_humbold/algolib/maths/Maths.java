// ALGORYTMY MATEMATYCZNE
package ref_humbold.algolib.maths;

import java.lang.Math;

class Maths
{
    /*
    Największy wspólny dzielnik dwóch liczb.
    @param number1 pierwsza liczba
    @param number2 druga liczba
    @return największy wspólny dzielnik
    */
    public static long gcd(long number1, long number2)
    {
        long min_number = Math.min(number1, number2);
        long max_number = Math.max(number1, number2);

        while(min_number > 0)
        {
            long rem = max_number%min_number;

            max_number = min_number;
            min_number = rem;
        }

        return max_number;
    }

    /*
    Najmniejsza wspólna wielokrotność dwóch liczb.
    @param number1 pierwsza liczba
    @param number2 druga liczba
    @return najmniejsza wspólna wielokrotność
    */
    public static long lcm(long number1, long number2)
    {
        long min_number = Math.min(number1, number2);
        long max_number = Math.max(number1, number2);

        return max_number/gcd(number1, number2)*min_number;
    }

    /*
    Iteracyjne szybkie potęgowanie binarne.
    @param base podstawa
    @param exponent wykładnik
    @return wynik potęgowania
    */
    public static long powerI(long base, long exponent)
    {
        long result = 1;

        while(exponent > 0)
        {
            if( (exponent&1) == 1 )
                result = multI(result, base);

            base = multI(base, base);
            exponent >>= 1;
        }

        return result;
    }

    /*
    Iteracyjne szybkie potęgowanie binarne modulowane.
    @param base podstawa
    @param exponent wykładnik
    @param modulo modulo
    @return wynik potęgowania wzięty modulo
    */
    public static long powerI(long base, long exponent, long modulo)
    {
        long result = 1;

        while(exponent > 0)
        {
            if( (exponent&1) == 1 )
                result = multI(result, base, modulo);

            base = multI(base, base, modulo);
            exponent >>= 1;
        }

        return result%modulo;
    }

    /*
    Rekurencyjne szybkie potęgowanie binarne.
    @param base podstawa
    @param exponent wykładnik
    @return wynik potęgowania
    */
    public static long powerR(long base, long exponent)
    {
        long result = 1;

        if(exponent > 0)
            result = powerR(multI(base, base), exponent>>1);

        return (exponent&1) == 1 ? multI(base, result) : result;
    }

    /*
    Rekurencyjne szybkie potęgowanie binarne modulowane.
    @param base podstawa
    @param exponent wykładnik
    @param modulo modulo
    @return wynik potęgowania wzięty modulo
    */
    public static long powerR(long base, long exponent, long modulo)
    {
        long result = 1;

        if(exponent > 0)
            result = powerR(multI(base, base, modulo), exponent>>1);

        return (exponent&1) == 1 ? multI(base, result, modulo) : result%modulo;
    }

    /*
    Iteracyjne mnożenie binarne.
    @param factor1 pierwszy czynnik
    @param factor2 drugi czynnik
    @return wynik mnożenia
    */
    public static long multI(long factor1, long factor2)
    {
        long result = 0;

        while(factor2 > 0)
        {
            if( (factor2&1) == 1)
                result += factor1;

            factor2 >>= 1;
            factor1 += factor1;
        }

        return result;
    }

    /*
    Iteracyjne mnożenie binarne modulowane.
    @param factor1 pierwszy czynnik
    @param factor2 drugi czynnik
    @param modulo modulo
    @return wynik mnożenia wzięty modulo
    */
    public static long multI(long factor1, long factor2, long modulo)
    {
        long result = 0;

        while(factor2 > 0)
        {
            if( (factor2&1) == 1)
                result = (result+factor1)%modulo;

            factor2 >>= 1;
            factor1 = (factor1+factor1)%modulo;
        }

        return result%modulo;
    }

    /*
    Rekurencyjne mnożenie binarne.
    @param factor1 pierwszy czynnik
    @param factor2 drugi czynnik
    @return wynik mnożenia
    */
    public static long multR(long factor1, long factor2)
    {
        long result = 0;

        if(factor2 > 0)
            result = multR(factor1+factor1, factor2>>1);

        return (factor2&1) == 1 ? factor1+result : result;
    }

    /*
    Rekurencyjne mnożenie binarne modulowane.
    @param factor1 pierwszy czynnik
    @param factor2 drugi czynnik
    @param modulo modulo
    @return wynik mnożenia wzięty modulo
    */
    public static long multR(long factor1, long factor2, long modulo)
    {
        long result = 0;

        if(factor2 > 0)
            result = multR(factor1+factor1, factor2>>1, modulo);

        return (factor2&1) == 1 ? (factor1+result)%modulo : result%modulo;
    }
}
