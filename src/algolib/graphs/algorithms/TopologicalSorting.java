// Algorithms for topological sorting of a directed graph
package algolib.graphs.algorithms;

import java.util.*;

import algolib.graphs.DirectedGraph;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class TopologicalSorting
{
    /**
     * Topological sorting algorithm using predecessors counting.
     * @param graph a directed graph
     * @return topological order of vertices
     */
    public static <V, VP, EP> List<V> sortTopological1(DirectedGraph<V, VP, EP> graph)
            throws DirectedCyclicGraphException
    {
        List<V> order = new ArrayList<>();
        Map<V, Integer> inputDegrees = new HashMap<>();
        PriorityQueue<V> vertexQueue = new PriorityQueue<>();

        for(V vertex : graph.getVertices())
            inputDegrees.put(vertex, graph.getInputDegree(vertex));

        for(V vertex : graph.getVertices())
            if(Objects.equals(inputDegrees.get(vertex), 0))
                vertexQueue.add(vertex);

        while(!vertexQueue.isEmpty())
        {
            V vertex = vertexQueue.remove();

            order.add(vertex);
            inputDegrees.remove(vertex);

            for(V neighbour : graph.getNeighbours(vertex))
            {
                inputDegrees.put(neighbour, inputDegrees.get(neighbour) - 1);

                if(Objects.equals(inputDegrees.get(neighbour), 0))
                    vertexQueue.add(neighbour);
            }
        }

        if(order.size() != graph.getVerticesCount())
            throw new DirectedCyclicGraphException("Given graph contains a cycle.");

        return order;
    }

    /**
     * Topological sorting algorithm using DFS.
     * @param graph a directed graph
     * @return topological order of vertices
     */
    public static <V, VP, EP> List<V> sortTopological2(DirectedGraph<V, VP, EP> graph)
            throws DirectedCyclicGraphException
    {
        List<V> vertices = new ArrayList<>(graph.getVertices());
        TopologicalStrategy<V> strategy = new TopologicalStrategy<>();

        Collections.reverse(vertices);
        Searching.dfsRecursive(graph, strategy, vertices);

        List<V> order = strategy.getOrder();

        Collections.reverse(order);
        return order;
    }

    private static class TopologicalStrategy<V>
            implements DFSStrategy<V>
    {
        private final List<V> order = new ArrayList<>();

        List<V> getOrder()
        {
            return order;
        }

        @Override
        public void forRoot(V root)
        {
        }

        @Override
        public void onEnter(V vertex)
        {
        }

        @Override
        public void onNextVertex(V vertex, V neighbour)
        {
        }

        @Override
        public void onExit(V vertex)
        {
            order.add(vertex);
        }

        @Override
        public void onEdgeToVisited(V vertex, V neighbour)
        {
            throw new DirectedCyclicGraphException("Given graph contains a cycle.");
        }
    }
}
