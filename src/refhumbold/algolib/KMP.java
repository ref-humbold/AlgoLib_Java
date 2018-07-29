package refhumbold.algolib;

import java.util.ArrayList;
import java.util.List;

public class KMP
{
    public static List<Integer> kmp(String text, String pattern)
    {
        if(text == null)
            throw new IllegalArgumentException("Text is null.");

        if(pattern == null)
            throw new IllegalArgumentException("Pattern is null.");

        if(pattern == "")
            return new ArrayList<>();

        List<Integer> places = new ArrayList<>();
        List<Integer> pi = prefixes(pattern);
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
