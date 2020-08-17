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
import algolib.graphs.properties.Weighted;
import algolib.tuples.Pair;

public final class Paths
{
    /**
     * Bellman-Ford algorithm.
     * @param graph a directed graph with weighted edges
     * @param source source vertex
     * @return map of vertices' distances
     */
    public static <V, VP, EP extends Weighted> Map<V, Double> bellmanFord(
            DirectedGraph<V, VP, EP> graph, V source)
            throws IllegalStateException
    {
        Map<V, Double> distances = graph.getVertices()
                                        .stream()
                                        .collect(Collectors.toMap(Function.identity(),
                                                                  v -> Weighted.INFINITY));

        distances.put(source, 0.0);

        for(int i = 0; i < graph.getVerticesCount() - 1; ++i)
            for(V vertex : graph.getVertices())
                for(Edge<V> edge : graph.getAdjacentEdges(vertex))
                    distances.put(edge.destination, Math.min(distances.get(edge.destination),
                                                             distances.get(vertex)
                                                                     + graph.getProperty(edge)
                                                                            .getWeight()));

        for(V vertex : graph.getVertices())
            for(Edge<V> edge : graph.getAdjacentEdges(vertex))
                if(distances.get(vertex) < Weighted.INFINITY
                        && distances.get(vertex) + graph.getProperty(edge).getWeight()
                        < distances.get(edge.destination))
                    throw new IllegalStateException("Graph contains a negative cycle.");

        return distances;
    }

    /**
     * Dijkstra algorithm.
     * @param graph a graph with weighted edges (weights are not negative)
     * @param source source vertex
     * @return map of vertices' distances
     */
    public static <V, VP, EP extends Weighted> Map<V, Double> dijkstra(Graph<V, VP, EP> graph,
                                                                       V source)
            throws IllegalStateException
    {
        for(Edge<V> edge : graph.getEdges())
            if(graph.getProperty(edge).getWeight() < 0.0)
                throw new IllegalStateException("Graph contains an edge with negative weight.");

        Map<V, Double> distances = graph.getVertices()
                                        .stream()
                                        .collect(Collectors.toMap(Function.identity(),
                                                                  v -> Weighted.INFINITY));
        Set<V> visited = new HashSet<>();
        PriorityQueue<Pair<Double, V>> vertexQueue =
                new PriorityQueue<>((pair1, pair2) -> Double.compare(pair1.first, pair2.first));

        distances.put(source, 0.0);
        vertexQueue.add(Pair.of(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            V vertex = vertexQueue.remove().second;

            if(!visited.contains(vertex))
            {
                visited.add(vertex);

                for(Edge<V> edge : graph.getAdjacentEdges(vertex))
                {
                    V neighbour = edge.getNeighbour(vertex);
                    double weight = graph.getProperty(edge).getWeight();

                    if(distances.get(vertex) + weight < distances.get(neighbour))
                    {
                        distances.put(neighbour, distances.get(vertex) + weight);
                        vertexQueue.add(Pair.of(distances.get(neighbour), neighbour));
                    }
                }
            }
        }

        return distances;
    }

    /**
     * Floyd-Warshall algorithm.
     * @param graph a directed graph with weighted edges
     * @return map of distances between all pairs of vertices
     */
    public static <V, VP, EP extends Weighted> Map<Pair<V, V>, Double> floydWarshall(
            DirectedGraph<V, VP, EP> graph)
    {
        Map<Pair<V, V>, Double> distances = new HashMap<>();

        for(V v : graph.getVertices())
            for(V u : graph.getVertices())
                distances.put(Pair.of(v, u), v.equals(u) ? 0.0 : Weighted.INFINITY);

        for(Edge<V> e : graph.getEdges())
            distances.put(Pair.of(e.source, e.destination), graph.getProperty(e).getWeight());

        for(V w : graph.getVertices())
            for(V v : graph.getVertices())
                for(V u : graph.getVertices())
                    distances.put(Pair.of(v, u), Math.min(distances.get(Pair.of(v, u)),
                                                          distances.get(Pair.of(v, w))
                                                                  + distances.get(Pair.of(w, u))));

        return distances;
    }
}
