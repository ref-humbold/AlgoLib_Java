package algolib.structures.heaps;

import java.util.Comparator;
import java.util.Queue;

public interface Heap<E>
        extends Queue<E>
{
    /**
     * Gets the comparator of this heap.
     * @return the comparator
     */
    Comparator<? super E> comparator();
}
