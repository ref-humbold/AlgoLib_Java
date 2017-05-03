// ALGORYTM HOPCROFTA-KARPA WYZNACZANIA SKOJARZEŃ W GRAFIE DWUDZIELNYM
package ref_humbold.algolib.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import ref_humbold.algolib.structures.Pair;

public class Matching
{
    /** Oznaczenie braku skojarzenia. */
    private static final Integer NO_MATCH = null;

    /** Oznaczenie nieskończoności. */
    private static final Integer INF = 1 << 30;

    private static class MatchAugmenter
    {
        /** Graf dwudzielny. */
        MultipartiteGraph partgraph;

        /** Skojarzenia wierzchołków. */
        List<Integer> matching;

        /** Odległości wierzchołków. */
        List<Integer> distances;

        /** Lista odwiedzonych wierzchołków. */
        List<Boolean> isVisited;

        public MatchAugmenter(MultipartiteGraph partgraph)
        {
            if(partgraph.getGroupsNumber() != 2)
                throw new IllegalArgumentException("Graph is not bipartite");

            this.partgraph = partgraph;
            this.matching = new ArrayList<>(Collections.nCopies(partgraph.getVerticesNumber(),
                                                                NO_MATCH));
        }

        /**
         * Getter dla aktualnego skojarzenia.
         * @return skojarzenia wierzchołków
         */
        public List<Integer> getMatching()
        {
            return matching;
        }

        /**
         * Powiększanie skojarzenia przy pomocy scieżek poiększających.
         * @return czy powiększono skojarzenie
         */
        public boolean augmentMatch()
        {
            boolean matchAdded = false;

            distances = new ArrayList<>(Collections.nCopies(partgraph.getVerticesNumber(), INF));
            isVisited = new ArrayList<>(Collections.nCopies(partgraph.getVerticesNumber(), false));

            bfs();

            for(Integer v : partgraph.getGroup(1))
                matchAdded = dfs(v) || matchAdded;

            return matchAdded;
        }

        /** Algorytm BFS wyliczający odległości wierzchołków. */
        private void bfs()
        {
            Deque<Integer> vertexDeque = new ArrayDeque<>();

            for(Integer v : partgraph.getGroup(1))
            {
                distances.set(v, 0);
                vertexDeque.addLast(v);
            }

            while(!vertexDeque.isEmpty())
            {
                Integer v = vertexDeque.removeFirst();

                for(Integer nb : partgraph.getNeighbours(v))
                    if(matching.get(nb) != NO_MATCH && distances.get(matching.get(nb)).equals(INF))
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

            for(Integer neighbour : partgraph.getNeighbours(vertex))
                if(matching.get(neighbour) == NO_MATCH)
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

    public static List<Pair<Integer, Integer>> match(MultipartiteGraph partgraph)
    {
        MatchAugmenter augmenter = new MatchAugmenter(partgraph);

        while(augmenter.augmentMatch())
        {
        }

        List<Integer> matching = augmenter.getMatching();
        List<Pair<Integer, Integer>> matchPairs = new ArrayList<>();

        for(Integer v : partgraph.getGroup(1))
            matchPairs.add(new Pair<>(v, matching.get(v)));

        return matchPairs;
    }
}
