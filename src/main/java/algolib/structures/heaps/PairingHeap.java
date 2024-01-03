package algolib.structures.heaps;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Structure of pairing heap. */
public class PairingHeap<E extends Comparable<E>>
        extends AbstractQueue<E>
        implements Heap<E>
{
    private HeapNode<E> heap = null;
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
        return new HeapIterator<>(heap);
    }

    @Override
    public E peek()
    {
        return heap == null ? null : heap.element;
    }

    @Override
    public boolean offer(E element)
    {
        heap = heap == null ? new HeapNode<>(element, comparator_) : heap.append(element);
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
     * Merges given pairing heap to this heap.
     * @param other the pairing heap
     */
    public void merge(PairingHeap<E> other)
    {
        heap = heap == null ? other.heap : heap.merge(other.heap);
        size_ += other.size_;
    }

    private record HeapNode<E>(E element, List<HeapNode<E>> children, Comparator<E> comparator)
    {
        HeapNode(E element, Comparator<E> comparator)
        {
            this(element, new ArrayList<>(), comparator);
        }

        HeapNode<E> append(E element)
        {
            return comparator.compare(this.element, element) <= 0
                   ? new HeapNode<>(this.element, Stream.concat(Stream.of(new HeapNode<>(element,
                                                                                         comparator)),
                                                                children.stream())
                                                        .collect(Collectors.toList()), comparator)
                   : new HeapNode<>(element, Stream.of(this).collect(Collectors.toList()),
                                    comparator);
        }

        HeapNode<E> pop()
        {
            return mergePairs(0);
        }

        HeapNode<E> merge(HeapNode<E> node)
        {
            return node == null
                   ? this
                   : comparator.compare(element, node.element) <= 0
                     ? new HeapNode<>(element, Stream.concat(Stream.of(node), children.stream())
                                                     .collect(Collectors.toList()), comparator)
                     : new HeapNode<>(node.element,
                                      Stream.concat(Stream.of(this), node.children.stream())
                                            .collect(Collectors.toList()), comparator);
        }

        private HeapNode<E> mergePairs(int index)
        {
            if(index >= children.size())
                return null;

            if(index == children.size() - 1)
                return children.get(index);

            return children.get(index).merge(children.get(index + 1)).merge(mergePairs(index + 2));
        }
    }

    private static final class HeapIterator<E extends Comparable<E>>
            implements Iterator<E>
    {
        private final Queue<HeapNode<E>> nodes = new ArrayDeque<>();

        HeapIterator(HeapNode<E> node)
        {
            if(node != null)
                nodes.add(node);
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

            HeapNode<E> node = nodes.remove();
            E returnValue = node.element;
            nodes.addAll(node.children);

            return returnValue;
        }
    }
}
