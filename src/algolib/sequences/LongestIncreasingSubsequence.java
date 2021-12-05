package algolib.sequences;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Algorithm for longest increasing subsequence */
public final class LongestIncreasingSubsequence
{
    /**
     * Constructs the longest increasing subsequence.
     * @param sequence sequence of elements
     * @param comparator comparator function of elements in subsequence
     * @return the longest increasing subsequence (least lexicographically)
     */
    public static <T> Collection<T> findLIS(List<T> sequence, Comparator<T> comparator)
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
                int index =
                        searchIndex(sequence, comparator, subsequence, i, 0, subsequence.size());

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
            return searchIndex(sequence, comparator, subsequence, indexElem, indexMiddle + 1,
                               indexEnd);

        return searchIndex(sequence, comparator, subsequence, indexElem, indexBegin,
                           indexMiddle + 1);
    }
}
