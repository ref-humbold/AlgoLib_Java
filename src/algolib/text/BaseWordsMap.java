package algolib.text;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;

/** Structure of base words map using Karp-Miller-Rosenberg algorithm */
public final class BaseWordsMap
{
    private final String text;
    private final Map<Pair<Integer, Integer>, Integer> factors = new HashMap<>();

    private BaseWordsMap(String text)
    {
        this.text = text;
        create();
    }

    public static BaseWordsMap build(String text)
    {
        return new BaseWordsMap(text);
    }

    public String getText()
    {
        return text;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        BaseWordsMap other = (BaseWordsMap)obj;

        return Objects.equals(text, other.text) && Objects.equals(factors, other.factors);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(text, factors);
    }

    /**
     * Retrieves code of substring starting at given index.
     * @param startIndex starting index, inclusive
     * @return the code of the substring
     */
    public Pair<Integer, Integer> getCode(int startIndex)
    {
        return getCode(startIndex, text.length());
    }

    /**
     * Retrieves code of substring denoted by given range indices.
     * @param startIndex starting index, inclusive
     * @param endIndex ending index, exclusive
     * @return the code of the substring
     */
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

    // Builds base words map using Karp-Miller-Rosenberg algorithm.
    private void create()
    {
        int codeValue = extend(1, 0, (i, length) -> new int[]{text.charAt(i), 1 + text.charAt(i), i,
                                                              i + length});

        for(int currentLength = 2; currentLength <= text.length(); currentLength *= 2)
            codeValue = extend(currentLength, codeValue,
                               (i, length) -> new int[]{factors.get(Pair.of(i, i + length / 2)),
                                                        factors.get(Pair.of(i + length / 2,
                                                                            i + length)), i,
                                                        i + length});
    }

    // Encodes substring of given length using already counted factors.
    private int extend(int length, int codeValue, BiFunction<Integer, Integer, int[]> func)
    {
        ComparablePair<Integer, Integer> previousCode = ComparablePair.of(0, 0);
        List<int[]> codes = IntStream.range(0, text.length() - length + 1)
                                     .mapToObj(i -> func.apply(i, length))
                                     .sorted(new CodesComparator())
                                     .collect(Collectors.toList());

        for(int[] code : codes)
        {
            ComparablePair<Integer, Integer> codePair = ComparablePair.of(code[0], code[1]);

            if(!Objects.equals(previousCode, codePair))
            {
                ++codeValue;
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
