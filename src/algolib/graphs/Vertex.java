// Structure of vertex in graph
package algolib.graphs;

import java.util.Objects;

public class Vertex<V>
        implements Comparable<Vertex<V>>
{
    public final int index;
    public V property;

    Vertex(int index, V property)
    {
        this.index = index;
        this.property = property;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Vertex<?> other = (Vertex<?>)obj;

        return index == other.index;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(index);
    }

    @Override
    public int compareTo(Vertex<V> vertex)
    {
        return Integer.compare(index, vertex.index);
    }
}
