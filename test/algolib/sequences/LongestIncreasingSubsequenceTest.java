package algolib.sequences;

import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithm for longest increasing subsequence
public class LongestIncreasingSubsequenceTest
{
    @Test
    public void findLIS_WhenIncreasing_ThenAllElements()
    {
        // given
        List<Integer> sequence = List.of(1, 3, 5, 7, 9, 11, 13, 15);
        // when
        Collection<Integer> result =
                LongestIncreasingSubsequence.findLIS(sequence, Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactlyElementsOf(sequence);
    }

    @Test
    public void findLIS_WhenDecreasing_ThenLastElementOnly()
    {
        // when
        Collection<Integer> result =
                LongestIncreasingSubsequence.findLIS(List.of(12, 10, 8, 6, 4, 2),
                                                     Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactly(2);
    }

    @Test
    public void findLIS_WhenMultipleSubsequences_ThenLeastLexicographically()
    {
        // when
        Collection<Integer> result =
                LongestIncreasingSubsequence.findLIS(List.of(2, 1, 4, 3, 6, 5, 8, 7, 10),
                                                     Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactly(1, 3, 5, 7, 10);
    }

    @Test
    public void findLIS_WhenSearchInMiddle_ThenLeastLexicographically()
    {
        // when
        Collection<Integer> result =
                LongestIncreasingSubsequence.findLIS(List.of(0, 2, 4, 6, 8, 3, 5, 7, 8),
                                                     Integer::compareTo);
        // then
        Assertions.assertThat(result).containsExactly(0, 2, 3, 5, 7, 8);
    }

    @Test
    public void findLIS_WhenIncreasingAndReversedComparator_ThenLastElementOnly()
    {
        // when
        Collection<Integer> result =
                LongestIncreasingSubsequence.findLIS(List.of(1, 3, 5, 7, 9, 11, 13, 15),
                                                     (i1, i2) -> i2.compareTo(i1));
        // then
        Assertions.assertThat(result).containsExactly(15);
    }

    @Test
    public void findLIS_WhenDecreasingAndReversedComparator_ThenAllElements()
    {
        // given
        List<Integer> sequence = List.of(12, 10, 8, 6, 4, 2);
        // when
        Collection<Integer> result =
                LongestIncreasingSubsequence.findLIS(sequence, (i1, i2) -> i2.compareTo(i1));
        // then
        Assertions.assertThat(result).containsExactlyElementsOf(sequence);
    }
}
