// Algorithms for graph cutting (edge cut and vertex cut)
package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import algolib.graphs.Edge;
import algolib.graphs.UndirectedGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class Cutting
{
    /**
     * Finds an edge cut of given graph.
     * @param graph an undirected graph
     * @return list of edges in the edge cut
     */
    public static <V, E> List<Edge<E, V>> findEdgeCut(UndirectedGraph<V, E> graph)
    {
        return new GraphCutting<>(graph).edgeCut();
    }

    /**
     * Finds a vertex cut of given graph.
     * @param graph an undirected graph
     * @return list of vertices in the vertex cut
     */
    public static <V, E> List<Vertex<V>> findVertexCut(UndirectedGraph<V, E> graph)
    {
        return new GraphCutting<>(graph).vertexCut();
    }

    private static class CuttingStrategy<V>
            implements DFSStrategy<V>
    {
        private final Map<Vertex<V>, Vertex<V>> dfsParents = new HashMap<>();
        private final Map<Vertex<V>, List<Vertex<V>>> dfsChildren = new HashMap<>();
        private final Map<Vertex<V>, Integer> dfsDepths = new HashMap<>();
        private final Map<Vertex<V>, Integer> lowValues = new HashMap<>();
        private int depth = 0;

        @Override
        public void forRoot(Vertex<V> root)
        {
        }

        @Override
        public void onEnter(Vertex<V> vertex)
        {
            dfsDepths.put(vertex, depth);
            lowValues.put(vertex, depth);
            dfsChildren.putIfAbsent(vertex, new ArrayList<>());
            ++depth;
        }

        @Override
        public void onNextVertex(Vertex<V> vertex, Vertex<V> neighbour)
        {
            dfsParents.put(neighbour, vertex);
            dfsChildren.get(vertex).add(neighbour);
        }

        @Override
        public void onExit(Vertex<V> vertex)
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
        public void onEdgeToVisited(Vertex<V> vertex, Vertex<V> neighbour)
        {
            if(!neighbour.equals(dfsParents.get(vertex)))
                lowValues.put(vertex, Math.min(lowValues.get(vertex), dfsDepths.get(neighbour)));
        }
    }

    private static class GraphCutting<V, E>
    {
        private final UndirectedGraph<V, E> graph;

        GraphCutting(UndirectedGraph<V, E> graph)
        {
            this.graph = graph;
        }

        List<Edge<E, V>> edgeCut()
        {
            CuttingStrategy<V> strategy = new CuttingStrategy<>();

            Searching.dfsRecursive(graph, strategy, graph.getVertices());
            return graph.getVertices()
                        .stream()
                        .filter(vertex -> hasBridge(vertex, strategy))
                        .map(vertex -> graph.getEdge(vertex, strategy.dfsParents.get(vertex)))
                        .collect(Collectors.toList());
        }

        List<Vertex<V>> vertexCut()
        {
            CuttingStrategy<V> strategy = new CuttingStrategy<>();

            Searching.dfsRecursive(graph, strategy, graph.getVertices());
            return graph.getVertices()
                        .stream()
                        .filter(vertex -> isSeparator(vertex, strategy))
                        .collect(Collectors.toList());
        }

        private boolean hasBridge(Vertex<V> vertex, CuttingStrategy<V> strategy)
        {
            return !isDFSRoot(vertex, strategy) && strategy.lowValues.get(vertex)
                                                                     .equals(strategy.dfsDepths.get(
                                                                             vertex));
        }

        private boolean isSeparator(Vertex<V> vertex, CuttingStrategy<V> strategy)
        {
            if(isDFSRoot(vertex, strategy))
                return strategy.dfsChildren.get(vertex).size() > 1;

            return strategy.dfsChildren.get(vertex)
                                       .stream()
                                       .anyMatch(child -> strategy.lowValues.get(child)
                                               >= strategy.dfsDepths.get(vertex));
        }

        private boolean isDFSRoot(Vertex<V> vertex, CuttingStrategy<V> strategy)
        {
            return strategy.dfsDepths.get(vertex) == 0;
        }
    }
}
