// NAJNIŻSZY WSPÓLNY PRZODEK DWÓCH WIERZCHOŁKÓW W DRZEWIE
package refhumbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import refhumbold.algolib.tuples.ImmutablePair;

public class LCA
{
    /**
     * Wyznaczanie najniższego wspólnego przodka.
     * @param treegraph graf drzewo
     * @param vertex1 wierzchołek 1
     * @param vertex2 wierzchołek 2
     * @return najniższy wspólny przodek
     */
    public static Integer findLCA(TreeGraph treegraph, int vertex1, int vertex2)
    {
        return LCA.findLCA(treegraph, vertex1, vertex2, 0);
    }

    /**
     * Wyznaczanie najniższego wspólnego przodka.
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

        /** Czas wejścia i wyjścia dla wierzchołka. */
        private List<ImmutablePair<Integer, Integer>> prePostTimes;

        public LCAFinder(TreeGraph treegraph)
        {
            graph = treegraph;
            paths = new ArrayList<>();
            prePostTimes = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), null));

            for(int i = 0; i < graph.getVerticesNumber(); ++i)
                paths.add(new ArrayList<>());
        }

        /**
         * Wyszukiwanie najniższego wspólnego przodka.
         * @param vertex1 wierzchołek 1
         * @param vertex2 wierzchołek 2
         * @param root korzeń drzewa
         * @return najniższy wspólny przodek
         */
        public int searchLCA(int vertex1, int vertex2, int root)
        {
            dfs(root, root, 0);

            for(int i = 0; i < Math.log(graph.getVerticesNumber()) / Math.log(2) + 3; ++i)
                for(Integer v : graph.getVertices())
                    if(!paths.get(v).isEmpty())
                        paths.get(v).add(paths.get(paths.get(v).get(i)).get(i));

            return search(vertex1, vertex2);
        }

        /**
         * Wyszukiwanie najniższego wspólnego przodka.
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
         * Algorytm dfs z licznikiem czasu wyznaczający kolejne wierzchołki na ścieżce do korzenia.
         * @param vertex aktualny wierzchołek
         * @param parent ojciec wierzchołka
         * @param timer aktualny czasu
         * @return nowy czas po przetworzeniu wierzchołka
         */
        private int dfs(int vertex, int parent, int timer)
        {
            int preTime = timer;

            prePostTimes.set(vertex, ImmutablePair.make(null, null));
            paths.get(vertex).add(parent);
            ++timer;

            for(Integer neighbour : graph.getNeighbours(vertex))
                if(prePostTimes.get(neighbour) == null)
                    timer = dfs(neighbour, vertex, timer);

            prePostTimes.set(vertex, ImmutablePair.make(preTime, timer));

            return timer + 1;
        }

        /**
         * Sprawdza, czy wierzchołki są potomkami.
         * @param vertex1 wierzchołek 1
         * @param vertex2 wierzchołek 2
         * @return czy wierzchołek 1 jest potomkiem wierzchołka 2
         */
        private boolean isOffspring(int vertex1, int vertex2)
        {
            return prePostTimes.get(vertex1).getFirst() >= prePostTimes.get(vertex2).getFirst()
                    && prePostTimes.get(vertex1).getSecond() <= prePostTimes.get(vertex2)
                                                                            .getSecond();
        }
    }
}
