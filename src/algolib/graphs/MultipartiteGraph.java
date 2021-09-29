package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/** Structure of multipartite graph */
public class MultipartiteGraph<VertexId, VertexProperty, EdgeProperty>
        implements UndirectedGraph<VertexId, VertexProperty, EdgeProperty>
{
    public final int groupsCount;
    private final UndirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> graph =
            new UndirectedSimpleGraph<>();
    private final Map<Vertex<VertexId>, Integer> vertexGroupMap = new HashMap<>();

    public MultipartiteGraph(int groupsCount)
    {
        if(groupsCount < 0)
            throw new IllegalArgumentException("Number of groups cannot be negative");

        this.groupsCount = groupsCount;
    }

    public MultipartiteGraph(int groupsCount, Collection<Collection<VertexId>> vertices)
    {
        this(groupsCount);

        if(vertices.size() > this.groupsCount)
            throw new IllegalArgumentException(
                    String.format("Cannot add vertices to group %d, graph contains only %d groups",
                                  vertices.size(), this.groupsCount));

        int i = 0;

        for(Collection<VertexId> groupVertices : vertices)
        {
            for(VertexId vertex : groupVertices)
                addVertex(i, vertex);

            ++i;
        }
    }

    @Override
    public Properties<VertexId, VertexProperty, EdgeProperty> getProperties()
    {
        return graph.getProperties();
    }

    @Override
    public int getVerticesCount()
    {
        return graph.getVerticesCount();
    }

    @Override
    public int getEdgesCount()
    {
        return graph.getEdgesCount();
    }

    @Override
    public Collection<Vertex<VertexId>> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public Collection<Edge<VertexId>> getEdges()
    {
        return graph.getEdges();
    }

    @Override
    public Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId)
    {
        return graph.getEdge(sourceId, destinationId);
    }

    @Override
    public Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex)
    {
        return graph.getNeighbours(vertex);
    }

    @Override
    public Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex)
    {
        return graph.getAdjacentEdges(vertex);
    }

    @Override
    public int getOutputDegree(Vertex<VertexId> vertex)
    {
        return graph.getOutputDegree(vertex);
    }

    @Override
    public int getInputDegree(Vertex<VertexId> vertex)
    {
        return graph.getInputDegree(vertex);
    }

    public DirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> asDirected()
    {
        return graph.asDirected();
    }

    public Collection<Vertex<VertexId>> getVerticesFromGroup(int groupNumber)
    {
        validateGroup(groupNumber);
        return vertexGroupMap.entrySet()
                             .stream()
                             .filter(entry -> entry.getValue() == groupNumber)
                             .map(Map.Entry::getKey)
                             .collect(Collectors.toList());
    }

    public Vertex<VertexId> addVertex(int groupNumber, VertexId vertexId)
    {
        return addVertex(groupNumber, vertexId, null);
    }

    public Vertex<VertexId> addVertex(int groupNumber, VertexId vertexId, VertexProperty property)
    {
        validateGroup(groupNumber);

        Vertex<VertexId> vertex = graph.addVertex(vertexId, property);

        if(vertex != null)
            vertexGroupMap.put(vertex, groupNumber);

        return vertex;
    }

    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return addEdge(new Edge<>(source, destination));
    }

    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination,
                                         EdgeProperty property)
    {

        return addEdge(new Edge<>(source, destination), property);
    }

    public Edge<VertexId> addEdge(Edge<VertexId> edge)
    {
        return addEdge(edge, null);
    }

    public Edge<VertexId> addEdge(Edge<VertexId> edge, EdgeProperty property)
    {
        if(areInSameGroup(edge.source, edge.destination))
            throw new GraphPartitionException(
                    "Cannot create an edge between vertices in the same group");

        return graph.addEdge(edge, property);
    }

    private boolean areInSameGroup(Vertex<VertexId> vertex1, Vertex<VertexId> vertex2)
    {
        return Objects.equals(vertexGroupMap.get(vertex1), vertexGroupMap.get(vertex2));
    }

    private void validateGroup(int groupNumber)
    {
        if(groupNumber < 0 || groupNumber >= groupsCount)
            throw new IndexOutOfBoundsException(
                    String.format("Invalid group number %d, graph contains only %d groups",
                                  groupNumber, groupsCount));
    }
}
