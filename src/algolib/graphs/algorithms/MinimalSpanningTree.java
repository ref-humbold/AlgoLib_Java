// Algorithms for minimal spanning tree
package algolib.graphs.algorithms;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.structures.DisjointSets;
import algolib.tuples.Pair;

public final class MinimalSpanningTree
{
    /**
     * Kruskal algorithm counting the size of MST
     * @param graph an undirected weighted graph
     * @return size of the minimal spanning tree
     */
    public static <V, E extends Weighted> UndirectedGraph<V, E> kruskal(UndirectedGraph<V, E> graph)
    {
        UndirectedSimpleGraph<V, E> mst = new UndirectedSimpleGraph<>(graph);
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
                mst.addEdge(edge.source, edge.destination, edge.property);

            vertexSets.unionSet(edge.source, edge.destination);
        }

        return mst;
    }

    /**
     * Prim algorithm counting the size of MST
     * @param graph an undirected weighted graph
     * @param source starting vertex
     * @return size of the minimal spanning tree
     */
    public static <V, E extends Weighted> UndirectedGraph<V, E> prim(UndirectedGraph<V, E> graph,
                                                                     Vertex<V> source)
    {
        UndirectedSimpleGraph<V, E> mst = new UndirectedSimpleGraph<>(graph);
        Set<Vertex<V>> visited = new HashSet<>();
        PriorityQueue<Pair<Edge<E, V>, Vertex<V>>> queue = new PriorityQueue<>((pair1, pair2) -> {
            int compareWeights = Double.compare(pair1.first.property.getWeight(),
                                                pair2.first.property.getWeight());

            return compareWeights != 0 ? compareWeights : pair1.second.compareTo(pair2.second);
        });

        visited.add(source);

        for(Edge<E, V> adjacentEdges : graph.getAdjacentEdges(source))
        {
            Vertex<V> neighbour = adjacentEdges.getNeighbour(source);

            if(!visited.contains(neighbour))
                queue.add(Pair.of(adjacentEdges, neighbour));
        }

        while(!queue.isEmpty())
        {
            Edge<E, V> edge = queue.element().first;
            Vertex<V> vertex = queue.element().second;

            queue.remove();

            if(!visited.contains(vertex))
            {
                visited.add(vertex);
                mst.addEdge(edge.source, edge.destination, edge.property);

                for(Edge<E, V> adjacentEdges : graph.getAdjacentEdges(vertex))
                {
                    Vertex<V> neighbour = adjacentEdges.getNeighbour(vertex);

                    if(!visited.contains(neighbour))
                        queue.add(Pair.of(adjacentEdges, neighbour));
                }
            }
        }

        return mst;
    }
}
