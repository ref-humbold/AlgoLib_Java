// Structure of directed simple graph
package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectedSimpleGraph<V, E>
        extends SimpleGraph<V, E>
        implements DirectedGraph<V, E>
{
    public DirectedSimpleGraph()
    {
        super();
    }

    public DirectedSimpleGraph(Collection<V> properties)
    {
        super(properties);
    }

    @Override
    public long getEdgesCount()
    {
        return graphMap.values().stream().mapToLong(Set::size).sum();
    }

    @Override
    public List<Edge<E, V>> getEdges()
    {
        return graphMap.values()
                       .stream()
                       .flatMap(Set::stream)
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
        return graphMap.values()
                       .stream()
                       .flatMap(edges -> edges.stream()
                                              .filter(edge -> edge.destination.equals(vertex)))
                       .count();
    }

    @Override
    public Edge<E, V> addEdge(Vertex<V> source, Vertex<V> destination, E property)
    {
        Edge<E, V> edge = new Edge<>(source, destination, property);

        graphMap.get(source).add(edge);
        return edge;
    }

    @Override
    public void reverse()
    {
        HashMap<Vertex<V>, Set<Edge<E, V>>> reverseGraphMap = new HashMap<>();

        getVertices().forEach(vertex -> reverseGraphMap.put(vertex, new HashSet<>()));
        getEdges().forEach(edge -> reverseGraphMap.get(edge.destination)
                                                  .add(new Edge<>(edge.destination, edge.source,
                                                                  edge.property)));

        graphMap = reverseGraphMap;
    }
}
