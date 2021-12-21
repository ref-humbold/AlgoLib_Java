package algolib.sequences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Algorithm for longest common subsequence */
public final class LongestCommonSubsequence
{
    public static <T> int countLCSLength(List<T> sequence1, List<T> sequence2)
    {
        List<T> shortList = sequence1.size() <= sequence2.size() ? sequence1 : sequence2;
        List<T> longList = sequence1.size() > sequence2.size() ? sequence1 : sequence2;

        List<Integer> previousLCS = new ArrayList<>(Collections.nCopies(shortList.size() + 1, 0));

        for(T element : longList)
        {
            List<Integer> nextLCS = new ArrayList<>(List.of(0));

            for(int i = 0; i < shortList.size(); ++i)
                nextLCS.add(element.equals(shortList.get(i))
                            ? previousLCS.get(i) + 1
                            : Math.max(previousLCS.get(i + 1), nextLCS.get(i)));

            previousLCS = nextLCS;
        }

        return previousLCS.get(previousLCS.size() - 1);
    }

    public static int countLCSLength(String text1, String text2)
    {
        String shortText = text1.length() <= text2.length() ? text1 : text2;
        String longText = text1.length() > text2.length() ? text1 : text2;

        List<Integer> previousLCS = new ArrayList<>(Collections.nCopies(shortText.length() + 1, 0));

        for(char element : longText.toCharArray())
        {
            List<Integer> nextLCS = new ArrayList<>(List.of(0));

            for(int i = 0; i < shortText.length(); ++i)
                nextLCS.add(element == shortText.charAt(i)
                            ? previousLCS.get(i) + 1
                            : Math.max(previousLCS.get(i + 1), nextLCS.get(i)));

            previousLCS = nextLCS;
        }

        return previousLCS.get(previousLCS.size() - 1);
    }
}
