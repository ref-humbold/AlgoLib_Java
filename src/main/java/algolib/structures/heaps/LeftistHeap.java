package algolib.structures.heaps;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Structure of leftist heap. */
public class LeftistHeap<E extends Comparable<E>>
        extends AbstractHeap<E>
{
    private HeapNode heap = null;
    private int size_ = 0;

    private final Comparator<E> comparator_ = Comparator.naturalOrder();

    public LeftistHeap()
    {
        super();
    }

    public LeftistHeap(Collection<? extends E> collection)
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
        HeapNode newNode = new HeapNode(element);

        heap = heap == null ? newNode : heap.merge(newNode);
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
    public void merge(LeftistHeap<E> other)
    {
        heap = heap == null ? other.heap : heap.merge(other.heap);
        size_ += other.size_;
    }

    private final class HeapNode
    {
        private final int rank;
        private final E element;
        private final HeapNode left;
        private final HeapNode right;

        private HeapNode(E element, HeapNode node1, HeapNode node2)
        {
            int rank1 = node1 == null ? 0 : node1.rank;
            int rank2 = node2 == null ? 0 : node2.rank;

            this.element = element;

            if(rank1 < rank2)
            {
                rank = rank1 + 1;
                left = node2;
                right = node1;
            }
            else
            {
                rank = rank2 + 1;
                left = node1;
                right = node2;
            }
        }

        private HeapNode(E element)
        {
            this(element, null, null);
        }

        HeapNode pop()
        {
            return left == null ? right : left.merge(right);
        }

        HeapNode merge(HeapNode node)
        {
            return node == null
                   ? this
                   : comparator_.compare(element, node.element) < 0
                     ? new HeapNode(element, left, right == null
                                                   ? node
                                                   : right.merge(node))
                     : new HeapNode(node.element, node.left,
                                    node.right == null ? this : node.right.merge(this));
        }
    }

    private class HeapIterator
            implements Iterator<E>
    {
        private final ArrayDeque<HeapNode> nodes = new ArrayDeque<>();

        HeapIterator()
        {
            if(heap != null)
                nodes.addFirst(heap);
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

            HeapNode node = nodes.removeFirst();
            E returnValue = node.element;

            if(node.left != null)
                nodes.addFirst(node.left);

            if(node.right != null)
                nodes.addFirst(node.right);

            return returnValue;
        }
    }
}
