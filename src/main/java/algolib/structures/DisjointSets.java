package algolib.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Structure of disjoint sets (union-find). */
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

    /**
     * Checks whether this structure is empty.
     * @return {@code true} if the structure is empty, otherwise {@code false}
     */
    public boolean isEmpty()
    {
        return size_ == 0;
    }

    /**
     * Gets the number of sets in this structure.
     * @return the number of sets
     */
    public int size()
    {
        return size_;
    }

    /**
     * Removes all sets from this structure.
     */
    public void clear()
    {
        represents.clear();
        size_ = 0;
    }

    /**
     * Checks whether given element belongs to any set.
     * @param element the element
     * @return {@code true} if the element belongs to the structure, otherwise {@code false}
     */
    public boolean contains(E element)
    {
        return represents.containsKey(element);
    }

    /**
     * Adds new element as singleton set.
     * @param element the new element
     * @return {@code this} for method chaining
     * @throws IllegalArgumentException if the element is already present
     */
    public DisjointSets<E> add(E element)
    {
        if(contains(element))
            throw new IllegalArgumentException(
                    "Value %s already present.".formatted(element.toString()));

        represents.put(element, element);
        ++size_;

        return this;
    }

    /**
     * Adds new elements as singleton sets.
     * @param elements the new elements
     * @return {@code this} for method chaining
     * @throws IllegalArgumentException if any of the elements is already present
     */
    public DisjointSets<E> addAll(Iterable<E> elements)
    {
        for(E elem : elements)
            if(contains(elem))
                throw new IllegalArgumentException(
                        "Value %s already present.".formatted(elem.toString()));

        for(E elem : elements)
        {
            represents.put(elem, elem);
            ++size_;
        }

        return this;
    }

    /**
     * Searches for represent of given element.
     * @param element the element
     * @return the represent of the element
     * @throws NoSuchElementException if the element is not present
     */
    public E findSet(E element)
    {
        if(represents.get(element) == null)
            throw new NoSuchElementException("Element does not belong to the structure");

        if(!Objects.equals(represents.get(element), element))
            represents.put(element, findSet(represents.get(element)));

        return represents.get(element);
    }

    /**
     * Searches for represent of given element, or returns given default value.
     * @param element the element
     * @param defaultValue the value to return if element not present
     * @return the represent of the element, if present, otherwise the default value
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
     * @throws NoSuchElementException if either element is not present
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
     * @return {@code true} if the elements are in the same set, otherwise {@code false}
     * @throws NoSuchElementException if either element is not present
     */
    public boolean isSameSet(E element1, E element2)
    {
        return Objects.equals(findSet(element1), findSet(element2));
    }
}
