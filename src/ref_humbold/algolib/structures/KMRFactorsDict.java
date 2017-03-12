// SŁOWNIK PODSŁÓW BAZOWYCH Z ALGORYTMEM KARPA-MILLERA-ROSENBERGA
package ref_humbold.algolib.structures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import ref_humbold.algolib.structures.Pair;

public class KMRFactorsDict
{
    /** Słowo. */
    private String text;

    /** Słownik podsłów bazowych. */
    private Map<String, Integer> factors;

    public KMRFactorsDict(String text)
    {
        this.text = text;
        this.factors = new HashMap<String, Integer>();
    }

    /**
    Getter dla słownika.
    @return słownik podsłów bazowych
    */
    public Map<String, Integer> getFactors()
    {
        return factors;
    }

    /**
    Getter dla słowa.
    @return słowo
    */
    public String getText()
    {
        return text;
    }

    /** Budowa słownika podsłów bazowych. */
    public void kmr()
    {
        signLetters();

        for(int ln = 2; ln <= text.length(); ln <<= 1)
            doubleLength(ln);
    }

    /** Budowa podsłów złożonych z pojedynczych znaków. */
    private void signLetters()
    {
        Integer codeValue = 0;
        List<String> letters = Arrays.asList( text.split("") );

        Collections.sort(letters);
        factors.put(letters.get(0), codeValue);

        for(int i = 1; i < letters.size(); ++i)
            if( letters.get(i).equals( letters.get(i-1) ) )
            {
                ++codeValue;
                factors.put(letters.get(i), codeValue);
            }
    }

    /**
    Budowa nowych podsłów o podwojonej długości.
    @param newLength nowa długość podsłów
    */
    private void doubleLength(int newLength)
    {
        int codeValue = 0;
        List< Pair< Pair<String, String>, Integer> > codes = new ArrayList<>();

        for(int i = 0; i <= text.length()-newLength; ++i)
        {
            String s1 = text.substring(i, i+newLength/2);
            String s2 = text.substring(i+newLength/2, i+newLength);

            codes.add( new Pair(new Pair( factors.get(s1), factors.get(s2) ), i) );
        }

        Collections.sort(codes);
        factors.put( text.substring(codes.get(0).second, newLength), codeValue);

        for(int i = 1; i < codes.size(); ++i)
            if( codes.get(i).first.equals(codes.get(i-1).first) )
            {
                int index = codes.get(i).second;

                ++codeValue;
                factors.put(text.substring(index, index+newLength), codeValue);
            }
    }
}

