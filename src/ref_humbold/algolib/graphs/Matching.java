// ALGORYTM HOPCROFTA-KARPA WYZNACZANIA SKOJARZEŃ W GRAFIE DWUDZIELNYM
package ref_humbold.algolib.graphs;

import java.lang.Math;
import java.util.Collections;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

import ref_humbold.algolib.structures.Pair;

public class Matching
{
    /** Oznaczenie braku skojarzenia. */
    private static final Integer NO_MATCH = null;

    /** Oznaczenie nieskończoności. */
    private static final Integer INF = 1<<30;

    private static class MatchAugmenter
    {
        /** Graf dwudzielny. */
        BipartiteGraph bigraph;

        /** Skojarzenia wierzchołków. */
        List<Integer> matching;

        /** Odległości wierzchołków. */
        List<Integer> distances;

        /** Lista odwiedzonych wierzchołków. */
        List<Boolean> isVisited;

        public MatchAugmenter(BipartiteGraph bigraph)
        {
            this.bigraph = bigraph;
            this.matching = new ArrayList<>(
                Collections.nCopies(bigraph.getVerticesNumber(), NO_MATCH) );
        }

        public MatchAugmenter(BipartiteGraph bigraph, List<Integer> matching)
        {
            this.bigraph = bigraph;
            this.matching = matching;
        }

        /**
        Getter dla aktualnego skojarzenia.
        @return skojarzenia wierzchołków
        */
        public List<Integer> getMatching()
        {
            return matching;
        }

        /**
        Powiększanie skojarzenia przy pomocy scieżek poiększających.
        @return czy powiększono skojarzenie
        */
        public boolean augmentMatch()
        {
            boolean matchAdded = false;

            distances = new ArrayList<>( Collections.nCopies(bigraph.getVerticesNumber(), INF) );
            isVisited = new ArrayList<>( Collections.nCopies(bigraph.getVerticesNumber(), false) );

            bfs();

            for(Integer v : bigraph.getFirstPart())
                matchAdded = dfs(v) || matchAdded;

            return matchAdded;
        }

        /** Algorytm BFS wyliczający odległości wierzchołków. */
        private void bfs()
        {
            Deque<Integer> vertexDeque = new ArrayDeque<>();

            for(Integer v : bigraph.getFirstPart())
            {
                distances.set(v, 0);
                vertexDeque.addLast(v);
            }

            while(!vertexDeque.isEmpty())
            {
                Integer v = vertexDeque.removeFirst();

                for( Integer nb : bigraph.getNeighbours(v) )
                    if( matching.get(nb) != NO_MATCH
                        && distances.get( matching.get(nb) ).equals(INF) )
                    {
                        distances.set(matching.get(nb), distances.get(v)+1);
                        vertexDeque.addLast( matching.get(nb) );
                    }
            }
        }

        /**
        Algorytm DFS powiększający skojarzenie za pomocą ścieżek powiekszających.
        @param vertex wierzchołek
        @return czy powiększono skojarzenie
        */
        private boolean dfs(int vertex)
        {
            isVisited.set(vertex, true);

            for( Integer neighbour : bigraph.getNeighbours(vertex) )
            {
                if(matching.get(neighbour) == NO_MATCH)
                {
                    matching.set(vertex, neighbour);
                    matching.set(neighbour, vertex);

                    return true;
                }
                else
                {
                    Integer mtc = matching.get(neighbour);

                    if( distances.get(mtc).equals( distances.get(vertex)+1 )
                        && !isVisited.get(mtc) && dfs(mtc) )
                    {
                        matching.set(vertex, neighbour);
                        matching.set(neighbour, vertex);

                        return true;
                    }
                }
            }

            return false;
        }
    }

    public static List< Pair<Integer, Integer> > match(BipartiteGraph bigraph)
    {
        MatchAugmenter augmenter = new MatchAugmenter(bigraph);

        while(augmenter.augmentMatch())
        {
        }

        List<Integer> matching = augmenter.getMatching();
        List< Pair<Integer, Integer> > matchPairs = new ArrayList<>();

        for(Integer v : bigraph.getFirstPart())
            matchPairs.add( new Pair( v, matching.get(v) ) );

        return matchPairs;
    }
}

