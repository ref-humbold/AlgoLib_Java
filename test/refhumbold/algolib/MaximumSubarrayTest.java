// TESTY DLA PODCIĄGU SPÓJNEGO O MAKSYMALNEJ SUMIE
package refhumbold.algolib;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MaximumSubarrayTest
{
    private List<Double> sequence = null;

    @Before
    public void setUp()
        throws Exception
    {
    }

    @After
    public void tearDown()
        throws Exception
    {
        sequence = null;
    }

    @Test
    public void testFindMaximumSubarray1()
    {
        sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);

        List<Double> result = MaximumSubarray.findMaximumSubarray(sequence);

        Assert.assertArrayEquals(new Double[]{3.5, 4.8, -1.6, 7.7, 2.1}, result.toArray());
    }

    @Test
    public void testFindMaximumSubarray2()
    {
        sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);

        List<Double> result = MaximumSubarray.findMaximumSubarray(sequence);

        Assert.assertArrayEquals(new Double[]{7.7, 2.1, 0.8, 4.0}, result.toArray());
    }

    @Test
    public void testFindMaximumSubarrayWhenAllElementsAreNegative()
    {
        sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);

        List<Double> result = MaximumSubarray.findMaximumSubarray(sequence);

        Assert.assertArrayEquals(new Double[]{}, result.toArray());
    }

    @Test
    public void testFindMaximumalSum1()
    {
        sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);

        double result = MaximumSubarray.findMaximalSum(sequence);

        Assert.assertEquals(16.5, result, 0.000001);
    }

    @Test
    public void testFindMaximumalSum2()
    {
        sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);

        double result = MaximumSubarray.findMaximalSum(sequence);

        Assert.assertEquals(14.6, result, 0.000001);
    }

    @Test
    public void testFindMaximumalSumWhenAllElementsAreNegative()
    {
        sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);

        double result = MaximumSubarray.findMaximalSum(sequence);

        Assert.assertEquals(0.0, result, 0.0);
    }
}
