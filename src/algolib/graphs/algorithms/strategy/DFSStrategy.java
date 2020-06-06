package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public interface DFSStrategy<V>
        extends BFSStrategy<V>
{
    void onEdgeToVisited(Vertex<V> vertex, Vertex<V> neighbour);
}
