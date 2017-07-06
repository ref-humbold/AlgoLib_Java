// ALGORYTMY WYZNACZANIA PODCIĄGU SPÓJNEGO O MAKSYMALNEJ SUMIE
package ref_humbold.algolib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaximumSubarray
{
    /**
     * Wyznaczanie spójnego podciągu o maksymalnej sumie w sposób dynamiczny.
     * @param sequence ciąg
     * @return elementy spójnego podciągu o maksymalnej sumie
     */
    public static List<Double> findMaximumSubarray(List<Double> sequence)
    {
        List<Double> maxSubseq = new ArrayList<>();
        List<Double> actualSubseq = new ArrayList<>();
        double maxSum = 0.0, actualSum = 0.0;

        for(Double elem : sequence)
        {
            if(actualSum < 0.0)
            {
                actualSum = 0.0;
                actualSubseq.clear();
            }

            actualSubseq.add(elem);
            actualSum += elem;

            if(actualSum > maxSum)
            {
                maxSum = actualSum;
                maxSubseq = new ArrayList<>(actualSubseq);
            }
        }

        return maxSubseq;
    }

    /**
     * Wyznaczanie maksymalnej sumy spójnego podciągu za pomocą drzewa przedziałowego.
     * @param sequence ciąg
     * @return maksymalna suma
     */
    public static double findMaximalSum(List<Double> sequence)
    {
        int size = 1;

        while(size < 2 * sequence.size())
            size <<= 1;

        List<Double> intervalSums = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> prefixSums = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> suffixSums = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> allSums = new ArrayList<>(Collections.nCopies(size, 0.0));

        for(int i = 0; i < sequence.size(); ++i)
        {
            int index = size / 2 + i;

            allSums.set(index, allSums.get(index) + sequence.get(i));
            intervalSums.set(index, Math.max(allSums.get(index), 0.0));
            prefixSums.set(index, Math.max(allSums.get(index), 0.0));
            suffixSums.set(index, Math.max(allSums.get(index), 0.0));
            index >>= 1;

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
                index >>= 1;
            }
        }

        return intervalSums.get(1);
    }
}
