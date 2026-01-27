package com.github.refhumbold.algolib.graphs;

/** Structure of graph vertex. */
public record Vertex<VertexId>(VertexId id)
{
    @Override
    public String toString()
    {
        return "Vertex(%s)".formatted(id);
    }
}
