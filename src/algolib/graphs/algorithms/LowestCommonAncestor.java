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
    private Map<V, List<V>> paths = new HashMap<>();
    private LCAStrategy<V> strategy = new LCAStrategy<>();

    public LowestCommonAncestor(TreeGraph<V, VP, EP> graph, V root)
    {
        this.graph = graph;
        this.root = root;
        initialize();
    }

    /**
     * Finds a lowest common ancestor of two vertices in a rooted tree.
     * @param vertex1 first vertex
     * @param vertex2 second vertex
     * @return lowest common ancestor of given vertices
     */
    public V find(V vertex1, V vertex2)
    {
        if(isOffspring(vertex1, vertex2))
            return vertex2;

        if(isOffspring(vertex2, vertex1))
            return vertex1;

        List<V> candidates = new ArrayList<>(paths.get(vertex1));

        Collections.reverse(candidates);

        for(V candidate : candidates)
            if(!isOffspring(vertex2, candidate))
                return find(candidate, vertex2);

        return find(paths.get(vertex1).get(0), vertex2);
    }

    private void initialize()
    {
        Searching.dfsRecursive(graph, strategy, List.of(root));

        for(V vertex : graph.getVertices())
        {
            List<V> initialPath = new ArrayList<>();

            initialPath.add(strategy.parents.get(vertex));
            paths.put(vertex, initialPath);
        }

        for(int i = 0; i < Math.log(graph.getVerticesCount()) / Math.log(2) + 3; ++i)
            for(V vertex : graph.getVertices())
                paths.get(vertex).add(paths.get(paths.get(vertex).get(i)).get(i));
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
