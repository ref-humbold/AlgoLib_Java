package com.github.refhumbold.algolib.graphs.algorithms.strategy;

import com.github.refhumbold.algolib.graphs.Vertex;

public interface DfsStrategy<VertexId>
        extends BfsStrategy<VertexId>
{
    void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour);
}
