// Tests: Algorithms for subsequences
package algolib.sequences;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class SubsequencesTest
{
    @Test
    public void maximumSubarray1()
    {
        // given
        List<Double> sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);
        // when
        List<Double> result = Subsequences.maximumSubarray(sequence);
        // then
        Assertions.assertThat(result).containsExactly(3.5, 4.8, -1.6, 7.7, 2.1);
    }

    @Test
    public void maximumSubarray2()
    {
        // given
        List<Double> sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);
        // when
        List<Double> result = Subsequences.maximumSubarray(sequence);
        // then
        Assertions.assertThat(result).containsExactly(7.7, 2.1, 0.8, 4.0);
    }

    @Test
    public void maximumSubarray_WhenAllElementsAreNegative()
    {
        // given
        List<Double> sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);
        // when
        List<Double> result = Subsequences.maximumSubarray(sequence);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void maximalSubsum1()
    {
        // given
        List<Double> sequence = Arrays.asList(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);
        // when
        double result = Subsequences.maximalSubsum(sequence);
        // then
        Assertions.assertThat(result).isCloseTo(16.5, Offset.offset(0.000001));
    }

    @Test
    public void maximalSubsum2()
    {
        // given
        List<Double> sequence = Arrays.asList(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);
        // when
        double result = Subsequences.maximalSubsum(sequence);
        // then
        Assertions.assertThat(result).isCloseTo(14.6, Offset.offset(0.000001));
    }

    @Test
    public void maximumalSubsum_WhenAllElementsAreNegative()
    {
        // given
        List<Double> sequence = Arrays.asList(-9.0, -2.4, -3.07, -1.93, -12.67);
        // when
        double result = Subsequences.maximalSubsum(sequence);
        // then
        Assertions.assertThat(result).isEqualTo(0.0);
    }
}
