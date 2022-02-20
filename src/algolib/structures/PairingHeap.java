package algolib.structures;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Structure of pairing heap */
public class PairingHeap<E extends Comparable<E>>
        extends AbstractQueue<E>
{
    private HeapNode<E> heap = null;

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
        return heap == null ? 0 : heap.size;
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
        heap = heap == null ? new HeapNode<>(element, new ArrayList<>()) : heap.add(element);
        return true;
    }

    @Override
    public E poll()
    {
        if(heap == null)
            return null;

        E value = heap.element;

        heap = heap.pop();
        return value;
    }

    /**
     * Merges given pairing heap to this heap.
     * @param other the pairing heap
     */
    public void merge(PairingHeap<E> other)
    {
        heap = heap == null ? other.heap : heap.merge(other.heap);
    }

    @Override
    public void clear()
    {
        heap = null;
    }

    private static class HeapNode<E extends Comparable<E>>
    {
        private final E element;
        private final List<HeapNode<E>> children;
        private int size;

        HeapNode(E element, List<HeapNode<E>> children)
        {
            this.children = children;
            this.element = element;
            size = 1 + children.stream().mapToInt(c -> c.size).sum();
        }

        private HeapNode(HeapNode<E> node)
        {
            element = node.element;
            children = new ArrayList<>(node.children);
            size = node.size;
        }

        HeapNode<E> add(E element)
        {
            if(element.compareTo(this.element) <= 0)
                return new HeapNode<>(element, Stream.of(this)
                                                     .collect(Collectors.toCollection(
                                                             ArrayList::new)));

            append(new HeapNode<>(element, new ArrayList<>()));
            return this;
        }

        HeapNode<E> pop()
        {
            return mergePairs(0);
        }

        HeapNode<E> merge(HeapNode<E> node)
        {
            if(node == null)
                return this;

            HeapNode<E> mergedNode;

            if(element.compareTo(node.element) <= 0)
            {
                mergedNode = new HeapNode<>(this);
                mergedNode.append(node);
            }
            else
            {
                mergedNode = new HeapNode<>(node);
                mergedNode.append(this);
            }

            return mergedNode;
        }

        private void append(HeapNode<E> node)
        {
            children.add(node);
            size += node.size;
        }

        private HeapNode<E> mergePairs(int index)
        {
            if(index >= children.size())
                return null;

            HeapNode<E> mergedNode = index >= children.size() - 1
                                     ? children.get(index)
                                     : children.get(index).merge(children.get(index + 1));

            return mergedNode.merge(mergePairs(index + 2));
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

            nodes.addAll(node.children);
            return returnValue;
        }
    }
}
