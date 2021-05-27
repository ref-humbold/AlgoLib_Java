package algolib.sequences;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for subsequences
public class SubsequencesTest
{
    // region longestIncreasing

    @Test
    public void longestIncreasing_WhenIncreasing_ThenAllElements()
    {
        // given
        List<Integer> sequence = List.of(1, 3, 5, 7, 9, 11, 13, 15);
        // when
        Collection<Integer> result = Subsequences.longestIncreasing(sequence, Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactlyElementsOf(sequence);
    }

    @Test
    public void longestIncreasing_WhenDecreasing_ThenLastElementOnly()
    {
        // given
        List<Integer> sequence = List.of(12, 10, 8, 6, 4, 2);
        // when
        Collection<Integer> result = Subsequences.longestIncreasing(sequence, Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactly(sequence.get(sequence.size() - 1));
    }

    @Test
    public void longestIncreasing_WhenMultipleSubsequences_ThenLeastLexicographically()
    {
        // given
        List<Integer> sequence = List.of(2, 1, 4, 3, 6, 5, 8, 7, 10);
        // when
        Collection<Integer> result = Subsequences.longestIncreasing(sequence, Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactly(1, 3, 5, 7, 10);
    }

    @Test
    public void longestIncreasing_WhenIncreasingAndReversedComparator_ThenLastElementOnly()
    {
        // given
        List<Integer> sequence = List.of(1, 3, 5, 7, 9, 11, 13, 15);
        // when
        Collection<Integer> result =
                Subsequences.longestIncreasing(sequence, (i1, i2) -> i2.compareTo(i1));
        // then
        Assertions.assertThat(result).containsExactly(sequence.get(sequence.size() - 1));
    }

    @Test
    public void longestIncreasing_WhenDecreasingAndReversedComparator_ThenAllElements()
    {
        // given
        List<Integer> sequence = List.of(12, 10, 8, 6, 4, 2);
        // when
        Collection<Integer> result =
                Subsequences.longestIncreasing(sequence, (i1, i2) -> i2.compareTo(i1));
        // then
        Assertions.assertThat(result).containsExactlyElementsOf(sequence);
    }

    // endregion
    // region maximumSubarray

    @Test
    public void maximumSubarray1()
    {
        // given
        List<Double> sequence = List.of(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);
        // when
        List<Double> result = Subsequences.maximumSubarray(sequence);
        // then
        Assertions.assertThat(result).containsExactly(3.5, 4.8, -1.6, 7.7, 2.1);
    }

    @Test
    public void maximumSubarray2()
    {
        // given
        List<Double> sequence = List.of(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);
        // when
        List<Double> result = Subsequences.maximumSubarray(sequence);
        // then
        Assertions.assertThat(result).containsExactly(7.7, 2.1, 0.8, 4.0);
    }

    @Test
    public void maximumSubarray_WhenAllElementsAreNegative_ThenEmpty()
    {
        // given
        List<Double> sequence = List.of(-9.0, -2.4, -3.07, -1.93, -12.67);
        // when
        List<Double> result = Subsequences.maximumSubarray(sequence);
        // then
        Assertions.assertThat(result).isEmpty();
    }

    // endregion
    // region maximalSubsum

    @Test
    public void maximalSubsum1()
    {
        // given
        List<Double> sequence = List.of(3.5, 4.8, -1.6, 7.7, 2.1, -9.3, 0.8);
        // when
        double result = Subsequences.maximalSubsum(sequence);
        // then
        Assertions.assertThat(result).isCloseTo(16.5, Offset.offset(0.000001));
    }

    @Test
    public void maximalSubsum2()
    {
        // given
        List<Double> sequence = List.of(-9.3, -1.2, 3.5, 4.8, -10.6, 7.7, 2.1, 0.8, 4.0);
        // when
        double result = Subsequences.maximalSubsum(sequence);
        // then
        Assertions.assertThat(result).isCloseTo(14.6, Offset.offset(0.000001));
    }

    @Test
    public void maximumalSubsum_WhenAllElementsAreNegative_ThenZero()
    {
        // given
        List<Double> sequence = List.of(-9.0, -2.4, -3.07, -1.93, -12.67);
        // when
        double result = Subsequences.maximalSubsum(sequence);
        // then
        Assertions.assertThat(result).isEqualTo(0.0);
    }

    // endregion
}
