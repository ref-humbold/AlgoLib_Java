package algolib.graphs;

/** Structure of undirected graph */
public interface UndirectedGraph<VertexId, VertexProperty, EdgeProperty>
        extends Graph<VertexId, VertexProperty, EdgeProperty>
{
    /**
     * Converts this graph to a directed graph with the same vertices.
     * @return directed graph
     */
    DirectedGraph<VertexId, VertexProperty, EdgeProperty> asDirected();
}
