package algolib.graphs.algorithms;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import algolib.graphs.MultipartiteGraph;
import algolib.graphs.Vertex;

/** Hopcroft-Karp algorithm for matching in bipartite graph */
public final class Matching
{
    /**
     * Finds maximal matching in given bipartite graph.
     * @param graph the bipartite graph
     * @return the map of matched vertices
     */
    public static <VertexId, VertexProperty, EdgeProperty> Map<Vertex<VertexId>,
                                                                      Vertex<VertexId>> match(
            MultipartiteGraph<VertexId, VertexProperty, EdgeProperty> graph)
    {
        MatchAugmenter<VertexId, VertexProperty, EdgeProperty> augmenter =
                new MatchAugmenter<>(graph);
        boolean wasAugmented = true;

        while(wasAugmented)
            wasAugmented = augmenter.augmentMatch();

        return augmenter.matching;
    }

    private static final class MatchAugmenter<VertexId, VertexProperty, EdgeProperty>
    {
        private static final double INFINITY = Double.POSITIVE_INFINITY;
        final Map<Vertex<VertexId>, Vertex<VertexId>> matching = new HashMap<>();
        private final MultipartiteGraph<VertexId, VertexProperty, EdgeProperty> graph;

        private MatchAugmenter(MultipartiteGraph<VertexId, VertexProperty, EdgeProperty> graph)
        {
            if(graph.groupsCount != 2)
                throw new IllegalArgumentException("Graph is not bipartite");

            this.graph = graph;
        }

        boolean augmentMatch()
        {
            Set<Vertex<VertexId>> visited = new HashSet<>();
            Map<Vertex<VertexId>, Double> distances = graph.getVertices()
                                                           .stream()
                                                           .collect(Collectors.toMap(
                                                                   Function.identity(),
                                                                   vertex -> INFINITY));

            bfs(distances);
            return unmatchedVertices().stream()
                                      .reduce(false, (acc, v) -> dfs(v, visited, distances) || acc,
                                              (b1, b2) -> b1 || b2);
        }

        private Collection<Vertex<VertexId>> unmatchedVertices()
        {
            return graph.getVerticesFromGroup(1)
                        .stream()
                        .filter(v -> !matching.containsKey(v))
                        .collect(Collectors.toSet());
        }

        private void bfs(Map<Vertex<VertexId>, Double> distances)
        {
            Deque<Vertex<VertexId>> vertexDeque = new ArrayDeque<>();

            for(Vertex<VertexId> vertex : unmatchedVertices())
            {
                distances.put(vertex, 0.0);
                vertexDeque.addLast(vertex);
            }

            while(!vertexDeque.isEmpty())
            {
                Vertex<VertexId> vertex = vertexDeque.removeFirst();

                for(Vertex<VertexId> neighbour : graph.getNeighbours(vertex))
                {
                    Vertex<VertexId> matched = matching.get(neighbour);

                    if(matched != null && distances.get(matched) == INFINITY)
                    {
                        distances.put(matched, distances.get(vertex) + 1);
                        vertexDeque.addLast(matched);
                    }
                }
            }
        }

        private boolean dfs(
                Vertex<VertexId> vertex,
                Set<Vertex<VertexId>> visited,
                Map<Vertex<VertexId>, Double> distances)
        {
            visited.add(vertex);

            for(Vertex<VertexId> neighbour : graph.getNeighbours(vertex))
            {
                Vertex<VertexId> matched = matching.get(neighbour);

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
