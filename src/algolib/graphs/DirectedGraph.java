package algolib.graphs;

/** Structure of directed graph */
public interface DirectedGraph<VertexId, VertexProperty, EdgeProperty>
        extends Graph<VertexId, VertexProperty, EdgeProperty>
{
    /** Reverses directions of edges in this graph. */
    void reverse();

    /** @return copy of this graph with reversed directions of edges */
    DirectedGraph<VertexId, VertexProperty, EdgeProperty> reversedCopy();
}
