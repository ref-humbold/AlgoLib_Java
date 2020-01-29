// TESTY DLA ALGORYTMÓW MATEMATYCZNYCH
package algolib.mathmat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MathsTest
{
    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void gCD_WhenNumbersAreComposite()
    {
        long number1 = 161;
        long number2 = 46;

        long result = Maths.gcd(number1, number2);

        Assertions.assertEquals(23, result);
    }

    @Test
    public void gCD_WhenNumbersArePrime()
    {
        long number1 = 127;
        long number2 = 41;

        long result = Maths.gcd(number1, number2);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void gCD_WhenNumbersAreMutuallyPrime()
    {
        long number1 = 119;
        long number2 = 57;

        long result = Maths.gcd(number1, number2);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void gCD_WhenOneOfNumbersIsMultipleOfAnother()
    {
        long number1 = 272;
        long number2 = 34;

        long result = Maths.gcd(number1, number2);

        Assertions.assertEquals(number2, result);
    }

    @Test
    public void gCD_WhenOneOfNumbersIsZero()
    {
        long number1 = 96;
        long number2 = 0;

        long result = Maths.gcd(number1, number2);

        Assertions.assertEquals(number1, result);
    }

    @Test
    public void lCM_WhenNumbersAreComposite()
    {
        long number1 = 161;
        long number2 = 46;

        long result = Maths.lcm(number1, number2);

        Assertions.assertEquals(322, result);
    }

    @Test
    public void lCM_WhenNumbersArePrime()
    {
        long number1 = 127;
        long number2 = 41;

        long result = Maths.lcm(number1, number2);

        Assertions.assertEquals(5207, result);
    }

    @Test
    public void lCM_WhenNumbersAreMutuallyPrime()
    {
        long number1 = 119;
        long number2 = 57;

        long result = Maths.lcm(number1, number2);

        Assertions.assertEquals(6783, result);
    }

    @Test
    public void lCM_WhenOneOfNumbersIsMultipleOfAnother()
    {
        long number1 = 272;
        long number2 = 34;

        long result = Maths.lcm(number1, number2);

        Assertions.assertEquals(number1, result);
    }

    @Test
    public void lCM_WhenOneOfNumbersIsZero()
    {
        long number1 = 96;
        long number2 = 0;

        long result = Maths.lcm(number1, number2);

        Assertions.assertEquals(number2, result);
    }

    @Test
    public void powerMod_WhenBaseIsZero()
    {
        long number1 = 0;
        long number2 = 14;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(number1, result);
    }

    @Test
    public void powerMod_WhenExponentIsZero()
    {
        long number1 = 14;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void powerMod_WhenBaseAndExponentAreZero_ThenArithmeticException()
    {
        long number1 = 0;
        long number2 = 0;
        long number3 = 0;

        Assertions.assertThrows(ArithmeticException.class,
                                () -> Maths.powerMod(number1, number2, number3));
    }

    @Test
    public void powerMod_WhenBaseAndExponentArePositive()
    {
        long number1 = 3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(59049, result);
    }

    @Test
    public void powerMod_WhenBaseIsNegativeAndExponentIsEven()
    {
        long number1 = -3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(59049, result);
    }

    @Test
    public void powerMod_WhenBaseIsNegativeAndExponentIsOdd()
    {
        long number1 = -3;
        long number2 = 9;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(-19683, result);
    }

    @Test
    public void powerMod_WhenExponentIsNegative_ThenArithmeticException()
    {
        long number1 = 3;
        long number2 = -10;
        long number3 = 0;

        Assertions.assertThrows(ArithmeticException.class,
                                () -> Maths.powerMod(number1, number2, number3));
    }

    @Test
    public void powerMod_WhenModuloAndBaseArePositive()
    {
        long number1 = 5;
        long number2 = 11;
        long number3 = 10000;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(8125, result);
    }

    @Test
    public void powerMod_WhenModuloIsPositiveAndBaseIsNegative()
    {
        long number1 = -5;
        long number2 = 11;
        long number3 = 10000;

        long result = Maths.powerMod(number1, number2, number3);

        Assertions.assertEquals(1875, result);
    }

    @Test
    public void powerMod_WhenModuloIsNegative_ThenArithmeticException()
    {
        long number1 = 5;
        long number2 = 11;
        long number3 = -10000;

        Assertions.assertThrows(ArithmeticException.class,
                                () -> Maths.powerMod(number1, number2, number3));
    }

    @Test
    public void multMod_WhenFactor1IsZero()
    {
        long number1 = 0;
        long number2 = 14;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(number1, result);
    }

    @Test
    public void powerMod_WhenFactor2IsZero()
    {
        long number1 = 14;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(number2, result);
    }

    @Test
    public void multMod_WhenFactorsAreZero()
    {
        long number1 = 0;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(number1, result);
    }

    @Test
    public void multMod_WhenFactor1IsNegativeAndFactor2IsPositive()
    {
        long number1 = -3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(-30, result);
    }

    @Test
    public void multMod_WhenFactor1IsPositiveAndFactor2IsNegative()
    {
        long number1 = 3;
        long number2 = -10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(-30, result);
    }

    @Test
    public void multMod_WhenFactorsAreNegative()
    {
        long number1 = -3;
        long number2 = -10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(30, result);
    }

    @Test
    public void multMod_WhenModuloAndFactorsArePositive()
    {
        long number1 = 547;
        long number2 = 312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(664, result);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactor1IsNegative()
    {
        long number1 = -547;
        long number2 = 312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(9336, result);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactor2IsNegative()
    {
        long number1 = 547;
        long number2 = -312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(9336, result);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactorsAreNegative()
    {
        long number1 = -547;
        long number2 = -312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assertions.assertEquals(664, result);
    }

    @Test
    public void multMod_WhenModuloIsNegative_ThenArithmeticException()
    {
        long number1 = 547;
        long number2 = 312;
        long number3 = -10000;

        Assertions.assertThrows(ArithmeticException.class,
                                () -> Maths.multMod(number1, number2, number3));
    }
}
