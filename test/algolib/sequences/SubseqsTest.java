// Tests: Algorithms for subsequences
package algolib.sequences;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubseqsTest
{
    private List<Double> sequence = null;

    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
        sequence = null;
    }

    @Test
    public void maximumSubarray1()
    {
        sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);

        List<Double> result = Subseqs.maximumSubarray(sequence);

        Assertions.assertArrayEquals(new Double[]{3.5, 4.8, -1.6, 7.7, 2.1}, result.toArray());
    }

    @Test
    public void maximumSubarray2()
    {
        sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);

        List<Double> result = Subseqs.maximumSubarray(sequence);

        Assertions.assertArrayEquals(new Double[]{7.7, 2.1, 0.8, 4.0}, result.toArray());
    }

    @Test
    public void maximumSubarray_WhenAllElementsAreNegative()
    {
        sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);

        List<Double> result = Subseqs.maximumSubarray(sequence);

        Assertions.assertArrayEquals(new Double[]{}, result.toArray());
    }

    @Test
    public void maximalSubsum1()
    {
        sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);

        double result = Subseqs.maximalSubsum(sequence);

        Assertions.assertEquals(16.5, result, 0.000001);
    }

    @Test
    public void maximalSubsum2()
    {
        sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);

        double result = Subseqs.maximalSubsum(sequence);

        Assertions.assertEquals(14.6, result, 0.000001);
    }

    @Test
    public void maximumalSubsum_WhenAllElementsAreNegative()
    {
        sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);

        double result = Subseqs.maximalSubsum(sequence);

        Assertions.assertEquals(0.0, result, 0.0);
    }
}
