// Algorithms for strongly connected components
package algolib.old.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algolib.old.graphs.DirectedGraph;
import algolib.tuples.ComparablePair;

public final class StronglyConnectedComponents
{
    /**
     * Algorytm wyznaczania silnie spójnych składowych grafu
     * @param digraph graf skierowany
     * @return numery silnie spójnych składowych dla wierzchołków
     */
    public static List<Set<Integer>> findSCC(DirectedGraph digraph)
    {
        List<Integer> comps = new GraphComponents(digraph).findSCC();
        List<Set<Integer>> components = new ArrayList<>();

        for(int i = 0; i <= Collections.max(comps); i++)
            components.add(new HashSet<>());

        for(Integer v : digraph.getVertices())
            components.get(comps.get(v)).add(v);

        return components;
    }

    private static class GraphComponents
    {
        /**
         * Reprezentacja grafu skierowanego
         */
        private DirectedGraph digraph;
        /**
         * Numery silnie spójnych składowych dla wierzchołków
         */
        private List<Integer> components;
        /**
         * Czasy post-order wierzchołków
         */
        private List<ComparablePair<Integer, Integer>> postorder;

        public GraphComponents(DirectedGraph digraph)
        {
            this.digraph = digraph;
            components = new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(), null));
            postorder = new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(), null));
        }

        /**
         * Algorytm wyznaczania silnie spójnych składowych grafu
         * @return numery silnie spójnych składowych dla wierzchołków
         */
        public List<Integer> findSCC()
        {
            int timer = 0;
            int component = 0;

            for(Integer v : digraph.getVertices())
                if(postorder.get(v) == null)
                {
                    timer = dfsOrder(v, timer);
                    ++timer;
                }

            postorder.sort((p1, p2) -> -p1.compareTo(p2));
            digraph.reverse();

            for(ComparablePair<Integer, Integer> vt : postorder)
                if(components.get(vt.second) == null)
                {
                    dfsSCC(vt.second, component);
                    ++component;
                }

            return components;
        }

        /**
         * Algorytm DFS z licznikiem czasu wyznaczający porządek post-order wierzchołków
         * @param vertex aktualny wierzchołek
         * @param timer aktualny czas
         * @return nowy czas po przetworzeniu wierzchołka
         */
        int dfsOrder(Integer vertex, int timer)
        {
            postorder.set(vertex, ComparablePair.of(null, vertex));
            ++timer;

            for(Integer neighbour : digraph.getNeighbours(vertex))
                if(postorder.get(neighbour) == null)
                    timer = dfsOrder(neighbour, timer);

            postorder.set(vertex, ComparablePair.of(timer, vertex));

            return timer + 1;
        }

        /**
         * Algorytm DFS wyznaczający silnie spójne składowe
         * @param vertex aktualny wierzchołek
         * @param component numer składowej
         */
        private void dfsSCC(Integer vertex, int component)
        {
            components.set(vertex, component);

            for(Integer neighbour : digraph.getNeighbours(vertex))
                if(components.get(neighbour) == null)
                    dfsSCC(neighbour, component);
        }
    }
}
