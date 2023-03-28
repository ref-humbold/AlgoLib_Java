package algolib.graphs.algorithms;

import java.util.*;

import algolib.graphs.DirectedGraph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.DFSStrategy;

/** Algorithms for topological sorting of a directed graph */
public final class TopologicalSorting
{
    /**
     * Topological sorting algorithm using predecessors counting.
     * @param graph the directed graph
     * @return topological order of vertices
     * @throws DirectedCyclicGraphException if given graph contains a cycle
     */
    public static <VertexId extends Comparable<VertexId>, VertexProperty, EdgeProperty> List<Vertex<VertexId>> inputsTopologicalSort(
            DirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
            throws DirectedCyclicGraphException
    {
        return inputsTopologicalSort(graph, VertexId::compareTo);
    }

    /**
     * Topological sorting algorithm using predecessors counting.
     * @param graph the directed graph
     * @param vertexIdComparator comparator of vertex indices
     * @return topological order of vertices
     * @throws DirectedCyclicGraphException if given graph contains a cycle
     */
    public static <VertexId, VertexProperty, EdgeProperty> List<Vertex<VertexId>> inputsTopologicalSort(
            DirectedGraph<VertexId, VertexProperty, EdgeProperty> graph,
            Comparator<VertexId> vertexIdComparator)
            throws DirectedCyclicGraphException
    {
        if(graph.getEdgesCount() == 0)
            return new ArrayList<>(graph.getVertices());

        List<Vertex<VertexId>> order = new ArrayList<>();
        Map<Vertex<VertexId>, Integer> inputDegrees = new HashMap<>();
        PriorityQueue<Vertex<VertexId>> vertexQueue = new PriorityQueue<>(
                ((vertex1, vertex2) -> vertexIdComparator.compare(vertex1.id, vertex2.id)));

        for(Vertex<VertexId> vertex : graph.getVertices())
        {
            int degree = graph.getInputDegree(vertex);

            inputDegrees.put(vertex, degree);

            if(degree == 0)
                vertexQueue.add(vertex);
        }

        while(!vertexQueue.isEmpty())
        {
            Vertex<VertexId> vertex = vertexQueue.remove();

            order.add(vertex);
            inputDegrees.remove(vertex);

            for(Vertex<VertexId> neighbour : graph.getNeighbours(vertex))
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
     * @param graph the directed graph
     * @return topological order of vertices
     * @throws DirectedCyclicGraphException if given graph contains a cycle
     */
    public static <VertexId, VertexProperty, EdgeProperty> List<Vertex<VertexId>> dfsTopologicalSort(
            DirectedGraph<VertexId, VertexProperty, EdgeProperty> graph)
            throws DirectedCyclicGraphException
    {
        if(graph.getEdgesCount() == 0)
            return new ArrayList<>(graph.getVertices());

        TopologicalStrategy<VertexId> strategy = new TopologicalStrategy<>();
        Searching.dfsRecursive(graph, strategy, graph.getVertices());

        Collections.reverse(strategy.order);
        return strategy.order;
    }

    private static class TopologicalStrategy<VertexId>
            implements DFSStrategy<VertexId>
    {
        final List<Vertex<VertexId>> order = new ArrayList<>();

        @Override
        public void forRoot(Vertex<VertexId> root)
        {
        }

        @Override
        public void onEntry(Vertex<VertexId> vertex)
        {
        }

        @Override
        public void onNextVertex(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
        }

        @Override
        public void onExit(Vertex<VertexId> vertex)
        {
            order.add(vertex);
        }

        @Override
        public void onEdgeToVisited(Vertex<VertexId> vertex, Vertex<VertexId> neighbour)
        {
            throw new DirectedCyclicGraphException("Given graph contains a cycle");
        }
    }
}
