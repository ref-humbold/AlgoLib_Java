package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class GraphRepresentation<V, E>
{
    private final Map<Vertex<V>, Set<Edge<E, V>>> graphMap = new HashMap<>();

    GraphRepresentation()
    {
    }

    GraphRepresentation(Collection<Vertex<V>> vertices)
    {
        vertices.forEach(vertex -> graphMap.put(vertex, new HashSet<>()));
    }

    Stream<Vertex<V>> getVertices()
    {
        return graphMap.keySet().stream();
    }

    Stream<Edge<E, V>> getEdges()
    {
        return graphMap.values().stream().flatMap(Collection::stream);
    }

    Stream<Set<Edge<E, V>>> getEdgesSet()
    {
        return graphMap.values().stream();
    }

    int size()
    {
        return graphMap.size();
    }

    Set<Edge<E, V>> getAdjacentEdges(Vertex<V> vertex)
    {
        validateVertex(vertex);
        return graphMap.get(vertex);
    }

    Vertex<V> addVertex(V property)
    {
        Vertex<V> vertex = new Vertex<>(graphMap.size(), property);

        graphMap.put(vertex, new HashSet<>());
        return vertex;
    }

    void addEdgeToSource(Edge<E, V> edge)
    {
        validateEdge(edge);
        graphMap.get(edge.source).add(edge);
    }

    void addEdgeToDestination(Edge<E, V> edge)
    {
        validateEdge(edge);
        graphMap.get(edge.destination).add(edge);
    }

    private void validateVertex(Vertex<V> vertex)
    {
        if(graphMap.keySet().stream().noneMatch(v -> v == vertex))
            throw new IllegalArgumentException("Vertex object does not belong to graph");
    }

    private void validateEdge(Edge<E, V> edge)
    {
        if(graphMap.keySet().stream().noneMatch(v -> v == edge.source))
            throw new IllegalArgumentException(
                    "Edge source or destination does not belong to graph");

        if(graphMap.keySet().stream().noneMatch(v -> v == edge.destination))
            throw new IllegalArgumentException(
                    "Edge source or destination does not belong to graph");
    }
}
