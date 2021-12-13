// Algorithm for lowest common ancestor
package algolib.graphs.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algolib.graphs.TreeGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class LowestCommonAncestor<VertexId, VertexProperty, EdgeProperty>
{
    public final TreeGraph<VertexId, VertexProperty, EdgeProperty> graph;
    public final Vertex<VertexId> root;
    private final Map<Vertex<VertexId>, List<Vertex<VertexId>>> paths = new HashMap<>();
    private final LCAStrategy<VertexId> strategy = new LCAStrategy<>();
    private boolean empty = true;

    public LowestCommonAncestor(TreeGraph<VertexId, VertexProperty, EdgeProperty> graph,
                                Vertex<VertexId> root)
    {
        this.graph = graph;
        this.root = root;
    }

    /**
     * Finds the lowest common ancestor of two vertices in a rooted tree.
     * @param vertex1 first vertex
     * @param vertex2 second vertex
     * @return lowest common ancestor of given vertices
     */
    public Vertex<VertexId> findLCA(Vertex<VertexId> vertex1, Vertex<VertexId> vertex2)
    {
        if(empty)
            initialize();

        return find(vertex1, vertex2);
    }

    private Vertex<VertexId> find(Vertex<VertexId> vertex1, Vertex<VertexId> vertex2)
    {
        if(isOffspring(vertex1, vertex2))
            return vertex2;

        if(isOffspring(vertex2, vertex1))
            return vertex1;

        List<Vertex<VertexId>> candidates = new ArrayList<>(paths.get(vertex1));

        Collections.reverse(candidates);

        Vertex<VertexId> nextVertex = candidates.stream()
                                                .filter(candidate -> !isOffspring(vertex2,
                                                                                  candidate))
                                                .findFirst()
                                                .orElse(paths.get(vertex1).get(0));

        return find(nextVertex, vertex2);
    }

    private void initialize()
    {
        Searching.dfsRecursive(graph, strategy, List.of(root));

        for(Vertex<VertexId> vertex : graph.getVertices())
            paths.put(vertex, new ArrayList<>(List.of(strategy.parents.get(vertex))));

        for(int i = 0; i < Math.log(graph.getVerticesCount()) / Math.log(2) + 3; ++i)
            for(Vertex<VertexId> vertex : graph.getVertices())
                paths.get(vertex).add(paths.get(paths.get(vertex).get(i)).get(i));

        empty = false;
    }

    private boolean isOffspring(Vertex<VertexId> vertex1, Vertex<VertexId> vertex2)
    {
        return strategy.preTimes.get(vertex1) >= strategy.preTimes.get(vertex2)
                && strategy.postTimes.get(vertex1) <= strategy.postTimes.get(vertex2);
    }

    private static class LCAStrategy<VertexId>
            implements DFSStrategy<VertexId>
    {
        final Map<Vertex<VertexId>, Vertex<VertexId>> parents = new HashMap<>();
        final Map<Vertex<VertexId>, Integer> preTimes = new HashMap<>();
        final Map<Vertex<VertexId>, Integer> postTimes = new HashMap<>();
        private int timer = 0;

        @Override
        public void forRoot(Vertex<VertexId> root)
        {
            parents.put(root, root);
        }

        @Override
        public void onEntry(Vertex<VertexId> vertex)
        {
            preTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
            parents.put(neighbour, vertex);
        }

        @Override
        public void onExit(Vertex<VertexId> vertex)
        {
            postTimes.put(vertex, timer);
            ++timer;
        }

        @Override
        public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }
    }
}
