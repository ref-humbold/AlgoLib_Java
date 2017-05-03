// STRUKTURA ZBIORÓW ROZŁĄCZNYCH UNION-FIND
package ref_humbold.algolib.structures;

import java.lang.Iterable;
import java.util.Map;
import java.util.HashMap;

public class DisjointSets<E>
{
    /** Mapa reprezentantów elementów. */
    private Map<E, E> represents;

    public DisjointSets(Iterable<E> universe)
    {
        represents = new HashMap<E, E>();

        for(E e : universe)
            represents.put(e, e);
    }

    /**
     * Ustala reprezentanta składowej.
     * @param element element ze składowej
     * @return reprezentant elementu
     */
    public E findSet(E element)
    {
        if(!represents.get(element).equals(element))
            represents.put(element, findSet(represents.get(element)));

        return represents.get(element);
    }

    /**
     * Scala dwie składowe.
     * @param element1 element pierwszej składowej
     * @param element2 element drugiej składowej
     */
    public void unionSet(E element1, E element2)
    {
        if(isSetDifferent(element1, element2))
            represents.put(findSet(element1), findSet(element2));
    }

    /**
     * Sprawdza, czy dwa elementy są w róznych składowych.
     * @param element1 element pierwszej składowej
     * @param element2 element drugiej składowej
     * @return czy elementy znajdują się w różnych składowych
     */
    public boolean isSetDifferent(E element1, E element2)
    {
        return !findSet(element1).equals(findSet(element2));
    }
}
