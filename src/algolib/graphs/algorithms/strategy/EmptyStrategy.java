package algolib.graphs.algorithms.strategy;

import algolib.graphs.Vertex;

public class EmptyStrategy<V>
        implements DFSStrategy<V>
{
    @Override
    public void forRoot(Vertex<V> root)
    {
    }

    @Override
    public void preProcess(Vertex<V> vertex)
    {
    }

    @Override
    public void forNext(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }

    @Override
    public void postProcess(Vertex<V> vertex)
    {
    }

    @Override
    public void forVisited(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }
}
