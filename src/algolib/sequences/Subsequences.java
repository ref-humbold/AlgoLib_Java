// Algorithms for subsequences
package algolib.sequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

import algolib.tuples.Pair;

public class Subsequences
{
    /**
     * Constructs longest ordered subsequence
     * @param sequence sequence of elements
     * @param order order function of elements in subsequence
     * @return least lexicographically longest ordered subsequence
     */
    public static <T> Collection<T> longestOrdered(List<T> sequence, BiPredicate<T, T> order)
    {
        List<Integer> previousElem = new ArrayList<>(Collections.nCopies(sequence.size(), null));
        List<Integer> subseqLast = new ArrayList<>();

        subseqLast.add(0);

        for(int i = 1; i < sequence.size(); ++i)
        {
            T elem = sequence.get(i);

            if(order.test(elem, sequence.get(subseqLast.get(subseqLast.size() - 1))))
            {
                previousElem.set(i, subseqLast.get(subseqLast.size() - 1));
                subseqLast.add(i);
            }
            else
            {
                int index = searchOrd(order, sequence, subseqLast, 0, subseqLast.size() - 1, i);

                subseqLast.set(index, i);
                previousElem.set(i, index > 0 ? subseqLast.get(index - 1) : null);
            }
        }

        List<T> longestSubseq = new ArrayList<>();
        Integer j = subseqLast.get(subseqLast.size() - 1);

        while(j != null)
        {
            longestSubseq.add(sequence.get(j));
            j = previousElem.get(j);
        }

        Collections.reverse(longestSubseq);

        return longestSubseq;
    }

    /**
     * Dynamically constructs coherent subarray with maximal sum
     * @param sequence sequence of numbers
     * @return maximum subarray
     */
    public static List<Double> maximumSubarray(Iterable<Double> sequence)
    {
        Pair<Double, List<Double>> actual = Pair.of(0.0, new ArrayList<>());
        Pair<Double, List<Double>> maximal = Pair.of(0.0, new ArrayList<>());

        for(Double elem : sequence)
        {
            if(actual.first < 0.0)
                actual = Pair.of(0.0, new ArrayList<>());

            actual = Pair.of(actual.first + elem, actual.second);
            actual.second.add(elem);

            if(actual.first > maximal.first)
                maximal = Pair.of(actual.first, new ArrayList<>(actual.second));
        }

        return maximal.second;
    }

    /**
     * Counts maximal sum from all coherent subarrays using interval tree
     * @param sequence sequence of numbers
     * @return the sum of maximum subarray
     */
    public static double maximalSubsum(Collection<Double> sequence)
    {
        int size = 1;

        while(size < 2 * sequence.size())
            size <<= 1;

        List<Double> intervalSums = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> prefixSums = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> suffixSums = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> allSums = new ArrayList<>(Collections.nCopies(size, 0.0));

        int i = 0;

        for(Double elem : sequence)
        {
            int index = size / 2 + i;

            allSums.set(index, allSums.get(index) + elem);
            intervalSums.set(index, Math.max(allSums.get(index), 0.0));
            prefixSums.set(index, Math.max(allSums.get(index), 0.0));
            suffixSums.set(index, Math.max(allSums.get(index), 0.0));
            index /= 2;
            ++i;

            while(index > 0)
            {
                int indexLeft = index + index;
                int indexRight = index + index + 1;

                intervalSums.set(index, Math.max(
                        Math.max(intervalSums.get(indexRight), intervalSums.get(indexLeft)),
                        suffixSums.get(indexRight) + prefixSums.get(indexLeft)));
                prefixSums.set(index, Math.max(prefixSums.get(indexRight),
                                               allSums.get(indexRight) + prefixSums.get(
                                                       indexLeft)));
                suffixSums.set(index, Math.max(suffixSums.get(indexLeft),
                                               suffixSums.get(indexRight) + allSums.get(
                                                       indexLeft)));
                allSums.set(index, allSums.get(indexRight) + allSums.get(indexLeft));
                index /= 2;
            }
        }

        return intervalSums.get(1);
    }

    /**
     * Searches for place of element in list of subsequences
     * @param order order function of elements in subsequence
     * @param sequence input sequence
     * @param subseqLast last elements of subsequences
     * @param indexBegin index of beginning
     * @param indexEnd index of end
     * @param indexElem index of current element
     * @return index in list of subsequences
     */
    private static <T> int searchOrd(BiPredicate<T, T> order, List<T> sequence,
                                     List<Integer> subseqLast, int indexBegin, int indexEnd,
                                     int indexElem)
    {
        if(indexBegin == indexEnd)
            return indexBegin;

        int indexMiddle = (indexBegin + indexEnd) / 2;

        if(order.test(sequence.get(indexElem), sequence.get(subseqLast.get(indexMiddle))))
            return searchOrd(order, sequence, subseqLast, indexMiddle + 1, indexEnd, indexElem);
        else
            return searchOrd(order, sequence, subseqLast, indexBegin, indexMiddle, indexElem);
    }
}
