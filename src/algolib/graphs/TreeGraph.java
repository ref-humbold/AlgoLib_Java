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

    @Override
    public Vertex<V> getVertex(int index)
    {
        return graph.getVertex(index);
    }

    @Override
    public Edge<E, V> getEdge(Vertex<V> source, Vertex<V> destination)
    {
        return graph.getEdge(source, destination);
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
    public int getOutputDegree(Vertex<V> vertex)
    {
        return graph.getOutputDegree(vertex);
    }

    @Override
    public int getInputDegree(Vertex<V> vertex)
    {
        return graph.getInputDegree(vertex);
    }
}
