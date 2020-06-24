// Structure of multipartite graph
package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultipartiteGraph<V, VP, EP>
        implements UndirectedGraph<V, VP, EP>
{
    public final int groupsCount;
    private final UndirectedSimpleGraph<V, VP, EP> graph = new UndirectedSimpleGraph<>();
    private final Map<V, Integer> vertexGroupMap = new HashMap<>();

    public MultipartiteGraph(int groupsCount)
    {
        this.groupsCount = groupsCount;
    }

    public MultipartiteGraph(int groupsCount, Collection<Collection<V>> vertices)
    {
        this(groupsCount);

        if(vertices.size() > groupsCount)
            throw new IllegalArgumentException("");

        int i = 0;

        for(Collection<V> groupVertices : vertices)
        {
            for(V vertex : groupVertices)
                addVertex(i, vertex);

            ++i;
        }
    }

    @Override
    public int getVerticesCount()
    {
        return graph.getVerticesCount();
    }

    @Override
    public Collection<V> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public int getEdgesCount()
    {
        return graph.getEdgesCount();
    }

    @Override
    public Collection<Edge<V>> getEdges()
    {
        return graph.getEdges();
    }

    @Override
    public VP getProperty(V vertex)
    {
        return graph.getProperty(vertex);
    }

    @Override
    public void setProperty(V vertex, VP property)
    {
        graph.setProperty(vertex, property);
    }

    @Override
    public EP getProperty(Edge<V> edge)
    {
        return graph.getProperty(edge);
    }

    @Override
    public void setProperty(Edge<V> edge, EP property)
    {
        graph.setProperty(edge, property);
    }

    @Override
    public Edge<V> getEdge(V source, V destination)
    {
        return graph.getEdge(source, destination);
    }

    @Override
    public Collection<V> getNeighbours(V vertex)
    {
        return graph.getNeighbours(vertex);
    }

    @Override
    public Collection<Edge<V>> getAdjacentEdges(V vertex)
    {
        return graph.getAdjacentEdges(vertex);
    }

    @Override
    public int getOutputDegree(V vertex)
    {
        return graph.getOutputDegree(vertex);
    }

    @Override
    public int getInputDegree(V vertex)
    {
        return graph.getInputDegree(vertex);
    }

    public Collection<V> getVerticesFromGroup(int groupNumber)
    {
        validateGroup(groupNumber);
        return vertexGroupMap.entrySet()
                             .stream()
                             .filter(entry -> entry.getValue() == groupNumber)
                             .map(Map.Entry::getKey)
                             .collect(Collectors.toList());
    }

    public boolean addVertex(int groupNumber, V vertex)
    {
        return addVertex(groupNumber, vertex, null);
    }

    public boolean addVertex(int groupNumber, V vertex, VP property)
    {
        validateGroup(groupNumber);

        boolean wasAdded = graph.addVertex(vertex, property);

        if(wasAdded)
            vertexGroupMap.put(vertex, groupNumber);

        return wasAdded;
    }

    public Edge<V> addEdgeBetween(V source, V destination)
    {
        return addEdge(new Edge<>(source, destination));
    }

    public Edge<V> addEdgeBetween(V source, V destination, EP property)
    {

        return addEdge(new Edge<>(source, destination), property);
    }

    public Edge<V> addEdge(Edge<V> edge)
    {
        return addEdge(edge, null);
    }

    public Edge<V> addEdge(Edge<V> edge, EP property)
    {
        if(areInSameGroup(edge.source, edge.destination))
            throw new GraphPartitionException(
                    "Cannot create an edge between vertices in the same group");

        return graph.addEdge(edge, property);
    }

    private boolean areInSameGroup(V vertex1, V vertex2)
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
