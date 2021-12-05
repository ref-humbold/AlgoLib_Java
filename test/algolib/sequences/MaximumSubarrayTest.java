package algolib.sequences;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for maximum subarray
public class MaximumSubarrayTest
{
    // region findMaximumSubarray

    @Test
    public void findMaximumSubarray_WhenNegativeIsLessThanSubsum_ThenIncludeNegative()
    {
        // when
        List<Double> result = MaximumSubarray.findMaximumSubarray(
                List.of(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8));
        // then
        Assertions.assertThat(result).containsExactly(3.5, 4.8, -1.6, 7.7, 2.1);
    }

    @Test
    public void findMaximumSubarray_WhenNegativeIsGreaterThanSubsum_ThenExcludeNegative()
    {
        // when
        List<Double> result = MaximumSubarray.findMaximumSubarray(
                List.of(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0));
        // then
        Assertions.assertThat(result).containsExactly(7.7, 2.1, 0.8, 4.0);
    }

    @Test
    public void findMaximumSubarray_WhenAllElementsAreNegative_ThenEmpty()
    {
        // when
        List<Double> result = MaximumSubarray.findMaximumSubarray(
                List.of(-9.0, -2.4, -3.07, -1.93, -12.67));
        // then
        Assertions.assertThat(result).isEmpty();
    }

    // endregion
    // region countMaximalSubsum

    @Test
    public void countMaximalSubsum_WhenNegativeIsLessThanSubsum_ThenIncludeNegative()
    {
        // when
        double result = MaximumSubarray.countMaximalSubsum(
                List.of(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8));
        // then
        Assertions.assertThat(result).isCloseTo(16.5, Offset.offset(0.000001));
    }

    @Test
    public void countMaximalSubsum_WhenNegativeIsGreaterThanSubsum_ThenExcludeNegative()
    {
        // when
        double result = MaximumSubarray.countMaximalSubsum(
                List.of(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0));
        // then
        Assertions.assertThat(result).isCloseTo(14.6, Offset.offset(0.000001));
    }

    @Test
    public void countMaximalSubsum_WhenAllElementsAreNegative_ThenZero()
    {
        // when
        double result = MaximumSubarray.countMaximalSubsum(
                List.of(-9.0, -2.4, -3.07, -1.93, -12.67));
        // then
        Assertions.assertThat(result).isEqualTo(0.0);
    }

    // endregion
}
