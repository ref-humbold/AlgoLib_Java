package algolib.structures;

import java.util.*;

/** Structure of double heap. */
public class DoubleHeap<E>
        extends AbstractQueue<E>
{
    private static final int INDEX_MIN = 0;
    private static final int INDEX_MAX = 1;
    private final Comparator<? super E> comparator_;
    private final List<E> heap = new ArrayList<>();

    public DoubleHeap()
    {
        this((Comparator<? super E>)null);
    }

    public DoubleHeap(Comparator<? super E> comparator)
    {
        super();
        comparator_ = comparator;
    }

    @SuppressWarnings("unchecked")
    public DoubleHeap(DoubleHeap<? extends E> doubleHeap)
    {
        super();
        comparator_ = (Comparator<? super E>)doubleHeap.comparator();
        addAll(doubleHeap);
    }

    @SuppressWarnings("unchecked")
    public DoubleHeap(PriorityQueue<? extends E> queue)
    {
        super();
        comparator_ = (Comparator<? super E>)queue.comparator();
        addAll(queue);
    }

    @SuppressWarnings("unchecked")
    public DoubleHeap(Collection<? extends E> collection)
    {
        super();
        comparator_ = (collection instanceof DoubleHeap<? extends E> doubleHeap)
                      ? (Comparator<? super E>)doubleHeap.comparator()
                      : (collection instanceof PriorityQueue<? extends E> priorityQueue)
                        ? (Comparator<? super E>)priorityQueue.comparator()
                        : null;
        addAll(collection);
    }

    @Override
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    /**
     * Gets the comparator of this double heap.
     * @return the comparator
     */
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
    public void clear()
    {
        heap.clear();
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

    @Override
    public E peek()
    {
        return peekMin();
    }

    /**
     * Retrieves minimal element from this double heap.
     * @return the minimal element
     */
    public E peekMin()
    {
        return isEmpty() ? null : heap.get(INDEX_MIN);
    }

    /**
     * Retrieves maximal element from this double heap.
     * @return the maximal element
     */
    public E peekMax()
    {
        return switch(size())
        {
            case 0 -> null;
            case 1 -> heap.get(INDEX_MIN);
            default -> heap.get(INDEX_MAX);
        };
    }

    /**
     * Retrieves minimal element from this double heap.
     * @return the minimal element
     * @throws NoSuchElementException if the double heap is empty
     */
    public E elementMin()
    {
        return Optional.ofNullable(peekMin()).orElseThrow(NoSuchElementException::new);
    }

    /**
     * Retrieves maximal element from this double heap.
     * @return the maximal element
     * @throws NoSuchElementException if the double heap is empty
     */
    public E elementMax()
    {
        return Optional.ofNullable(peekMax()).orElseThrow(NoSuchElementException::new);
    }

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

    /**
     * Retrieves and removes minimal element from this double heap.
     * @return the removed minimal element
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
     * Retrieves and removes maximal element from this double heap.
     * @return the maximal element
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
     * Retrieves and removes minimal element from this double heap.
     * @return the removed minimal element
     * @throws NoSuchElementException if the double heap is empty
     */
    public E removeMin()
    {
        return Optional.ofNullable(pollMin()).orElseThrow(NoSuchElementException::new);
    }

    /**
     * Retrieves and removes maximal element from this double heap.
     * @return the maximal element
     * @throws NoSuchElementException if the double heap is empty
     */
    public E removeMax()
    {
        return Optional.ofNullable(pollMax()).orElseThrow(NoSuchElementException::new);
    }

    // Compares two elements using comparator or natural order.
    @SuppressWarnings("unchecked")
    private int compare(int index1, int index2)
    {
        if(comparator_ == null)
            return ((Comparable<E>)heap.get(index1)).compareTo(heap.get(index2));

        return comparator_.compare(heap.get(index1), heap.get(index2));
    }

    // Moves element from given index towards minimum.
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

    // Performs a single step of movement towards minimum.
    private void stepToMin(int index, int nextIndex)
    {
        if(compare(index, nextIndex) < 0)
        {
            swap(index, nextIndex);
            moveToMin(nextIndex);
        }
    }

    // Moves element from given index towards maximum.
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

    // Performs a single step of movement towards maximum.
    private void stepToMax(int index, int nextIndex)
    {
        if(compare(index, nextIndex) > 0)
        {
            swap(index, nextIndex);
            moveToMax(nextIndex);
        }
    }

    // Swaps two elements in the double heap.
    private void swap(int index1, int index2)
    {
        E temp = heap.get(index1);

        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private abstract static class AbstractHeapIterator<E>
            implements Iterator<E>
    {
        final Queue<E> orderQueue = new ArrayDeque<>();

        @Override
        public boolean hasNext()
        {
            return !orderQueue.isEmpty();
        }

        @Override
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator.");

            return orderQueue.remove();
        }

        List<E> createOrderedMinimalList(List<E> heap)
        {
            Queue<Integer> indices = new ArrayDeque<>();
            List<E> minimalList = new ArrayList<>();

            if(!heap.isEmpty())
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

            if(heap.size() > DoubleHeap.INDEX_MAX)
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
            orderQueue.addAll(minimalList);
            orderQueue.addAll(maximalList);
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
            orderQueue.addAll(maximalList);
            orderQueue.addAll(minimalList);
        }
    }
}
