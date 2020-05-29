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

    @Override
    public int getEdgesCount()
    {
        return graphRepresentation.getEdges().distinct().mapToInt(edge -> 1).sum();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return graphRepresentation.getEdges().distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public int getOutputDegree(Vertex<V> vertex)
    {
        return graphRepresentation.getAdjacentEdges(vertex).size();
    }

    @Override
    public int getInputDegree(Vertex<V> vertex)
    {
        return graphRepresentation.getAdjacentEdges(vertex).size();
    }

    @Override
    public Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property)
    {
        Vertex<V> newSource = source.compareTo(destination) < 0 ? source : destination;
        Vertex<V> newDestination = source.compareTo(destination) >= 0 ? source : destination;
        Edge<E, V> edge = new Edge<>(newSource, newDestination, property);

        graphRepresentation.addEdgeToSource(edge);
        graphRepresentation.addEdgeToDestination(edge);
        return edge;
    }

    /**
     * Converts this graph to a directed graph with same vertices.
     * @return directed graph
     */
    public DirectedSimpleGraph<V, E> asDirected()
    {
        DirectedSimpleGraph<V, E> directedSimpleGraph = new DirectedSimpleGraph<>();

        directedSimpleGraph.graphRepresentation = new GraphRepresentation<>(graphRepresentation);
        getEdges().forEach(edge -> {
            directedSimpleGraph.addEdge(edge.source, edge.destination, edge.property);
            directedSimpleGraph.addEdge(edge.destination, edge.source, edge.property);
        });

        return directedSimpleGraph;
    }
}
