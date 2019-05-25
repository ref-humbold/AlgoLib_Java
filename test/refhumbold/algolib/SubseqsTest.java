// TEST : Algorithms for subsequences.
package refhumbold.algolib;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubseqsTest
{
    private List<Double> sequence = null;

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
        sequence = null;
    }

    @Test
    public void testMaximumSubarray1()
    {
        sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);

        List<Double> result = Subseqs.maximumSubarray(sequence);

        Assert.assertArrayEquals(new Double[]{3.5, 4.8, -1.6, 7.7, 2.1}, result.toArray());
    }

    @Test
    public void testMaximumSubarray2()
    {
        sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);

        List<Double> result = Subseqs.maximumSubarray(sequence);

        Assert.assertArrayEquals(new Double[]{7.7, 2.1, 0.8, 4.0}, result.toArray());
    }

    @Test
    public void testMaximumSubarrayWhenAllElementsAreNegative()
    {
        sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);

        List<Double> result = Subseqs.maximumSubarray(sequence);

        Assert.assertArrayEquals(new Double[]{}, result.toArray());
    }

    @Test
    public void testMaximalSum1()
    {
        sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);

        double result = Subseqs.maximalSubsum(sequence);

        Assert.assertEquals(16.5, result, 0.000001);
    }

    @Test
    public void testMaximalSum2()
    {
        sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);

        double result = Subseqs.maximalSubsum(sequence);

        Assert.assertEquals(14.6, result, 0.000001);
    }

    @Test
    public void testMaximumalSumWhenAllElementsAreNegative()
    {
        sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);

        double result = Subseqs.maximalSubsum(sequence);

        Assert.assertEquals(0.0, result, 0.0);
    }
}
