package algolib.graphs.algorithms;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.UndirectedSimpleGraph;
import algolib.graphs.Vertex;
import algolib.graphs.properties.Weighted;
import algolib.structures.DisjointSets;
import algolib.tuples.Pair;

/** Algorithms for minimal spanning tree. */
public final class MinimalSpanningTree
{
    /**
     * Computes minimal spanning tree of given undirected graph using Kruskal algorithm.
     * @param graph the undirected weighted graph
     * @return the minimal spanning tree
     */
    public static <VertexId, VertexProperty, EdgeProperty extends Weighted> UndirectedGraph<VertexId, VertexProperty, EdgeProperty> kruskal(
            UndirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
    {
        UndirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> mst =
                new UndirectedSimpleGraph<>(
                        graph.getVertices().stream().map(v -> v.id).collect(Collectors.toList()));
        DisjointSets<Vertex<VertexId>> vertexSets = new DisjointSets<>(graph.getVertices());
        PriorityQueue<Edge<VertexId>> edgeQueue = new PriorityQueue<>(
                (edge1, edge2) -> Double.compare(graph.getProperties().get(edge1).getWeight(),
                                                 graph.getProperties().get(edge2).getWeight()));

        edgeQueue.addAll(graph.getEdges());

        while(vertexSets.size() > 1 && !edgeQueue.isEmpty())
        {
            Edge<VertexId> edge = edgeQueue.remove();

            if(!vertexSets.isSameSet(edge.source, edge.destination))
                mst.addEdge(edge, graph.getProperties().get(edge));

            vertexSets.unionSet(edge.source, edge.destination);
        }

        return mst;
    }

    /**
     * Computes minimal spanning tree of given undirected graph using Prim algorithm.
     * @param graph the undirected weighted graph
     * @param source the starting vertex
     * @return the minimal spanning tree
     */
    public static <VertexId, VertexProperty, EdgeProperty extends Weighted> UndirectedGraph<VertexId, VertexProperty, EdgeProperty> prim(
            UndirectedGraph<VertexId, VertexProperty, EdgeProperty> graph, Vertex<VertexId> source)
    {
        UndirectedSimpleGraph<VertexId, VertexProperty, EdgeProperty> mst =
                new UndirectedSimpleGraph<>(
                        graph.getVertices().stream().map(v -> v.id).collect(Collectors.toList()));
        Set<Vertex<VertexId>> visited = new HashSet<>();
        PriorityQueue<Pair<Edge<VertexId>, Vertex<VertexId>>> queue = new PriorityQueue<>(
                (pair1, pair2) -> Double.compare(graph.getProperties().get(pair1.first).getWeight(),
                                                 graph.getProperties()
                                                      .get(pair2.first)
                                                      .getWeight()));

        visited.add(source);

        for(Edge<VertexId> adjacentEdge : graph.getAdjacentEdges(source))
        {
            Vertex<VertexId> neighbour = adjacentEdge.getNeighbour(source);

            if(neighbour != source)
                queue.add(Pair.of(adjacentEdge, neighbour));
        }

        while(!queue.isEmpty())
        {
            Edge<VertexId> edge = queue.element().first;
            Vertex<VertexId> vertex = queue.element().second;

            queue.remove();

            if(!visited.contains(vertex))
            {
                visited.add(vertex);
                mst.addEdge(edge, graph.getProperties().get(edge));

                for(Edge<VertexId> adjacentEdge : graph.getAdjacentEdges(vertex))
                {
                    Vertex<VertexId> neighbour = adjacentEdge.getNeighbour(vertex);

                    if(!visited.contains(neighbour))
                        queue.add(Pair.of(adjacentEdge, neighbour));
                }
            }
        }

        return mst;
    }
}
