package algolib.graphs.algorithms;

import java.util.*;
import java.util.stream.Collectors;

import algolib.graphs.DirectedGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DFSStrategy;

/** Algorithm for strongly connected components */
public final class StronglyConnectedComponents
{
    /**
     * Finds strongly connected components in given directed graph.
     * @param graph the directed graph
     * @return list of vertices in strongly connected components
     */
    public static <VertexId, VertexProperty, EdgeProperty> List<Set<Vertex<VertexId>>> findSCC(
            DirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
    {
        PostOrderStrategy<VertexId> postOrderStrategy = new PostOrderStrategy<>();

        Searching.dfsRecursive(graph, postOrderStrategy, graph.getVertices());

        List<Vertex<VertexId>> vertices = postOrderStrategy.postTimes.entrySet()
                                                                     .stream()
                                                                     .sorted(new ReversedPostOrderComparator<>())
                                                                     .map(Map.Entry::getKey)
                                                                     .collect(Collectors.toList());
        DirectedGraph<VertexId, VertexProperty, EdgeProperty> reversedGraph = graph.reversedCopy();
        SCCStrategy<VertexId> sccStrategy = new SCCStrategy<>();

        Searching.dfsRecursive(reversedGraph, sccStrategy, vertices);

        return sccStrategy.components;
    }

    private static class ReversedPostOrderComparator<VertexId>
            implements Comparator<Map.Entry<VertexId, Integer>>
    {

        @Override
        public int compare(Map.Entry<VertexId, Integer> entry1, Map.Entry<VertexId, Integer> entry2)
        {
            return entry2.getValue().compareTo(entry1.getValue());
        }
    }

    private static class PostOrderStrategy<VertexId>
            implements DFSStrategy<VertexId>
    {
        final Map<Vertex<VertexId>, Integer> postTimes = new HashMap<>();
        int timer = 0;

        @Override
        public void forRoot(Vertex<VertexId> root)
        {
        }

        @Override
        public void onEntry(Vertex<VertexId> vertex)
        {
        }

        @Override
        public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }

        @Override
        public void onExit(Vertex<VertexId> vertex)
        {
            postTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }
    }

    private static class SCCStrategy<VertexId>
            implements DFSStrategy<VertexId>
    {
        final List<Set<Vertex<VertexId>>> components = new ArrayList<>();

        @Override
        public void forRoot(Vertex<VertexId> root)
        {
            components.add(new HashSet<>());
        }

        @Override
        public void onEntry(Vertex<VertexId> vertex)
        {
            components.get(components.size() - 1).add(vertex);
        }

        @Override
        public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }

        @Override
        public void onExit(Vertex<VertexId> vertex)
        {
        }

        @Override
        public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }
    }
}
