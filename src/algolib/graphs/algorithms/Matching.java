// Hopcroft-Karp algorithm for matching in bipartite graph
package algolib.graphs.algorithms;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import algolib.graphs.MultipartiteGraph;

public final class Matching
{
    /**
     * Finds maximal matching in given bipartite graph.
     * @param graph a bipartite graph
     * @return map of matched vertices
     */
    public static <V, VP, EP> Map<V, V> match(MultipartiteGraph<V, VP, EP> graph)
    {
        MatchAugmenter<V, VP, EP> augmenter = new MatchAugmenter<>(graph);
        boolean wasAugmented = true;

        while(wasAugmented)
            wasAugmented = augmenter.augmentMatch();

        return augmenter.matching;
    }

    private static final class MatchAugmenter<V, VP, EP>
    {
        private static final double INFINITY = Double.POSITIVE_INFINITY;
        final Map<V, V> matching = new HashMap<>();
        private final MultipartiteGraph<V, VP, EP> graph;

        private MatchAugmenter(MultipartiteGraph<V, VP, EP> graph)
        {
            if(graph.groupsCount != 2)
                throw new IllegalArgumentException("Graph is not bipartite");

            this.graph = graph;
        }

        boolean augmentMatch()
        {
            boolean wasAdded = false;
            Set<V> visited = new HashSet<>();
            Map<V, Double> distances = graph.getVertices()
                                            .stream()
                                            .collect(Collectors.toMap(Function.identity(),
                                                                      vertex -> INFINITY));

            bfs(distances);

            for(V vertex : unmatchedVertices())
                wasAdded = dfs(vertex, visited, distances) || wasAdded;

            return wasAdded;
        }

        private Collection<V> unmatchedVertices()
        {
            return graph.getVerticesFromGroup(1)
                        .stream()
                        .filter(v -> !matching.containsKey(v))
                        .collect(Collectors.toSet());
        }

        private void bfs(Map<V, Double> distances)
        {
            Deque<V> vertexDeque = new ArrayDeque<>();

            for(V vertex : unmatchedVertices())
            {
                distances.put(vertex, 0.0);
                vertexDeque.addLast(vertex);
            }

            while(!vertexDeque.isEmpty())
            {
                V vertex = vertexDeque.removeFirst();

                for(V neighbour : graph.getNeighbours(vertex))
                {
                    V matched = matching.get(neighbour);

                    if(matched != null && distances.get(matched) == INFINITY)
                    {
                        distances.put(matched, distances.get(vertex) + 1);
                        vertexDeque.addLast(matched);
                    }
                }
            }
        }

        private boolean dfs(V vertex, Set<V> visited, Map<V, Double> distances)
        {
            visited.add(vertex);

            for(V neighbour : graph.getNeighbours(vertex))

            {
                V matched = matching.get(neighbour);

                if(matched == null)
                {
                    matching.put(vertex, neighbour);
                    matching.put(neighbour, vertex);
                    return true;
                }

                if(!visited.contains(matched) && distances.get(matched) == distances.get(vertex) + 1
                        && dfs(matched, visited, distances))
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
