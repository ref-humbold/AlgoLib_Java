package algolib.structures.heaps;

import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.NoSuchElementException;

public abstract class AbstractHeap<E>
        extends AbstractQueue<E>
{
    /**
     * Gets the comparator of this heap.
     * @return the comparator
     */
    public abstract Comparator<? super E> comparator();

    /**
     * Retrieves minimal element from this heap.
     * @return the minimal element, or {@code null} if the heap is empty
     */
    @Override
    public abstract E peek();

    /**
     * Retrieves minimal element from this heap.
     * @return the minimal element
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public E element()
    {
        return super.element();
    }

    /**
     * Adds new element to this heap.
     * @param element the new element
     * @return {@code true}, if the element was added, otherwise {@code false}
     */
    @Override
    public abstract boolean offer(E element);

    /**
     * Adds new element to this heap.
     * @param element the new element
     * @return {@code true}
     * @throws IllegalStateException if the element cannot be added
     */
    @Override
    public boolean add(E element)
    {
        if(offer(element))
            return true;
        else
            throw new IllegalStateException("Heap full");
    }

    /**
     * Retrieves and removes minimal element from this heap.
     * @return the removed minimal element, or {@code null} if the heap is empty
     */
    @Override
    public abstract E poll();

    /**
     * Retrieves and removes minimal element from this heap.
     * @return the removed minimal element
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public E remove()
    {
        return super.remove();
    }
}
