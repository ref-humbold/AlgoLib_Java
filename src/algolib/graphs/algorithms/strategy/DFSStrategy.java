package algolib.graphs.algorithms.strategy;

public interface DFSStrategy<V>
        extends BFSStrategy<V>
{
    void onEdgeToVisited(V vertex, V neighbour);
}
