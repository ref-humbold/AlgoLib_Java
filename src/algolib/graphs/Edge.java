// Structure of edge in graph
package algolib.graphs;

import java.util.Objects;
import java.util.function.Function;

public class Edge<E, V>
        implements Comparable<Edge<E, V>>
{
    public final Vertex<V> source;
    public final Vertex<V> destination;
    public E property;

    Edge(Vertex<V> source, Vertex<V> destination, E property)
    {
        this.source = source;
        this.destination = destination;
        this.property = property;
    }

    /**
     * Reverses direction of this edge.
     * @param propertyMapper function that creates property of reversed edge from current edge property
     * @return reversed edge
     */
    public Edge<E, V> reverse(Function<E, E> propertyMapper)
    {
        return new Edge<>(destination, source, propertyMapper.apply(property));
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Edge<?, ?> other = (Edge<?, ?>)obj;

        return source.equals(other.source) && destination.equals(other.destination);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(source, destination);
    }

    @Override
    public int compareTo(Edge<E, V> edge)
    {
        int compareFrom = source.compareTo(edge.source);

        return compareFrom != 0 ? compareFrom : destination.compareTo(edge.destination);
    }
}
