package com.github.refhumbold.algolib.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.github.refhumbold.algolib.tuples.Pair;

/** Structure of basic factors map using Karp-Miller-Rosenberg algorithm. */
public final class BasicFactorsMap
{
    private final String text;
    private final Map<Pair<Integer, Integer>, Integer> factors = new HashMap<>();

    private BasicFactorsMap(String text)
    {
        this.text = text;
        create();
    }

    public String getText()
    {
        return text;
    }

    public static BasicFactorsMap build(String text)
    {
        return new BasicFactorsMap(text);
    }

    @Override
    public boolean equals(Object obj)
    {
        return this == obj || obj instanceof BasicFactorsMap other && Objects.equals(text,
                other.text) && Objects.equals(factors, other.factors);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(text, factors);
    }

    /**
     * Retrieves code of substring starting at given index.
     * @param startIndex the start index, inclusive
     * @return the code of the substring
     */
    public Pair<Integer, Integer> getCode(int startIndex)
    {
        return getCode(startIndex, text.length());
    }

    /**
     * Retrieves code of substring denoted by given indices range.
     * @param startIndex the start index, inclusive
     * @param endIndex the end index, exclusive
     * @return the code of the substring
     */
    public Pair<Integer, Integer> getCode(int startIndex, int endIndex)
    {
        if(startIndex < 0 || startIndex >= text.length() || endIndex < 0
                || endIndex > text.length())
            throw new IndexOutOfBoundsException(
                    "Range [%d, %d) out of bounds for text".formatted(startIndex, endIndex));

        if(endIndex <= startIndex)
            throw new IllegalArgumentException("Empty range of indices");

        Integer code = factors.get(Pair.of(startIndex, endIndex));

        if(code != null)
            return Pair.of(code, 0);

        int n = getMaxLength(endIndex - startIndex);

        return Pair.of(factors.get(Pair.of(startIndex, startIndex + n)),
                factors.get(Pair.of(endIndex - n, endIndex)));
    }

    // Builds basic factors map using Karp-Miller-Rosenberg algorithm.
    private void create()
    {
        int codeValue = extend(1, 0,
                (i, length) -> new ExtensionCode(text.charAt(i), 1 + text.charAt(i), i));

        for(int currentLength = 2; currentLength <= text.length(); currentLength *= 2)
            codeValue = extend(currentLength, codeValue,
                    (i, length) -> new ExtensionCode(factors.get(Pair.of(i, i + length / 2)),
                            factors.get(Pair.of(i + length / 2, i + length)), i));
    }

    // Encodes substring of given length using already counted factors.
    private int extend(int length, int codeValue, BiFunction<Integer, Integer, ExtensionCode> func)
    {
        List<ExtensionCode> codes = Stream.concat(Stream.of(new ExtensionCode(0, 0, -1)),
                IntStream.range(0, text.length() - length + 1)
                         .mapToObj(i -> func.apply(i, length))
                         .sorted()).toList();

        for(int i = 1; i < codes.size(); ++i)
        {
            if(!codes.get(i).equals(codes.get(i - 1)))
                ++codeValue;

            factors.put(Pair.of(codes.get(i).index, codes.get(i).index + length), codeValue);
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

    private record ExtensionCode(int prefixCode, int suffixCode, int index)
            implements Comparable<ExtensionCode>
    {
        @Override
        public boolean equals(Object obj)
        {
            return this == obj
                    || obj instanceof ExtensionCode other && prefixCode == other.prefixCode
                    && suffixCode == other.suffixCode;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(prefixCode, suffixCode);
        }

        @Override
        public int compareTo(ExtensionCode code)
        {
            int comparePrefixCodes = Integer.compare(prefixCode, code.prefixCode);

            return comparePrefixCodes != 0
                   ? comparePrefixCodes
                   : Integer.compare(suffixCode, code.suffixCode);
        }
    }
}
