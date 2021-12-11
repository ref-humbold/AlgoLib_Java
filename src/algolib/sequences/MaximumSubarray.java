package algolib.sequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import algolib.tuples.Pair;

/** Algorithms for maximum subarray */
public final class MaximumSubarray
{
    /**
     * Dynamically constructs coherent subarray with maximal sum.
     * @param sequence sequence of numbers
     * @return maximum subarray
     */
    public static List<Double> findMaximumSubarray(Iterable<Double> sequence)
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
     * Calculates maximal sum from all coherent subarrays using interval tree.
     * @param sequence sequence of numbers
     * @return sum of maximum subarray
     */
    public static double countMaximalSubsum(Collection<Double> sequence)
    {
        int size = 1;

        while(size < 2 * sequence.size())
            size *= 2;

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
}
