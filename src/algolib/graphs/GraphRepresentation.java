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

    int size()
    {
        return graphMap.size();
    }

    Stream<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex)
    {
        validate(vertex);
        return graphMap.get(vertex).stream();
    }

    Vertex<VertexId> addVertex(VertexId vertexId)
    {
        Vertex<VertexId> vertex = new Vertex<>(vertexId);
        Set<Edge<VertexId>> value = graphMap.putIfAbsent(vertex, new HashSet<>());

        return value != null ? vertex : null;
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
                    String.format("Vertex %s does not belong to this graph", vertex.toString()));
    }

    private void validate(Edge<VertexId> edge, boolean existing)
    {
        if(!graphMap.containsKey(edge.source) || !graphMap.containsKey(edge.destination))
            throw new IllegalArgumentException(
                    String.format("Edge %s does not belong to this graph", edge));

        if(existing && !graphMap.get(edge.source).contains(edge) && !graphMap.get(edge.destination)
                                                                             .contains(edge))
            throw new IllegalArgumentException(
                    String.format("Edge %s does not belong to this graph", edge));
    }
}
