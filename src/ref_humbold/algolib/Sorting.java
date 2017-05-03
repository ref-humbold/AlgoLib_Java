package ref_humbold.algolib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.lang.Math;

import ref_humbold.algolib.structures.Pair;

class AngleComparator
    implements Comparator<Pair<Double, Double>>
{
    @Override
    public int compare(Pair<Double, Double> pt1, Pair<Double, Double> pt2)
    {
        Double angle1 = countAngle(pt1), angle2 = countAngle(pt2);

        return angle1 != angle2 ? angle1.compareTo(angle2)
                               : countRadius(pt1).compareTo(countRadius(pt2));
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

public class Sorting
{
    public static void angleSort(List<Pair<Double, Double>> points)
    {
        Collections.sort(points, new AngleComparator());
    }

    public static List<Pair<Double, Double>> angleSorted(List<Pair<Double, Double>> points)
    {
        List<Pair<Double, Double>> pointsCopy = new ArrayList<>(points);

        angleSort(pointsCopy);

        return pointsCopy;
    }

    public static <T extends Comparable<T>> void heapSort(List<T> sequence, int index_begin,
                                                          int index_end)
    {
        index_begin = (index_begin + sequence.size()) % sequence.size();
        index_end = (index_end + sequence.size()) % sequence.size();

        int heap_size = index_end - index_begin;

        for(int i = index_begin + heap_size / 2; i >= index_begin; --i)
            moveDown(sequence, i, index_begin, index_end);

        while(heap_size > 1)
        {
            int index_heap = index_begin + heap_size;
            T temp = sequence.get(index_heap);

            sequence.set(index_heap, sequence.get(index_begin));
            sequence.set(index_begin, temp);
            moveDown(sequence, index_begin, index_begin, index_heap - 1);
        }
    }

    public static <T extends Comparable<T>> List<T> heapSorted(List<T> sequence, int index_begin,
                                                               int index_end)
    {
        heapSort(sequence, index_begin, index_end);

        return sequence;
    }

    public static <T extends Comparable<T>> void mergeSort(List<T> sequence, int index_begin,
                                                           int index_end)
    {
        index_begin = (index_begin + sequence.size()) % sequence.size();
        index_end = (index_end + sequence.size()) % sequence.size();

        if(index_begin < index_end)
        {
            int index_middle = (index_begin + index_end) / 2;

            mergeSort(sequence, index_begin, index_middle);
            mergeSort(sequence, index_middle + 1, index_end);
            merge(sequence, index_begin, index_middle, index_end);
        }
    }

    public static <T extends Comparable<T>> List<T> mergeSorted(List<T> sequence, int index_begin,
                                                                int index_end)
    {
        mergeSort(sequence, index_begin, index_end);

        return sequence;
    }

    public static <T extends Comparable<T>> void quickSort(List<T> sequence, int index_begin,
                                                           int index_end)
    {
        index_begin = (index_begin + sequence.size()) % sequence.size();
        index_end = (index_end + sequence.size()) % sequence.size();

        if(index_begin < index_end)
        {
            int index_pivot = index_begin, index_front = index_begin + 1, index_back = index_end;
            int rdpv = index_begin + choosePivot(index_end - index_begin + 1);
            T temp1 = sequence.get(index_pivot);

            sequence.set(index_pivot, sequence.get(rdpv));
            sequence.set(rdpv, temp1);

            while(index_pivot < index_back)
                if(sequence.get(index_front).compareTo(sequence.get(index_pivot)) < 0)
                {
                    T temp2 = sequence.get(index_front);

                    sequence.set(index_front, sequence.get(index_pivot));
                    sequence.set(index_pivot, temp2);
                    index_pivot = index_front;
                    ++index_front;
                }
                else
                {
                    T temp2 = sequence.get(index_front);

                    sequence.set(index_front, sequence.get(index_back));
                    sequence.set(index_back, temp2);
                    --index_back;
                }

            quickSort(sequence, index_begin, index_pivot - 1);
            quickSort(sequence, index_pivot + 1, index_end);
        }
    }

    public static <T extends Comparable<T>> List<T> quickSorted(List<T> sequence, int index_begin,
                                                                int index_end)
    {
        quickSort(sequence, index_begin, index_end);

        return sequence;
    }

    private static <T extends Comparable<T>> void moveDown(List<T> heap, int vertex,
                                                           int index_begin, int index_end)
    {
        int next_vertex = -1;
        int left_vertex = (vertex << 1) - index_begin + 1;
        int right_vertex = (vertex << 1) - index_begin + 2;

        if(right_vertex <= index_end)
            next_vertex =
                heap.get(right_vertex).compareTo(heap.get(left_vertex)) < 0 ? left_vertex
                                                                           : right_vertex;

        if(left_vertex == index_end)
            next_vertex = left_vertex;

        if(next_vertex >= 0)
        {
            if(heap.get(next_vertex).compareTo(heap.get(vertex)) > 0)
            {
                T temp = heap.get(next_vertex);

                heap.set(next_vertex, heap.get(vertex));
                heap.set(vertex, temp);
            }

            moveDown(heap, next_vertex, index_begin, index_end);
        }
    }

    private static <T extends Comparable<T>> void merge(List<T> sequence, int index_begin,
                                                        int index_middle, int index_end)
    {
        List<T> ordered = new ArrayList<>();
        int iter1 = index_begin, iter2 = index_middle + 1;

        while(iter1 <= index_middle && iter2 <= index_end)
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

        if(iter1 <= index_middle)
            for(int i = iter1; i <= index_middle; ++i)
                ordered.add(sequence.get(i));

        if(iter2 <= index_end)
            for(int i = iter2; i <= index_end; ++i)
                ordered.add(sequence.get(i));

        for(int i = 0; i < ordered.size(); ++i)
            sequence.set(index_begin + i, ordered.get(i));
    }

    private static int choosePivot(int size)
    {
        Random random = new Random();
        int candidate1 = random.nextInt(size);
        int candidate2 = random.nextInt(size);
        int candidate3 = random.nextInt(size);

        if(Math.min(candidate2, candidate3) <= candidate1
           && candidate1 <= Math.max(candidate2, candidate3))
            return candidate2;
        else if(Math.min(candidate1, candidate3) <= candidate2
                && candidate2 <= Math.max(candidate1, candidate3))
            return candidate2;
        else
            return candidate3;
    }
}
