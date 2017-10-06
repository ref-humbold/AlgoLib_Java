package ref_humbold.algolib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import ref_humbold.algolib.structures.Pair;

public class Sorting
{
    private static class AngleComparator
        implements Comparator<Pair<Double, Double>>
    {
        @Override
        public int compare(Pair<Double, Double> pt1, Pair<Double, Double> pt2)
        {
            Double angle1 = countAngle(pt1), angle2 = countAngle(pt2);

            return angle1.equals(angle2) ? countRadius(pt1).compareTo(countRadius(pt2))
                                         : angle1.compareTo(angle2);
        }

        private Double countAngle(Pair<Double, Double> pt)
        {
            double ang = Math.atan2(pt.getSecond(), pt.getFirst()) * 90.0 / Math.acos(0);

            return pt.getSecond() >= 0.0 ? ang : ang + 360.0;
        }

        private Double countRadius(Pair<Double, Double> pt)
        {
            return pt.getFirst() * pt.getFirst() + pt.getSecond() * pt.getSecond();
        }
    }

    /**
     * Mutowalne sortowanie kątowe punktów na płaszczyźnie.
     * @param points lista punktów
     */
    public static void angleSort(List<Pair<Double, Double>> points)
    {
        if(points == null)
            throw new IllegalArgumentException("List of points is null.");

        points.sort(new AngleComparator());
    }

    /**
     * Niemutowalne sortowanie kątowe punktów na płaszczyźnie.
     * @param points lista punktów
     * @return lista punktów posortowana względem kąta
     */
    public static List<Pair<Double, Double>> angleSorted(List<Pair<Double, Double>> points)
    {
        if(points == null)
            throw new IllegalArgumentException("List of points is null.");

        List<Pair<Double, Double>> pointsCopy = new ArrayList<>(points);

        angleSort(pointsCopy);

        return pointsCopy;
    }

    /**
     * Mutowalne sortowanie ciągu przez kopcowanie.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void heapSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        heapSort(sequence, 0, sequence.size());
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
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        if(indexBegin < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence beginning index out of range.");

        if(indexEnd < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence ending index out of range.");

        int heapSize = indexEnd - indexBegin;

        if(heapSize <= 1)
            return;

        for(int i = indexBegin + heapSize / 2; i >= indexBegin; --i)
            moveDown(sequence, i, indexBegin, indexEnd);

        while(heapSize > 1)
        {
            int indexHeap = indexBegin + heapSize - 1;
            T temp = sequence.get(indexHeap);

            sequence.set(indexHeap, sequence.get(indexBegin));
            sequence.set(indexBegin, temp);
            moveDown(sequence, indexBegin, indexBegin, indexHeap);
            --heapSize;
        }
    }

    /**
     * Niemutowalne sortowanie ciągu przez kopcowanie.
     * @param sequence ciąg
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> heapSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return heapSorted(sequence, 0, sequence.size());
    }

    /**
     * Niemutowalne sortowanie ciągu przez kopcowanie.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> heapSorted(List<T> sequence, int indexBegin,
                                                               int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        heapSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
    }

    /**
     * Mutowalne sortowanie ciągu przez scalanie top-down.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void mergedownSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        mergedownSort(sequence, 0, sequence.size());
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
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        if(indexBegin < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence beginning index out of range.");

        if(indexEnd < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence ending index out of range.");

        if(indexEnd - indexBegin <= 1)
            return;

        int indexMiddle = (indexBegin + indexEnd) / 2;

        mergedownSort(sequence, indexBegin, indexMiddle);
        mergedownSort(sequence, indexMiddle, indexEnd);
        merge(sequence, indexBegin, indexMiddle, indexEnd);
    }

    /**
     * Niemutowalne sortowanie ciągu przez scalanie top-down.
     * @param sequence ciąg
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> mergedownSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return mergedownSorted(sequence, 0, sequence.size());
    }

    /**
     * Niemutowalne sortowanie ciągu przez scalanie top-down.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> mergedownSorted(List<T> sequence,
                                                                    int indexBegin, int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        mergedownSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
    }

    /**
     * Mutowalne sortowanie ciągu przez scalanie bottom-up.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void mergeupSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        mergeupSort(sequence, 0, sequence.size());
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
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        if(indexBegin < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence beginning index out of range.");

        if(indexEnd < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence ending index out of range.");

        if(indexEnd - indexBegin <= 1)
            return;

        for(int i = 2; i < 2 * (indexEnd - indexBegin); i *= 2)
            for(int j = indexBegin; j < indexEnd; j += i)
                merge(sequence, j, Math.min(j + i / 2, indexEnd), Math.min(j + i, indexEnd));
    }

    /**
     * Niemutowalne sortowanie ciągu przez scalanie bottom-up.
     * @param sequence ciąg
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> mergeupSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return mergeupSorted(sequence, 0, sequence.size());
    }

    /**
     * Niemutowalne sortowanie ciągu przez scalanie bottom-up.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> mergeupSorted(List<T> sequence, int indexBegin,
                                                                  int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        mergeupSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
    }

    /**
     * Mutowalne szybkie sortowanie ciągu.
     * @param sequence ciąg
     */
    public static <T extends Comparable<T>> void quickSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        quickSort(sequence, 0, sequence.size());
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
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        if(indexBegin < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence beginning index out of range.");

        if(indexEnd < 0 || indexEnd > sequence.size())
            throw new IndexOutOfBoundsException("Sequence ending index out of range.");

        if(indexEnd - indexBegin <= 1)
            return;

        int indexPivot = indexBegin, indexFront = indexBegin + 1, indexBack = indexEnd - 1;
        int rdpv = indexBegin + choosePivot(indexEnd - indexBegin);
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

        quickSort(sequence, indexBegin, indexPivot);
        quickSort(sequence, indexPivot + 1, indexEnd);
    }

    /**
     * Niemutowalne szybkie sortowanie ciągu.
     * @param sequence ciąg
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> quickSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return quickSorted(sequence, 0, sequence.size());
    }

    /**
     * Niemutowalne szybkie sortowanie ciągu.
     * @param sequence ciąg
     * @param indexBegin początkowy indeks ciągu
     * @param indexEnd końcowy indeks ciągu
     * @return ciąg posortowanych elementów
     */
    public static <T extends Comparable<T>> List<T> quickSorted(List<T> sequence, int indexBegin,
                                                                int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        quickSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
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

        moveDown(heap, nextVertex, indexBegin, indexEnd);
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
}
