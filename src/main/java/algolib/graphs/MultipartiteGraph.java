package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        if(groupsCount <= 0)
            throw new IllegalArgumentException("Number of groups cannot be negative nor zero");

        this.groupsCount = groupsCount;
    }

    public MultipartiteGraph(int groupsCount, Collection<Collection<VertexId>> vertexIds)
    {
        this(groupsCount);

        if(vertexIds.size() > this.groupsCount)
            throw new IllegalArgumentException(
                    String.format("Cannot add vertices to group %d, graph contains only %d groups",
                                  vertexIds.size(), this.groupsCount));

        int i = 0;

        for(Collection<VertexId> groupVertexIds : vertexIds)
        {
            for(VertexId vertexId : groupVertexIds)
                addVertex(i, vertexId);

            ++i;
        }
    }

    @Override
    public GraphProperties<VertexId, VertexProperty, EdgeProperty> getProperties()
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
    public Vertex<VertexId> getVertex(VertexId vertexId)
    {
        return graph.getVertex(vertexId);
    }

    @Override
    public Edge<VertexId> getEdge(VertexId sourceId, VertexId destinationId)
    {
        return graph.getEdge(sourceId, destinationId);
    }

    @Override
    public Collection<Edge<VertexId>> getAdjacentEdges(Vertex<VertexId> vertex)
    {
        return graph.getAdjacentEdges(vertex);
    }

    @Override
    public Collection<Vertex<VertexId>> getNeighbours(Vertex<VertexId> vertex)
    {
        return graph.getNeighbours(vertex);
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

    @Override
    public DirectedGraph<VertexId, VertexProperty, EdgeProperty> asDirected()
    {
        return graph.asDirected();
    }

    /**
     * @param groupNumber group number
     * @return vertices that belong to the group
     */
    public Collection<Vertex<VertexId>> getVerticesFromGroup(int groupNumber)
    {
        validateGroup(groupNumber);
        return vertexGroupMap.entrySet()
                             .stream()
                             .filter(entry -> entry.getValue() == groupNumber)
                             .map(Map.Entry::getKey)
                             .collect(Collectors.toList());
    }

    /**
     * Adds new vertex to given group in this graph.
     * @param groupNumber group number
     * @param vertexId identifier of new vertex
     * @return new vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(int groupNumber, VertexId vertexId)
    {
        return addVertex(groupNumber, new Vertex<>(vertexId));
    }

    /**
     * Adds new vertex with given property to given group in this graph.
     * @param groupNumber group number
     * @param vertexId identifier of new vertex
     * @param property vertex property
     * @return new vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(int groupNumber, VertexId vertexId, VertexProperty property)
    {
        return addVertex(groupNumber, new Vertex<>(vertexId), property);
    }

    /**
     * Adds new vertex to given group in this graph.
     * @param groupNumber group number
     * @param vertex new vertex
     * @return new vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(int groupNumber, Vertex<VertexId> vertex)
    {
        return addVertex(groupNumber, vertex, null);
    }

    /**
     * Adds new vertex with given property to given group in this graph.
     * @param groupNumber group number
     * @param vertex new vertex
     * @param property vertex property
     * @return new vertex, or {@code null} if vertex already exists
     */
    public Vertex<VertexId> addVertex(int groupNumber, Vertex<VertexId> vertex,
                                      VertexProperty property)
    {
        validateGroup(groupNumber);
        return Optional.ofNullable(graph.addVertex(vertex, property)).map(newVertex -> {
            vertexGroupMap.put(newVertex, groupNumber);
            return newVertex;
        }).orElse(null);
    }

    /**
     * Adds new edge between given vertices to this graph.
     * @param source source vertex
     * @param destination destination vertex
     * @return new edge, or {@code null} if edge already exists
     * @throws GraphPartitionException if vertices belong to same group
     */
    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination)
    {
        return addEdge(new Edge<>(source, destination));
    }

    /**
     * Adds new edge between given vertices with given property to this graph.
     * @param source source vertex
     * @param destination destination vertex
     * @param property edge property
     * @return new edge, or {@code null} if edge already exists
     * @throws GraphPartitionException if the edge connects vertices that belong to same group
     */
    public Edge<VertexId> addEdgeBetween(Vertex<VertexId> source, Vertex<VertexId> destination,
                                         EdgeProperty property)
    {
        return addEdge(new Edge<>(source, destination), property);
    }

    /**
     * Adds new edge to this graph.
     * @param edge new edge
     * @return new edge, or {@code null} if edge already exists
     * @throws GraphPartitionException if the edge connects vertices that belong to same group
     */
    public Edge<VertexId> addEdge(Edge<VertexId> edge)
    {
        return addEdge(edge, null);
    }

    /**
     * Adds new edge with given property to this graph.
     * @param edge new edge
     * @param property edge property
     * @return new edge, or {@code null} if edge already exists
     * @throws GraphPartitionException if vertices belong to same group
     */
    public Edge<VertexId> addEdge(Edge<VertexId> edge, EdgeProperty property)
    {
        if(areInSameGroup(edge.source, edge.destination))
            throw new GraphPartitionException(
                    "Cannot create an edge between vertices in the same group");

        return graph.addEdge(edge, property);
    }

    private boolean areInSameGroup(Vertex<VertexId> vertex1, Vertex<VertexId> vertex2)
    {
        Integer group1 = vertexGroupMap.get(vertex1);
        Integer group2 = vertexGroupMap.get(vertex2);

        return group1 != null && group1.equals(group2);
    }

    private void validateGroup(int groupNumber)
    {
        if(groupNumber < 0 || groupNumber >= groupsCount)
            throw new IndexOutOfBoundsException(
                    String.format("Invalid group number %d, graph contains only %d groups",
                                  groupNumber, groupsCount));
    }
}
