// Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algolib.graphs.TreeGraph;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class LowestCommonAncestor<V, VP, EP>
{
    public final TreeGraph<V, VP, EP> graph;
    public final V root;
    private final Map<V, List<V>> paths = new HashMap<>();
    private final LCAStrategy<V> strategy = new LCAStrategy<>();
    private boolean empty = true;

    public LowestCommonAncestor(TreeGraph<V, VP, EP> graph, V root)
    {
        this.graph = graph;
        this.root = root;
    }

    /**
     * Finds a lowest common ancestor of two vertices in a rooted tree.
     * @param vertex1 first vertex
     * @param vertex2 second vertex
     * @return lowest common ancestor of given vertices
     */
    public V find(V vertex1, V vertex2)
    {
        if(empty)
            initialize();

        return doFind(vertex1, vertex2);
    }

    private V doFind(V vertex1, V vertex2)
    {
        if(isOffspring(vertex1, vertex2))
            return vertex2;

        if(isOffspring(vertex2, vertex1))
            return vertex1;

        List<V> candidates = new ArrayList<>(paths.get(vertex1));

        Collections.reverse(candidates);

        V nextVertex = candidates.stream()
                                 .filter(candidate -> !isOffspring(vertex2, candidate))
                                 .findFirst()
                                 .orElse(paths.get(vertex1).get(0));

        return doFind(nextVertex, vertex2);
    }

    private void initialize()
    {
        Searching.dfsRecursive(graph, strategy, List.of(root));

        for(V vertex : graph.getVertices())
            paths.put(vertex, new ArrayList<>(List.of(strategy.parents.get(vertex))));

        for(int i = 0; i < Math.log(graph.getVerticesCount()) / Math.log(2) + 3; ++i)
            for(V vertex : graph.getVertices())
                paths.get(vertex).add(paths.get(paths.get(vertex).get(i)).get(i));

        empty = false;
    }

    private boolean isOffspring(V vertex1, V vertex2)
    {
        return strategy.preTimes.get(vertex1) >= strategy.preTimes.get(vertex2)
                && strategy.postTimes.get(vertex1) <= strategy.postTimes.get(vertex2);
    }

    private static class LCAStrategy<V>
            implements DFSStrategy<V>
    {
        final Map<V, V> parents = new HashMap<>();
        final Map<V, Integer> preTimes = new HashMap<>();
        final Map<V, Integer> postTimes = new HashMap<>();
        private int timer = 0;

        @Override
        public void forRoot(V root)
        {
            parents.put(root, root);
        }

        @Override
        public void onEntry(V vertex)
        {
            preTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onNextVertex(V vertex, V neighbour)
        {
            parents.put(neighbour, vertex);
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
}
