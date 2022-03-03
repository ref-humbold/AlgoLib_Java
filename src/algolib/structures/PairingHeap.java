package algolib.structures;

import java.util.*;

/** Structure of pairing heap */
public class PairingHeap<E extends Comparable<E>>
        extends AbstractQueue<E>
{
    private HeapNode<E> heap = null;
    private int size_ = 0;

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
        heap = heap == null ? new HeapNode<>(element, null) : heap.append(element);
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

    private static class HeapNodeList<E extends Comparable<E>>
    {
        final HeapNode<E> node;
        final HeapNodeList<E> next;

        HeapNodeList(HeapNode<E> node, HeapNodeList<E> next)
        {
            this.node = node;
            this.next = next;
        }
    }

    private static class HeapNode<E extends Comparable<E>>
    {
        private final E element;
        private final HeapNodeList<E> children;

        HeapNode(E element, HeapNodeList<E> children)
        {
            this.element = element;
            this.children = children;
        }

        HeapNode<E> append(E element)
        {
            return this.element.compareTo(element) <= 0
                    ? new HeapNode<>(this.element, new HeapNodeList<>(new HeapNode<>(element, null),
                                                                      children))
                    : new HeapNode<>(element, new HeapNodeList<>(this, null));
        }

        HeapNode<E> pop()
        {
            return mergePairs(children);
        }

        HeapNode<E> merge(HeapNode<E> node)
        {
            return node == null
                    ? this
                    : element.compareTo(node.element) <= 0
                            ? new HeapNode<>(element, new HeapNodeList<>(node, children))
                            : new HeapNode<>(node.element, new HeapNodeList<>(this, node.children));
        }

        private HeapNode<E> mergePairs(HeapNodeList<E> list)
        {
            if(list == null)
                return null;

            if(list.next == null)
                return list.node;

            return list.node.merge(list.next.node).merge(mergePairs(list.next.next));
        }
    }

    private static class HeapIterator<E extends Comparable<E>>
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
                throw new NoSuchElementException("No more elements in iterator.");

            HeapNode<E> node = nodes.remove();
            E returnValue = node.element;
            HeapNodeList<E> list = node.children;

            while(list != null)
            {
                nodes.add(list.node);
                list = list.next;
            }

            return returnValue;
        }
    }
}
