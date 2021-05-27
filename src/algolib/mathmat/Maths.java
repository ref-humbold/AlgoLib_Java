package algolib.mathmat;

/** Algorithms for basic mathematical calculations */
public final class Maths
{
    // region gcd

    /**
     * Calculates greatest common divisor of two numbers.
     * @param number1 first number
     * @param number2 second number
     * @return greatest common divisor
     */
    public static int gcd(int number1, int number2)
    {
        int min_number = Math.min(Math.abs(number1), Math.abs(number2));
        int max_number = Math.max(Math.abs(number1), Math.abs(number2));

        while(min_number > 0)
        {
            int rem = max_number % min_number;

            max_number = min_number;
            min_number = rem;
        }

        return max_number;
    }

    /**
     * Calculates greatest common divisor of two numbers.
     * @param number1 first number
     * @param number2 second number
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

    // endregion
    // region lcm

    /**
     * Calculates lowest common multiple of two numbers.
     * @param number1 first number
     * @param number2 second number
     * @return lowest common multiple
     */
    public static int lcm(int number1, int number2)
    {
        int min_number = Math.min(Math.abs(number1), Math.abs(number2));
        int max_number = Math.max(Math.abs(number1), Math.abs(number2));

        return max_number / Maths.gcd(number1, number2) * min_number;
    }

    /**
     * Calculates lowest common multiple of two numbers.
     * @param number1 first number
     * @param number2 second number
     * @return lowest common multiple
     */
    public static long lcm(long number1, long number2)
    {
        long min_number = Math.min(Math.abs(number1), Math.abs(number2));
        long max_number = Math.max(Math.abs(number1), Math.abs(number2));

        return max_number / Maths.gcd(number1, number2) * min_number;
    }

    // endregion
    // region multiply

    /**
     * Performs fast multiplication of two numbers.
     * @param factor1 first factor
     * @param factor2 second factor
     * @return multiplication result
     */
    public static int multiply(int factor1, int factor2)
    {
        int result = 0;

        if(factor1 < 0 && factor2 < 0)
            return Maths.multiply(-factor1, -factor2);

        if(factor1 < 0)
            return -Maths.multiply(-factor1, factor2);

        if(factor2 < 0)
            return -Maths.multiply(factor1, -factor2);

        while(factor2 > 0)
        {
            if(factor2 % 2 == 1)
                result = result + factor1;

            factor1 = factor1 + factor1;
            factor2 /= 2;
        }

        return result;
    }

    /**
     * Performs fast multiplication of two numbers.
     * @param factor1 first factor
     * @param factor2 second factor
     * @return multiplication result
     */
    public static long multiply(long factor1, long factor2)
    {
        long result = 0;

        if(factor1 < 0 && factor2 < 0)
            return Maths.multiply(-factor1, -factor2);

        if(factor1 < 0)
            return -Maths.multiply(-factor1, factor2);

        if(factor2 < 0)
            return -Maths.multiply(factor1, -factor2);

        while(factor2 > 0)
        {
            if(factor2 % 2 == 1)
                result = result + factor1;

            factor1 = factor1 + factor1;
            factor2 /= 2;
        }

        return result;
    }

    /**
     * Performs fast multiplication of two numbers with modulo taken.
     * @param factor1 first factor
     * @param factor2 second factor
     * @param modulo modulo
     * @return multiplication result with modulo taken
     */
    public static int multiply(int factor1, int factor2, int modulo)
    {
        int result = 0;

        if(modulo <= 0)
            throw new ArithmeticException("Non-positive modulo");

        if(factor1 < 0 && factor2 < 0)
            return Maths.multiply(-factor1, -factor2, modulo);

        if(factor1 < 0)
            return modulo - Maths.multiply(-factor1, factor2, modulo);

        if(factor2 < 0)
            return modulo - Maths.multiply(factor1, -factor2, modulo);

        while(factor2 > 0)
        {
            if(factor2 % 2 == 1)
                result = (result + factor1) % modulo;

            factor1 = (factor1 + factor1) % modulo;
            factor2 /= 2;
        }

        return result;
    }

    /**
     * Performs fast multiplication of two numbers with modulo taken.
     * @param factor1 first factor
     * @param factor2 second factor
     * @param modulo modulo
     * @return multiplication result with modulo taken
     */
    public static long multiply(long factor1, long factor2, long modulo)
    {
        long result = 0;

        if(modulo <= 0)
            throw new ArithmeticException("Non-positive modulo");

        if(factor1 < 0 && factor2 < 0)
            return Maths.multiply(-factor1, -factor2, modulo);

        if(factor1 < 0)
            return modulo - Maths.multiply(-factor1, factor2, modulo);

        if(factor2 < 0)
            return modulo - Maths.multiply(factor1, -factor2, modulo);

        while(factor2 > 0)
        {
            if(factor2 % 2 == 1)
                result = (result + factor1) % modulo;

            factor1 = (factor1 + factor1) % modulo;
            factor2 /= 2;
        }

        return result;
    }

    // endregion
    // region power

    /**
     * Performs fast exponentiation of two numbers.
     * @param base base
     * @param exponent exponent
     * @return exponentiation result
     */
    public static int power(int base, int exponent)
    {
        int result = 1;

        if(exponent < 0)
            throw new ArithmeticException("Negative exponent");

        if(base == 0 && exponent == 0)
            throw new ArithmeticException("Not a number");

        while(exponent > 0)
        {
            if(exponent % 2 == 1)
                result = Maths.multiply(result, base);

            base = Maths.multiply(base, base);
            exponent /= 2;
        }

        return result;
    }

    /**
     * Performs fast exponentiation of two numbers.
     * @param base base
     * @param exponent exponent
     * @return exponentiation result
     */
    public static long power(long base, long exponent)
    {
        long result = 1;

        if(exponent < 0)
            throw new ArithmeticException("Negative exponent");

        if(base == 0 && exponent == 0)
            throw new ArithmeticException("Not a number");

        while(exponent > 0)
        {
            if(exponent % 2 == 1)
                result = Maths.multiply(result, base);

            base = Maths.multiply(base, base);
            exponent /= 2;
        }

        return result;
    }

    /**
     * Performs fast exponentiation of two numbers with modulo taken.
     * @param base base
     * @param exponent exponent
     * @param modulo modulo
     * @return exponentiation result with modulo taken
     */
    public static int power(int base, int exponent, int modulo)
    {
        int result = 1;

        if(modulo < 0)
            throw new ArithmeticException("Non-positive modulo");

        if(exponent < 0)
            throw new ArithmeticException("Negative exponent");

        if(base == 0 && exponent == 0)
            throw new ArithmeticException("Not a number");

        while(exponent > 0)
        {
            if(exponent % 2 == 1)
                result = Maths.multiply(result, base, modulo);

            base = Maths.multiply(base, base, modulo);
            exponent /= 2;
        }

        return result;
    }

    /**
     * Performs fast exponentiation of two numbers with modulo taken.
     * @param base base
     * @param exponent exponent
     * @param modulo modulo
     * @return exponentiation result with modulo taken
     */
    public static long power(long base, long exponent, long modulo)
    {
        long result = 1;

        if(modulo < 0)
            throw new ArithmeticException("Non-positive modulo");

        if(exponent < 0)
            throw new ArithmeticException("Negative exponent");

        if(base == 0 && exponent == 0)
            throw new ArithmeticException("Not a number");

        while(exponent > 0)
        {
            if(exponent % 2 == 1)
                result = Maths.multiply(result, base, modulo);

            base = Maths.multiply(base, base, modulo);
            exponent /= 2;
        }

        return result;
    }

    //endregion
}
