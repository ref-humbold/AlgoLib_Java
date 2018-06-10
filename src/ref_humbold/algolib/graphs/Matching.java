// ALGORYTM HOPCROFTA-KARPA WYZNACZANIA SKOJARZEŃ W GRAFIE DWUDZIELNYM
package ref_humbold.algolib.graphs;

import java.util.*;

import ref_humbold.algolib.tuples.Pair;

public class Matching
{
    /**
     * Wyznaczanie maksymalnego skojarzenia.
     * @param multipartiteGraph graf wielodzielny
     * @return pary skojarzonych wierzchołków
     */
    public static List<Pair<Integer, Integer>> match(MultipartiteGraph multipartiteGraph)
    {
        MatchAugmenter augmenter = new MatchAugmenter(multipartiteGraph);

        boolean hasAugmented = true;

        while(hasAugmented)
        {
            hasAugmented = augmenter.augmentMatch();
        }

        List<Integer> matching = augmenter.getMatching();
        List<Pair<Integer, Integer>> matchPairs = new ArrayList<>();

        for(Integer v : multipartiteGraph.getVertices(1))
            matchPairs.add(Pair.make(v, matching.get(v)));

        return matchPairs;
    }

    private static class MatchAugmenter
    {
        /**
         * Graf dwudzielny.
         */
        private MultipartiteGraph graph;

        /**
         * Skojarzenia wierzchołków.
         */
        private List<Integer> matching;

        /**
         * Odległości wierzchołków.
         */
        private List<Double> distances;

        /**
         * Lista odwiedzonych wierzchołków.
         */
        private List<Boolean> isVisited;

        private MatchAugmenter(MultipartiteGraph multipartiteGraph)
        {
            if(multipartiteGraph.getGroupsNumber() != 2)
                throw new IllegalArgumentException("Graph is not bipartite");

            this.graph = multipartiteGraph;
            this.matching = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), null));
        }

        /**
         * @return skojarzenia wierzchołków
         */
        private List<Integer> getMatching()
        {
            return new ArrayList<>(this.matching);
        }

        /**
         * Powiększanie skojarzenia przy pomocy scieżek poiększających.
         * @return czy powiększono skojarzenie
         */
        private boolean augmentMatch()
        {
            boolean matchAdded = false;

            distances = new ArrayList<>(
                Collections.nCopies(graph.getVerticesNumber(), MultipartiteGraph.INF));
            isVisited = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));

            bfs();

            for(Integer v : graph.getVertices(1))
                matchAdded = dfs(v) || matchAdded;

            return matchAdded;
        }

        /**
         * Algorytm BFS wyliczający odległości wierzchołków.
         */
        private void bfs()
        {
            Deque<Integer> vertexDeque = new ArrayDeque<>();

            for(Integer v : graph.getVertices(1))
            {
                distances.set(v, 0.0);
                vertexDeque.addLast(v);
            }

            while(!vertexDeque.isEmpty())
            {
                Integer v = vertexDeque.removeFirst();

                for(Integer nb : graph.getNeighbours(v))
                    if(matching.get(nb) != null && distances.get(matching.get(nb))
                                                            .equals(MultipartiteGraph.INF))
                    {
                        distances.set(matching.get(nb), distances.get(v) + 1);
                        vertexDeque.addLast(matching.get(nb));
                    }
            }
        }

        /**
         * Algorytm DFS powiększający skojarzenie za pomocą ścieżek powiekszających.
         * @param vertex wierzchołek
         * @return czy powiększono skojarzenie
         */
        private boolean dfs(int vertex)
        {
            isVisited.set(vertex, true);

            for(Integer neighbour : graph.getNeighbours(vertex))
                if(matching.get(neighbour) == null)
                {
                    matching.set(vertex, neighbour);
                    matching.set(neighbour, vertex);

                    return true;
                }
                else
                {
                    Integer mtc = matching.get(neighbour);

                    if(distances.get(mtc).equals(distances.get(vertex) + 1) && !isVisited.get(mtc)
                        && dfs(mtc))
                    {
                        matching.set(vertex, neighbour);
                        matching.set(neighbour, vertex);

                        return true;
                    }
                }

            return false;
        }
    }
}
