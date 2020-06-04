// Algorithms for topological sorting of a directed graph
package algolib.graphs.algorithms;

import java.util.*;

import algolib.graphs.DirectedGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class TopologicalSorting
{
    /**
     * Topological sorting algorithm using predecessors counting.
     * @param graph a directed graph
     * @return topological order of vertices
     */
    public static <V, E> List<Vertex<V>> sortTopological1(DirectedGraph<V, E> graph)
            throws DirectedCyclicGraphException
    {
        List<Vertex<V>> order = new ArrayList<>();
        Map<Vertex<V>, Integer> inputDegrees = new HashMap<>();
        PriorityQueue<Vertex<V>> vertexQueue = new PriorityQueue<>();

        for(Vertex<V> vertex : graph.getVertices())
            inputDegrees.put(vertex, graph.getInputDegree(vertex));

        for(Vertex<V> vertex : graph.getVertices())
            if(Objects.equals(inputDegrees.get(vertex), 0))
                vertexQueue.add(vertex);

        while(!vertexQueue.isEmpty())
        {
            Vertex<V> vertex = vertexQueue.remove();

            order.add(vertex);
            inputDegrees.remove(vertex);

            for(Vertex<V> neighbour : graph.getNeighbours(vertex))
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
    public static <V, E> List<Vertex<V>> sortTopological2(DirectedGraph<V, E> graph)
            throws DirectedCyclicGraphException
    {
        List<Vertex<V>> vertices = new ArrayList<>(graph.getVertices());
        TopologicalStrategy<V> strategy = new TopologicalStrategy<>();

        Collections.reverse(vertices);
        Searching.dfsRecursive(graph, strategy, vertices);

        List<Vertex<V>> order = strategy.getOrder();

        Collections.reverse(order);
        return order;
    }

    private static class TopologicalStrategy<V>
            implements DFSStrategy<V>
    {
        private final List<Vertex<V>> order = new ArrayList<>();

        public List<Vertex<V>> getOrder()
        {
            return order;
        }

        @Override
        public void forRoot(Vertex<V> root)
        {
        }

        @Override
        public void preProcess(Vertex<V> vertex)
        {
        }

        @Override
        public void forNext(Vertex<V> vertex, Vertex<V> neighbour)
        {
        }

        @Override
        public void postProcess(Vertex<V> vertex)
        {
            order.add(vertex);
        }

        @Override
        public void forVisited(Vertex<V> vertex, Vertex<V> neighbour)
        {
            throw new DirectedCyclicGraphException("Given graph contains a cycle.");
        }
    }
}
