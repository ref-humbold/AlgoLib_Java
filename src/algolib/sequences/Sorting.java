// Algorithms for sequence sorting
package algolib.sequences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class Sorting
{
    /**
     * Mutably sorts specified sequence using a heap.
     * @param sequence a sequence of elements
     */
    public static <T extends Comparable<T>> void heapSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        int heapSize = sequence.size();

        if(heapSize <= 1)
            return;

        for(int i = heapSize / 2; i >= 0; --i)
            Sorting.moveDown(sequence, i, sequence.size());

        while(heapSize > 1)
        {
            int indexHeap = heapSize - 1;

            swap(sequence, indexHeap, 0);
            Sorting.moveDown(sequence, 0, indexHeap);
            --heapSize;
        }
    }

    /**
     * Mutably sorts specified sequence using a top-down merge-sort algorithm.
     * @param sequence a sequence of elements
     */
    public static <T extends Comparable<T>> void mergedownSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");
        Sorting.doMergeSort(sequence, 0, sequence.size());
    }

    /**
     * Mutably sorts specified sequence using a bottom-up merge-sort algorithm.
     * @param sequence a sequence of elements
     */
    public static <T extends Comparable<T>> void mergeupSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        if(sequence.size() <= 1)
            return;

        for(int half_step = 1; half_step < sequence.size(); half_step *= 2)
            for(int i = 0; i < sequence.size(); i += half_step + half_step)
                Sorting.merge(sequence, i, Math.min(i + half_step, sequence.size()),
                              Math.min(i + half_step + half_step, sequence.size()));
    }

    /**
     * Mutably sorts specified sequence using a quick-sort algorithm.
     * @param sequence a sequence of elements
     */
    public static <T extends Comparable<T>> void quickSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");
        doQuickSort(sequence, 0, sequence.size());
    }

    // Move element down inside specified heap
    private static <T extends Comparable<T>> void moveDown(List<T> heap, int vertex, int indexEnd)
    {
        int nextVertex = -1;
        int leftVertex = vertex + vertex + 1;
        int rightVertex = vertex + vertex + 2;

        if(rightVertex < indexEnd)
            nextVertex = heap.get(rightVertex).compareTo(heap.get(leftVertex)) < 0 ? leftVertex
                                                                                   : rightVertex;

        if(leftVertex == indexEnd - 1)
            nextVertex = leftVertex;

        if(nextVertex < 0)
            return;

        if(heap.get(nextVertex).compareTo(heap.get(vertex)) > 0)
            swap(heap, nextVertex, vertex);

        Sorting.moveDown(heap, nextVertex, indexEnd);
    }

    // Mutably sorts specified sequence using a recursive merge-sort algorithm.
    private static <T extends Comparable<T>> void doMergeSort(List<T> sequence, int indexBegin,
                                                              int indexEnd)
    {
        if(indexEnd - indexBegin <= 1)
            return;

        int indexMiddle = (indexBegin + indexEnd) / 2;

        Sorting.doMergeSort(sequence, indexBegin, indexMiddle);
        Sorting.doMergeSort(sequence, indexMiddle, indexEnd);
        Sorting.merge(sequence, indexBegin, indexMiddle, indexEnd);
    }

    // Merges two sorted fragments of a sequence.
    private static <T extends Comparable<T>> void merge(List<T> sequence, int indexBegin,
                                                        int indexMiddle, int indexEnd)
    {
        List<T> ordered = new ArrayList<>();
        int iter1 = indexBegin;
        int iter2 = indexMiddle;

        while(iter1 < indexMiddle && iter2 < indexEnd)
            if(sequence.get(iter1).compareTo(sequence.get(iter2)) < 0)
            {
                ordered.add(sequence.get(iter1));
                ++iter1;
            }
            else
            {
                ordered.add(sequence.get(iter2));
                ++iter2;
            }

        for(int i = iter1; i < indexMiddle; ++i)
            ordered.add(sequence.get(i));

        for(int i = iter2; i < indexEnd; ++i)
            ordered.add(sequence.get(i));

        for(int i = 0; i < ordered.size(); ++i)
            sequence.set(indexBegin + i, ordered.get(i));
    }

    // Mutably sorts specified sequence using a quick-sort algorithm.
    private static <T extends Comparable<T>> void doQuickSort(List<T> sequence, int indexBegin,
                                                              int indexEnd)
    {
        if(indexEnd - indexBegin <= 1)
            return;

        int indexPivot = indexBegin + Sorting.choosePivot(indexEnd - indexBegin);

        swap(sequence, indexPivot, indexBegin);
        indexPivot = indexBegin;

        int indexFront = indexBegin + 1;
        int indexBack = indexEnd - 1;

        while(indexPivot < indexBack)
            if(sequence.get(indexFront).compareTo(sequence.get(indexPivot)) < 0)
            {
                swap(sequence, indexPivot, indexFront);
                indexPivot = indexFront;
                ++indexFront;
            }
            else
            {
                swap(sequence, indexBack, indexFront);
                --indexBack;
            }

        Sorting.doQuickSort(sequence, indexBegin, indexPivot);
        Sorting.doQuickSort(sequence, indexPivot + 1, indexEnd);
    }

    // Randomly chooses pivot for quick-sort algorithm.
    private static int choosePivot(int size)
    {
        Random random = new Random();
        int candidate1 = random.nextInt(size);
        int candidate2 = random.nextInt(size);
        int candidate3 = random.nextInt(size);

        if(Math.min(candidate2, candidate3) <= candidate1 && candidate1 <= Math.max(candidate2,
                                                                                    candidate3))
            return candidate2;

        if(Math.min(candidate1, candidate3) <= candidate2 && candidate2 <= Math.max(candidate1,
                                                                                    candidate3))
            return candidate2;

        return candidate3;
    }

    // Swaps two elements in specified sequence.
    private static <T extends Comparable<T>> void swap(List<T> sequence, int index1, int index2)
    {
        T temp = sequence.get(index1);

        sequence.set(index1, sequence.get(index2));
        sequence.set(index2, temp);
    }
}
