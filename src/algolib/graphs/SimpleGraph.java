// Structure of simple graph
package algolib.graphs;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SimpleGraph<V, E>
        implements Graph<V, E>
{
    // Adjacency list
    protected Map<Vertex<V>, Set<Edge<E, V>>> graphMap = new HashMap<>();

    @Override
    public long getVerticesCount()
    {
        return graphMap.size();
    }

    @Override
    public List<Vertex<V>> getVertices()
    {
        return graphMap.keySet().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public Collection<Edge<E, V>> getAdjacentEdges(Vertex<V> vertex)
    {
        return graphMap.get(vertex);
    }

    @Override
    public Collection<Vertex<V>> getNeighbours(Vertex<V> vertex)
    {
        return graphMap.get(vertex)
                       .stream()
                       .map(edge -> vertex.equals(edge.source) ? edge.destination : edge.source)
                       .collect(Collectors.toSet());
    }

    /**
     * Adds a new vertex with specified properties to this graph.
     * @param property a vertex property
     * @return the new vertex
     */
    public Vertex<V> addVertex(V property)
    {
        Vertex<V> vertex = new Vertex<>(graphMap.size(), property);

        graphMap.put(vertex, new HashSet<>());
        return vertex;
    }

    /**
     * Adds a new edge with specified properties to this graph.
     * @param source a source vertex
     * @param destination a destination vertex
     * @param property an edge property
     * @return the new edge
     */
    public abstract Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property);
}
