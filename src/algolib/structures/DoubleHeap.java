// Structure of double heap
package algolib.structures;

import java.util.*;

public class DoubleHeap<E>
        extends AbstractQueue<E>
{
    private static final int INDEX_MIN = 0;
    private static final int INDEX_MAX = 1;
    private final Comparator<? super E> comparator_;
    private final List<E> heap = new ArrayList<>();

    public DoubleHeap()
    {
        super();
        comparator_ = null;
    }

    public DoubleHeap(Collection<E> collection)
    {
        this();
        addAll(collection);
    }

    public DoubleHeap(Comparator<? super E> comparator)
    {
        super();
        comparator_ = comparator;
    }

    @Override
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    public Comparator<? super E> comparator()
    {
        return comparator_;
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

    public Iterator<E> descendingIterator()
    {
        return new HeapDescendingIterator<>(heap);
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
                    moveToMin(index - 1);
                }
                else
                    moveToMax(index);
            }
            else
            {
                int newIndex = ((index + 1) / 2 - 1) / 2 * 2 + 1;

                if(compare(index, newIndex) > 0)
                {
                    swap(index, newIndex);
                    moveToMax(newIndex);
                }
                else
                    moveToMin(index);
            }
        }

        return true;
    }

    @Override
    public E poll()
    {
        return pollMin();
    }

    @Override
    public E peek()
    {
        return peekMin();
    }

    /**
     * Retrieves and removes minimal element from this double heap.
     * @return removed minimal element
     */
    public E pollMin()
    {
        E minimal = peekMin();

        if(minimal != null)
        {
            heap.set(INDEX_MIN, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            moveToMax(INDEX_MIN);
        }

        return minimal;
    }

    /**
     * Retrieves minimal element from this double heap.
     * @return minimal element
     */
    public E peekMin()
    {
        return isEmpty() ? null : heap.get(INDEX_MIN);
    }

    /**
     * Retrieves and removes minimal element from this double heap.
     * @return removed minimal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E removeMin()
    {
        E element = pollMin();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    /**
     * Retrieves minimal element from this double heap.
     * @return minimal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E elementMin()
    {
        E element = peekMin();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    /**
     * Retrieves and removes maximal element from this double heap.
     * @return maximal element
     */
    public E pollMax()
    {
        if(heap.size() == 1)
            return pollMin();

        E maximal = peekMax();

        if(maximal != null)
        {
            heap.set(INDEX_MAX, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            moveToMin(INDEX_MAX);
        }

        return maximal;
    }

    /**
     * Retrieves maximal element from this double heap.
     * @return maximal element
     */
    public E peekMax()
    {
        switch(size())
        {
            case 0:
                return null;

            case 1:
                return heap.get(INDEX_MIN);

            default:
                return heap.get(INDEX_MAX);
        }
    }

    /**
     * Retrieves and removes maximal element from this double heap.
     * @return maximal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E removeMax()
    {
        E element = pollMax();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    /**
     * Retrieves maximal element from this double heap.
     * @return maximal element
     * @throws NoSuchElementException if double heap is empty
     */
    public E elementMax()
    {
        E element = peekMax();

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
        if(comparator_ == null)
            return ((Comparable<E>)heap.get(index1)).compareTo(heap.get(index2));

        return comparator_.compare(heap.get(index1), heap.get(index2));
    }

    private void moveToMin(int index)
    {
        if(index == INDEX_MIN)
            return;

        if(index % 2 == 0)
            stepToMin(index, (index / 2 - 1) / 2 * 2);
        else
        {
            int leftIndex = index + index + 1;
            int rightIndex = index + index + 3;

            if(rightIndex < heap.size())
            {
                int childIndex = compare(leftIndex, rightIndex) > 0 ? leftIndex : rightIndex;

                stepToMin(index, childIndex);
            }
            else if(leftIndex < heap.size())
                stepToMin(index, leftIndex);
            else if(index < heap.size())
                stepToMin(index, index - 1);
        }
    }

    private void stepToMin(int index, int nextIndex)
    {
        if(compare(index, nextIndex) < 0)
        {
            swap(index, nextIndex);
            moveToMin(nextIndex);
        }
    }

    private void moveToMax(int index)
    {
        if(index == INDEX_MAX)
            return;

        if(index % 2 == 1)
            stepToMax(index, (index / 2 - 1) / 2 * 2 + 1);
        else
        {
            int leftIndex = index + index + 2;
            int rightIndex = index + index + 4;

            if(rightIndex < heap.size())
            {
                int childIndex = compare(leftIndex, rightIndex) < 0 ? leftIndex : rightIndex;

                stepToMax(index, childIndex);
            }
            else if(leftIndex < heap.size())
                stepToMax(index, leftIndex);
            else if(index + 1 < heap.size())
                stepToMax(index, index + 1);
        }
    }

    private void stepToMax(int index, int nextIndex)
    {
        if(compare(index, nextIndex) > 0)
        {
            swap(index, nextIndex);
            moveToMax(nextIndex);
        }
    }

    private void swap(int index1, int index2)
    {
        E temp = heap.get(index1);

        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private static abstract class AbstractHeapIterator<E>
            implements Iterator<E>
    {
        final List<E> orderList = new ArrayList<>();
        private int currentIndex = 0;

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

        List<E> createOrderedMinimalList(List<E> heap)
        {
            Queue<Integer> indices = new ArrayDeque<>();
            List<E> minimalList = new ArrayList<>();

            indices.add(DoubleHeap.INDEX_MIN);

            while(!indices.isEmpty())
            {
                int index = indices.remove();

                minimalList.add(heap.get(index));

                if(index + index + 2 < heap.size())
                    indices.add(index + index + 2);

                if(index + index + 4 < heap.size())
                    indices.add(index + index + 4);
            }

            return minimalList;
        }

        List<E> createOrderedMaximalList(List<E> heap)
        {
            Queue<Integer> indices = new ArrayDeque<>();
            List<E> maximalList = new ArrayList<>();

            indices.add(DoubleHeap.INDEX_MAX);

            while(!indices.isEmpty())
            {
                int index = indices.remove();

                maximalList.add(heap.get(index));

                if(index + index + 1 < heap.size())
                    indices.add(index + index + 1);

                if(index + index + 3 < heap.size())
                    indices.add(index + index + 3);
            }

            return maximalList;
        }
    }

    private static final class HeapIterator<E>
            extends AbstractHeapIterator<E>
    {
        private HeapIterator(List<E> heap)
        {
            super();

            List<E> minimalList = createOrderedMinimalList(heap);
            List<E> maximalList = createOrderedMaximalList(heap);

            Collections.reverse(maximalList);
            orderList.addAll(minimalList);
            orderList.addAll(maximalList);
        }
    }

    private static final class HeapDescendingIterator<E>
            extends AbstractHeapIterator<E>
    {
        private HeapDescendingIterator(List<E> heap)
        {
            super();

            List<E> minimalList = createOrderedMinimalList(heap);
            List<E> maximalList = createOrderedMaximalList(heap);

            Collections.reverse(minimalList);
            orderList.addAll(maximalList);
            orderList.addAll(minimalList);
        }
    }
}
