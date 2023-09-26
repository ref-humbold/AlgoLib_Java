package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class GraphRepresentation<VertexId, VertexProperty, EdgeProperty>
{
    private final Map<Vertex<VertexId>, Set<Edge<VertexId>>> graphMap = new HashMap<>();
    private final Map<Vertex<VertexId>, VertexProperty> vertexProperties = new HashMap<>();
    private final Map<Edge<VertexId>, EdgeProperty> edgeProperties = new HashMap<>();

    GraphRepresentation()
    {
    }

    GraphRepresentation(Collection<VertexId> vertexIds)
    {
        vertexIds.forEach(vertexId -> graphMap.put(new Vertex<>(vertexId), new HashSet<>()));
    }

    Stream<Vertex<VertexId>> getVertices()
    {
        return graphMap.keySet().stream();
    }

    Stream<Edge<VertexId>> getEdges()
    {
        return graphMap.values().stream().flatMap(Collection::stream);
    }

    Stream<Set<Edge<VertexId>>> getEdgesSet()
    {
        return graphMap.values().stream();
    }

    int size()
    {
        return graphMap.size();
    }

    Vertex<VertexId> getVertex(VertexId vertexId)
    {
        return graphMap.keySet()
                       .stream()
                       .filter(v -> v.id.equals(vertexId))
                       .findFirst()
                       .orElse(null);
    }

    Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId)
    {
        return graphMap.entrySet()
                       .stream()
                       .filter(entry -> entry.getKey().id.equals(sourceId))
                       .findFirst()
                       .flatMap(entry -> entry.getValue()
                                              .stream()
                                              .filter(edge -> edge.getNeighbour(
                                                      entry.getKey()).id.equals(destinationId))
                                              .findFirst())
                       .orElse(null);
    }

    Stream<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex)
    {
        validate(vertex);
        return graphMap.get(vertex).stream();
    }

    VertexProperty getProperty(Vertex<VertexId> vertex)
    {
        validate(vertex);
        return vertexProperties.get(vertex);
    }

    void setProperty(Vertex<VertexId> vertex, VertexProperty property)
    {
        validate(vertex);
        vertexProperties.put(vertex, property);
    }

    EdgeProperty getProperty(Edge<VertexId> edge)
    {
        validate(edge, true);
        return edgeProperties.get(edge);
    }

    void setProperty(Edge<VertexId> edge, EdgeProperty property)
    {
        validate(edge, true);
        edgeProperties.put(edge, property);
    }

    boolean addVertex(Vertex<VertexId> vertex)
    {
        Set<Edge<VertexId>> edges = graphMap.putIfAbsent(vertex, new HashSet<>());

        return edges == null;
    }

    void addEdgeToSource(Edge<VertexId> edge)
    {
        validate(edge, false);
        graphMap.get(edge.source).add(edge);
    }

    void addEdgeToDestination(Edge<VertexId> edge)
    {
        validate(edge, false);
        graphMap.get(edge.destination).add(edge);
    }

    private void validate(Vertex<VertexId> vertex)
    {
        if(!graphMap.containsKey(vertex))
            throw new IllegalArgumentException(
                    "Vertex %s does not belong to this graph".formatted(vertex.toString()));
    }

    private void validate(Edge<VertexId> edge, boolean existing)
    {
        if(!graphMap.containsKey(edge.source) || !graphMap.containsKey(edge.destination))
            throw new IllegalArgumentException(
                    "Edge %s does not belong to this graph".formatted(edge));

        if(existing && !graphMap.get(edge.source).contains(edge) && !graphMap.get(edge.destination)
                                                                             .contains(edge))
            throw new IllegalArgumentException(
                    "Edge %s does not belong to this graph".formatted(edge));
    }
}
