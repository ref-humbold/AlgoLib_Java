// Hopcroft-Karp algorithm for matching in bipartite graph
package algolib.graphs.algorithms;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import algolib.graphs.MultipartiteGraph;

public final class Matching
{
    /**
     * Finds maximal matching in given graph.
     * @param graph a bipartite graph
     * @return map of matched vertices
     */
    public static <V, VP, EP> Map<V, V> match(MultipartiteGraph<V, VP, EP> graph)
    {
        MatchAugmenter<V, VP, EP> augmenter = new MatchAugmenter<>(graph);
        boolean wasAugmented = true;

        while(wasAugmented)
            wasAugmented = augmenter.augmentMatch();

        return augmenter.getMatching();
    }

    private static final class MatchAugmenter<V, VP, EP>
    {
        private static final double INFINITY = Double.POSITIVE_INFINITY;
        private final MultipartiteGraph<V, VP, EP> graph;
        private final Map<V, V> matching = new HashMap<>();

        private MatchAugmenter(MultipartiteGraph<V, VP, EP> graph)
        {
            if(graph.groupsCount != 2)
                throw new IllegalArgumentException("Graph is not bipartite");

            this.graph = graph;
        }

        private Map<V, V> getMatching()
        {
            return matching;
        }

        private boolean augmentMatch()
        {
            boolean matchAdded = false;
            Set<V> isVisited = new HashSet<>();
            Map<V, Double> distances = graph.getVertices()
                                            .stream()
                                            .collect(Collectors.toMap(Function.identity(),
                                                                      vertex -> INFINITY));

            bfs(distances);

            for(V vertex : graph.getVerticesFromGroup(1))
                matchAdded = dfs(vertex, isVisited, distances) || matchAdded;

            return matchAdded;
        }

        private void bfs(Map<V, Double> distances)
        {
            Deque<V> vertexDeque = new ArrayDeque<>();

            for(V vertex : graph.getVerticesFromGroup(1))
            {
                distances.put(vertex, 0.0);
                vertexDeque.addLast(vertex);
            }

            while(!vertexDeque.isEmpty())
            {
                V vertex = vertexDeque.removeFirst();

                for(V neighbour : graph.getNeighbours(vertex))
                    if(!matching.containsKey(neighbour) && distances.get(matching.get(neighbour))
                                                                    .equals(INFINITY))
                    {
                        distances.put(matching.get(neighbour), distances.get(vertex) + 1);
                        vertexDeque.addLast(matching.get(neighbour));
                    }
            }
        }

        private boolean dfs(V vertex, Set<V> isVisited, Map<V, Double> distances)
        {
            isVisited.add(vertex);

            for(V neighbour : graph.getNeighbours(vertex))
                if(matching.get(neighbour) == null)
                {
                    matching.put(vertex, neighbour);
                    matching.put(neighbour, vertex);
                    return true;
                }
                else
                {
                    V mtc = matching.get(neighbour);

                    if(!isVisited.contains(mtc) && distances.get(mtc)
                                                            .equals(distances.get(vertex) + 1)
                            && dfs(mtc, isVisited, distances))
                    {
                        matching.put(vertex, neighbour);
                        matching.put(neighbour, vertex);
                        return true;
                    }
                }

            return false;
        }
    }
}
