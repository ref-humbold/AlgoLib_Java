// TESTY DLA ALGORYTMÓW SORTOWANIA
package algolib.sequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SortingTest
{
    @BeforeEach
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void heapSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.heapSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void heapSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        Sorting.heapSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void heapSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.heapSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void heapSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class, () -> Sorting.heapSort(sequence));
    }

    @Test
    public void mergedownSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.mergedownSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        Sorting.mergedownSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.mergedownSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class, () -> Sorting.mergedownSort(sequence));
    }

    @Test
    public void mergeupSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.mergeupSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        Sorting.mergeupSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.mergeupSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class, () -> Sorting.mergeupSort(sequence));
    }

    @Test
    public void quickSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.quickSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void quickSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        Sorting.quickSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void quickSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.quickSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void quickSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class, () -> Sorting.quickSort(sequence));
    }
}
