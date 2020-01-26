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

public class ListSortingTest
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

        ListSorting.heapSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void heapSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.heapSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void heapSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);

        Assertions.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2},
                                     sequence.toArray());
    }

    @Test
    public void heapSort_WhenLeftIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.heapSort(sequence, index1, index2));
    }

    @Test
    public void heapSort_WhenRightIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.heapSort(sequence, index1, index2));
    }

    @Test
    public void heapSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.heapSort(sequence, index1, index2);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void heapSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.heapSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void heapSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class, () -> ListSorting.heapSort(sequence));
    }

    @Test
    public void mergedownSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergedownSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.mergedownSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);

        Assertions.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2},
                                     sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenLeftIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.mergedownSort(sequence, index1, index2));
    }

    @Test
    public void mergedownSort_WhenRightIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.mergedownSort(sequence, index1, index2));
    }

    @Test
    public void mergedownSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergedownSort(sequence, index1, index2);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.mergedownSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class,
                                () -> ListSorting.mergedownSort(sequence));
    }

    @Test
    public void mergeupSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergeupSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.mergeupSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);

        Assertions.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2},
                                     sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenLeftIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.mergeupSort(sequence, index1, index2));
    }

    @Test
    public void mergeupSort_WhenRightIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.mergeupSort(sequence, index1, index2));
    }

    @Test
    public void mergeupSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergeupSort(sequence, index1, index2);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.mergeupSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class,
                                () -> ListSorting.mergeupSort(sequence));
    }

    @Test
    public void quickSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.quickSort(sequence);
        Collections.sort(sequenceCopy);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void quickSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.quickSort(sequence);

        Assertions.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                     sequence.toArray());
    }

    @Test
    public void quickSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);

        Assertions.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2},
                                     sequence.toArray());
    }

    @Test
    public void quickSort_WhenLeftIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.quickSort(sequence, index1, index2));
    }

    @Test
    public void quickSort_WhenRightIndexOutOfRange_ThenIndexOutOfBoundsException()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                () -> ListSorting.quickSort(sequence, index1, index2));
    }

    @Test
    public void quickSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.quickSort(sequence, index1, index2);

        Assertions.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void quickSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.quickSort(sequence);

        Assertions.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void quickSort_WhenNull_ThenNullPointerException()
    {
        List<Integer> sequence = null;

        Assertions.assertThrows(NullPointerException.class, () -> ListSorting.quickSort(sequence));
    }
}
