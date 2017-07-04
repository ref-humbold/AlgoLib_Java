package ref_humbold.algolib.maths;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SieveTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindPrimesWhenTwoArgsDescending()
    {
        Sieve.findPrimes(100, 30);
    }

    @Test
    public void testFindPrimesOneArgIsTwoArgsWithZeroAsMin()
    {
        Collection<Integer> result1 = Sieve.findPrimes(100);
        Collection<Integer> result2 = Sieve.findPrimes(0, 100);

        Assert.assertArrayEquals(result1.toArray(), result2.toArray());
    }

    @Test
    public void testFindPrimesOneArg()
    {
        Collection<Integer> result = Sieve.findPrimes(100);

        Assert.assertArrayEquals(
            new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
                          71, 73, 79, 83, 89, 97}, result.toArray());
    }

    @Test
    public void testFindPrimesOneArgWhenMaxIsPrime()
    {
        Collection<Integer> result = Sieve.findPrimes(67);

        Assert.assertArrayEquals(
            new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67},
            result.toArray());
    }

    @Test
    public void testFindPrimesOneArgWhenLessThanTwo()
    {
        Collection<Integer> result = Sieve.findPrimes(1);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }

    @Test
    public void testFindPrimesTwoArgs()
    {
        Collection<Integer> result = Sieve.findPrimes(30, 200);

        Assert.assertArrayEquals(
            new Integer[]{31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107,
                          109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191,
                          193, 197, 199}, result.toArray());
    }

    @Test
    public void testFindPrimesTwoArgsWhenMinLessThanSqrtOfMax()
    {
        Collection<Integer> result = Sieve.findPrimes(5, 150);

        Assert.assertArrayEquals(
            new Integer[]{5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
                          79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149},
            result.toArray());
    }

    @Test
    public void testFindPrimesTwoArgsWhenMinAndMaxAreFindPrimes()
    {
        Collection<Integer> result = Sieve.findPrimes(137, 317);

        Assert.assertArrayEquals(
            new Integer[]{137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211,
                          223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
                          307, 311, 313, 317}, result.toArray());
    }

    @Test
    public void testFindPrimesTwoArgsWhenMinEqualsMaxAndPrime()
    {
        Collection<Integer> result = Sieve.findPrimes(41, 41);

        Assert.assertArrayEquals(new Integer[]{41}, result.toArray());
    }

    @Test
    public void testFindPrimesTwoArgsWhenMinEqualsMaxAndComposite()
    {
        Collection<Integer> result = Sieve.findPrimes(91, 91);

        Assert.assertArrayEquals(new Integer[]{}, result.toArray());
    }
}
