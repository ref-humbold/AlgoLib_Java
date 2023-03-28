package algolib.graphs;

import java.util.Objects;

/** Structure of edge in graph */
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
     * @param vertex vertex adjacent to this edge
     * @return neighbour of the vertex along this edge
     * @throws IllegalArgumentException if the vertex is not adjacent to this edge
     */
    public Vertex<VertexId> getNeighbour(Vertex<VertexId> vertex)
    {
        if(source.equals(vertex))
            return destination;

        if(destination.equals(vertex))
            return source;

        throw new IllegalArgumentException(
                String.format("Edge %s is not adjacent to given vertex %s", this, vertex));
    }

    /** @return edge with reversed direction */
    public Edge<VertexId> reversed()
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
