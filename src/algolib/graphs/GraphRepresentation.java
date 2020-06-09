package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class GraphRepresentation<V, VP, EP>
{
    // Adjacency list
    private final Map<V, Set<Edge<V>>> graphMap = new HashMap<>();

    // Vertex properties
    private final Map<V, VP> vertexProperties = new HashMap<>();

    // Edge properties
    private final Map<Edge<V>, EP> edgeProperties = new HashMap<>();

    GraphRepresentation()
    {
    }

    GraphRepresentation(Collection<V> vertices)
    {
        vertices.forEach(vertex -> graphMap.put(vertex, new HashSet<>()));
    }

    Stream<V> getVertices()
    {
        return graphMap.keySet().stream();
    }

    Stream<Edge<V>> getEdges()
    {
        return graphMap.values().stream().flatMap(Collection::stream);
    }

    Stream<Set<Edge<V>>> getEdgesSet()
    {
        return graphMap.values().stream();
    }

    VP getProperty(V vertex)
    {
        validate(vertex);
        return vertexProperties.get(vertex);
    }

    void setProperty(V vertex, VP property)
    {
        validate(vertex);
        vertexProperties.put(vertex, property);
    }

    EP getProperty(Edge<V> edge)
    {
        validate(edge);
        return edgeProperties.get(edge);
    }

    void setProperty(Edge<V> edge, EP property)
    {
        validate(edge);
        edgeProperties.put(edge, property);
    }

    int size()
    {
        return graphMap.size();
    }

    Stream<Edge<V>> getAdjacentEdges(V vertex)
    {
        validate(vertex);
        return graphMap.get(vertex).stream();
    }

    boolean addVertex(V vertex)
    {
        Set<Edge<V>> value = graphMap.putIfAbsent(vertex, new HashSet<>());

        return value == null;
    }

    void addEdgeToSource(Edge<V> edge)
    {
        validate(edge);
        graphMap.get(edge.source).add(edge);
    }

    void addEdgeToDestination(Edge<V> edge)
    {
        validate(edge);
        graphMap.get(edge.destination).add(edge);
    }

    private void validate(V vertex)
    {
        if(!graphMap.containsKey(vertex))
            throw new IllegalArgumentException(
                    String.format("Vertex %s does not belong to this graph", vertex.toString()));
    }

    private void validate(Edge<V> edge)
    {
        if(!graphMap.containsKey(edge.source) || !graphMap.containsKey((edge.destination)))
            throw new IllegalArgumentException(
                    String.format("Edge %s does not belong to this graph", edge.toString()));
    }
}
