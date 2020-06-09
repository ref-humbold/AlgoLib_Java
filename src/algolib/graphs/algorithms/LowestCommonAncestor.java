// Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algolib.graphs.TreeGraph;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class LowestCommonAncestor
{
    /**
     * Finds a lowest common ancestor of two vertices in a rooted tree.
     * @param graph a tree graph
     * @param vertex1 first vertex
     * @param vertex2 second vertex
     * @param root a root of given tree
     * @return lowest common ancestor of given vertices
     */
    public static <V, VP, EP> V findLCA(TreeGraph<V, VP, EP> graph, V vertex1, V vertex2, V root)
    {
        return new LCAFinder<>(graph).searchLCA(vertex1, vertex2, root);
    }

    private static class LCAFinder<V, VP, EP>
    {
        private TreeGraph<V, VP, EP> graph;
        private Map<V, List<V>> paths = new HashMap<>();
        private LCAStrategy<V> strategy = new LCAStrategy<>();

        public LCAFinder(TreeGraph<V, VP, EP> graph)
        {
            this.graph = graph;

            for(V vertex : graph.getVertices())
                paths.put(vertex, new ArrayList<>());
        }

        public V searchLCA(V vertex1, V vertex2, V root)
        {
            Searching.dfsRecursive(graph, strategy, List.of(root));

            for(V vertex : graph.getVertices())
                paths.get(vertex).add(strategy.getParent(vertex));

            for(int i = 0; i < Math.log(graph.getVerticesCount()) / Math.log(2) + 3; ++i)
                for(V vertex : graph.getVertices())
                    paths.get(vertex).add(paths.get(paths.get(vertex).get(i)).get(i));

            return search(vertex1, vertex2);
        }

        private V search(V vertex1, V vertex2)
        {
            if(isOffspring(vertex1, vertex2))
                return vertex2;

            if(isOffspring(vertex2, vertex1))
                return vertex1;

            List<V> candidates = new ArrayList<>(paths.get(vertex1));

            Collections.reverse(candidates);

            for(V candidate : candidates)
                if(!isOffspring(vertex2, candidate))
                    return search(candidate, vertex2);

            return search(paths.get(vertex1).get(0), vertex2);
        }

        private boolean isOffspring(V vertex1, V vertex2)
        {
            return strategy.getPreTime(vertex1) >= strategy.getPreTime(vertex2)
                    && strategy.getPostTime(vertex1) <= strategy.getPostTime(vertex2);
        }
    }

    private static class LCAStrategy<V>
            implements DFSStrategy<V>
    {
        private final Map<V, V> parents = new HashMap<>();
        private final Map<V, Integer> preTimes = new HashMap<>();
        private final Map<V, Integer> postTimes = new HashMap<>();
        private int timer = 0;

        @Override
        public void forRoot(V root)
        {
            parents.put(root, root);
        }

        @Override
        public void onEnter(V vertex)
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

        V getParent(V vertex)
        {
            return parents.get(vertex);
        }

        int getPreTime(V vertex)
        {
            return preTimes.get(vertex);
        }

        int getPostTime(V vertex)
        {
            return postTimes.get(vertex);
        }
    }
}
