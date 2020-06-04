package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface BFSStrategy<V>
{
    void forRoot(Vertex<V> root);

    void preProcess(Vertex<V> vertex);

    void forNext(Vertex<V> vertex, Vertex<V> neighbour);

    void postProcess(Vertex<V> vertex);
}
