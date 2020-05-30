// Algorithms for shortest paths in graph
package algolib.graphs.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import algolib.graphs.DirectedGraph;
import algolib.graphs.Edge;
import algolib.graphs.Graph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.tuples.ComparablePair;
import algolib.tuples.Pair;

public final class Paths
{
    /**
     * Bellman-Ford algorithm
     * @param graph directed graph with weighted edges
     * @param source source vertex
     * @return map of vertices' distances
     */
    public static <V, E extends Weighted> Map<Vertex<V>, Double> bellmanFord(
            DirectedGraph<V, E> graph, Vertex<V> source)
            throws IllegalStateException
    {
        Map<Vertex<V>, Double> distances = graph.getVertices()
                                                .stream()
                                                .collect(Collectors.toMap(Function.identity(),
                                                                          v -> Weighted.INFINITY));

        distances.put(source, 0.0);

        for(int i = 0; i < graph.getVerticesCount() - 1; ++i)
            for(Vertex<V> v : graph.getVertices())
                for(Edge<E, V> e : graph.getAdjacentEdges(v))
                    distances.put(e.destination, Math.min(distances.get(e.destination),
                                                          distances.get(v)
                                                                  + e.property.getWeight()));

        for(Vertex<V> v : graph.getVertices())
            for(Edge<E, V> e : graph.getAdjacentEdges(v))
                if(distances.get(v) < Weighted.INFINITY
                        && distances.get(v) + e.property.getWeight() < distances.get(e.destination))
                    throw new IllegalStateException("Graph contains a negative cycle.");

        return distances;
    }

    /**
     * Dijkstra algorithm
     * @param graph graph with weighted edges (weights are not negative)
     * @param source source vertex
     * @return map of vertices' distances
     */
    public static <V, E extends Weighted> Map<Vertex<V>, Double> dijkstra(Graph<V, E> graph,
                                                                          Vertex<V> source)
            throws IllegalStateException
    {
        for(Edge<E, V> edge : graph.getEdges())
            if(edge.property.getWeight() < 0.0)
                throw new IllegalStateException("Graph contains an edge with negative weight.");

        Map<Vertex<V>, Double> distances = graph.getVertices()
                                                .stream()
                                                .collect(Collectors.toMap(Function.identity(),
                                                                          v -> Weighted.INFINITY));
        Set<Vertex<V>> visited = new HashSet<>();
        PriorityQueue<ComparablePair<Double, Vertex<V>>> vertexQueue = new PriorityQueue<>();

        distances.put(source, 0.0);
        vertexQueue.add(ComparablePair.of(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Vertex<V> v = vertexQueue.remove().second;

            if(!visited.contains(v))
            {
                visited.add(v);

                for(Edge<E, V> e : graph.getAdjacentEdges(v))
                {
                    Vertex<V> neighbour = e.getNeighbour(v);
                    double weight = e.property.getWeight();

                    if(distances.get(v) + weight < distances.get(neighbour))
                    {
                        distances.put(neighbour, distances.get(v) + weight);
                        vertexQueue.add(ComparablePair.of(distances.get(neighbour), neighbour));
                    }
                }
            }
        }

        return distances;
    }

    /**
     * Floyd-Warshall algorithm
     * @param graph directed graph with weighted edges
     * @return map of distances between all pairs of vertices
     */
    public static <V, E extends Weighted> Map<Pair<Vertex<V>, Vertex<V>>, Double> floydWarshall(
            DirectedGraph<V, E> graph)
    {
        Map<Pair<Vertex<V>, Vertex<V>>, Double> distances = new HashMap<>();

        for(Vertex<V> v : graph.getVertices())
            for(Vertex<V> u : graph.getVertices())
                distances.put(Pair.of(v, u), v.equals(u) ? 0.0 : Weighted.INFINITY);

        for(Edge<E, V> e : graph.getEdges())
            distances.put(Pair.of(e.source, e.destination), e.property.getWeight());

        for(Vertex<V> w : graph.getVertices())
            for(Vertex<V> v : graph.getVertices())
                for(Vertex<V> u : graph.getVertices())
                    distances.put(Pair.of(v, u), Math.min(distances.get(Pair.of(v, u)),
                                                          distances.get(Pair.of(v, w))
                                                                  + distances.get(Pair.of(w, u))));

        return distances;
    }
}
