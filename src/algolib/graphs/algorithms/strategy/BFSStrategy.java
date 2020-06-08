package algolib.graphs.algorithms.strategy;

public interface BFSStrategy<V>
{
    void forRoot(V root);

    void onEnter(V vertex);

    void onNextVertex(V vertex, V neighbour);

    void onExit(V vertex);
}
