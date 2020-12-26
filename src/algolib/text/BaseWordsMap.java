// Structure of base words map using Karp-Miller-Rosenberg algorithm
package algolib.text;

import java.util.*;
import java.util.function.BiFunction;

import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;

public class BaseWordsMap
{
    private final String text;
    private final Map<Pair<Integer, Integer>, Integer> factors = new HashMap<>();

    public BaseWordsMap(String text)
    {
        this.text = text;
        create();
    }

    public String getText()
    {
        return text;
    }

    public Pair<Integer, Integer> getCode(int startIndex)
    {
        return getCode(startIndex, text.length());
    }

    public Pair<Integer, Integer> getCode(int startIndex, int endIndex)
    {
        if(startIndex < 0 || startIndex >= text.length())
            throw new IndexOutOfBoundsException(String.format("Index out of range %d", startIndex));

        if(endIndex < 0 || endIndex > text.length())
            throw new IndexOutOfBoundsException(String.format("Index out of range %d", endIndex));

        if(endIndex <= startIndex)
            return Pair.of(0, 0);

        Integer code = factors.get(Pair.of(startIndex, endIndex));

        if(code != null)
            return Pair.of(code, 0);

        int n = getMaxLength(endIndex - startIndex);
        return Pair.of(factors.get(Pair.of(startIndex, startIndex + n)),
                       factors.get(Pair.of(endIndex - n, endIndex)));
    }

    // Builds a base words map using Karp-Miller-Rosenberg algorithm
    private void create()
    {
        int currentLength = 2;
        int codeValue = extend(1, 0, (i, length) -> new int[]{text.charAt(i), 1 + text.charAt(i), i,
                                                              i + length});

        while(currentLength <= text.length())
        {
            codeValue = extend(currentLength, codeValue,
                               (i, length) -> new int[]{factors.get(Pair.of(i, i + length / 2)),
                                                        factors.get(Pair.of(i + length / 2,
                                                                            i + length)), i,
                                                        i + length});
            currentLength *= 2;
        }
    }

    // Encodes substring of given length using already counted factors
    private int extend(int length, int codeValue, BiFunction<Integer, Integer, int[]> func)
    {
        ComparablePair<Integer, Integer> previousCode = ComparablePair.of(0, 0);
        List<int[]> codes = new ArrayList<>();

        for(int i = 0; i <= text.length() - length; ++i)
            codes.add(func.apply(i, length));

        Collections.sort(codes, new CodesComparator());

        for(int[] code : codes)
        {
            ComparablePair<Integer, Integer> codePair = ComparablePair.of(code[0], code[1]);

            if(!Objects.equals(previousCode, codePair))
            {
                codeValue++;
                previousCode = codePair;
            }

            factors.put(Pair.of(code[2], code[3]), codeValue);
        }

        return codeValue;
    }

    private int getMaxLength(int n)
    {
        int prev = 0;
        int power = 1;

        while(power < n)
        {
            prev = power;
            power *= 2;
        }

        return prev;
    }

    private static class CodesComparator
            implements Comparator<int[]>
    {
        @Override
        public int compare(int[] a1, int[] a2)
        {
            for(int i = 0; i < Math.min(a1.length, a2.length); ++i)
            {
                int compareInts = Integer.compare(a1[i], a2[i]);

                if(compareInts != 0)
                    return compareInts;
            }

            return 0;
        }
    }
}
