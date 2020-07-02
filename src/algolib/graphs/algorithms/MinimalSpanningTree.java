// Algorithms for minimal spanning tree
package algolib.graphs.algorithms;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.properties.Weighted;
import algolib.structures.DisjointSets;
import algolib.tuples.Pair;

public final class MinimalSpanningTree
{
    /**
     * Kruskal algorithm.
     * @param graph an undirected weighted graph
     * @return the minimal spanning tree
     */
    public static <V, VP, EP extends Weighted> UndirectedGraph<V, VP, EP> kruskal(
            UndirectedGraph<V, VP, EP> graph)
    {
        UndirectedSimpleGraph<V, VP, EP> mst = new UndirectedSimpleGraph<>(graph.getVertices());
        DisjointSets<V> vertexSets = new DisjointSets<>(graph.getVertices());
        PriorityQueue<Edge<V>> edgeQueue = new PriorityQueue<>(
                (edge1, edge2) -> Double.compare(graph.getProperty(edge1).getWeight(),
                                                 graph.getProperty(edge2).getWeight()));

        edgeQueue.addAll(graph.getEdges());

        while(vertexSets.size() > 1 && !edgeQueue.isEmpty())
        {
            Edge<V> edge = edgeQueue.remove();

            if(!vertexSets.isSameSet(edge.source, edge.destination))
                mst.addEdge(edge, graph.getProperty(edge));

            vertexSets.unionSet(edge.source, edge.destination);
        }

        return mst;
    }

    /**
     * Prim algorithm.
     * @param graph an undirected weighted graph
     * @param source starting vertex
     * @return the minimal spanning tree
     */
    public static <V, VP, EP extends Weighted> UndirectedGraph<V, VP, EP> prim(
            UndirectedGraph<V, VP, EP> graph, V source)
    {
        UndirectedSimpleGraph<V, VP, EP> mst = new UndirectedSimpleGraph<>(graph.getVertices());
        Set<V> visited = new HashSet<>();
        PriorityQueue<Pair<Edge<V>, V>> queue = new PriorityQueue<>(
                (pair1, pair2) -> Double.compare(graph.getProperty(pair1.first).getWeight(),
                                                 graph.getProperty(pair2.first).getWeight()));

        visited.add(source);

        for(Edge<V> adjacentEdge : graph.getAdjacentEdges(source))
        {
            V neighbour = adjacentEdge.getNeighbour(source);

            if(neighbour != source)
                queue.add(Pair.of(adjacentEdge, neighbour));
        }

        while(!queue.isEmpty())
        {
            Edge<V> edge = queue.element().first;
            V vertex = queue.element().second;

            queue.remove();

            if(!visited.contains(vertex))
            {
                visited.add(vertex);
                mst.addEdge(edge, graph.getProperty(edge));

                for(Edge<V> adjacentEdge : graph.getAdjacentEdges(vertex))
                {
                    V neighbour = adjacentEdge.getNeighbour(vertex);

                    if(!visited.contains(neighbour))
                        queue.add(Pair.of(adjacentEdge, neighbour));
                }
            }
        }

        return mst;
    }
}
