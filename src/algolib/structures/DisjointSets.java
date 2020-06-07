// Structure of disjoint sets (union-find)
package algolib.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DisjointSets<E>
{
    private final Map<E, E> represents = new HashMap<>();
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

    /** @return {@code true} if structure is empty, otherwise {@code false} */
    public boolean isEmpty()
    {
        return count == 0;
    }

    /** @return number of sets */
    public int size()
    {
        return count;
    }

    /**
     * Checks if given element belongs to any set.
     * @param element element to be found
     * @return {@code true} if the element is included in one of sets, otherwise {@code false}
     */
    public boolean contains(E element)
    {
        return represents.containsKey(element);
    }

    /**
     * Adds a new value as singleton set.
     * @param element value to be added
     * @return {@code this} for method chaining
     * @throws IllegalArgumentException if the element is already in this structure
     */
    public DisjointSets<E> add(E element)
    {
        if(contains(element))
            throw new IllegalArgumentException("Value " + element.toString() + "already present.");

        represents.put(element, element);
        ++count;

        return this;
    }

    /**
     * Adds new values as singleton sets.
     * @param elements values to be added
     * @return {@code this} for method chaining
     * @throws IllegalArgumentException if any of the elements is already in this structure
     */
    public DisjointSets<E> addAll(Iterable<E> elements)
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
     * Finds a represent of an element.
     * @param element an element
     * @return the represent of the element
     * @throws NoSuchElementException if element is not in this structure
     */
    public E findSet(E element)
    {
        if(represents.get(element) == null)
            throw new NoSuchElementException();

        if(!Objects.equals(represents.get(element), element))
            represents.put(element, findSet(represents.get(element)));

        return represents.get(element);
    }

    /**
     * Finds a represent of an element.
     * @param element an element
     * @param defaultValue a value to return if the element not inside
     * @return the represent of the element
     */
    public E findSetOrDefault(E element, E defaultValue)
    {
        try
        {
            return findSet(element);
        }
        catch(NoSuchElementException e)
        {
            return defaultValue;
        }
    }

    /**
     * Joins two sets together.
     * @param element1 element from the first set
     * @param element2 element from the second set
     * @return {@code this} for method chaining
     * @throws NoSuchElementException if either element is not in this structure
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
     * Checks whether two elements belong to the same set.
     * @param element1 element from the first set
     * @param element2 element from the second set
     * @return {@code true} if both elements are in the same set, otherwise {@code false}
     * @throws NoSuchElementException if either element is not in this structure
     */
    public boolean isSameSet(E element1, E element2)
    {
        return Objects.equals(findSet(element1), findSet(element2));
    }
}
