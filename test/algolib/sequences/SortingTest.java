// Tests: Algorithms for sequence sorting
package algolib.sequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
    public void heapSort_WhenEmptyList_ThenEmpty()
    {
        // given
        List<Integer> sequence = new ArrayList<>();
        // when
        Sorting.heapSort(sequence);
        // then
        Assertions.assertThat(sequence).isEmpty();
    }

    @Test
    public void heapSort_WhenNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Sorting.heapSort(null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void mergedownSort_ThenSortedAscending()
    {
        // given
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        // when
        Sorting.mergedownSort(sequence);
        // then
        Assertions.assertThat(sequence).isSorted();
    }

    @Test
    public void mergedownSort_WhenEmptyList_ThenEmpty()
    {
        // given
        List<Integer> sequence = new ArrayList<>();
        // when
        Sorting.mergedownSort(sequence);
        // then
        Assertions.assertThat(sequence).isEmpty();
    }

    @Test
    public void mergedownSort_WhenNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Sorting.mergedownSort(null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void mergeupSort_ThenSortedAscending()
    {
        // given
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        // when
        Sorting.mergeupSort(sequence);
        // then
        Assertions.assertThat(sequence).isSorted();
    }

    @Test
    public void mergeupSort_WhenEmptyList_ThenEmpty()
    {
        // given
        List<Integer> sequence = new ArrayList<>();
        // when
        Sorting.mergeupSort(sequence);
        // then
        Assertions.assertThat(sequence).isEmpty();
    }

    @Test
    public void mergeupSort_WhenNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Sorting.mergeupSort(null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
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
    public void quickSort_WhenEmptyList_ThenEmpty()
    {
        // given
        List<Integer> sequence = new ArrayList<>();
        // when
        Sorting.quickSort(sequence);
        // then
        Assertions.assertThat(sequence).isEmpty();
    }

    @Test
    public void quickSort_WhenNull_ThenNullPointerException()
    {
        // when
        Throwable throwable = Assertions.catchThrowable(() -> Sorting.quickSort(null));
        // then
        Assertions.assertThat(throwable).isInstanceOf(NullPointerException.class);
    }
}
