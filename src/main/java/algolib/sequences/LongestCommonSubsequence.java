package algolib.sequences;

import java.util.List;
import java.util.stream.IntStream;

/** Algorithm for longest common subsequence. */
public final class LongestCommonSubsequence
{
    /**
     * Computes length of the longest common subsequence of given sequences.
     * @param <T> the type of sequence elements
     * @param sequence1 the first sequence
     * @param sequence2 the second sequence
     * @return the length of the longest common subsequence
     */
    public static <T> int countLcsLength(List<T> sequence1, List<T> sequence2)
    {
        List<T> shortList = sequence1.size() <= sequence2.size() ? sequence1 : sequence2;
        List<T> longList = sequence1.size() > sequence2.size() ? sequence1 : sequence2;
        int[] lcs = IntStream.range(0, shortList.size() + 1).map(i -> 0).toArray();

        for(T element : longList)
        {
            int previousAbove = lcs[0];

            for(int i = 0; i < shortList.size(); ++i)
            {
                int previousDiagonal = previousAbove;

                previousAbove = lcs[i + 1];
                lcs[i + 1] = element.equals(shortList.get(i))
                             ? previousDiagonal + 1
                             : Math.max(previousAbove, lcs[i]);
            }
        }

        return lcs[lcs.length - 1];
    }

    /**
     * Computes length of the longest common subsequence of given texts.
     * @param text1 the first text
     * @param text2 the second text
     * @return the length of the longest common subsequence
     */
    public static int countLcsLength(String text1, String text2)
    {
        String shortText = text1.length() <= text2.length() ? text1 : text2;
        String longText = text1.length() > text2.length() ? text1 : text2;
        int[] lcs = IntStream.range(0, shortText.length() + 1).map(i -> 0).toArray();

        for(char element : longText.toCharArray())
        {
            int previousAbove = lcs[0];

            for(int i = 0; i < shortText.length(); ++i)
            {
                int previousDiagonal = previousAbove;

                previousAbove = lcs[i + 1];
                lcs[i + 1] = element == shortText.charAt(i)
                             ? previousDiagonal + 1
                             : Math.max(previousAbove, lcs[i]);
            }
        }

        return lcs[lcs.length - 1];
    }
}
