// TESTY DLA ALGORYTMÓW SORTOWANIA
package ref_humbold.algolib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ref_humbold.algolib.structures.Pair;

public class SortingTest
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
    public void testAngleSorted()
    {
        List<Pair<Double, Double>> sequence = Arrays.asList(Pair.make(0.0, 0.0),
                                                            Pair.make(-2.0, -3.0),
                                                            Pair.make(-3.0, -2.0),
                                                            Pair.make(3.0, -2.0),
                                                            Pair.make(-2.0, 3.0),
                                                            Pair.make(3.0, 2.0),
                                                            Pair.make(2.0, -3.0),
                                                            Pair.make(2.0, 3.0),
                                                            Pair.make(-3.0, 2.0));
        List<Pair<Double, Double>> sequenceCopy = new ArrayList<>(sequence);

        List<Pair<Double, Double>> result = Sorting.angleSorted(sequence);

        Assert.assertArrayEquals(
            new Object[]{Pair.make(0.0, 0.0), Pair.make(3.0, 2.0), Pair.make(2.0, 3.0),
                         Pair.make(-2.0, 3.0), Pair.make(-3.0, 2.0), Pair.make(-3.0, -2.0),
                         Pair.make(-2.0, -3.0), Pair.make(2.0, -3.0), Pair.make(3.0, -2.0)},
            result.toArray());
        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testAngleSort()
    {
        List<Pair<Double, Double>> sequence = Arrays.asList(Pair.make(0.0, 0.0),
                                                            Pair.make(-2.0, -3.0),
                                                            Pair.make(-3.0, -2.0),
                                                            Pair.make(3.0, -2.0),
                                                            Pair.make(-2.0, 3.0),
                                                            Pair.make(3.0, 2.0),
                                                            Pair.make(2.0, -3.0),
                                                            Pair.make(2.0, 3.0),
                                                            Pair.make(-3.0, 2.0));

        Sorting.angleSort(sequence);

        Assert.assertArrayEquals(
            new Object[]{Pair.make(0.0, 0.0), Pair.make(3.0, 2.0), Pair.make(2.0, 3.0),
                         Pair.make(-2.0, 3.0), Pair.make(-3.0, 2.0), Pair.make(-3.0, -2.0),
                         Pair.make(-2.0, -3.0), Pair.make(2.0, -3.0), Pair.make(3.0, -2.0)},
            sequence.toArray());
    }

    @Test
    public void testAngleSortWhenEmptyList()
    {
        List<Pair<Double, Double>> sequence = new ArrayList<>();

        Sorting.angleSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test
    public void testHeapSorted()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);
        List<Integer> sequenceSorted = new ArrayList<>(sequence);

        List<Integer> result = Sorting.heapSorted(sequence);
        Collections.sort(sequenceSorted);

        Assert.assertArrayEquals(sequenceSorted.toArray(), result.toArray());
        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeapSortedWhenNull()
    {
        List<Integer> sequence = null;

        Sorting.heapSorted(sequence);
    }

    @Test
    public void testHeapSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.heapSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testHeapSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.heapSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHeapSortWhenIndexOutOfRange1()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.heapSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHeapSortWhenIndexOutOfRange2()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.heapSort(sequence, index1, index2);
    }

    @Test
    public void testHeapSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.heapSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testHeapSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.heapSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeapSortWhenNull()
    {
        List<Integer> sequence = null;

        Sorting.heapSort(sequence);
    }

    @Test
    public void testMergeSorted()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);
        List<Integer> sequenceSorted = new ArrayList<>(sequence);

        List<Integer> result = Sorting.mergeSorted(sequence);
        Collections.sort(sequenceSorted);

        Assert.assertArrayEquals(sequenceSorted.toArray(), result.toArray());
        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortedWhenNull()
    {
        List<Integer> sequence = null;

        Sorting.mergeSorted(sequence);
    }

    @Test
    public void testMergeSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.mergeSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testMergeSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.mergeSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMergeSortWhenIndexOutOfRange1()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.mergeSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMergeSortWhenIndexOutOfRange2()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.mergeSort(sequence, index1, index2);
    }

    @Test
    public void testMergeSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.mergeSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testMergeSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.mergeSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortWhenNull()
    {
        List<Integer> sequence = null;

        Sorting.mergeSort(sequence);
    }

    @Test
    public void testQuickSorted()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);
        List<Integer> sequenceSorted = new ArrayList<>(sequence);

        List<Integer> result = Sorting.quickSorted(sequence);
        Collections.sort(sequenceSorted);

        Assert.assertArrayEquals(sequenceSorted.toArray(), result.toArray());
        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortedWhenNull()
    {
        List<Integer> sequence = null;

        Sorting.quickSorted(sequence);
    }

    @Test
    public void testQuickSort()
    {
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.quickSort(sequence);
        Collections.sort(sequenceCopy);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testQuickSortWhenIndices()
    {
        int index1 = 3;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.quickSort(sequence, index1, index2);

        Assert.assertArrayEquals(new Integer[]{3, 17, -6, -12, 0, 7, 9, 4, 2}, sequence.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQuickSortWhenIndexOutOfRange1()
    {
        int index1 = -13;
        int index2 = 7;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.quickSort(sequence, index1, index2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQuickSortWhenIndexOutOfRange2()
    {
        int index1 = 3;
        int index2 = 17;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);

        Sorting.quickSort(sequence, index1, index2);
    }

    @Test
    public void testQuickSortWhenIndicesReversed()
    {
        int index1 = 7;
        int index2 = 3;
        List<Integer> sequence = Arrays.asList(3, 17, -6, 0, 9, -12, 7, 4, 2);
        List<Integer> sequenceCopy = new ArrayList<>(sequence);

        Sorting.quickSort(sequence, index1, index2);

        Assert.assertArrayEquals(sequenceCopy.toArray(), sequence.toArray());
    }

    @Test
    public void testQuickSortWhenEmptyList()
    {
        List<Integer> sequence = new ArrayList<>();

        Sorting.quickSort(sequence);

        Assert.assertArrayEquals(new Object[]{}, sequence.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortWhenNull()
    {
        List<Integer> sequence = null;

        Sorting.quickSort(sequence);
    }
}
