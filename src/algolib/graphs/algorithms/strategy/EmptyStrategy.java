package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public class EmptyStrategy<V>
        implements SearchingStrategy<V>
{
    @Override
    public void preProcess(Vertex<V> vertex)
    {
    }

    @Override
    public void forNeighbour(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }

    @Override
    public void postProcess(Vertex<V> vertex)
    {
    }

    @Override
    public void onCycle(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }
}
