// Algorithms for strongly connected components
package algolib.graphs.algorithms;

import java.util.*;
import java.util.stream.Collectors;

import algolib.graphs.DirectedGraph;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class StronglyConnectedComponents
{
    /**
     * Finds strongly connected components in given directed graph.
     * @param graph a directed graph
     * @return list of vertices in strongly connected components
     */
    public static <V, E> List<Set<Vertex<V>>> findSCC(DirectedGraph<V, E> graph)
    {
        PostOrderStrategy<V> postOrderStrategy = new PostOrderStrategy<>();

        Searching.dfsRecursive(graph, postOrderStrategy, graph.getVertices());

        List<Vertex<V>> vertices = postOrderStrategy.postTimes.entrySet()
                                                              .stream()
                                                              .sorted(new ReversedPostOrderComparator<>())
                                                              .map(Map.Entry::getKey)
                                                              .collect(Collectors.toList());
        DirectedGraph<V, E> reversedCopy = graph.reversedCopy();
        SCCStrategy<V> sccStrategy = new SCCStrategy<>();

        Searching.dfsRecursive(reversedCopy, sccStrategy, vertices);

        return sccStrategy.components;
    }

    private static class ReversedPostOrderComparator<V>
            implements Comparator<Map.Entry<Vertex<V>, Integer>>
    {

        @Override
        public int compare(Map.Entry<Vertex<V>, Integer> entry1,
                           Map.Entry<Vertex<V>, Integer> entry2)
        {
            return entry2.getValue().compareTo(entry1.getValue());
        }
    }

    private static class PostOrderStrategy<V>
            implements DFSStrategy<V>
    {
        final Map<Vertex<V>, Integer> postTimes = new HashMap<>();
        int timer = 0;

        @Override
        public void forRoot(Vertex<V> root)
        {
        }

        @Override
        public void onEnter(Vertex<V> vertex)
        {
        }

        @Override
        public void onNextVertex(Vertex<V> vertex, Vertex<V> neighbour)
        {
        }

        @Override
        public void onExit(Vertex<V> vertex)
        {
            postTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onEdgeToVisited(Vertex<V> vertex, Vertex<V> neighbour)
        {
        }
    }

    private static class SCCStrategy<V>
            implements DFSStrategy<V>
    {
        final List<Set<Vertex<V>>> components = new ArrayList<>();

        @Override
        public void forRoot(Vertex<V> root)
        {
            components.add(new HashSet<>());
        }

        @Override
        public void onEnter(Vertex<V> vertex)
        {
            components.get(components.size() - 1).add(vertex);
        }

        @Override
        public void onNextVertex(Vertex<V> vertex, Vertex<V> neighbour)
        {
        }

        @Override
        public void onExit(Vertex<V> vertex)
        {
        }

        @Override
        public void onEdgeToVisited(Vertex<V> vertex, Vertex<V> neighbour)
        {
        }
    }
}
