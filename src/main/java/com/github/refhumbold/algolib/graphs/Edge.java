package com.github.refhumbold.algolib.graphs;

/** Structure of graph edge. */
public record Edge<VertexId>(Vertex<VertexId> source, Vertex<VertexId> destination)
{
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
    public String toString()
    {
        return "Edge{%s -- %s}".formatted(source.toString(), destination.toString());
    }
}
