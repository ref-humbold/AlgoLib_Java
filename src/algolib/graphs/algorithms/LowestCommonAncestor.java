// Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algolib.graphs.Graph;
import algolib.graphs.TreeGraph;

public final class LowestCommonAncestor
{
    /**
     * Wyznaczanie najniższego wspólnego przodka
     * @param treegraph graf drzewo
     * @param vertex1 wierzchołek 1
     * @param vertex2 wierzchołek 2
     * @return najniższy wspólny przodek
     */
    public static Integer findLCA(TreeGraph treegraph, int vertex1, int vertex2)
    {
        return LowestCommonAncestor.findLCA(treegraph, vertex1, vertex2, 0);
    }

    /**
     * Wyznaczanie najniższego wspólnego przodka
     * @param treegraph graf drzewo
     * @param vertex1 wierzchołek 1
     * @param vertex2 wierzchołek 2
     * @param root korzeń drzewa
     * @return najniższy wspólny przodek
     */
    public static Integer findLCA(TreeGraph treegraph, int vertex1, int vertex2, int root)
    {
        return new LCAFinder(treegraph).searchLCA(vertex1, vertex2, root);
    }

    private static class LCAFinder
    {
        /** Reprezentacja drzewa. */
        private TreeGraph graph;

        /** Skompresowane ścieżki do korzenia drzewa. */
        private List<List<Integer>> paths;

        /** Strategia przeszukiwania DFS. */
        private LCAStrategy strategy;

        public LCAFinder(TreeGraph treegraph)
        {
            graph = treegraph;
            paths = new ArrayList<>();
            strategy = new LCAStrategy(treegraph);

            for(int i = 0; i < graph.getVerticesNumber(); ++i)
                paths.add(new ArrayList<>());
        }

        /**
         * Wyszukiwanie najniższego wspólnego przodka
         * @param vertex1 wierzchołek 1
         * @param vertex2 wierzchołek 2
         * @param root korzeń drzewa
         * @return najniższy wspólny przodek
         */
        public int searchLCA(int vertex1, int vertex2, int root)
        {
            Searching.dfsr(graph, strategy, root);

            for(Integer v : graph.getVertices())
                paths.get(v).add(strategy.getParent(v));

            for(int i = 0; i < Math.log(graph.getVerticesNumber()) / Math.log(2) + 3; ++i)
                for(Integer v : graph.getVertices())
                    paths.get(v).add(paths.get(paths.get(v).get(i)).get(i));

            return search(vertex1, vertex2);
        }

        /**
         * Wyszukiwanie najniższego wspólnego przodka
         * @param vertex1 wierzchołek 1
         * @param vertex2 wierzchołek 2
         * @return najniższy wspólny przodek
         */
        private int search(int vertex1, int vertex2)
        {
            if(isOffspring(vertex1, vertex2))
                return vertex2;

            if(isOffspring(vertex2, vertex1))
                return vertex1;

            List<Integer> candidates = new ArrayList<>(paths.get(vertex1));

            Collections.reverse(candidates);

            for(Integer candidate : candidates)
                if(!isOffspring(vertex2, candidate))
                    return search(candidate, vertex2);

            return search(paths.get(vertex1).get(0), vertex2);
        }

        /**
         * Sprawdza, czy wierzchołki są potomkami
         * @param vertex1 wierzchołek 1
         * @param vertex2 wierzchołek 2
         * @return czy wierzchołek 1 jest potomkiem wierzchołka 2
         */
        private boolean isOffspring(int vertex1, int vertex2)
        {
            return strategy.getPreTime(vertex1) >= strategy.getPreTime(vertex2)
                    && strategy.getPostTime(vertex1) <= strategy.getPostTime(vertex2);
        }
    }

    private static class LCAStrategy
            extends TimerStrategy
    {
        private List<Integer> parents;

        public LCAStrategy(Graph graph)
        {
            super(graph);
            parents = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), null));

            for(Integer v : graph.getVertices())
                parents.set(v, v);
        }

        @Override
        public void forNeighbour(int vertex, int neighbour)
        {
            super.forNeighbour(vertex, neighbour);
            parents.set(neighbour, vertex);
        }

        public Integer getParent(int vertex)
        {
            return parents.get(vertex);
        }
    }
}
