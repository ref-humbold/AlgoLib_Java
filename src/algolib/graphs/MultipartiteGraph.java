// Structure of multipartite graph
package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultipartiteGraph<V, E>
        implements UndirectedGraph<V, E>
{
    private final int groupsCount;
    private final UndirectedSimpleGraph<V, E> graph = new UndirectedSimpleGraph<>();
    private final Map<Vertex<V>, Integer> vertexGroupMap = new HashMap<>();

    public MultipartiteGraph(Collection<Collection<V>> properties)
    {
        groupsCount = properties.size();

        int i = 0;

        for(Collection<V> groupProperties : properties)
        {
            for(V property : groupProperties)
                addVertex(property, i);

            ++i;
        }
    }

    @Override
    public int getVerticesCount()
    {
        return graph.getVerticesCount();
    }

    @Override
    public List<Vertex<V>> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public int getEdgesCount()
    {
        return graph.getEdgesCount();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return graph.getEdges();
    }

    public Vertex<V> addVertex(V property, int group)
    {
        validateGroup(group);

        Vertex<V> vertex = graph.addVertex(property);

        vertexGroupMap.put(vertex, group);
        return vertex;
    }

    public Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property)
    {
        if(areInSameGroup(source, destination))
            throw new GraphPartitionException(
                    "Cannot create an edge between vertices in the same group");

        return graph.addEdge(source, destination, property);
    }

    @Override
    public Collection<Vertex<V>> getNeighbours(Vertex<V> vertex)
    {
        return graph.getNeighbours(vertex);
    }

    @Override
    public Collection<Edge<E, V>> getAdjacentEdges(Vertex<V> vertex)
    {
        return graph.getAdjacentEdges(vertex);
    }

    @Override
    public int getOutputDegree(Vertex<V> vertex)
    {
        return graph.getOutputDegree(vertex);
    }

    @Override
    public int getInputDegree(Vertex<V> vertex)
    {
        return graph.getInputDegree(vertex);
    }

    public boolean areInSameGroup(Vertex<V> vertex1, Vertex<V> vertex2)
    {
        return Objects.equals(vertexGroupMap.get(vertex1), vertexGroupMap.get(vertex2));
    }

    private void validateGroup(int group)
    {
        if(group < 0 || group > groupsCount)
            throw new IndexOutOfBoundsException(
                    String.format("Invalid group number %d, graph contains only %d groups", group,
                                  groupsCount));
    }
}
