// Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class Cutting
{
    /**
     * Finds an edge cut of given graph.
     * @param graph an undirected graph
     * @return collection of edges in the edge cut
     */
    public static <V, VP, EP> Collection<Edge<V>> findEdgeCut(UndirectedGraph<V, VP, EP> graph)
    {
        CuttingStrategy<V> strategy = new CuttingStrategy<>();

        Searching.dfsRecursive(graph, strategy, graph.getVertices());
        return graph.getVertices()
                    .stream()
                    .filter(vertex -> strategy.hasBridge(vertex))
                    .map(vertex -> graph.getEdge(vertex, strategy.dfsParents.get(vertex)))
                    .collect(Collectors.toList());
    }

    /**
     * Finds a vertex cut of given graph.
     * @param graph an undirected graph
     * @return collection of vertices in the vertex cut
     */
    public static <V, VP, EP> Collection<V> findVertexCut(UndirectedGraph<V, VP, EP> graph)
    {
        CuttingStrategy<V> strategy = new CuttingStrategy<>();

        Searching.dfsRecursive(graph, strategy, graph.getVertices());
        return graph.getVertices()
                    .stream()
                    .filter(vertex -> strategy.isSeparator(vertex))
                    .collect(Collectors.toList());
    }

    private static class CuttingStrategy<V>
            implements DFSStrategy<V>
    {
        final Map<V, V> dfsParents = new HashMap<>();
        final Map<V, List<V>> dfsChildren = new HashMap<>();
        final Map<V, Integer> dfsDepths = new HashMap<>();
        final Map<V, Integer> lowValues = new HashMap<>();
        int depth = 0;

        @Override
        public void forRoot(V root)
        {
        }

        @Override
        public void onEntry(V vertex)
        {
            dfsDepths.put(vertex, depth);
            lowValues.put(vertex, depth);
            dfsChildren.putIfAbsent(vertex, new ArrayList<>());
            ++depth;
        }

        @Override
        public void onNextVertex(V vertex, V neighbour)
        {
            dfsParents.put(neighbour, vertex);
            dfsChildren.get(vertex).add(neighbour);
        }

        @Override
        public void onExit(V vertex)
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
        public void onEdgeToVisited(V vertex, V neighbour)
        {
            if(!neighbour.equals(dfsParents.get(vertex)))
                lowValues.put(vertex, Math.min(lowValues.get(vertex), dfsDepths.get(neighbour)));
        }

        boolean hasBridge(V vertex)
        {
            return !isDFSRoot(vertex) && lowValues.get(vertex).equals(dfsDepths.get(vertex));
        }

        boolean isSeparator(V vertex)
        {
            if(isDFSRoot(vertex))
                return dfsChildren.get(vertex).size() > 1;

            return dfsChildren.get(vertex)
                              .stream()
                              .anyMatch(child -> lowValues.get(child) >= dfsDepths.get(vertex));
        }

        boolean isDFSRoot(V vertex)
        {
            return dfsDepths.get(vertex) == 0;
        }
    }
}
