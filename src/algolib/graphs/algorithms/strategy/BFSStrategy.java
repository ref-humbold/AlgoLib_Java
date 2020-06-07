package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface BFSStrategy<V>
{
    void onEnter(Vertex<V> vertex);

    void onNextVertex(Vertex<V> vertex, Vertex<V> neighbour);

    void onExit(Vertex<V> vertex);
}
