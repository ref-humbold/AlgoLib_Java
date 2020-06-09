// Structure of tree graph
package algolib.graphs;

import java.util.Collection;
import java.util.Collections;

public class TreeGraph<V, VP, EP>
        implements UndirectedGraph<V, VP, EP>
{
    private final UndirectedSimpleGraph<V, VP, EP> graph;

    public TreeGraph(V vertex)
    {
        graph = new UndirectedSimpleGraph<>(Collections.singleton(vertex));
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

    public Edge<V> addVertex(V vertex, V neighbour)
    {
        graph.addVertex(vertex);
        return graph.addEdge(vertex, neighbour);
    }

    public Edge<V> addVertex(V vertex, V neighbour, VP vertexProperty, EP edgeProperty)
    {
        graph.addVertex(vertex);
        graph.setProperty(vertex, vertexProperty);
        return graph.addEdge(vertex, neighbour, edgeProperty);
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
}
