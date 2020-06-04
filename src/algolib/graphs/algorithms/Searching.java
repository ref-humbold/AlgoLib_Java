// Algorithms for graph searching
package algolib.graphs.algorithms;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import algolib.graphs.Graph;
import algolib.graphs.Vertex;
import algolib.graphs.algorithms.strategy.BFSStrategy;
import algolib.graphs.algorithms.strategy.DFSStrategy;

public final class Searching
{
    /**
     * Breadth-first-search algorithm.
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return list of visited vertices
     */
    public static <V, E> Collection<Vertex<V>> bfs(Graph<V, E> graph, BFSStrategy<V> strategy,
                                                   Collection<Vertex<V>> roots)
    {
        Map<Vertex<V>, Integer> reached = new HashMap<>();
        Deque<Vertex<V>> vertexDeque = new ArrayDeque<>();
        int iteration = 1;

        for(Vertex<V> root : roots)
            if(!reached.containsKey(root))
            {
                strategy.forRoot(root);
                vertexDeque.addLast(root);
                reached.put(root, iteration);

                while(!vertexDeque.isEmpty())
                {
                    Vertex<V> vertex = vertexDeque.removeFirst();

                    strategy.preProcess(vertex);

                    for(Vertex<V> neighbour : graph.getNeighbours(vertex))
                        if(!reached.containsKey(neighbour))
                        {
                            strategy.forNext(vertex, neighbour);
                            reached.put(neighbour, iteration);
                            vertexDeque.addLast(neighbour);
                        }

                    strategy.postProcess(vertex);
                    reached.put(root, -iteration);
                }

                ++iteration;
            }

        return reached.keySet();
    }

    /**
     * Iterative depth-first-search algorithm
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return list of visited vertices
     */
    public static <V, E> Collection<Vertex<V>> dfsIterative(Graph<V, E> graph,
                                                            DFSStrategy<V> strategy,
                                                            Collection<Vertex<V>> roots)
    {
        Map<Vertex<V>, Integer> reached = new HashMap<>();
        Deque<Vertex<V>> vertexDeque = new ArrayDeque<>();
        int iteration = 1;

        for(Vertex<V> root : roots)
            if(!reached.containsKey(root))
            {
                strategy.forRoot(root);
                vertexDeque.addFirst(root);

                while(!vertexDeque.isEmpty())
                {
                    Vertex<V> vertex = vertexDeque.removeFirst();

                    if(!reached.containsKey(vertex))
                    {
                        reached.put(vertex, iteration);
                        strategy.preProcess(vertex);

                        for(Vertex<V> neighbour : graph.getNeighbours(vertex))
                            if(!reached.containsKey(neighbour))
                            {
                                strategy.forNext(vertex, neighbour);
                                vertexDeque.addFirst(neighbour);
                            }
                            else if(reached.get(neighbour) == iteration)
                                strategy.forVisited(vertex, neighbour);

                        strategy.postProcess(vertex);
                        reached.put(root, -iteration);
                    }
                }

                ++iteration;
            }

        return reached.keySet();
    }

    /**
     * Recursive depth-first-search algorithm
     * @param graph a graph
     * @param strategy a searching strategy
     * @param roots starting vertices
     * @return list of visited vertices
     */
    public static <V, E> Collection<Vertex<V>> dfsRecursive(Graph<V, E> graph,
                                                            DFSStrategy<V> strategy,
                                                            Collection<Vertex<V>> roots)
    {
        DfsRecursiveState<V> state = new DfsRecursiveState<>();

        for(Vertex<V> root : roots)
            if(!state.reached.containsKey(root))
            {
                strategy.forRoot(root);
                state.vertex = root;
                dfsRecursiveStep(graph, strategy, state);
                ++state.iteration;
            }

        return state.reached.keySet();
    }

    // Single step of the recursive DFS
    private static <V, E> void dfsRecursiveStep(Graph<V, E> graph, DFSStrategy<V> strategy,
                                                DfsRecursiveState<V> state)
    {
        Vertex<V> vertex = state.vertex;

        state.onEntry(vertex);
        strategy.preProcess(vertex);

        for(Vertex<V> neighbour : graph.getNeighbours(vertex))
            if(!state.reached.containsKey(neighbour))
            {
                strategy.forNext(vertex, neighbour);
                state.vertex = neighbour;
                dfsRecursiveStep(graph, strategy, state);
            }
            else if(state.reached.get(neighbour) == state.iteration)
                strategy.forVisited(vertex, neighbour);

        strategy.postProcess(vertex);
        state.onExit(vertex);
    }

    private static class DfsRecursiveState<V>
    {
        Vertex<V> vertex;
        int iteration = 1;
        Map<Vertex<V>, Integer> reached = new HashMap<>();

        void onEntry(Vertex<V> vertex)
        {
            reached.put(vertex, iteration);
        }

        void onExit(Vertex<V> vertex)
        {
            reached.put(vertex, -iteration);
        }
    }
}
