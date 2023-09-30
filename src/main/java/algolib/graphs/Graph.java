package algolib.graphs;

import java.util.Collection;

/** Structure of graph */
public interface Graph<VertexId, VertexProperty, EdgeProperty>
{
    GraphProperties<VertexId, VertexProperty, EdgeProperty> getProperties();

    /** @return the number of vertices */
    int getVerticesCount();

    /** @return the number of edges */
    int getEdgesCount();

    /** @return all vertices */
    Collection<Vertex<VertexId>> getVertices();

    /** @return all edges */
    Collection<Edge<VertexId>> getEdges();

    /**
     * @param vertexId the vertex identifier
     * @return the vertex with the identifier, or {@code null} if no such vertex
     */
    Vertex<VertexId> getVertex(VertexId vertexId);

    /**
     * @param sourceId the source vertex identifier
     * @param destinationId the destination vertex identifier
     * @return the edge between the vertices, or {@code null} if no such edge
     */
    Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId);

    /**
     * @param source the source vertex
     * @param destination the destination vertex
     * @return the edge between the vertices, or {@code null} if no such edge
     */
    default Edge<VertexId> getEdge(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return getEdge(source.id, destination.id);
    }

    /**
     * @param vertex the vertex from this graph
     * @return the edges adjacent to the vertex
     */
    Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex);

    /**
     * @param vertex the vertex from this graph
     * @return the neighbouring vertices
     */
    Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex);

    /**
     * @param vertex the vertex from this graph
     * @return the output degree of the vertex
     */
    int getOutputDegree(Vertex<VertexId> vertex);

    /**
     * @param vertex the vertex from this graph
     * @return the input degree of the vertex
     */
    int getInputDegree(Vertex<VertexId> vertex);

    interface GraphProperties<VertexId, VertexProperty, EdgeProperty>
    {
        /**
         * Extracts property of given vertex.
         * @param vertex the vertex from this graph
         * @return the property of the vertex, or {@code null} if no property
         */
        VertexProperty get(Vertex<VertexId> vertex);

        /**
         * Assigns new property for given vertex.
         * @param vertex the vertex from this graph
         * @param property the new property of given vertex
         */
        void set(Vertex<VertexId> vertex, VertexProperty property);

        /**
         * Extracts property of given edge.
         * @param edge the edge from this graph
         * @return the property of the vertex, or {@code null} if no property
         */
        EdgeProperty get(Edge<VertexId> edge);

        /**
         * Assigns new property for given edge.
         * @param edge the edge from this graph
         * @param property the new property of given edge
         */
        void set(Edge<VertexId> edge, EdgeProperty property);
    }
}
