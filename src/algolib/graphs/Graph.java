// Structure of graph
package algolib.graphs;

import java.util.Collection;

public interface Graph<V, VP, EP>
{
    /** @return number of vertices */
    int getVerticesCount();

    /** @return number of edges */
    int getEdgesCount();

    /** @return collection of graph vertices */
    Collection<V> getVertices();

    /** @return collection of graph edges */
    Collection<Edge<V>> getEdges();

    /**
     * @param vertex a vertex from this graph
     * @return the property of the vertex
     */
    VP getProperty(V vertex);

    /**
     * @param vertex a vertex from this graph
     * @param property new property of given vertex
     */
    void setProperty(V vertex, VP property);

    /**
     * @param edge an edge from this graph
     * @return the property of the vertex
     */
    EP getProperty(Edge<V> edge);

    /**
     * @param edge an edge from this graph
     * @param property new property of given edge
     */
    void setProperty(Edge<V> edge, EP property);

    /**
     * @param source source vertex
     * @param destination destination vertex
     * @return the edge between the vertices, or {@code null} if no edge
     */
    Edge<V> getEdge(V source, V destination);

    /**
     * @param vertex a vertex from this graph
     * @return collection of edges adjacent to the vertex
     */
    Collection<Edge<V>> getAdjacentEdges(V vertex);

    /**
     * @param vertex a vertex from this graph
     * @return collection of neighbouring vertices
     */
    Collection<V> getNeighbours(V vertex);

    /**
     * @param vertex a vertex from this graph
     * @return the output degree of the vertex
     */
    int getOutputDegree(V vertex);

    /**
     * @param vertex a vertex from this graph
     * @return the input degree of the vertex
     */
    int getInputDegree(V vertex);
}
