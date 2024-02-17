package algolib.structures.heaps;

import java.util.*;

/** Structure of pairing heap. */
public class PairingHeap<E extends Comparable<E>>
        extends AbstractHeap<E>
{
    private HeapNode heap = null;
    private int size_ = 0;

    public PairingHeap()
    {
        super(Comparator.naturalOrder());
    }

    public PairingHeap(Collection<? extends E> collection)
    {
        super(Comparator.naturalOrder());
        addAll(collection);
    }

    @Override
    public boolean isEmpty()
    {
        return heap == null;
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

    private class HeapNodeList
            implements Iterable<HeapNode>
    {
        final HeapNode node;
        final HeapNodeList next;

        HeapNodeList(HeapNode node, HeapNodeList next)
        {
            this.node = node;
            this.next = next;
        }

        HeapNodeList(HeapNode node)
        {
            this(node, null);
        }

        @Override
        public Iterator<HeapNode> iterator()
        {
            HeapNodeList starting = this;

            return new Iterator<>()
            {
                private HeapNodeList current = starting;

                @Override
                public boolean hasNext()
                {
                    return current != null;
                }

                @Override
                public HeapNode next()
                {
                    if(!hasNext())
                        throw new NoSuchElementException();

                    HeapNode node = current.node;

                    current = current.next;
                    return node;
                }
            };
        }
    }

    private class HeapNode
    {
        private final E element;
        private final HeapNodeList children;

        HeapNode(E element, HeapNodeList children)
        {
            this.element = element;
            this.children = children;
        }

        HeapNode(E element)
        {
            this(element, null);
        }

        HeapNode append(E element)
        {
            return comparator().compare(this.element, element) <= 0
                   ? new HeapNode(this.element, new HeapNodeList(new HeapNode(element), children))
                   : new HeapNode(element, new HeapNodeList(this));
        }

        HeapNode pop()
        {
            return mergePairs(children);
        }

        HeapNode merge(HeapNode node)
        {
            return node == null
                   ? this
                   : comparator().compare(element, node.element) <= 0
                     ? new HeapNode(element, new HeapNodeList(node, children))
                     : new HeapNode(node.element, new HeapNodeList(this, node.children));
        }

        private HeapNode mergePairs(HeapNodeList list)
        {
            if(list == null)
                return null;

            if(list.next == null)
                return list.node;

            return list.node.merge(list.next.node).merge(mergePairs(list.next.next));
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

            if(node.children != null)
                for(HeapNode child : node.children)
                    nodes.add(child);

            return returnValue;
        }
    }
}
