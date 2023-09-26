package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface DfsStrategy<VertexId>
        extends BfsStrategy<VertexId>
{
    void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour);
}
