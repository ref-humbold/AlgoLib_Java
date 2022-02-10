package algolib.graphs.algorithms;

import java.util.*;

import algolib.graphs.Graph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.BFSStrategy;
import algolib.graphs.algorithms.strategy.DFSStrategy;

/** Algorithms for graph searching */
public final class Searching
{
    /**
     * Breadth-first search algorithm.
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return collection of visited vertices
     */
    public static <VertexId, VertexProperty, EdgeProperty> Collection<Vertex<VertexId>> bfs(
            Graph<VertexId, VertexProperty, EdgeProperty> graph, BFSStrategy<VertexId> strategy,
            Collection<Vertex<VertexId>> roots)
    {
        Set<Vertex<VertexId>> reached = new HashSet<>();
        Deque<Vertex<VertexId>> vertexDeque = new ArrayDeque<>();

        for(Vertex<VertexId> root : roots)
            if(!reached.contains(root))
            {
                strategy.forRoot(root);
                vertexDeque.addLast(root);
                reached.add(root);

                while(!vertexDeque.isEmpty())
                {
                    Vertex<VertexId> vertex = vertexDeque.removeFirst();

                    strategy.onEntry(vertex);

                    for(Vertex<VertexId> neighbour : graph.getNeighbours(vertex))
                        if(!reached.contains(neighbour))
                        {
                            strategy.onNextVertex(vertex, neighbour);
                            reached.add(neighbour);
                            vertexDeque.addLast(neighbour);
                        }

                    strategy.onExit(vertex);
                }
            }

        return reached;
    }

    /**
     * Iterative depth-first search algorithm.
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return collection of visited vertices
     */
    public static <VertexId, VertexProperty, EdgeProperty> Collection<Vertex<VertexId>> dfsIterative(
            Graph<VertexId, VertexProperty, EdgeProperty> graph, DFSStrategy<VertexId> strategy,
            Collection<Vertex<VertexId>> roots)
    {
        Map<Vertex<VertexId>, Integer> reached = new HashMap<>();
        Deque<Vertex<VertexId>> vertexDeque = new ArrayDeque<>();
        int iteration = 1;

        for(Vertex<VertexId> root : roots)
            if(!reached.containsKey(root))
            {
                strategy.forRoot(root);
                vertexDeque.addFirst(root);

                while(!vertexDeque.isEmpty())
                {
                    Vertex<VertexId> vertex = vertexDeque.removeFirst();

                    if(!reached.containsKey(vertex))
                    {
                        reached.put(vertex, iteration);
                        strategy.onEntry(vertex);

                        for(Vertex<VertexId> neighbour : graph.getNeighbours(vertex))
                            if(!reached.containsKey(neighbour))
                            {
                                strategy.onNextVertex(vertex, neighbour);
                                vertexDeque.addFirst(neighbour);
                            }
                            else if(reached.get(neighbour) == iteration)
                                strategy.onEdgeToVisited(vertex, neighbour);

                        strategy.onExit(vertex);
                        reached.put(root, -iteration);
                    }
                }

                ++iteration;
            }

        return reached.keySet();
    }

    /**
     * Recursive depth-first search algorithm
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return collection of visited vertices
     */
    public static <VertexId, VertexProperty, EdgeProperty> Collection<Vertex<VertexId>> dfsRecursive(
            Graph<VertexId, VertexProperty, EdgeProperty> graph, DFSStrategy<VertexId> strategy,
            Collection<Vertex<VertexId>> roots)
    {
        DfsRecursiveState<VertexId> state = new DfsRecursiveState<>();

        for(Vertex<VertexId> root : roots)
            if(!state.reached.containsKey(root))
            {
                strategy.forRoot(root);
                state.vertex = root;
                dfsRecursiveStep(graph, strategy, state);
                ++state.iteration;
            }

        return state.reached.keySet();
    }

    // Single step of recursive DFS.
    private static <VertexId, VertexProperty, EdgeProperty> void dfsRecursiveStep(
            Graph<VertexId, VertexProperty, EdgeProperty> graph, DFSStrategy<VertexId> strategy,
            DfsRecursiveState<VertexId> state)
    {
        Vertex<VertexId> vertex = state.vertex;

        state.onEntry(vertex);
        strategy.onEntry(vertex);

        for(Vertex<VertexId> neighbour : graph.getNeighbours(vertex))
            if(!state.reached.containsKey(neighbour))
            {
                strategy.onNextVertex(vertex, neighbour);
                state.vertex = neighbour;
                dfsRecursiveStep(graph, strategy, state);
            }
            else if(state.reached.get(neighbour) == state.iteration)
                strategy.onEdgeToVisited(vertex, neighbour);

        strategy.onExit(vertex);
        state.onExit(vertex);
    }

    private static class DfsRecursiveState<VertexId>
    {
        Vertex<VertexId> vertex;
        int iteration = 1;
        Map<Vertex<VertexId>, Integer> reached = new HashMap<>();

        void onEntry(Vertex<VertexId> vertex_)
        {
            reached.put(vertex_, iteration);
        }

        void onExit(Vertex<VertexId> vertex_)
        {
            reached.put(vertex_, -iteration);
        }
    }
}
