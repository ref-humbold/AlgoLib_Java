package algolib.sequences;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import algolib.tuples.Pair;

/** Algorithms for subsequences */
public final class Subsequences
{
    /**
     * Constructs the longest increasing subsequence.
     * @param sequence a sequence of elements
     * @param comparator comparator function of elements in subsequence
     * @return the longest increasing subsequence (least lexicographically)
     */
    public static <T> Collection<T> longestIncreasing(List<T> sequence, Comparator<T> comparator)
    {
        List<Optional<Integer>> previousElem =
                Stream.of(Optional.<Integer>empty()).collect(Collectors.toList());
        List<Integer> subsequence = Stream.of(0).collect(Collectors.toList());

        for(int i = 1; i < sequence.size(); ++i)
        {
            Integer subsequenceEnd = subsequence.get(subsequence.size() - 1);

            if(comparator.compare(sequence.get(i), sequence.get(subsequenceEnd)) > 0)
            {
                previousElem.add(Optional.of(subsequenceEnd));
                subsequence.add(i);
            }
            else
            {
                int index = searchIndex(sequence, comparator, subsequence, i, 0, subsequence.size());

                subsequence.set(index, i);
                previousElem.add(
                        Optional.of(index).filter(ix -> ix > 0).map(ix -> subsequence.get(ix - 1)));
            }
        }

        List<T> longestSubsequence = new ArrayList<>();
        Optional<Integer> subsequenceIndex = Optional.of(subsequence.get(subsequence.size() - 1));

        while(subsequenceIndex.isPresent())
        {
            longestSubsequence.add(sequence.get(subsequenceIndex.get()));
            subsequenceIndex = previousElem.get(subsequenceIndex.get());
        }

        Collections.reverse(longestSubsequence);

        return longestSubsequence;
    }

    /**
     * Dynamically constructs coherent subarray with maximal sum.
     * @param sequence sequence of numbers
     * @return the maximum subarray
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
     * Calculates maximal sum from all coherent subarrays using interval tree.
     * @param sequence a sequence of numbers
     * @return the sum of maximum subarray
     */
    public static double maximalSubsum(Collection<Double> sequence)
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

    // Searches for place of element in list of subsequences.
    // (indexBegin inclusive, indexEnd exclusive)
    private static <T> int searchIndex(List<T> sequence, Comparator<T> comparator,
                                       List<Integer> subsequence, int indexElem, int indexBegin,
                                       int indexEnd)
    {
        if(indexEnd - indexBegin <= 1)
            return indexBegin;

        int indexMiddle = (indexBegin + indexEnd - 1) / 2;
        T middleElem = sequence.get(subsequence.get(indexMiddle));

        if(comparator.compare(sequence.get(indexElem), middleElem) > 0)
            return searchIndex(sequence, comparator, subsequence,indexElem, indexMiddle + 1, indexEnd);

        return searchIndex(sequence, comparator, subsequence,indexElem, indexBegin, indexMiddle + 1);
    }
}
