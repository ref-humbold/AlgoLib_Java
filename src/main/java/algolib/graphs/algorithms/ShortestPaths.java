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
import algolib.tuples.Pair;

/** Algorithms for shortest paths in a weighted graph. */
public final class ShortestPaths
{
    /**
     * Computes shortest paths in given directed graph from given vertex using Bellman-Ford
     * algorithm.
     * @param graph the directed weighted graph
     * @param source the source vertex
     * @return the map of distances to each vertex
     * @throws IllegalStateException if the graph contains a negative cycle
     */
    public static <VertexId, VertexProperty, EdgeProperty extends Weighted> Map<Vertex<VertexId>,
                                                                                       Double> bellmanFord(
            DirectedGraph<VertexId, VertexProperty, EdgeProperty> graph, Vertex<VertexId> source)
            throws IllegalStateException
    {
        Map<Vertex<VertexId>, Double> distances = graph.getVertices()
                                                       .stream()
                                                       .collect(
                                                               Collectors.toMap(Function.identity(),
                                                                                v -> Weighted.INFINITY));

        distances.put(source, 0.0);

        for(int i = 0; i < graph.getVerticesCount() - 1; ++i)
            for(Vertex<VertexId> vertex : graph.getVertices())
                for(Edge<VertexId> edge : graph.getAdjacentEdges(vertex))
                    distances.put(edge.destination, Math.min(distances.get(edge.destination),
                                                             distances.get(vertex)
                                                                     + graph.getProperties()
                                                                            .get(edge)
                                                                            .getWeight()));

        for(Vertex<VertexId> vertex : graph.getVertices())
            for(Edge<VertexId> edge : graph.getAdjacentEdges(vertex))
                if(distances.get(vertex) < Weighted.INFINITY
                           && distances.get(vertex) + graph.getProperties().get(edge).getWeight()
                                      < distances.get(edge.destination))
                    throw new IllegalStateException("Graph contains a negative cycle.");

        return distances;
    }

    /**
     * Computes shortest paths in given graph from given vertex using Dijkstra algorithm.
     * @param graph the weighted graph with non-negative weights
     * @param source the source vertex
     * @return the map of distances to each vertex.
     * @throws IllegalStateException if the graph contains an edge with negative weight
     */
    public static <VertexId, VertexProperty, EdgeProperty extends Weighted> Map<Vertex<VertexId>,
                                                                                       Double> dijkstra(
            Graph<VertexId, VertexProperty, EdgeProperty> graph, Vertex<VertexId> source)
            throws IllegalStateException
    {
        for(Edge<VertexId> edge : graph.getEdges())
            if(graph.getProperties().get(edge).getWeight() < 0.0)
                throw new IllegalStateException("Graph contains an edge with negative weight.");

        Map<Vertex<VertexId>, Double> distances = graph.getVertices()
                                                       .stream()
                                                       .collect(
                                                               Collectors.toMap(Function.identity(),
                                                                                v -> Weighted.INFINITY));
        Set<Vertex<VertexId>> visited = new HashSet<>();
        PriorityQueue<Pair<Double, Vertex<VertexId>>> vertexQueue =
                new PriorityQueue<>((pair1, pair2) -> Double.compare(pair1.first, pair2.first));

        distances.put(source, 0.0);
        vertexQueue.add(Pair.of(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Vertex<VertexId> vertex = vertexQueue.remove().second;

            if(!visited.contains(vertex))
            {
                visited.add(vertex);

                for(Edge<VertexId> edge : graph.getAdjacentEdges(vertex))
                {
                    Vertex<VertexId> neighbour = edge.getNeighbour(vertex);
                    double weight = graph.getProperties().get(edge).getWeight();

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
     * Computes shortest paths in given directed graph between all vertices using Floyd-Warshall
     * algorithm.
     * @param graph the directed weighted graph
     * @return the map of distances between all pairs of vertices
     */
    public static <VertexId, VertexProperty, EdgeProperty extends Weighted> Map<Pair<Vertex<VertexId>, Vertex<VertexId>>, Double> floydWarshall(
            DirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
    {
        Map<Pair<Vertex<VertexId>, Vertex<VertexId>>, Double> distances = new HashMap<>();

        for(Vertex<VertexId> v : graph.getVertices())
            for(Vertex<VertexId> u : graph.getVertices())
                distances.put(Pair.of(v, u), v.equals(u) ? 0.0 : Weighted.INFINITY);

        for(Edge<VertexId> e : graph.getEdges())
            distances.put(Pair.of(e.source, e.destination),
                          graph.getProperties().get(e).getWeight());

        for(Vertex<VertexId> w : graph.getVertices())
            for(Vertex<VertexId> v : graph.getVertices())
                for(Vertex<VertexId> u : graph.getVertices())
                    distances.put(Pair.of(v, u), Math.min(distances.get(Pair.of(v, u)),
                                                          distances.get(Pair.of(v, w))
                                                                  + distances.get(Pair.of(w, u))));

        return distances;
    }
}
