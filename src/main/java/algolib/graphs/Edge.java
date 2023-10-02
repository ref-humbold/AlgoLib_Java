package algolib.graphs;

import java.util.Objects;

/** Structure of graph edge. */
public class Edge<VertexId>
{
    public final Vertex<VertexId> source;
    public final Vertex<VertexId> destination;

    public Edge(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Gets the neighbour of given adjacent vertex.
     * @param vertex the vertex adjacent to this edge
     * @return the neighbour of the vertex along this edge
     * @throws IllegalArgumentException if the vertex is not adjacent to this edge
     */
    public Vertex<VertexId> getNeighbour(Vertex<VertexId> vertex)
    {
        if(source.equals(vertex))
            return destination;

        if(destination.equals(vertex))
            return source;

        throw new IllegalArgumentException(
                "Edge %s is not adjacent to given vertex %s".formatted(this, vertex));
    }

    /**
     * Gets the reversed copy of this edge.
     * @return the edge with reversed direction
     */
    public Edge<VertexId> reversed()
    {
        return new Edge<>(destination, source);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof Edge<?> other))
            return false;

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
        return "Edge{%s -- %s}".formatted(source.toString(), destination.toString());
    }
}
