package algolib.graphs;

/** Structure of directed graph */
public interface DirectedGraph<VertexId, VertexProperty, EdgeProperty>
        extends Graph<VertexId, VertexProperty, EdgeProperty>
{
    /** Reverses directions of all edges in this graph. */
    void reverse();

    /** @return the copy of the graph with reversed directions of all edges */
    DirectedGraph<VertexId, VertexProperty, EdgeProperty> reversedCopy();
}
