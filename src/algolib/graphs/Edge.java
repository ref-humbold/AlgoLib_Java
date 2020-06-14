// Structure of edge in graph
package algolib.graphs;

import java.util.Objects;

public class Edge<V>
{
    public final V source;
    public final V destination;

    public Edge(V source, V destination)
    {
        this.source = source;
        this.destination = destination;
    }

    public V getNeighbour(V vertex)
    {
        if(source.equals(vertex))
            return destination;

        if(destination.equals(vertex))
            return source;

        throw new IllegalArgumentException(
                String.format("Edge %s is not adjacent to given vertex %s", toString(),
                              vertex.toString()));
    }

    public Edge<V> reversed()
    {
        return new Edge<>(destination, source);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Edge<?> other = (Edge<?>)obj;

        return source.equals(other.source) && destination.equals(other.destination);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(source, destination);
    }

    @Override
    public String toString()
    {
        return String.format("Edge{%s -- %s}", source.toString(), destination.toString());
    }
}
