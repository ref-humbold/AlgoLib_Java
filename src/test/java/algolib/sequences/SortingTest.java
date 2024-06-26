package algolib.sequences;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Tests: Algorithms for sequence sorting.
public class SortingTest
{
    @Test
    public void heapSort_ThenSortedAscending()
    {
        // given
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        // when
        Sorting.heapSort(sequence);
        // then
        Assertions.assertThat(sequence).isSorted();
    }

    @Test
    public void heapSort_WhenNull_ThenNullPointerException()
    {
        Assertions.assertThatThrownBy(() -> Sorting.heapSort(null))
                  .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void topDownMergeSort_ThenSortedAscending()
    {
        // given
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        // when
        Sorting.topDownMergeSort(sequence);
        // then
        Assertions.assertThat(sequence).isSorted();
    }

    @Test
    public void topDownMergeSort_WhenNull_ThenNullPointerException()
    {
        Assertions.assertThatThrownBy(() -> Sorting.topDownMergeSort(null))
                  .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void bottomUpMergeSort_ThenSortedAscending()
    {
        // given
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        // when
        Sorting.bottomUpMergeSort(sequence);
        // then
        Assertions.assertThat(sequence).isSorted();
    }

    @Test
    public void bottomUpMergeSort_WhenNull_ThenNullPointerException()
    {
        Assertions.assertThatThrownBy(() -> Sorting.bottomUpMergeSort(null))
                  .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void quickSort_ThenSortedAscending()
    {
        // given
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        // when
        Sorting.quickSort(sequence);
        // then
        Assertions.assertThat(sequence).isSorted();
    }

    @Test
    public void quickSort_WhenNull_ThenNullPointerException()
    {
        Assertions.assertThatThrownBy(() -> Sorting.quickSort(null))
                  .isInstanceOf(NullPointerException.class);
    }
}
