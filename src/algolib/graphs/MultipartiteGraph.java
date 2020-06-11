// Structure of multipartite graph
package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultipartiteGraph<V, VP, EP>
        implements UndirectedGraph<V, VP, EP>
{
    private final int groupsCount;
    private final UndirectedSimpleGraph<V, VP, EP> graph = new UndirectedSimpleGraph<>();
    private final Map<V, Integer> vertexGroupMap = new HashMap<>();

    public MultipartiteGraph(int groupsCount)
    {
        this.groupsCount = groupsCount;
    }

    public MultipartiteGraph(Collection<Collection<V>> vertices)
    {
        int i = 0;

        groupsCount = vertices.size();

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

    public int getGroupsCount()
    {
        return vertexGroupMap.values().stream().distinct().mapToInt(g -> 1).sum();
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

    public void addVertex(int group, V vertex)
    {
        validateGroup(group);
        graph.addVertex(vertex);
        vertexGroupMap.put(vertex, group);
    }

    public void addVertex(int group, V vertex, VP property)
    {
        validateGroup(group);
        graph.addVertex(vertex, property);
        vertexGroupMap.put(vertex, group);
    }

    public Edge<V> addEdge(V source, V destination)
    {
        if(areInSameGroup(source, destination))
            throw new GraphPartitionException(
                    "Cannot create an edge between vertices in the same group");

        return graph.addEdge(source, destination);
    }

    public Edge<V> addEdge(V source, V destination, EP property)
    {
        if(areInSameGroup(source, destination))
            throw new GraphPartitionException(
                    "Cannot create an edge between vertices in the same group");

        return graph.addEdge(source, destination, property);
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

    public boolean areInSameGroup(V vertex1, V vertex2)
    {
        return Objects.equals(vertexGroupMap.get(vertex1), vertexGroupMap.get(vertex2));
    }

    public List<V> getVerticesFromGroup(int groupNumber)
    {
        return vertexGroupMap.entrySet()
                             .stream()
                             .filter(entry -> entry.getValue() == groupNumber)
                             .map(Map.Entry::getKey)
                             .sorted()
                             .collect(Collectors.toList());
    }

    private void validateGroup(int group)
    {
        if(group < 0 || group > groupsCount)
            throw new IndexOutOfBoundsException(
                    String.format("Invalid group number %d, graph contains only %d groups", group,
                                  groupsCount));
    }
}
