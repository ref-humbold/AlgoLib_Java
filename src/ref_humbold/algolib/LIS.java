// ALGORYTM WYZNACZAJĄCY NAJMNIEJSZY LEKSYKOGRAFICZNIE NAJDŁUŻSZY PODCIĄG ROSNĄCY
package ref_humbold.algolib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LIS
{
    /**
     * Wyznaczanie najdłuższego podciągu rosnącego.
     * @param sequence ciąg wejściowy
     * @return najdłuższy podciąg rosnący
     */
    public static <T extends Comparable<T>> Collection<T> findLIS(List<T> sequence)
    {
        List<Integer> previousElem = new ArrayList<>(Collections.nCopies(sequence.size(), null));
        List<Integer> subseqLast = new ArrayList<>();

        subseqLast.add(0);

        for(int i = 1; i < sequence.size(); ++i)
        {
            T elem = sequence.get(i);

            if(elem.compareTo(sequence.get(subseqLast.get(subseqLast.size() - 1))) > 0)
            {
                previousElem.set(i, subseqLast.get(subseqLast.size() - 1));
                subseqLast.add(i);
            }
            else
            {
                int index = search(sequence, subseqLast, 0, subseqLast.size() - 1, i);
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

    private static <T extends Comparable<T>> int search(List<T> sequence, List<Integer> subseqLast,
                                                        int indexBegin, int indexEnd, int indexElem)
    {
        if(indexBegin == indexEnd)
            return indexBegin;

        int indexMiddle = (indexBegin + indexEnd) / 2;

        if(sequence.get(indexElem).compareTo(sequence.get(subseqLast.get(indexMiddle))) > 0)
            return search(sequence, subseqLast, indexMiddle + 1, indexEnd, indexElem);
        else
            return search(sequence, subseqLast, indexBegin, indexMiddle, indexElem);
    }
}
