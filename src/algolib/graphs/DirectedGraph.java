package algolib.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DirectedGraph<V, E>
        extends SimpleGraph<V, E>
{
    @Override
    public long getEdgesCount()
    {
        return graphMap.values().stream().mapToInt(edges -> edges.size()).sum();
    }

    @Override
    public Collection<Edge<E, V>> getEdges()
    {
        return graphMap.values().stream().reduce(new HashSet<>(), (acc, edges) -> {
            acc.addAll(edges);
            return acc;
        }, (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        });
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
