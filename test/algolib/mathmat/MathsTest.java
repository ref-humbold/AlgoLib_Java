// TESTY DLA ALGORYTMÓW MATEMATYCZNYCH
package algolib.mathmat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MathsTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void gCD_WhenNumbersAreComposite()
    {
        long number1 = 161;
        long number2 = 46;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(23, result);
    }

    @Test
    public void gCD_WhenNumbersArePrime()
    {
        long number1 = 127;
        long number2 = 41;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(1, result);
    }

    @Test
    public void gCD_WhenNumbersAreMutuallyPrime()
    {
        long number1 = 119;
        long number2 = 57;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(1, result);
    }

    @Test
    public void gCD_WhenOneOfNumbersIsMultipleOfAnother()
    {
        long number1 = 272;
        long number2 = 34;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(number2, result);
    }

    @Test
    public void gCD_WhenOneOfNumbersIsZero()
    {
        long number1 = 96;
        long number2 = 0;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void lCM_WhenNumbersAreComposite()
    {
        long number1 = 161;
        long number2 = 46;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(322, result);
    }

    @Test
    public void lCM_WhenNumbersArePrime()
    {
        long number1 = 127;
        long number2 = 41;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(5207, result);
    }

    @Test
    public void lCM_WhenNumbersAreMutuallyPrime()
    {
        long number1 = 119;
        long number2 = 57;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(6783, result);
    }

    @Test
    public void lCM_WhenOneOfNumbersIsMultipleOfAnother()
    {
        long number1 = 272;
        long number2 = 34;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void lCM_WhenOneOfNumbersIsZero()
    {
        long number1 = 96;
        long number2 = 0;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(number2, result);
    }

    @Test
    public void powerMod_WhenBaseIsZero()
    {
        long number1 = 0;
        long number2 = 14;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void powerMod_WhenExponentIsZero()
    {
        long number1 = 14;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(1, result);
    }

    @Test(expected = ArithmeticException.class)
    public void powerMod_WhenBaseAndExponentAreZero()
    {
        long number1 = 0;
        long number2 = 0;
        long number3 = 0;

        Maths.powerMod(number1, number2, number3);
    }

    @Test
    public void powerMod_WhenBaseAndExponentArePositive()
    {
        long number1 = 3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(59049, result);
    }

    @Test
    public void powerMod_WhenBaseIsNegativeAndExponentIsEven()
    {
        long number1 = -3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(59049, result);
    }

    @Test
    public void powerMod_WhenBaseIsNegativeAndExponentIsOdd()
    {
        long number1 = -3;
        long number2 = 9;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(-19683, result);
    }

    @Test(expected = ArithmeticException.class)
    public void powerMod_WhenExponentIsNegative()
    {
        long number1 = 3;
        long number2 = -10;
        long number3 = 0;

        Maths.powerMod(number1, number2, number3);
    }

    @Test
    public void powerMod_WhenModuloAndBaseArePositive()
    {
        long number1 = 5;
        long number2 = 11;
        long number3 = 10000;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(8125, result);
    }

    @Test
    public void powerMod_WhenModuloIsPositiveAndBaseIsNegative()
    {
        long number1 = -5;
        long number2 = 11;
        long number3 = 10000;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(1875, result);
    }

    @Test(expected = ArithmeticException.class)
    public void powerMod_WhenModuloIsNegative()
    {
        long number1 = 5;
        long number2 = 11;
        long number3 = -10000;

        Maths.powerMod(number1, number2, number3);
    }

    @Test
    public void multMod_WhenFactor1IsZero()
    {
        long number1 = 0;
        long number2 = 14;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void powerMod_WhenFactor2IsZero()
    {
        long number1 = 14;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(number2, result);
    }

    @Test
    public void multMod_WhenFactorsAreZero()
    {
        long number1 = 0;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void multMod_WhenFactor1IsNegativeAndFactor2IsPositive()
    {
        long number1 = -3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(-30, result);
    }

    @Test
    public void multMod_WhenFactor1IsPositiveAndFactor2IsNegative()
    {
        long number1 = 3;
        long number2 = -10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(-30, result);
    }

    @Test
    public void multMod_WhenFactorsAreNegative()
    {
        long number1 = -3;
        long number2 = -10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(30, result);
    }

    @Test
    public void multMod_WhenModuloAndFactorsArePositive()
    {
        long number1 = 547;
        long number2 = 312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(664, result);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactor1IsNegative()
    {
        long number1 = -547;
        long number2 = 312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(9336, result);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactor2IsNegative()
    {
        long number1 = 547;
        long number2 = -312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(9336, result);
    }

    @Test
    public void multMod_WhenModuloIsPositiveAndFactorsAreNegative()
    {
        long number1 = -547;
        long number2 = -312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(664, result);
    }

    @Test(expected = ArithmeticException.class)
    public void multMod_WhenModuloIsNegative()
    {
        long number1 = 547;
        long number2 = 312;
        long number3 = -10000;

        Maths.multMod(number1, number2, number3);
    }
}
