package refhumbold.algolib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ListSorting
{
    /**
     * Mutowalne sortowanie ciągu przez kopcowanie.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void heapSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        ListSorting.heapSort(sequence, 0, sequence.size());
    }

    /**
     * Mutowalne sortowanie ciągu przez kopcowanie.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     */
    public static <T extends Comparable<T>> void heapSort(List<T> sequence, int indexBegin,
                                                          int indexEnd)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        assertIndices(indexBegin, indexEnd, sequence.size());

        int heapSize = indexEnd - indexBegin;

        if(heapSize <= 1)
            return;

        for(int i = indexBegin + heapSize / 2; i >= indexBegin; --i)
            ListSorting.moveDown(sequence, i, indexBegin, indexEnd);

        while(heapSize > 1)
        {
            int indexHeap = indexBegin + heapSize - 1;
            T temp = sequence.get(indexHeap);

            sequence.set(indexHeap, sequence.get(indexBegin));
            sequence.set(indexBegin, temp);
            ListSorting.moveDown(sequence, indexBegin, indexBegin, indexHeap);
            --heapSize;
        }
    }

    /**
     * Mutowalne sortowanie ciągu przez scalanie top-down.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void mergedownSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        ListSorting.mergedownSort(sequence, 0, sequence.size());
    }

    /**
     * Mutowalne sortowanie ciągu przez scalanie top-down.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     */
    public static <T extends Comparable<T>> void mergedownSort(List<T> sequence, int indexBegin,
                                                               int indexEnd)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");
        assertIndices(indexBegin, indexEnd, sequence.size());

        if(indexEnd - indexBegin <= 1)
            return;

        int indexMiddle = (indexBegin + indexEnd) / 2;

        ListSorting.mergedownSort(sequence, indexBegin, indexMiddle);
        ListSorting.mergedownSort(sequence, indexMiddle, indexEnd);
        ListSorting.merge(sequence, indexBegin, indexMiddle, indexEnd);
    }

    /**
     * Mutowalne sortowanie ciągu przez scalanie bottom-up.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void mergeupSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        ListSorting.mergeupSort(sequence, 0, sequence.size());
    }

    /**
     * Mutowalne sortowanie ciągu przez scalanie bottom-up.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     */
    public static <T extends Comparable<T>> void mergeupSort(List<T> sequence, int indexBegin,
                                                             int indexEnd)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");
        assertIndices(indexBegin, indexEnd, sequence.size());

        if(indexEnd - indexBegin <= 1)
            return;

        for(int i = 2; i < 2 * (indexEnd - indexBegin); i *= 2)
            for(int j = indexBegin; j < indexEnd; j += i)
                ListSorting.merge(sequence, j, Math.min(j + i / 2, indexEnd),
                                  Math.min(j + i, indexEnd));
    }

    /**
     * Mutowalne szybkie sortowanie ciągu.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void quickSort(List<T> sequence)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");

        ListSorting.quickSort(sequence, 0, sequence.size());
    }

    /**
     * Mutowalne szybkie sortowanie ciągu.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     */
    public static <T extends Comparable<T>> void quickSort(List<T> sequence, int indexBegin,
                                                           int indexEnd)
    {
        Objects.requireNonNull(sequence, "Sequence is null.");
        assertIndices(indexBegin, indexEnd, sequence.size());

        if(indexEnd - indexBegin <= 1)
            return;

        int indexPivot = indexBegin, indexFront = indexBegin + 1, indexBack = indexEnd - 1;
        int rdpv = indexBegin + ListSorting.choosePivot(indexEnd - indexBegin);
        T temp1 = sequence.get(indexPivot);

        sequence.set(indexPivot, sequence.get(rdpv));
        sequence.set(rdpv, temp1);

        while(indexPivot < indexBack)
            if(sequence.get(indexFront).compareTo(sequence.get(indexPivot)) < 0)
            {
                T temp2 = sequence.get(indexFront);

                sequence.set(indexFront, sequence.get(indexPivot));
                sequence.set(indexPivot, temp2);
                indexPivot = indexFront;
                ++indexFront;
            }
            else
            {
                T temp2 = sequence.get(indexFront);

                sequence.set(indexFront, sequence.get(indexBack));
                sequence.set(indexBack, temp2);
                --indexBack;
            }

        ListSorting.quickSort(sequence, indexBegin, indexPivot);
        ListSorting.quickSort(sequence, indexPivot + 1, indexEnd);
    }

    /**
     * Przywracanie własności kopca.
     * @param heap kopiec
     * @param vertex wierzchołek kopca
     * @param indexBegin początkowy indeks kopca
     * @param indexEnd końcowy indeks kopca
     */
    private static <T extends Comparable<T>> void moveDown(List<T> heap, int vertex, int indexBegin,
                                                           int indexEnd)
    {
        int nextVertex = -1;
        int leftVertex = vertex + vertex - indexBegin + 1;
        int rightVertex = vertex + vertex - indexBegin + 2;

        if(rightVertex < indexEnd)
            nextVertex = heap.get(rightVertex).compareTo(heap.get(leftVertex)) < 0 ? leftVertex
                                                                                   : rightVertex;

        if(leftVertex == indexEnd - 1)
            nextVertex = leftVertex;

        if(nextVertex < 0)
            return;

        if(heap.get(nextVertex).compareTo(heap.get(vertex)) > 0)
        {
            T temp = heap.get(nextVertex);

            heap.set(nextVertex, heap.get(vertex));
            heap.set(vertex, temp);
        }

        ListSorting.moveDown(heap, nextVertex, indexBegin, indexEnd);
    }

    /**
     * Scalanie dwóch uporządkowanych fragmentów ciągu.
     * @param sequence ciąg
     * @param indexBegin początek fragmentu
     * @param indexMiddle środek fragmentu
     * @param indexEnd koniec fragmentu
     */
    private static <T extends Comparable<T>> void merge(List<T> sequence, int indexBegin,
                                                        int indexMiddle, int indexEnd)
    {
        List<T> ordered = new ArrayList<>();
        int iter1 = indexBegin, iter2 = indexMiddle;

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

    /**
     * Losowanie piwota.
     * @param size liczba elementów
     * @return indeks piwota
     */
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

    private static void assertIndices(int indexBegin, int indexEnd, int size)
    {
        if(indexBegin < 0 || indexBegin > size)
            throw new IndexOutOfBoundsException("Sequence beginning index out of range.");

        if(indexEnd < 0 || indexEnd > size)
            throw new IndexOutOfBoundsException("Sequence ending index out of range.");
    }
}
