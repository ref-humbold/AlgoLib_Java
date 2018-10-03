// SŁOWNIK PODSŁÓW BAZOWYCH Z ALGORYTMEM KARPA-MILLERA-ROSENBERGA
package refhumbold.algolib.text;

import java.util.*;

import refhumbold.algolib.tuples.ComparableTriple;
import refhumbold.algolib.tuples.Triple;

public class KMR
{
    /**
     * Budowa słownika podsłów bazowych.
     * @param text słowo
     * @return słownik podsłów bazowych
     */
    public static Map<String, Integer> kmr(String text)
    {
        Map<String, Integer> factors = KMR.signLetters(text);

        for(int length = 2; length <= text.length(); length <<= 1)
            KMR.doubleLength(length, text, factors);

        return factors;
    }

    /**
     * Budowa podsłów złożonych z pojedynczych znaków.
     * @param text słowo
     * @return słownik dla pojedynczych znaków
     */
    private static Map<String, Integer> signLetters(String text)
    {
        Map<String, Integer> factors = new HashMap<>();
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
        List<ComparableTriple<Integer, Integer, Integer>> codes = new ArrayList<>();
        int codeValue = 0;

        for(int i = 0; i <= text.length() - newLength; ++i)
        {
            String s1 = text.substring(i, i + newLength / 2);
            String s2 = text.substring(i + newLength / 2, i + newLength);

            codes.add(ComparableTriple.make(factors.get(s1), factors.get(s2), i));
        }

        Collections.sort(codes);
        factors.put(text.substring(codes.get(0).getSecond(), newLength), codeValue);

        for(int i = 1; i < codes.size(); ++i)
        {
            Triple<Integer, Integer, Integer> current = codes.get(i);
            Triple<Integer, Integer, Integer> last = codes.get(i - 1);

            if(current.getFirst().equals(last.getFirst()) && current.getSecond()
                                                                    .equals(last.getSecond()))
            {
                int index = codes.get(i).getThird();

                ++codeValue;
                factors.put(text.substring(index, index + newLength), codeValue);
            }
        }
    }
}
