// Algorithms for minimal spanning tree
package algolib.graphs.algorithms;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.structures.DisjointSets;
import algolib.tuples.ComparablePair;

public final class MinimalSpanningTree
{
    /**
     * Kruskal algorithm counting the size of MST
     * @param graph an undirected weighted graph
     * @return size of the minimal spanning tree
     */
    public static <V, E extends Weighted> double kruskal(UndirectedGraph<V, E> graph)
    {
        double sizeMST = 0.0;
        DisjointSets<Vertex<V>> vertexSets = new DisjointSets<>(graph.getVertices());
        PriorityQueue<Edge<E, V>> edgeQueue = new PriorityQueue<>((edge1, edge2) -> {
            int compareWeights =
                    Double.compare(edge1.property.getWeight(), edge2.property.getWeight());

            return compareWeights != 0 ? compareWeights : edge1.compareTo(edge2);
        });

        for(Vertex<V> vertex : graph.getVertices())
            edgeQueue.addAll(graph.getAdjacentEdges(vertex));

        while(vertexSets.size() > 1 && !edgeQueue.isEmpty())
        {
            Edge<E, V> edge = edgeQueue.remove();

            if(!vertexSets.isSameSet(edge.source, edge.destination))
                sizeMST += edge.property.getWeight();

            vertexSets.unionSet(edge.source, edge.destination);
        }

        return sizeMST;
    }

    /**
     * Prim algorithm counting the size of MST
     * @param graph an undirected weighted graph
     * @param source starting vertex
     * @return size of the minimal spanning tree
     */
    public static <V, E extends Weighted> double prim(UndirectedGraph<V, E> graph, Vertex<V> source)
    {
        double sizeMST = 0.0;
        PriorityQueue<ComparablePair<Double, Vertex<V>>> vertexQueue = new PriorityQueue<>();
        Set<Vertex<V>> visited = new HashSet<>();

        vertexQueue.add(ComparablePair.of(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Double edgeWeight = vertexQueue.element().first;
            Vertex<V> vertex = vertexQueue.element().second;

            vertexQueue.remove();

            if(!visited.contains(vertex))
            {
                visited.add(vertex);
                sizeMST += edgeWeight;

                for(Edge<E, V> edge : graph.getAdjacentEdges(vertex))
                {
                    Vertex<V> neighbour = edge.getNeighbour(vertex);

                    if(!visited.contains(neighbour))
                        vertexQueue.add(ComparablePair.of(edge.property.getWeight(), neighbour));
                }
            }
        }

        return sizeMST;
    }
}
