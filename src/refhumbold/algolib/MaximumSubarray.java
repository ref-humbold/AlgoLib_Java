// ALGORYTMY WYZNACZANIA PODCIĄGU SPÓJNEGO O MAKSYMALNEJ SUMIE
package refhumbold.algolib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import refhumbold.algolib.tuples.Pair;

public class MaximumSubarray
{
    /**
     * Wyznaczanie spójnego podciągu o maksymalnej sumie w sposób dynamiczny.
     * @param sequence ciąg
     * @return elementy spójnego podciągu o maksymalnej sumie
     */
    public static List<Double> findMaximumSubarray(Iterable<Double> sequence)
    {
        Pair<Double, List<Double>> actual = Pair.make(0.0, new ArrayList<Double>());
        Pair<Double, List<Double>> maximal = Pair.make(0.0, new ArrayList<Double>());

        for(Double elem : sequence)
        {
            if(actual.getFirst() < 0.0)
                actual = Pair.make(0.0, new ArrayList<Double>());

            actual = Pair.make(actual.getFirst() + elem, actual.getSecond());
            actual.getSecond().add(elem);

            if(actual.getFirst() > maximal.getFirst())
                maximal = Pair.make(actual.getFirst(), new ArrayList<>(actual.getSecond()));
        }

        return maximal.getSecond();
    }

    /**
     * Wyznaczanie maksymalnej sumy spójnego podciągu za pomocą drzewa przedziałowego.
     * @param sequence ciąg
     * @return maksymalna suma
     */
    public static double findMaximalSum(Collection<Double> sequence)
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
}
