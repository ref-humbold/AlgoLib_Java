// SŁOWNIK PODSŁÓW BAZOWYCH Z ALGORYTMEM KARPA-MILLERA-ROSENBERGA
package ref_humbold.algolib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ref_humbold.algolib.structures.Pair;

public class KMR
{
    /**
     * Budowa słownika podsłów bazowych.
     * @param text słowo
     * @return słownik podsłów bazowych
     */
    public static Map<String, Integer> kmr(String text)
    {
        Map<String, Integer> factors = signLetters(text);

        for(int length = 2; length <= text.length(); length <<= 1)
            doubleLength(length, text, factors);

        return factors;
    }

    /**
     * Budowa podsłów złożonych z pojedynczych znaków.
     * @param text słowo
     * @return słownik dla pojedynczych znaków
     */
    private static Map<String, Integer> signLetters(String text)
    {
        Map<String, Integer> factors = new HashMap<String, Integer>();
        List<String> letters = Arrays.asList(text.split(""));
        Integer codeValue = 0;

        Collections.sort(letters);
        factors.put(letters.get(0), codeValue);

        for(int i = 1; i < letters.size(); ++i)
            if(letters.get(i).equals(letters.get(i - 1)))
            {
                ++codeValue;
                factors.put(letters.get(i), codeValue);
            }

        return factors;
    }

    /**
     * Budowa nowych podsłów o podwojonej długości.
     * @param newLength nowa długość podsłów
     * @param text słowo
     * @param factors słownik podsłów bazowych
     */
    private static void doubleLength(int newLength, String text, Map<String, Integer> factors)
    {
        List<Pair<Pair<Integer, Integer>, Integer>> codes = new ArrayList<>();
        int codeValue = 0;

        for(int i = 0; i <= text.length() - newLength; ++i)
        {
            String s1 = text.substring(i, i + newLength / 2);
            String s2 = text.substring(i + newLength / 2, i + newLength);

            codes.add(new Pair<>(new Pair<>(factors.get(s1), factors.get(s2)), i));
        }

        Collections.sort(codes);
        factors.put(text.substring(codes.get(0).getSecond(), newLength), codeValue);

        for(int i = 1; i < codes.size(); ++i)
            if(codes.get(i).getFirst().equals(codes.get(i - 1).getFirst()))
            {
                int index = codes.get(i).getSecond();

                ++codeValue;
                factors.put(text.substring(index, index + newLength), codeValue);
            }
    }
}
