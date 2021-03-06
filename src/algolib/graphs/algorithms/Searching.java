// Algorithms for graph searching
package algolib.graphs.algorithms;

import java.util.*;

import algolib.graphs.Graph;
import algolib.graphs.algorithms.strategy.BFSStrategy;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class Searching
{
    /**
     * Breadth-first search algorithm.
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return collection of visited vertices
     */
    public static <V, VP, EP> Collection<V> bfs(Graph<V, VP, EP> graph, BFSStrategy<V> strategy,
                                                Collection<V> roots)
    {
        Set<V> reached = new HashSet<>();
        Deque<V> vertexDeque = new ArrayDeque<>();

        for(V root : roots)
            if(!reached.contains(root))
            {
                strategy.forRoot(root);
                vertexDeque.addLast(root);
                reached.add(root);

                while(!vertexDeque.isEmpty())
                {
                    V vertex = vertexDeque.removeFirst();

                    strategy.onEntry(vertex);

                    for(V neighbour : graph.getNeighbours(vertex))
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
    public static <V, VP, EP> Collection<V> dfsIterative(Graph<V, VP, EP> graph,
                                                         DFSStrategy<V> strategy,
                                                         Collection<V> roots)
    {
        Map<V, Integer> reached = new HashMap<>();
        Deque<V> vertexDeque = new ArrayDeque<>();
        int iteration = 1;

        for(V root : roots)
            if(!reached.containsKey(root))
            {
                strategy.forRoot(root);
                vertexDeque.addFirst(root);

                while(!vertexDeque.isEmpty())
                {
                    V vertex = vertexDeque.removeFirst();

                    if(!reached.containsKey(vertex))
                    {
                        reached.put(vertex, iteration);
                        strategy.onEntry(vertex);

                        for(V neighbour : graph.getNeighbours(vertex))
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
    public static <V, VP, EP> Collection<V> dfsRecursive(Graph<V, VP, EP> graph,
                                                         DFSStrategy<V> strategy,
                                                         Collection<V> roots)
    {
        DfsRecursiveState<V> state = new DfsRecursiveState<>();

        for(V root : roots)
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
    private static <V, VP, EP> void dfsRecursiveStep(Graph<V, VP, EP> graph,
                                                     DFSStrategy<V> strategy,
                                                     DfsRecursiveState<V> state)
    {
        V vertex = state.vertex;

        state.onEntry(vertex);
        strategy.onEntry(vertex);

        for(V neighbour : graph.getNeighbours(vertex))
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

    private static class DfsRecursiveState<V>
    {
        V vertex;
        int iteration = 1;
        Map<V, Integer> reached = new HashMap<>();

        void onEntry(V vertex_)
        {
            reached.put(vertex_, iteration);
        }

        void onExit(V vertex_)
        {
            reached.put(vertex_, -iteration);
        }
    }
}
