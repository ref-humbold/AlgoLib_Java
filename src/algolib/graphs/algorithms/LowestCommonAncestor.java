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
    public static <V, E> Vertex<V> findLCA(TreeGraph<V, E> graph, Vertex<V> vertex1,
                                           Vertex<V> vertex2, Vertex<V> root)
    {
        return new LCAFinder<>(graph).searchLCA(vertex1, vertex2, root);
    }

    private static class LCAFinder<V, E>
    {
        private TreeGraph<V, E> graph;
        private Map<Vertex<V>, List<Vertex<V>>> paths = new HashMap<>();
        private LCAStrategy<V> strategy = new LCAStrategy<>();

        public LCAFinder(TreeGraph<V, E> graph)
        {
            this.graph = graph;

            for(Vertex<V> vertex : graph.getVertices())
                paths.put(vertex, new ArrayList<>());
        }

        public Vertex<V> searchLCA(Vertex<V> vertex1, Vertex<V> vertex2, Vertex<V> root)
        {
            Searching.dfsRecursive(graph, strategy, List.of(root));

            for(Vertex<V> vertex : graph.getVertices())
                paths.get(vertex).add(strategy.getParent(vertex));

            for(int i = 0; i < Math.log(graph.getVerticesCount()) / Math.log(2) + 3; ++i)
                for(Vertex<V> vertex : graph.getVertices())
                    paths.get(vertex).add(paths.get(paths.get(vertex).get(i)).get(i));

            return search(vertex1, vertex2);
        }

        private Vertex<V> search(Vertex<V> vertex1, Vertex<V> vertex2)
        {
            if(isOffspring(vertex1, vertex2))
                return vertex2;

            if(isOffspring(vertex2, vertex1))
                return vertex1;

            List<Vertex<V>> candidates = new ArrayList<>(paths.get(vertex1));

            Collections.reverse(candidates);

            for(Vertex<V> candidate : candidates)
                if(!isOffspring(vertex2, candidate))
                    return search(candidate, vertex2);

            return search(paths.get(vertex1).get(0), vertex2);
        }

        private boolean isOffspring(Vertex<V> vertex1, Vertex<V> vertex2)
        {
            return strategy.getPreTime(vertex1) >= strategy.getPreTime(vertex2)
                    && strategy.getPostTime(vertex1) <= strategy.getPostTime(vertex2);
        }
    }

    private static class LCAStrategy<V>
            implements DFSStrategy<V>
    {
        private final Map<Vertex<V>, Vertex<V>> parents = new HashMap<>();
        private final Map<Vertex<V>, Integer> preTimes = new HashMap<>();
        private final Map<Vertex<V>, Integer> postTimes = new HashMap<>();
        private int timer = 0;

        @Override
        public void forRoot(Vertex<V> root)
        {
            parents.put(root, root);
        }

        @Override
        public void onEnter(Vertex<V> vertex)
        {
            preTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onNextVertex(Vertex<V> vertex, Vertex<V> neighbour)
        {
            parents.put(neighbour, vertex);
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

        Vertex<V> getParent(Vertex<V> vertex)
        {
            return parents.get(vertex);
        }

        int getPreTime(Vertex<V> vertex)
        {
            return preTimes.get(vertex);
        }

        int getPostTime(Vertex<V> vertex)
        {
            return postTimes.get(vertex);
        }
    }
}
