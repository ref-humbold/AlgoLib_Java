package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface DFSStrategy<VertexId>
        extends BFSStrategy<VertexId>
{
    void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour);
}
