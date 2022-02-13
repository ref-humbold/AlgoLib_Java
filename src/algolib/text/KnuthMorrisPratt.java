package algolib.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Knuth-Morris-Pratt algorithm for pattern searching */
public final class KnuthMorrisPratt
{
    /**
     * Searches for pattern occurrences in given text using Knuth-Morris-Pratt algorithm.
     * @param text the text
     * @param pattern the pattern
     * @return list of indices with pattern occurrences
     */
    public static List<Integer> kmpSearch(String text, String pattern)
    {
        Objects.requireNonNull(text, "Text is null.");
        Objects.requireNonNull(pattern, "Pattern is null.");

        if("".equals(pattern))
            return new ArrayList<>();

        List<Integer> places = new ArrayList<>();
        List<Integer> pi = KnuthMorrisPratt.prefixes(pattern);
        int pos = 0;

        for(int i = 0; i < text.length(); ++i)
        {
            while(pos > 0 && pattern.charAt(pos) != text.charAt(i))
                pos = pi.get(pos - 1);

            if(pattern.charAt(pos) == text.charAt(i))
                ++pos;

            if(pos == pattern.length())
            {
                places.add(i - pattern.length() + 1);
                pos = pi.get(pos - 1);
            }
        }

        return places;
    }

    // Computes Knuth's PI prefix function values for specified pattern.
    private static List<Integer> prefixes(String pattern)
    {
        List<Integer> pi = new ArrayList<>();
        int pos = 0;

        pi.add(0);

        for(char ltr : pattern.toCharArray())
        {
            while(pos > 0 && pattern.charAt(pos) != ltr)
                pos = pi.get(pos - 1);

            if(pattern.charAt(pos) == ltr)
                ++pos;

            pi.add(pos);
        }

        return pi;
    }
}
