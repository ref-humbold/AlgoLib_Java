// Hopcroft-Karp algorithm for matching in bipartite graph
package algolib.graphs.algorithms;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import algolib.graphs.MultipartiteGraph;
import algolib.graphs.Vertex;

public final class Matching
{
    /**
     * Finds maximal matching in given graph.
     * @param graph a bipartite graph
     * @return map of matched vertices
     */
    public static <V, E> Map<Vertex<V>, Vertex<V>> match(MultipartiteGraph<V, E> graph)
    {
        MatchAugmenter<V, E> augmenter = new MatchAugmenter<>(graph);
        boolean wasAugmented = true;

        while(wasAugmented)
            wasAugmented = augmenter.augmentMatch();

        return augmenter.getMatching();
    }

    private static final class MatchAugmenter<V, E>
    {
        private static final double INFINITY = Double.POSITIVE_INFINITY;
        private final MultipartiteGraph<V, E> graph;
        private final Map<Vertex<V>, Vertex<V>> matching = new HashMap<>();

        private MatchAugmenter(MultipartiteGraph<V, E> graph)
        {
            if(graph.getGroupsCount() != 2)
                throw new IllegalArgumentException("Graph is not bipartite");

            this.graph = graph;
        }

        private Map<Vertex<V>, Vertex<V>> getMatching()
        {
            return matching;
        }

        private boolean augmentMatch()
        {
            boolean matchAdded = false;
            Set<Vertex<V>> isVisited = new HashSet<>();
            Map<Vertex<V>, Double> distances = graph.getVertices()
                                                    .stream()
                                                    .collect(Collectors.toMap(Function.identity(),
                                                                              vertex -> INFINITY));

            bfs(distances);

            for(Vertex<V> vertex : graph.getVerticesFromGroup(1))
                matchAdded = dfs(vertex, isVisited, distances) || matchAdded;

            return matchAdded;
        }

        private void bfs(Map<Vertex<V>, Double> distances)
        {
            Deque<Vertex<V>> vertexDeque = new ArrayDeque<>();

            for(Vertex<V> vertex : graph.getVerticesFromGroup(1))
            {
                distances.put(vertex, 0.0);
                vertexDeque.addLast(vertex);
            }

            while(!vertexDeque.isEmpty())
            {
                Vertex<V> vertex = vertexDeque.removeFirst();

                for(Vertex<V> neighbour : graph.getNeighbours(vertex))
                    if(!matching.containsKey(neighbour) && distances.get(matching.get(neighbour))
                                                                    .equals(INFINITY))
                    {
                        distances.put(matching.get(neighbour), distances.get(vertex) + 1);
                        vertexDeque.addLast(matching.get(neighbour));
                    }
            }
        }

        private boolean dfs(Vertex<V> vertex, Set<Vertex<V>> isVisited,
                            Map<Vertex<V>, Double> distances)
        {
            isVisited.add(vertex);

            for(Vertex<V> neighbour : graph.getNeighbours(vertex))
                if(matching.get(neighbour) == null)
                {
                    matching.put(vertex, neighbour);
                    matching.put(neighbour, vertex);
                    return true;
                }
                else
                {
                    Vertex<V> mtc = matching.get(neighbour);

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
