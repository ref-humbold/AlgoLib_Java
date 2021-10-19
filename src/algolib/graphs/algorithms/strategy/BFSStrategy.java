package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface BFSStrategy<VertexId>
{
    void forRoot(Vertex<VertexId> root);

    void onEntry(Vertex<VertexId> vertex);

    void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour);

    void onExit(Vertex<VertexId> vertex);
}
