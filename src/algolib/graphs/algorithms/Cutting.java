// Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithms;

import java.util.ArrayList;
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
     * @return list of edges in the edge cut
     */
    public static <V, VP, EP> List<Edge<V>> findEdgeCut(UndirectedGraph<V, VP, EP> graph)
    {
        return new GraphCutting<>(graph).edgeCut();
    }

    /**
     * Finds a vertex cut of given graph.
     * @param graph an undirected graph
     * @return list of vertices in the vertex cut
     */
    public static <V, VP, EP> List<V> findVertexCut(UndirectedGraph<V, VP, EP> graph)
    {
        return new GraphCutting<>(graph).vertexCut();
    }

    private static class CuttingStrategy<V>
            implements DFSStrategy<V>
    {
        private final Map<V, V> dfsParents = new HashMap<>();
        private final Map<V, List<V>> dfsChildren = new HashMap<>();
        private final Map<V, Integer> dfsDepths = new HashMap<>();
        private final Map<V, Integer> lowValues = new HashMap<>();
        private int depth = 0;

        @Override
        public void forRoot(V root)
        {
        }

        @Override
        public void onEnter(V vertex)
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
    }

    private static class GraphCutting<V, VP, EP>
    {
        private final UndirectedGraph<V, VP, EP> graph;

        GraphCutting(UndirectedGraph<V, VP, EP> graph)
        {
            this.graph = graph;
        }

        List<Edge<V>> edgeCut()
        {
            CuttingStrategy<V> strategy = new CuttingStrategy<>();

            Searching.dfsRecursive(graph, strategy, graph.getVertices());
            return graph.getVertices()
                        .stream()
                        .filter(vertex -> hasBridge(vertex, strategy))
                        .map(vertex -> graph.getEdge(vertex, strategy.dfsParents.get(vertex)))
                        .collect(Collectors.toList());
        }

        List<V> vertexCut()
        {
            CuttingStrategy<V> strategy = new CuttingStrategy<>();

            Searching.dfsRecursive(graph, strategy, graph.getVertices());
            return graph.getVertices()
                        .stream()
                        .filter(vertex -> isSeparator(vertex, strategy))
                        .collect(Collectors.toList());
        }

        private boolean hasBridge(V vertex, CuttingStrategy<V> strategy)
        {
            return !isDFSRoot(vertex, strategy) && strategy.lowValues.get(vertex)
                                                                     .equals(strategy.dfsDepths.get(
                                                                             vertex));
        }

        private boolean isSeparator(V vertex, CuttingStrategy<V> strategy)
        {
            if(isDFSRoot(vertex, strategy))
                return strategy.dfsChildren.get(vertex).size() > 1;

            return strategy.dfsChildren.get(vertex)
                                       .stream()
                                       .anyMatch(child -> strategy.lowValues.get(child)
                                               >= strategy.dfsDepths.get(vertex));
        }

        private boolean isDFSRoot(V vertex, CuttingStrategy<V> strategy)
        {
            return strategy.dfsDepths.get(vertex) == 0;
        }
    }
}
