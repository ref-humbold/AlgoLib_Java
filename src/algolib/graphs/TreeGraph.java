// Structure of tree graph
package algolib.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TreeGraph<V, E>
        implements UndirectedGraph<V, E>
{
    private final UndirectedSimpleGraph<V, E> graph;

    public TreeGraph(V property)
    {
        graph = new UndirectedSimpleGraph<>(Collections.singleton(property));
    }

    @Override
    public long getVerticesCount()
    {
        return graph.getVerticesCount();
    }

    @Override
    public List<Vertex<V>> getVertices()
    {
        return graph.getVertices();
    }

    @Override
    public long getEdgesCount()
    {
        return graph.getEdgesCount();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return graph.getEdges();
    }

    public Vertex<V> addVertex(V vertexProperty, E edgeProperty, Vertex<V> neighbour)
    {
        Vertex<V> vertex = graph.addVertex(vertexProperty);

        graph.addEdge(vertex, neighbour, edgeProperty);
        return vertex;
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
    public long getOutputDegree(Vertex<V> vertex)
    {
        return graph.getOutputDegree(vertex);
    }

    @Override
    public long getInputDegree(Vertex<V> vertex)
    {
        return graph.getInputDegree(vertex);
    }
}
