// Structure of edge in graph
package algolib.graphs;

import java.util.Objects;

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

    public Vertex<V> getNeighbour(Vertex<V> vertex)
    {
        if(source.equals(vertex))
            return destination;

        if(destination.equals(vertex))
            return source;

        throw new IllegalArgumentException(
                String.format("Edge %s is not adjacent to given vertex %s", toString(),
                              vertex.toString()));
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

    @Override
    public String toString()
    {
        return String.format("Edge{%d -> %d (%s)}", source.index, destination.index,
                             Objects.toString(property));
    }
}
