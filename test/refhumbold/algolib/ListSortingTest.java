// TESTY DLA ALGORYTMÓW SORTOWANIA
package refhumbold.algolib;

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
    public void testHeapSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.heapSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testHeapSortWhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.heapSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void testHeapSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHeapSortWhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHeapSortWhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.heapSort(sequence, index1, index2);
    }

    @Test
    public void testHeapSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.heapSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testHeapSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.heapSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void testHeapSortWhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.heapSort(sequence);
    }

    @Test
    public void testMergedownSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergedownSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testMergedownSortWhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.mergedownSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void testMergedownSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMergedownSortWhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMergedownSortWhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergedownSort(sequence, index1, index2);
    }

    @Test
    public void testMergedownSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergedownSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testMergedownSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.mergedownSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void testMergedownSortWhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.mergedownSort(sequence);
    }

    @Test
    public void testMergeupSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergeupSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testMergeupSortWhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.mergeupSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void testMergeupSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMergeupSortWhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMergeupSortWhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.mergeupSort(sequence, index1, index2);
    }

    @Test
    public void testMergeupSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.mergeupSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testMergeupSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.mergeupSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void testMergeupSortWhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.mergeupSort(sequence);
    }

    @Test
    public void testQuickSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.quickSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testQuickSortWhenAllEqual()
    {
        List<Integer> sequence = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10);

        ListSorting.quickSort(sequence);

        Assert.assertArrayEquals(new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10},
                                 sequence.toArray());
    }

    @Test
    public void testQuickSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQuickSortWhenLeftIndexOutOfRange()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQuickSortWhenRightIndexOutOfRange()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        ListSorting.quickSort(sequence, index1, index2);
    }

    @Test
    public void testQuickSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        ListSorting.quickSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testQuickSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        ListSorting.quickSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void testQuickSortWhenNull()
    {
        List<Integer> sequence = null;

        ListSorting.quickSort(sequence);
    }
}
