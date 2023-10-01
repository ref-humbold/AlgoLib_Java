package algolib.graphs;

import java.util.Collection;

/** Structure of graph. */
public interface Graph<VertexId, VertexProperty, EdgeProperty>
{
    GraphProperties<VertexId, VertexProperty, EdgeProperty> getProperties();

    /**
     * Gets the number of vertices in this graph.
     * @return the number of vertices
     */
    int getVerticesCount();

    /**
     * Gets the number of edges in this graph.
     * @return the number of edges
     */
    int getEdgesCount();

    /**
     * Gets all vertices in this graph.
     * @return all vertices
     */
    Collection<Vertex<VertexId>> getVertices();

    /**
     * Gets all edges in this graph.
     * @return all edges
     */
    Collection<Edge<VertexId>> getEdges();

    /**
     * Gets the vertex from this graph with given identifier.
     * @param vertexId the vertex identifier
     * @return the vertex with the identifier, or {@code null} if no such vertex
     */
    Vertex<VertexId> getVertex(VertexId vertexId);

    /**
     * Gets the edge between the vertices with given identifiers.
     * @param sourceId the source vertex identifier
     * @param destinationId the destination vertex identifier
     * @return the edge between the vertices, or {@code null} if no such edge
     */
    Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId);

    /**
     * Gets the edge between given vertices.
     * @param source the source vertex
     * @param destination the destination vertex
     * @return the edge between the vertices, or {@code null} if no such edge
     */
    default Edge<VertexId> getEdge(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return getEdge(source.id, destination.id);
    }

    /**
     * Gets the neighbours of given vertex.
     * @param vertex the vertex from this graph
     * @return the neighbouring vertices
     */
    Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex);

    /**
     * Gets the adjacent edges of given vertex.
     * @param vertex the vertex from this graph
     * @return the edges adjacent to the vertex
     */
    Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex);

    /**
     * Gets the output degree of given vertex.
     * @param vertex the vertex from this graph
     * @return the output degree of the vertex
     */
    int getOutputDegree(Vertex<VertexId> vertex);

    /**
     * Gets the input degree of given vertex.
     * @param vertex the vertex from this graph
     * @return the input degree of the vertex
     */
    int getInputDegree(Vertex<VertexId> vertex);

    interface GraphProperties<VertexId, VertexProperty, EdgeProperty>
    {
        /**
         * Gets property of given vertex.
         * @param vertex the vertex from this graph
         * @return the property of the vertex, or {@code null} if no property
         */
        VertexProperty get(Vertex<VertexId> vertex);

        /**
         * Sets new property for given vertex.
         * @param vertex the vertex from this graph
         * @param property the new property of given vertex
         */
        void set(Vertex<VertexId> vertex, VertexProperty property);

        /**
         * Gets property of given edge.
         * @param edge the edge from this graph
         * @return the property of the vertex, or {@code null} if no property
         */
        EdgeProperty get(Edge<VertexId> edge);

        /**
         * Sets new property for given edge.
         * @param edge the edge from this graph
         * @param property the new property of given edge
         */
        void set(Edge<VertexId> edge, EdgeProperty property);
    }
}
