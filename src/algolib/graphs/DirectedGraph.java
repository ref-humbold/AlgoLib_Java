package algolib.graphs;

public interface DirectedGraph<V, VP, EP>
        extends Graph<V, VP, EP>
{
    /** Reverses directions of edges in this graph. */
    void reverse();

    /** @return the copy of this graph with reversed directions of edges */
    DirectedGraph<V, VP, EP> reversedCopy();
}
