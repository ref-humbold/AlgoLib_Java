// Structure of double heap
package algolib.structures;

import java.util.*;

public class DoubleHeap<E>
        extends AbstractQueue<E>
{
    private static final int INDEX_FIRST = 0;
    private static final int INDEX_LAST = 1;
    private final Comparator<? super E> theComparator;
    private final List<E> heap = new ArrayList<>();

    public DoubleHeap()
    {
        super();
        theComparator = null;
    }

    public DoubleHeap(Collection<E> collection)
    {
        this();
        addAll(collection);
    }

    public DoubleHeap(Comparator<? super E> comparator)
    {
        super();
        theComparator = comparator;
    }

    @Override
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    public Comparator<? super E> comparator()
    {
        return theComparator;
    }

    @Override
    public int size()
    {
        return heap.size();
    }

    @Override
    public Iterator<E> iterator()
    {
        return new HeapIterator<>(heap);
    }

    /**
     * Adds a new value to this double heap.
     * @param element value to be added
     * @return {@code true} if the value was added successfully, otherwise {@code false}
     */
    @Override
    public boolean offer(E element)
    {
        heap.add(element);

        if(heap.size() > 1)
        {
            int index = heap.size() - 1;

            if(index % 2 == 1)
            {
                if(compare(index, index - 1) < 0)
                {
                    swap(index, index - 1);
                    moveToFirst(index - 1);
                }
                else
                    moveToLast(index);
            }
            else
            {
                int newIndex = ((index + 1) / 2 - 1) / 2 * 2 + 1;

                if(compare(index, newIndex) > 0)
                {
                    swap(index, newIndex);
                    moveToLast(newIndex);
                }
                else
                    moveToFirst(index);
            }
        }

        return true;
    }

    @Override
    public E poll()
    {
        return pollFirst();
    }

    @Override
    public E peek()
    {
        return peekFirst();
    }

    /**
     * Retrieves and removes minimal element from this double heap.
     * @return removed minimal element
     */
    public E pollFirst()
    {
        E minimal = peekFirst();

        if(minimal != null)
        {
            heap.set(INDEX_FIRST, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            moveToLast(INDEX_FIRST);
        }

        return minimal;
    }

    /**
     * Retrieves minimal element from this double heap.
     * @return minimal element
     */
    public E peekFirst()
    {
        return isEmpty() ? null : heap.get(INDEX_FIRST);
    }

    /**
     * Retrieves and removes minimal element from this double heap.
     * @return removed minimal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E removeFirst()
    {
        E element = pollFirst();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    /**
     * Retrieves minimal element from this double heap.
     * @return minimal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E elementFirst()
    {
        E element = peekFirst();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    /**
     * Retrieves and removes maximal element from this double heap.
     * @return maximal element
     */
    public E pollLast()
    {
        if(heap.size() == 1)
            return pollFirst();

        E maximal = peekLast();

        if(maximal != null)
        {
            heap.set(INDEX_LAST, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            moveToFirst(INDEX_LAST);
        }

        return maximal;
    }

    /**
     * Retrieves maximal element from this double heap.
     * @return maximal element
     */
    public E peekLast()
    {
        switch(size())
        {
            case 0:
                return null;

            case 1:
                return heap.get(INDEX_FIRST);

            default:
                return heap.get(INDEX_LAST);
        }
    }

    /**
     * Retrieves and removes maximal element from this double heap.
     * @return maximal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E removeLast()
    {
        E element = pollLast();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    /**
     * Retrieves maximal element from this double heap.
     * @return maximal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E elementLast()
    {
        E element = peekLast();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    @Override
    public void clear()
    {
        heap.clear();
    }

    // Compares two elements using a comparator or a natural order.
    @SuppressWarnings("unchecked")
    private int compare(int index1, int index2)
    {
        if(theComparator == null)
            return ((Comparable<E>)heap.get(index1)).compareTo(heap.get(index2));

        return theComparator.compare(heap.get(index1), heap.get(index2));
    }

    private void moveToFirst(int index)
    {
        if(index == INDEX_FIRST)
            return;

        if(index % 2 == 0)
            stepToFirst(index, (index / 2 - 1) / 2 * 2);
        else
        {
            int leftIndex = index + index + 1;
            int rightIndex = index + index + 3;

            if(rightIndex < heap.size())
            {
                int childIndex = compare(leftIndex, rightIndex) > 0 ? leftIndex : rightIndex;

                stepToFirst(index, childIndex);
            }
            else if(leftIndex < heap.size())
                stepToFirst(index, leftIndex);
            else if(index < heap.size())
                stepToFirst(index, index - 1);
        }
    }

    private void stepToFirst(int index, int nextIndex)
    {
        if(compare(index, nextIndex) < 0)
        {
            swap(index, nextIndex);
            moveToFirst(nextIndex);
        }
    }

    private void moveToLast(int index)
    {
        if(index == INDEX_LAST)
            return;

        if(index % 2 == 1)
            stepToLast(index, (index / 2 - 1) / 2 * 2 + 1);
        else
        {
            int leftIndex = index + index + 2;
            int rightIndex = index + index + 4;

            if(rightIndex < heap.size())
            {
                int childIndex = compare(leftIndex, rightIndex) < 0 ? leftIndex : rightIndex;

                stepToLast(index, childIndex);
            }
            else if(leftIndex < heap.size())
                stepToLast(index, leftIndex);
            else if(index + 1 < heap.size())
                stepToLast(index, index + 1);
        }
    }

    private void stepToLast(int index, int nextIndex)
    {
        if(compare(index, nextIndex) > 0)
        {
            swap(index, nextIndex);
            moveToLast(nextIndex);
        }
    }

    private void swap(int index1, int index2)
    {
        E temp = heap.get(index1);

        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private static final class HeapIterator<E>
            implements Iterator<E>
    {
        private final List<E> orderList = new ArrayList<>();
        private int currentIndex;

        private HeapIterator(List<E> heap)
        {
            Queue<Integer> indices = new ArrayDeque<>();
            List<E> minimalHeap = new ArrayList<>();

            indices.add(DoubleHeap.INDEX_FIRST);

            while(!indices.isEmpty())
            {
                int index = indices.remove();

                minimalHeap.add(heap.get(index));

                if(index + index + 2 < heap.size())
                    indices.add(index + index + 2);

                if(index + index + 4 < heap.size())
                    indices.add(index + index + 4);
            }

            List<E> maximalHeap = new ArrayList<>();

            indices.add(DoubleHeap.INDEX_LAST);

            while(!indices.isEmpty())
            {
                int index = indices.remove();

                maximalHeap.add(heap.get(index));

                if(index + index + 1 < heap.size())
                    indices.add(index + index + 1);

                if(index + index + 3 < heap.size())
                    indices.add(index + index + 3);
            }

            Collections.reverse(maximalHeap);
            orderList.addAll(minimalHeap);
            orderList.addAll(maximalHeap);
        }

        @Override
        public boolean hasNext()
        {
            return currentIndex < orderList.size();
        }

        @Override
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator.");

            E returnValue = orderList.get(currentIndex);

            ++currentIndex;
            return returnValue;
        }
    }
}
