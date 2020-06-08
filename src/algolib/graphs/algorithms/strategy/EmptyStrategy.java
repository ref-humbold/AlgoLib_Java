package algolib.graphs.algorithms.strategy;

public class EmptyStrategy<V>
        implements DFSStrategy<V>
{
    @Override
    public void forRoot(V root)
    {
    }

    @Override
    public void onEnter(V vertex)
    {
    }

    @Override
    public void onNextVertex(V vertex, V neighbour)
    {
    }

    @Override
    public void onExit(V vertex)
    {
    }

    @Override
    public void onEdgeToVisited(V vertex, V neighbour)
    {
    }
}
