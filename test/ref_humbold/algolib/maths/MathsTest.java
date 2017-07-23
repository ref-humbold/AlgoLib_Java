// TESTY DLA ALGORYTMÓW MATEMATYCZNYCH
package ref_humbold.algolib.maths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MathsTest
{
    @Before
    public void setUp()
        throws Exception
    {
    }

    @After
    public void tearDown()
        throws Exception
    {
    }

    @Test
    public void testGCDWhenNumbersAreComposite()
    {
        long number1 = 161;
        long number2 = 46;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(23, result);
    }

    @Test
    public void testGCDWhenNumbersArePrime()
    {
        long number1 = 127;
        long number2 = 41;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(1, result);
    }

    @Test
    public void testGCDWhenNumbersAreMutuallyPrime()
    {
        long number1 = 119;
        long number2 = 57;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(1, result);
    }

    @Test
    public void testGCDWhenOneOfNumbersIsMultipleOfAnother()
    {
        long number1 = 272;
        long number2 = 34;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(number2, result);
    }

    @Test
    public void testGCDWhenOneOfNumbersIsZero()
    {
        long number1 = 96;
        long number2 = 0;

        long result = Maths.gcd(number1, number2);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void testLCMWhenNumbersAreComposite()
    {
        long number1 = 161;
        long number2 = 46;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(322, result);
    }

    @Test
    public void testLCMWhenNumbersArePrime()
    {
        long number1 = 127;
        long number2 = 41;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(5207, result);
    }

    @Test
    public void testLCMWhenNumbersAreMutuallyPrime()
    {
        long number1 = 119;
        long number2 = 57;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(6783, result);
    }

    @Test
    public void testLCMWhenOneOfNumbersIsMultipleOfAnother()
    {
        long number1 = 272;
        long number2 = 34;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void testLCMWhenOneOfNumbersIsZero()
    {
        long number1 = 96;
        long number2 = 0;

        long result = Maths.lcm(number1, number2);

        Assert.assertEquals(number2, result);
    }

    @Test
    public void testPowerModWhenBaseIsZero()
    {
        long number1 = 0;
        long number2 = 14;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void testPowerModWhenExponentIsZero()
    {
        long number1 = 14;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(1, result);
    }

    @Test(expected = ArithmeticException.class)
    public void testPowerModWhenBaseAndExponentAreZero()
    {
        long number1 = 0;
        long number2 = 0;
        long number3 = 0;

        Maths.powerMod(number1, number2, number3);
    }

    @Test
    public void testPowerModWhenBaseAndExponentArePositive()
    {
        long number1 = 3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(59049, result);
    }

    @Test
    public void testPowerModWhenBaseIsNegativeAndExponentIsEven()
    {
        long number1 = -3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(59049, result);
    }

    @Test
    public void testPowerModWhenBaseIsNegativeAndExponentIsOdd()
    {
        long number1 = -3;
        long number2 = 9;
        long number3 = 0;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(-19683, result);
    }

    @Test(expected = ArithmeticException.class)
    public void testPowerModWhenExponentIsNegative()
    {
        long number1 = 3;
        long number2 = -10;
        long number3 = 0;

        Maths.powerMod(number1, number2, number3);
    }

    @Test
    public void testPowerModWhenModuloAndBaseArePositive()
    {
        long number1 = 5;
        long number2 = 11;
        long number3 = 10000;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(8125, result);
    }

    @Test
    public void testPowerModWhenModuloIsPositiveAndBaseIsNegative()
    {
        long number1 = -5;
        long number2 = 11;
        long number3 = 10000;

        long result = Maths.powerMod(number1, number2, number3);

        Assert.assertEquals(1875, result);
    }

    @Test(expected = ArithmeticException.class)
    public void testPowerModWhenModuloIsNegative()
    {
        long number1 = 5;
        long number2 = 11;
        long number3 = -10000;

        Maths.powerMod(number1, number2, number3);
    }

    @Test
    public void testMultModWhenFactor1IsZero()
    {
        long number1 = 0;
        long number2 = 14;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void testPowerModWhenFactor2IsZero()
    {
        long number1 = 14;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(number2, result);
    }

    @Test
    public void testMultModWhenFactorsAreZero()
    {
        long number1 = 0;
        long number2 = 0;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(number1, result);
    }

    @Test
    public void testMultModWhenFactor1IsNegativeAndFactor2IsPositive()
    {
        long number1 = -3;
        long number2 = 10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(-30, result);
    }

    @Test
    public void testMultModWhenFactor1IsPositiveAndFactor2IsNegative()
    {
        long number1 = 3;
        long number2 = -10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(-30, result);
    }

    @Test
    public void testMultModWhenFactorsAreNegative()
    {
        long number1 = -3;
        long number2 = -10;
        long number3 = 0;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(30, result);
    }

    @Test
    public void testMultModWhenModuloAndFactorsArePositive()
    {
        long number1 = 547;
        long number2 = 312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(664, result);
    }

    @Test
    public void testMultModWhenModuloIsPositiveAndFactor1IsNegative()
    {
        long number1 = -547;
        long number2 = 312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(9336, result);
    }

    @Test
    public void testMultModWhenModuloIsPositiveAndFactor2IsNegative()
    {
        long number1 = 547;
        long number2 = -312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(9336, result);
    }

    @Test
    public void testMultModWhenModuloIsPositiveAndFactorsAreNegative()
    {
        long number1 = -547;
        long number2 = -312;
        long number3 = 10000;

        long result = Maths.multMod(number1, number2, number3);

        Assert.assertEquals(664, result);
    }

    @Test(expected = ArithmeticException.class)
    public void testMultModWhenModuloIsNegative()
    {
        long number1 = 547;
        long number2 = 312;
        long number3 = -10000;

        Maths.multMod(number1, number2, number3);
    }
}
