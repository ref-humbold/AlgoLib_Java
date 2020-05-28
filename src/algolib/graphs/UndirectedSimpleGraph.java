// Structure of undirected simple graph
package algolib.graphs;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UndirectedSimpleGraph<V, E>
        extends SimpleGraph<V, E>
        implements UndirectedGraph<V, E>
{
    public UndirectedSimpleGraph()
    {
        super();
    }

    public UndirectedSimpleGraph(Iterable<V> properties)
    {
        super(properties);
    }

    @Override
    public long getEdgesCount()
    {
        return graphMap.values().stream().flatMap(Set::stream).distinct().count();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return graphMap.values()
                       .stream()
                       .flatMap(Set::stream)
                       .distinct()
                       .sorted()
                       .collect(Collectors.toList());
    }

    @Override
    public long getOutputDegree(Vertex<V> vertex)
    {
        return graphMap.get(vertex).size();
    }

    @Override
    public long getInputDegree(Vertex<V> vertex)
    {
        return graphMap.get(vertex).size();
    }

    @Override
    public Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property)
    {
        Vertex<V> newSource = source.compareTo(destination) < 0 ? source : destination;
        Vertex<V> newDestination = source.compareTo(destination) >= 0 ? source : destination;
        Edge<E, V> edge = new Edge<>(newSource, newDestination, property);

        graphMap.get(source).add(edge);
        graphMap.get(destination).add(edge);
        return edge;
    }

    /**
     * Converts this graph to a directed graph with same vertices and edges
     * @param edgePropertyMapper mapping function for edge properties
     * @return directed graph
     */
    public DirectedSimpleGraph<V, E> asDirected(Function<E, E> edgePropertyMapper)
    {
        DirectedSimpleGraph<V, E> directedSimpleGraph = new DirectedSimpleGraph<>();

        for(Map.Entry<Vertex<V>, Set<Edge<E, V>>> entry : graphMap.entrySet())
        {
            directedSimpleGraph.graphMap.put(entry.getKey(), entry.getValue()
                                                                  .stream()
                                                                  .map(edge -> getEdgeFrom(
                                                                          entry.getKey(), edge,
                                                                          edgePropertyMapper))
                                                                  .collect(Collectors.toSet()));
        }

        return directedSimpleGraph;
    }

    private Edge<E, V> getEdgeFrom(Vertex<V> vertex, Edge<E, V> edge,
                                   Function<E, E> edgePropertyMapper)
    {
        return vertex.equals(edge.source) ? edge : edge.reverse(edgePropertyMapper);
    }
}
