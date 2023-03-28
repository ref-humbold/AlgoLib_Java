// Structure of disjoint sets (union-find)
package algolib.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DisjointSets<E>
{
    private final Map<E, E> represents = new HashMap<>();
    private int size_ = 0;

    public DisjointSets()
    {
    }

    public DisjointSets(Iterable<? extends E> universe)
    {
        for(E e : universe)
        {
            represents.put(e, e);
            ++size_;
        }
    }

    /** @return {@code true} if structure is empty, otherwise {@code false} */
    public boolean isEmpty()
    {
        return size_ == 0;
    }

    /** @return the number of sets */
    public int size()
    {
        return size_;
    }

    /**
     * Checks if given element belongs to any set.
     * @param element the element
     * @return {@code true} if the element is included in one of sets, otherwise {@code false}
     */
    public boolean contains(E element)
    {
        return represents.containsKey(element);
    }

    /**
     * Adds given new value as singleton set.
     * @param element the value
     * @return {@code this} for method chaining
     * @throws IllegalArgumentException if the element is already in this structure
     */
    public DisjointSets<E> add(E element)
    {
        if(contains(element))
            throw new IllegalArgumentException("Value " + element.toString() + "already present.");

        represents.put(element, element);
        ++size_;

        return this;
    }

    /**
     * Adds given new values as singleton sets.
     * @param elements the values
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
            ++size_;
        }

        return this;
    }

    /**
     * Finds represent of given element.
     * @param element the element
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
     * Finds represent of given element, or returns given default value.
     * @param element the element
     * @param defaultValue the value to return if element not inside
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
     * @param element1 the element from the first set
     * @param element2 the element from the second set
     * @return {@code this} for method chaining
     * @throws NoSuchElementException if either element is not in this structure
     */
    public DisjointSets<E> unionSet(E element1, E element2)
    {
        if(!isSameSet(element1, element2))
        {
            represents.put(findSet(element1), findSet(element2));
            --size_;
        }

        return this;
    }

    /**
     * Checks whether given elements belong to the same set.
     * @param element1 the element from the first set
     * @param element2 the element from the second set
     * @return {@code true} if both elements are in the same set, otherwise {@code false}
     * @throws NoSuchElementException if either element is not in this structure
     */
    public boolean isSameSet(E element1, E element2)
    {
        return Objects.equals(findSet(element1), findSet(element2));
    }
}
