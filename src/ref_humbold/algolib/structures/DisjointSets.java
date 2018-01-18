// STRUKTURA ZBIORÓW ROZŁĄCZNYCH UNION-FIND
package ref_humbold.algolib.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DisjointSets<E>
{
    /**
     * Mapa reprezentantów elementów.
     */
    private Map<E, E> represents = new HashMap<>();

    /**
     * Liczba zbiorów.
     */
    private int elems = 0;

    public DisjointSets()
    {
    }

    public DisjointSets(Iterable<E> universe)
    {
        for(E e : universe)
        {
            this.represents.put(e, e);
            ++this.elems;
        }
    }

    /**
     * @return liczba zbiorów
     */
    public int size()
    {
        return elems;
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
    public void addElem(E element)
    {
        if(contains(element))
            throw new IllegalArgumentException("Value " + element.toString() + "already present.");

        represents.put(element, element);
        ++elems;
    }

    /**
     * Ustalanie reprezentanta zbioru.
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
     * Scalanie dwóch zbiorów.
     * @param element1 element pierwszego zbioru
     * @param element2 element drugiego zbioru
     */
    public void unionSet(E element1, E element2)
    {
        if(!isSameSet(element1, element2))
        {
            represents.put(findSet(element1), findSet(element2));
            --elems;
        }
    }

    /**
     * Sprawdzanie, czy elementy należą do tego samego zbioru.
     * @param element1 element pierwszego zbioru
     * @param element2 element drugiego zbioru
     * @return czy elementy znajdują się w jednym zbiorze
     */

    public boolean isSameSet(E element1, E element2)
    {
        return Objects.equals(findSet(element1), findSet(element2));
    }
}
