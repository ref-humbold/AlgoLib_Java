// Algorithms for basic mathematical computations
package algolib.mathmat;

public final class Maths
{
    /**
     * Counts a greatest common divisor of two numbers.
     * @param number1 a first number
     * @param number2 a second number
     * @return greatest common divisor
     */
    public static long gcd(long number1, long number2)
    {
        long min_number = Math.min(Math.abs(number1), Math.abs(number2));
        long max_number = Math.max(Math.abs(number1), Math.abs(number2));

        while(min_number > 0)
        {
            long rem = max_number % min_number;

            max_number = min_number;
            min_number = rem;
        }

        return max_number;
    }

    /**
     * Counts a lowest common multiple of two numbers.
     * @param number1 a first number
     * @param number2 a second number
     * @return lowest common multiple
     */
    public static long lcm(long number1, long number2)
    {
        long min_number = Math.min(Math.abs(number1), Math.abs(number2));
        long max_number = Math.max(Math.abs(number1), Math.abs(number2));

        return max_number / Maths.gcd(number1, number2) * min_number;
    }

    /**
     * Performs a fast multiplication of two numbers with modulo taken.
     * @param factor1 a first factor
     * @param factor2 a second factor
     * @param modulo a modulo value
     * @return multiplication result with modulo taken
     */
    public static long multMod(long factor1, long factor2, long modulo)
    {
        long result = 0;

        if(modulo < 0)
            throw new ArithmeticException("Negative modulo");

        if(factor1 < 0 && factor2 < 0)
            return Maths.multMod(-factor1, -factor2, modulo);

        if(factor1 < 0)
            return modulo - Maths.multMod(-factor1, factor2, modulo);

        if(factor2 < 0)
            return modulo - Maths.multMod(factor1, -factor2, modulo);

        while(factor2 > 0)
        {
            if(factor2 % 2 == 1)
                result = modulo == 0 ? result + factor1 : (result + factor1) % modulo;

            factor1 = modulo == 0 ? factor1 + factor1 : (factor1 + factor1) % modulo;
            factor2 >>= 1;
        }

        return result;
    }

    /**
     * Performs a fast exponentiation of two numbers with modulo taken.
     * @param base a base value
     * @param exponent an exponent value
     * @param modulo a modulo value
     * @return exponentiation result with modulo taken
     */
    public static long powerMod(long base, long exponent, long modulo)
    {
        long result = 1;

        if(modulo < 0)
            throw new ArithmeticException("Negative modulo");

        if(exponent < 0)
            throw new ArithmeticException("Negative exponent");

        if(base == 0 && exponent == 0)
            throw new ArithmeticException("Not a number");

        while(exponent > 0)
        {
            if(exponent % 2 == 1)
                result = Maths.multMod(result, base, modulo);

            base = Maths.multMod(base, base, modulo);
            exponent >>= 1;
        }

        return result;
    }
}
