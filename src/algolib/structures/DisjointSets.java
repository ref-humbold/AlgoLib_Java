// STRUKTURA ZBIORÓW ROZŁĄCZNYCH UNION-FIND
package algolib.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DisjointSets<E>
{
    private Map<E, E> represents = new HashMap<>();
    private int count = 0;

    public DisjointSets()
    {
    }

    public DisjointSets(Iterable<E> universe)
    {
        for(E e : universe)
        {
            represents.put(e, e);
            ++count;
        }
    }

    public int size()
    {
        return count;
    }

    /**
     * Należenie do dowolnego zbioru
     * @param element element
     * @return czy element w jednym ze zbiorów
     */
    public boolean contains(E element)
    {
        return represents.containsKey(element);
    }

    /**
     * Tworzenie nowych zbiorów jednoelementowych
     * @param elements nowe elementy
     * @return ``this``
     */
    public DisjointSets<E> add(Iterable<E> elements)
    {
        for(E elem : elements)
            if(contains(elem))
                throw new IllegalArgumentException("Value " + elem.toString() + "already present.");

        for(E elem : elements)
        {
            represents.put(elem, elem);
            ++count;
        }

        return this;
    }

    /**
     * Ustalanie reprezentanta zbioru
     * @param element element ze zbioru
     * @return reprezentant elementu
     */
    public E findSet(E element)
    {
        if(!Objects.equals(represents.get(element), element))
            represents.put(element, findSet(represents.get(element)));

        return represents.get(element);
    }

    /**
     * Scalanie dwóch zbiorów
     * @param element1 element pierwszego zbioru
     * @param element2 element drugiego zbioru
     * @return ``this``
     */
    public DisjointSets<E> unionSet(E element1, E element2)
    {
        if(!isSameSet(element1, element2))
        {
            represents.put(findSet(element1), findSet(element2));
            --count;
        }

        return this;
    }

    /**
     * Sprawdzanie, czy elementy należą do tego samego zbioru
     * @param element1 element pierwszego zbioru
     * @param element2 element drugiego zbioru
     * @return czy elementy znajdują się w jednym zbiorze
     */

    public boolean isSameSet(E element1, E element2)
    {
        return Objects.equals(findSet(element1), findSet(element2));
    }
}
