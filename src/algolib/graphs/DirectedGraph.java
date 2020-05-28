package algolib.graphs;

public interface DirectedGraph<V, E>
        extends Graph<V, E>
{
    /** Reverses directions of edges in this graph. */
    void reverse();
}
