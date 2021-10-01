// Structure of graph
package algolib.graphs;

import java.util.Collection;

public interface Graph<VertexId, VertexProperty, EdgeProperty>
{
    Properties<VertexId, VertexProperty, EdgeProperty> getProperties();

    /** @return number of vertices */
    int getVerticesCount();

    /** @return number of edges */
    int getEdgesCount();

    /** @return collection of graph vertices */
    Collection<Vertex<VertexId>> getVertices();

    /** @return collection of graph edges */
    Collection<Edge<VertexId>> getEdges();

    /**
     * @param vertexId vertex indentifier
     * @return vertex with the identifier, or {@code null} if no vertex
     */
    Vertex<VertexId> getVertex(VertexId vertexId);

    /**
     * @param sourceId source vertex identifier
     * @param destinationId destination vertex identifier
     * @return edge between the vertices, or {@code null} if no edge
     */
    Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId);

    /**
     * @param source source vertex
     * @param destination destination vertex
     * @return edge between the vertices, or {@code null} if no edge
     */
    default Edge<VertexId> getEdge(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return getEdge(source.id, destination.id);
    }

    /**
     * @param vertex vertex from this graph
     * @return collection of edges adjacent to the vertex
     */
    Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex);

    /**
     * @param vertex a vertex from this graph
     * @return collection of neighbouring vertices
     */
    Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex);

    /**
     * @param vertex vertex from this graph
     * @return output degree of the vertex
     */
    int getOutputDegree(Vertex<VertexId> vertex);

    /**
     * @param vertex vertex from this graph
     * @return input degree of the vertex
     */
    int getInputDegree(Vertex<VertexId> vertex);

    interface Properties<VertexId, VertexProperty, EdgeProperty>
    {
        /**
         * @param vertex vertex from this graph
         * @return property of the vertex
         */
        VertexProperty get(Vertex<VertexId> vertex);

        /**
         * @param vertex vertex from this graph
         * @param property new property of given vertex
         */
        void set(Vertex<VertexId> vertex, VertexProperty property);

        /**
         * @param edge edge from this graph
         * @return property of the vertex
         */
        EdgeProperty get(Edge<VertexId> edge);

        /**
         * @param edge edge from this graph
         * @param property new property of given edge
         */
        void set(Edge<VertexId> edge, EdgeProperty property);
    }
}
