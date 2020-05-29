package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface SearchingStrategy<V>
{
    void preProcess(Vertex<V> vertex);

    void forNeighbour(Vertex<V> vertex, Vertex<V> neighbour);

    void postProcess(Vertex<V> vertex);

    void onCycle(Vertex<V> vertex, Vertex<V> neighbour);
}
