package algolib.structures.heaps;

import java.util.*;
import java.util.stream.Stream;

/** Structure of pairing heap. */
public class PairingHeap<E extends Comparable<E>>
        extends AbstractHeap<E>
{
    private HeapNode heap = null;
    private int size_ = 0;
    private final Comparator<E> comparator_ = Comparator.naturalOrder();

    public PairingHeap()
    {
        super();
    }

    public PairingHeap(Collection<? extends E> collection)
    {
        super();
        addAll(collection);
    }

    @Override
    public boolean isEmpty()
    {
        return heap == null;
    }

    @Override
    public Comparator<? super E> comparator()
    {
        return comparator_;
    }

    @Override
    public int size()
    {
        return size_;
    }

    @Override
    public void clear()
    {
        heap = null;
        size_ = 0;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new HeapIterator();
    }

    @Override
    public E peek()
    {
        return heap == null ? null : heap.element;
    }

    @Override
    public boolean offer(E element)
    {
        heap = heap == null ? new HeapNode(element) : heap.append(element);
        ++size_;
        return true;
    }

    @Override
    public E poll()
    {
        if(heap == null)
            return null;

        E value = heap.element;

        heap = heap.pop();
        --size_;
        return value;
    }

    /**
     * Merges given heap to this heap.
     * @param other the heap
     */
    public void merge(PairingHeap<E> other)
    {
        heap = heap == null ? other.heap : heap.merge(other.heap);
        size_ += other.size_;
    }

    private final class HeapNode
    {
        private final E element;
        private final List<HeapNode> children;

        private HeapNode(E element, List<HeapNode> children)
        {
            this.element = element;
            this.children = children;
        }

        private HeapNode(E element)
        {
            this(element, List.of());
        }

        HeapNode append(E element)
        {
            return comparator_.compare(this.element, element) <= 0
                   ? new HeapNode(this.element, Stream.concat(Stream.of(new HeapNode(element)),
                                                              children.stream())
                                                      .toList())
                   : new HeapNode(element, List.of(this));
        }

        HeapNode pop()
        {
            return mergePairs(0);
        }

        HeapNode merge(HeapNode node)
        {
            return node == null
                   ? this
                   : comparator_.compare(element, node.element) <= 0
                     ? new HeapNode(element, Stream.concat(Stream.of(node), children.stream())
                                                   .toList())
                     : new HeapNode(node.element,
                                    Stream.concat(Stream.of(this), node.children.stream())
                                          .toList());
        }

        private HeapNode mergePairs(int index)
        {
            if(index >= children.size())
                return null;

            if(index == children.size() - 1)
                return children.get(index);

            return children.get(index).merge(children.get(index + 1)).merge(mergePairs(index + 2));
        }
    }

    private class HeapIterator
            implements Iterator<E>
    {
        private final Queue<HeapNode> nodes = new ArrayDeque<>();

        HeapIterator()
        {
            if(heap != null)
                nodes.add(heap);
        }

        @Override
        public boolean hasNext()
        {
            return !nodes.isEmpty();
        }

        @Override
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator");

            HeapNode node = nodes.remove();
            E returnValue = node.element;

            nodes.addAll(node.children);
            return returnValue;
        }
    }
}
