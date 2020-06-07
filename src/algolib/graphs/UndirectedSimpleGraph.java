// Structure of undirected simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UndirectedSimpleGraph<V, E>
        extends SimpleGraph<V, E>
        implements UndirectedGraph<V, E>
{
    public UndirectedSimpleGraph()
    {
        super();
    }

    public UndirectedSimpleGraph(Collection<V> properties)
    {
        super(properties);
    }

    public UndirectedSimpleGraph(Graph<V, E> graph)
    {
        super(graph);
    }

    @Override
    public int getEdgesCount()
    {
        return representation.getEdges().distinct().mapToInt(edge -> 1).sum();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return representation.getEdges().distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(Vertex<V> vertex)
    {
        return representation.getAdjacentEdges(vertex).size();
    }

    @Override
    public int getInputDegree(Vertex<V> vertex)
    {
        return representation.getAdjacentEdges(vertex).size();
    }

    @Override
    public Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property)
    {
        Vertex<V> newSource = source.compareTo(destination) < 0 ? source : destination;
        Vertex<V> newDestination = source.compareTo(destination) >= 0 ? source : destination;
        Edge<E, V> edge = new Edge<>(newSource, newDestination, property);

        representation.addEdgeToSource(edge);
        representation.addEdgeToDestination(edge);
        return edge;
    }

    /**
     * Converts this graph to a directed graph with same vertices.
     * @return directed graph
     */
    public DirectedSimpleGraph<V, E> asDirected()
    {
        DirectedSimpleGraph<V, E> directedSimpleGraph = new DirectedSimpleGraph<>(this);

        getEdges().forEach(edge -> {
            directedSimpleGraph.addEdge(edge.source, edge.destination, edge.property);
            directedSimpleGraph.addEdge(edge.destination, edge.source, edge.property);
        });

        return directedSimpleGraph;
    }
}
