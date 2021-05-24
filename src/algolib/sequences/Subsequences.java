package algolib.sequences;

import java.util.*;

import algolib.tuples.Pair;

/** Algorithms for subsequences */
public final class Subsequences
{
    /**
     * Constructs longest increasing subsequence.
     * @param sequence sequence of elements
     * @param comparator comparator function of elements in subsequence
     * @return least lexicographically longest increasing subsequence
     */
    public static <T> Collection<T> longestIncreasing(List<T> sequence, Comparator<T> comparator)
    {
        List<Optional<Integer>> previousElem = new ArrayList<>();
        List<Integer> subsequence = new ArrayList<>();

        previousElem.add(Optional.empty());
        subsequence.add(0);

        for(int i = 1; i < sequence.size(); ++i)
        {
            T elem = sequence.get(i);
            Integer subseqEnd = subsequence.get(subsequence.size() - 1);

            if(comparator.compare(elem, sequence.get(subseqEnd)) > 0)
            {
                previousElem.add(Optional.of(subseqEnd));
                subsequence.add(i);
            }
            else
            {
                int index =
                        searchIndex(comparator, sequence, subsequence, 0, subsequence.size() - 1,
                                    i);

                subsequence.set(index, i);
                previousElem.add(
                        Optional.of(index).filter(ix -> ix > 0).map(ix -> subsequence.get(ix - 1)));
            }
        }

        List<T> longestSubseq = new ArrayList<>();
        Optional<Integer> j = Optional.of(subsequence.get(subsequence.size() - 1));

        while(j.isPresent())
        {
            longestSubseq.add(sequence.get(j.get()));
            j = previousElem.get(j.get());
        }

        Collections.reverse(longestSubseq);

        return longestSubseq;
    }

    /**
     * Dynamically constructs coherent subarray with maximal sum.
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
     * Calculates maximal sum from all coherent subarrays using interval tree.
     * @param sequence sequence of numbers
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
    private static <T> int searchIndex(Comparator<T> comparator, List<T> sequence,
                                       List<Integer> subsequence, int indexBegin, int indexEnd,
                                       int indexElem)
    {
        if(indexBegin == indexEnd)
            return indexBegin;

        int indexMiddle = (indexBegin + indexEnd) / 2;
        T middleElem = sequence.get(subsequence.get(indexMiddle));

        if(comparator.compare(sequence.get(indexElem), middleElem) > 0)
            return searchIndex(comparator, sequence, subsequence, indexMiddle + 1, indexEnd,
                               indexElem);

        return searchIndex(comparator, sequence, subsequence, indexBegin, indexMiddle, indexElem);
    }
}
