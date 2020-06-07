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
    public void onEnter(Vertex<V> vertex)
    {
    }

    @Override
    public void onNextVertex(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }

    @Override
    public void onExit(Vertex<V> vertex)
    {
    }

    @Override
    public void onEdgeToVisited(Vertex<V> vertex, Vertex<V> neighbour)
    {
    }
}
