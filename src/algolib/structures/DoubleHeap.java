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
    public boolean offer(E e)
    {
        heap.add(e);

        // TODO

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

    public E pollFirst()
    {
        E minimal = peekFirst();

        if(minimal != null)
        {
            heap.set(INDEX_FIRST, heap.get(heap.size() - 1));

            // TODO
        }

        return minimal;
    }

    public E peekFirst()
    {
        return isEmpty() ? null : heap.get(INDEX_FIRST);
    }

    public E removeFirst()
    {
        E element = pollFirst();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    public E elementFirst()
    {
        E element = peekFirst();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

    public E pollLast()
    {
        E maximal = peekLast();

        if(maximal != null)
        {
            heap.set(INDEX_LAST, heap.get(heap.size() - 1));

            // TODO
        }

        return maximal;
    }

    public E peekLast()
    {
        return isEmpty() ? null : heap.get(INDEX_LAST);
    }

    public E removeLast()
    {
        E element = pollLast();

        if(element == null)
            throw new NoSuchElementException();

        return element;
    }

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

    @Override
    public Iterator<E> iterator()
    {
        return new HeapIterator<>(heap);
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
            return currentIndex >= orderList.size();
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
