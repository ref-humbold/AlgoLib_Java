package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DfsStrategy;

/** Algorithms for graph cutting (edge cut and vertex cut) */
public final class Cutting
{
    /**
     * Finds edge cut of given graph.
     * @param graph the undirected graph
     * @return the edges in the edge cut
     */
    public static <VertexId, VertexProperty, EdgeProperty> Collection<Edge<VertexId>> findEdgeCut(
            UndirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
    {
        CuttingStrategy<VertexId> strategy = new CuttingStrategy<>();

        Searching.dfsRecursive(graph, strategy, graph.getVertices());
        return graph.getVertices()
                    .stream()
                    .filter(strategy::hasBridge)
                    .map(vertex -> graph.getEdge(vertex, strategy.dfsParents.get(vertex)))
                    .collect(Collectors.toList());
    }

    /**
     * Finds vertex cut of given graph.
     * @param graph the undirected graph
     * @return the vertices in the vertex cut
     */
    public static <VertexId, VertexProperty, EdgeProperty> Collection<Vertex<VertexId>> findVertexCut(
            UndirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
    {
        CuttingStrategy<VertexId> strategy = new CuttingStrategy<>();

        Searching.dfsRecursive(graph, strategy, graph.getVertices());
        return graph.getVertices()
                    .stream()
                    .filter(strategy::isSeparator)
                    .collect(Collectors.toList());
    }

    private static class CuttingStrategy<VertexId>
            implements DfsStrategy<VertexId>
    {
        final Map<Vertex<VertexId>, Vertex<VertexId>> dfsParents = new HashMap<>();
        final Map<Vertex<VertexId>, List<Vertex<VertexId>>> dfsChildren = new HashMap<>();
        final Map<Vertex<VertexId>, Integer> dfsDepths = new HashMap<>();
        final Map<Vertex<VertexId>, Integer> lowValues = new HashMap<>();
        int depth = 0;

        @Override
        public void forRoot(Vertex<VertexId> root)
        {
        }

        @Override
        public void onEntry(Vertex<VertexId> vertex)
        {
            dfsDepths.put(vertex, depth);
            lowValues.put(vertex, depth);
            dfsChildren.putIfAbsent(vertex, new ArrayList<>());
            ++depth;
        }

        @Override
        public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
            dfsParents.put(neighbour, vertex);
            dfsChildren.get(vertex).add(neighbour);
        }

        @Override
        public void onExit(Vertex<VertexId> vertex)
        {
            int minimalLowValue = dfsChildren.get(vertex)
                                             .stream()
                                             .mapToInt(child -> lowValues.get(child))
                                             .min()
                                             .orElse(Integer.MAX_VALUE);

            lowValues.put(vertex, Math.min(lowValues.get(vertex), minimalLowValue));
            --depth;
        }

        @Override
        public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
            if(!neighbour.equals(dfsParents.get(vertex)))
                lowValues.put(vertex, Math.min(lowValues.get(vertex), dfsDepths.get(neighbour)));
        }

        boolean hasBridge(Vertex<VertexId> vertex)
        {
            return !isDFSRoot(vertex) && lowValues.get(vertex).equals(dfsDepths.get(vertex));
        }

        boolean isSeparator(Vertex<VertexId> vertex)
        {
            if(isDFSRoot(vertex))
                return dfsChildren.get(vertex).size() > 1;

            return dfsChildren.get(vertex)
                              .stream()
                              .anyMatch(child -> lowValues.get(child) >= dfsDepths.get(vertex));
        }

        boolean isDFSRoot(Vertex<VertexId> vertex)
        {
            return dfsDepths.get(vertex) == 0;
        }
    }
}
