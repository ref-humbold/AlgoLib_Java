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

    public static void angleSort(List<Pair<Double, Double>> points)
    {
        if(points == null)
            throw new IllegalArgumentException("List of points is null.");

        points.sort(new AngleComparator());
    }

    public static List<Pair<Double, Double>> angleSorted(List<Pair<Double, Double>> points)
    {
        if(points == null)
            throw new IllegalArgumentException("List of points is null.");

        List<Pair<Double, Double>> pointsCopy = new ArrayList<>(points);

        angleSort(pointsCopy);

        return pointsCopy;
    }

    public static <T extends Comparable<T>> void heapSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        heapSort(sequence, 0, sequence.size());
    }

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

    public static <T extends Comparable<T>> List<T> heapSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return heapSorted(sequence, 0, sequence.size());
    }

    public static <T extends Comparable<T>> List<T> heapSorted(List<T> sequence, int indexBegin,
                                                               int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        heapSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
    }

    public static <T extends Comparable<T>> void mergeSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        mergeSort(sequence, 0, sequence.size());
    }

    public static <T extends Comparable<T>> void mergeSort(List<T> sequence, int indexBegin,
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

        mergeSort(sequence, indexBegin, indexMiddle);
        mergeSort(sequence, indexMiddle, indexEnd);
        merge(sequence, indexBegin, indexMiddle, indexEnd);
    }

    public static <T extends Comparable<T>> List<T> mergeSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return mergeSorted(sequence, 0, sequence.size());
    }

    public static <T extends Comparable<T>> List<T> mergeSorted(List<T> sequence, int indexBegin,
                                                                int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        mergeSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
    }

    public static <T extends Comparable<T>> void quickSort(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        quickSort(sequence, 0, sequence.size());
    }

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

    public static <T extends Comparable<T>> List<T> quickSorted(List<T> sequence)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        return quickSorted(sequence, 0, sequence.size());
    }

    public static <T extends Comparable<T>> List<T> quickSorted(List<T> sequence, int indexBegin,
                                                                int indexEnd)
    {
        if(sequence == null)
            throw new IllegalArgumentException("Sequence is null.");

        List<T> sequenceCopy = new ArrayList<>(sequence);

        quickSort(sequenceCopy, indexBegin, indexEnd);

        return sequenceCopy;
    }

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

    private static int choosePivot(int size)
    {
        Random random = new Random();
        int candidate1 = random.nextInt(size);
        int candidate2 = random.nextInt(size);
        int candidate3 = random.nextInt(size);

        if(Math.min(candidate2, candidate3) <= candidate1 && candidate1 <= Math.max(candidate2,
                                                                                    candidate3))
            return candidate2;
        else if(Math.min(candidate1, candidate3) <= candidate2 && candidate2 <= Math.max(candidate1,
                                                                                         candidate3))
            return candidate2;
        else
            return candidate3;
    }
}
