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
    public static <V, VP, EP> List<Set<V>> findSCC(DirectedGraph<V, VP, EP> graph)
    {
        PostOrderStrategy<V> postOrderStrategy = new PostOrderStrategy<>();

        Searching.dfsRecursive(graph, postOrderStrategy, graph.getVertices());

        List<V> vertices = postOrderStrategy.postTimes.entrySet()
                                                      .stream()
                                                      .sorted(new ReversedPostOrderComparator<>())
                                                      .map(Map.Entry::getKey)
                                                      .collect(Collectors.toList());
        DirectedGraph<V, VP, EP> reversedCopy = graph.reversedCopy();
        SCCStrategy<V> sccStrategy = new SCCStrategy<>();

        Searching.dfsRecursive(reversedCopy, sccStrategy, vertices);

        return sccStrategy.components;
    }

    private static class ReversedPostOrderComparator<V>
            implements Comparator<Map.Entry<V, Integer>>
    {

        @Override
        public int compare(Map.Entry<V, Integer> entry1, Map.Entry<V, Integer> entry2)
        {
            return entry2.getValue().compareTo(entry1.getValue());
        }
    }

    private static class PostOrderStrategy<V>
            implements DFSStrategy<V>
    {
        final Map<V, Integer> postTimes = new HashMap<>();
        int timer = 0;

        @Override
        public void forRoot(V root)
        {
        }

        @Override
        public void onEnter(V vertex)
        {
        }

        @Override
        public void onNextVertex(V vertex, V neighbour)
        {
        }

        @Override
        public void onExit(V vertex)
        {
            postTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onEdgeToVisited(V vertex, V neighbour)
        {
        }
    }

    private static class SCCStrategy<V>
            implements DFSStrategy<V>
    {
        final List<Set<V>> components = new ArrayList<>();

        @Override
        public void forRoot(V root)
        {
            components.add(new HashSet<>());
        }

        @Override
        public void onEnter(V vertex)
        {
            components.get(components.size() - 1).add(vertex);
        }

        @Override
        public void onNextVertex(V vertex, V neighbour)
        {
        }

        @Override
        public void onExit(V vertex)
        {
        }

        @Override
        public void onEdgeToVisited(V vertex, V neighbour)
        {
        }
    }
}
