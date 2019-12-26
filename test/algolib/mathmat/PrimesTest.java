// TESTY DLA ALGORYTMÓW DLA LICZB PIERWSZYCH
package algolib.mathmat;

import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrimesTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    //region testFindPrimes

    @Test(expected = IllegalArgumentException.class)
    public void findPrimes_WhenTwoArgsDescending()
    {
        Primes.find(100, 30);
    }

    @Test
    public void findPrimesOneArgIsTwoArgsWithZeroAsMin()
    {
        Collection<Integer> result1 = Primes.find(100);
        Collection<Integer> result2 = Primes.find(0, 100);

        Assert.assertArrayEquals(result1.toArray(), result2.toArray());
    }

    @Test
    public void findPrimesOneArg()
    {
        Collection<Integer> result = Primes.find(100);

        Assert.assertArrayEquals(
                new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
                              67, 71, 73, 79, 83, 89, 97}, result.toArray());
    }

    @Test
    public void findPrimesOneArg_WhenMaxIsPrime()
    {
        Collection<Integer> result = Primes.find(67);

        Assert.assertArrayEquals(
                new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
                              67}, result.toArray());
    }

    @Test
    public void findPrimesOneArg_WhenLessThanTwo()
    {
        Collection<Integer> result = Primes.find(1);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test
    public void findPrimesTwoArgs()
    {
        Collection<Integer> result = Primes.find(30, 200);

        Assert.assertArrayEquals(
                new Integer[]{31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
                              107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
                              181, 191, 193, 197, 199}, result.toArray());
    }

    @Test
    public void findPrimesTwoArgs_WhenMinLessThanSqrtOfMax()
    {
        Collection<Integer> result = Primes.find(5, 150);

        Assert.assertArrayEquals(
                new Integer[]{5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                              73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149},
                result.toArray());
    }

    @Test
    public void findPrimesTwoArgs_WhenMinAndMaxAreFindPrimes()
    {
        Collection<Integer> result = Primes.find(137, 317);

        Assert.assertArrayEquals(
                new Integer[]{137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199,
                              211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
                              283, 293, 307, 311, 313, 317}, result.toArray());
    }

    @Test
    public void findPrimesTwoArgs_WhenMinEqualsMaxAndPrime()
    {
        Collection<Integer> result = Primes.find(41, 41);

        Assert.assertArrayEquals(new Integer[]{41}, result.toArray());
    }

    @Test
    public void findPrimesTwoArgs_WhenMinEqualsMaxAndComposite()
    {
        Collection<Integer> result = Primes.find(91, 91);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    //endregion
    //region testTestFermat

    @Test
    public void testFermat_WhenZero()
    {
        boolean result = Primes.testFermat(0);

        Assert.assertFalse(result);
    }

    @Test
    public void testFermat_WhenOne()
    {
        boolean result = Primes.testFermat(1);

        Assert.assertFalse(result);
    }

    @Test
    public void testFermat_WhenTwo()
    {
        boolean result = Primes.testFermat(2);

        Assert.assertTrue(result);
    }

    @Test
    public void testFermat_WhenPrime()
    {
        boolean result = Primes.testFermat(1013);

        Assert.assertTrue(result);
    }

    @Test
    public void testFermat_WhenComposite()
    {
        boolean result = Primes.testFermat(1001);

        Assert.assertFalse(result);
    }

    @Test
    public void testFermat_WhenCarmichaelNumber()
    {
        boolean result = Primes.testFermat(1105);  // 1105 = 5 * 13 * 17 is a Carmichael number

        Assert.assertFalse(result);
    }

    //endregion
    //region testTestMiller

    @Test
    public void testMiller_WhenZero()
    {
        boolean result = Primes.testMiller(0);

        Assert.assertFalse(result);
    }

    @Test
    public void testMiller_WhenOne()
    {
        boolean result = Primes.testMiller(1);

        Assert.assertFalse(result);
    }

    @Test
    public void testMiller_WhenTwo()
    {
        boolean result = Primes.testMiller(2);

        Assert.assertTrue(result);
    }

    @Test
    public void testMiller_WhenPrime()
    {
        boolean result = Primes.testMiller(1013);

        Assert.assertTrue(result);
    }

    @Test
    public void testMiller_WhenComposite1()
    {
        boolean result = Primes.testMiller(1001);

        Assert.assertFalse(result);
    }

    @Test
    public void testMiller_WhenComposite2()
    {
        boolean result = Primes.testMiller(1105);

        Assert.assertFalse(result);
    }

    //endregion
}
