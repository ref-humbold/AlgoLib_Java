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
        validateVertex(vertex);
        return graphMap.get(vertex).stream();
    }

    VertexProperty getProperty(Vertex<VertexId> vertex)
    {
        validateVertex(vertex);
        return vertexProperties.get(vertex);
    }

    void setProperty(Vertex<VertexId> vertex, VertexProperty property)
    {
        validateVertex(vertex);
        vertexProperties.put(vertex, property);
    }

    EdgeProperty getProperty(Edge<VertexId> edge)
    {
        validateEdge(edge);
        return edgeProperties.get(edge);
    }

    void setProperty(Edge<VertexId> edge, EdgeProperty property)
    {
        validateEdge(edge);
        edgeProperties.put(edge, property);
    }

    boolean addVertex(Vertex<VertexId> vertex)
    {
        Set<Edge<VertexId>> edges = graphMap.putIfAbsent(vertex, new HashSet<>());

        return edges == null;
    }

    void addEdgeToSource(Edge<VertexId> edge)
    {
        validateEdgeVertices(edge);
        graphMap.get(edge.source).add(edge);
    }

    void addEdgeToDestination(Edge<VertexId> edge)
    {
        validateEdgeVertices(edge);
        graphMap.get(edge.destination).add(edge);
    }

    private void validateVertex(Vertex<VertexId> vertex)
    {
        if(!graphMap.containsKey(vertex))
            throw new IllegalArgumentException(
                    "Vertex %s does not belong to this graph".formatted(vertex.toString()));
    }

    private void validateEdgeVertices(Edge<VertexId> edge)
    {
        if(!graphMap.containsKey(edge.source))
            throw new IllegalArgumentException(
                    "Edge source %s does not belong to this graph".formatted(edge.source));

        if(!graphMap.containsKey(edge.destination))
            throw new IllegalArgumentException(
                    "Edge destination %s does not belong to this graph".formatted(
                            edge.destination));
    }

    private void validateEdge(Edge<VertexId> edge)
    {
        validateEdgeVertices(edge);

        if(!graphMap.get(edge.source).contains(edge) && !graphMap.get(edge.destination)
                                                                 .contains(edge))
            throw new IllegalArgumentException(
                    "Edge %s does not belong to this graph".formatted(edge));
    }
}
