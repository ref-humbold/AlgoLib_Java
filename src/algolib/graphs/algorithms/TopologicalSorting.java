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
     * @throws DirectedCyclicGraphException if given graph contains a cycle
     */
    public static <V, VP, EP> List<V> sortUsingInputs(DirectedGraph<V, VP, EP> graph)
            throws DirectedCyclicGraphException
    {
        if(graph.getEdgesCount() == 0)
            return new ArrayList<>(graph.getVertices());

        List<V> order = new ArrayList<>();
        Map<V, Integer> inputDegrees = new HashMap<>();
        PriorityQueue<V> vertexQueue = new PriorityQueue<>();

        for(V vertex : graph.getVertices())
        {
            int degree = graph.getInputDegree(vertex);

            inputDegrees.put(vertex, degree);

            if(degree == 0)
                vertexQueue.add(vertex);
        }

        while(!vertexQueue.isEmpty())
        {
            V vertex = vertexQueue.remove();

            order.add(vertex);
            inputDegrees.remove(vertex);

            for(V neighbour : graph.getNeighbours(vertex))
            {
                inputDegrees.put(neighbour, inputDegrees.get(neighbour) - 1);

                if(inputDegrees.get(neighbour) == 0)
                    vertexQueue.add(neighbour);
            }
        }

        if(order.size() != graph.getVerticesCount())
            throw new DirectedCyclicGraphException("Given graph contains a cycle");

        return order;
    }

    /**
     * Topological sorting algorithm using DFS.
     * @param graph a directed graph
     * @return topological order of vertices
     * @throws DirectedCyclicGraphException if given graph contains a cycle
     */
    public static <V, VP, EP> List<V> sortUsingDFS(DirectedGraph<V, VP, EP> graph)
            throws DirectedCyclicGraphException
    {
        if(graph.getEdgesCount() == 0)
            return new ArrayList<>(graph.getVertices());

        TopologicalStrategy<V> strategy = new TopologicalStrategy<>();
        Searching.dfsRecursive(graph, strategy, graph.getVertices());

        Collections.reverse(strategy.order);
        return strategy.order;
    }

    private static class TopologicalStrategy<V>
            implements DFSStrategy<V>
    {
        final List<V> order = new ArrayList<>();

        @Override
        public void forRoot(V root)
        {
        }

        @Override
        public void onEntry(V vertex)
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
            throw new DirectedCyclicGraphException("Given graph contains a cycle");
        }
    }
}
