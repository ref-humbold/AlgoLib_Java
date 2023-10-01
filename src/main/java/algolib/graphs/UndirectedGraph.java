package algolib.graphs;

/** Structure of undirected graph. */
public interface UndirectedGraph<VertexId, VertexProperty, EdgeProperty>
        extends Graph<VertexId, VertexProperty, EdgeProperty>
{
    /**
     * Converts this graph to the directed graph with same vertices.
     * @return the directed copy of this graph
     */
    DirectedGraph<VertexId, VertexProperty, EdgeProperty> asDirected();
}
