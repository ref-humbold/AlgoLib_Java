// STRUKTURA ZBIORÓW ROZŁĄCZNYCH UNION-FIND
package ref_humbold.algolib.structures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
     * Liczenie zbiorów.
     * @return liczba zbiorów
     */
    public int size()
    {
        Set<E> reprs = new HashSet<>();

        for(E e : represents.keySet())
            reprs.add(findSet(e));

        return reprs.size();
    }

    /**
     * Należenie do dowolnego zbioru.
     * @param element element
     * @return czy element w jednym ze zbiorów
     */
    public boolean contains(E element)
    {
        return represents.containsKey(element);
    }

    /**
     * Tworzenie nowego zbioru jednoelementowego.
     * @param element nowy element
     */
    public void make_set(E element)
    {
        if(!contains(element))
            represents.put(element, element);
    }

    /**
     * Ustalanie reprezentanta zbioru.
     * @param element element ze zbioru
     * @return reprezentant elementu
     */
    public E findSet(E element)
    {
        if(!represents.get(element).equals(element))
            represents.put(element, findSet(represents.get(element)));

        return represents.get(element);
    }

    /**
     * Scalanie dwóch zbiorów.
     * @param element1 element pierwszego zbioru
     * @param element2 element drugiego zbioru
     */
    public void unionSet(E element1, E element2)
    {
        if(!isSameSet(element1, element2))
            represents.put(findSet(element1), findSet(element2));
    }

    /**
     * Sprawdzanie, czy elementy należą do tego samego zbioru.
     * @param element1 element pierwszego zbioru
     * @param element2 element drugiego zbioru
     * @return czy elementy znajdują się w jednym zbiorze
     */
    public boolean isSameSet(E element1, E element2)
    {
        return findSet(element1).equals(findSet(element2));
    }
}
