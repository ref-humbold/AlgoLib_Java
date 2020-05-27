// Structure of graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;

public interface Graph<V, E>
{
    /** @return number of vertices */
    long getVerticesCount();

    /** @return sorted list of vertices */
    List<Vertex<V>> getVertices();

    /** @return number of edges */
    long getEdgesCount();

    /** @return sorted list of edges */
    List<Edge<E, V>> getEdges();

    /**
     * @param vertex a vertex from this graph
     * @return list of neighbouring vertices
     */
    Collection<Vertex<V>> getNeighbours(Vertex<V> vertex);

    /**
     * @param vertex a vertex from this graph
     * @return list of edges adjacent to this vertex
     */
    Collection<Edge<E, V>> getAdjacentEdges(Vertex<V> vertex);

    /**
     * @param vertex a vertex from this graph
     * @return the output degree of this vertex
     */
    long getOutputDegree(Vertex<V> vertex);

    /**
     * @param vertex a vertex from this graph
     * @return the input degree of this vertex
     */
    long getInputDegree(Vertex<V> vertex);
}
