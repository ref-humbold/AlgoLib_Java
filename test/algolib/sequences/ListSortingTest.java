// TESTY DLA ALGORYTMÓW SORTOWANIA
package algolib.sequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListSortingTest
{
    @Before
    public void setUp()
    {
    }

    @After
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

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void heapSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.heapSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void heapSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void heapSort_WhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void heapSort_WhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);
    }

    @Test
    public void heapSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.heapSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void heapSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.heapSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void heapSort_WhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.heapSort(sequence);
    }

    @Test
    public void mergedownSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergedownSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.mergedownSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void mergedownSort_WhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void mergedownSort_WhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);
    }

    @Test
    public void mergedownSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergedownSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergedownSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.mergedownSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void mergedownSort_WhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.mergedownSort(sequence);
    }

    @Test
    public void mergeupSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergeupSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.mergeupSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void mergeupSort_WhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void mergeupSort_WhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);
    }

    @Test
    public void mergeupSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergeupSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void mergeupSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.mergeupSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void mergeupSort_WhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.mergeupSort(sequence);
    }

    @Test
    public void quickSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.quickSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void quickSort_WhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.quickSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void quickSort_WhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void quickSort_WhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void quickSort_WhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);
    }

    @Test
    public void quickSort_WhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.quickSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void quickSort_WhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.quickSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void quickSort_WhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.quickSort(sequence);
    }
}
