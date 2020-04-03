// ALGORYTMY PRZESZUKIWANIA GRAFU
package algolib.graphs.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import algolib.graphs.Graph;

public class Searching
{
    /**
     * Algorytm BFS od danych wierzchołków
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> bfs(Graph graph, SearchingStrategy strategy, Integer... roots)
    {
        List<Integer> reached = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), 0));
        Deque<Integer> vertexDeque = new ArrayDeque<>();
        int iter = 1;

        for(int root : roots)
            if(reached.get(root) == 0)
            {
                vertexDeque.addLast(root);
                reached.set(root, iter);

                while(!vertexDeque.isEmpty())
                {
                    Integer vertex = vertexDeque.removeFirst();

                    strategy.preprocess(vertex);

                    for(Integer neighbour : graph.getNeighbours(vertex))
                        if(reached.get(neighbour) == 0)
                        {
                            strategy.forNeighbour(vertex, neighbour);
                            reached.set(neighbour, iter);
                            vertexDeque.addLast(neighbour);
                        }
                        else if(reached.get(neighbour) == iter)
                            strategy.onCycle(vertex, neighbour);

                    strategy.postprocess(vertex);
                    reached.set(root, -iter);
                }

                ++iter;
            }

        return reached.stream().map(i -> i != 0).collect(Collectors.toList());
    }

    /**
     * Iteracyjny algorytm DFS od danych wierzchołków
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsi(Graph graph, SearchingStrategy strategy, Integer... roots)
    {
        List<Integer> reached = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), 0));
        Deque<Integer> vertexDeque = new ArrayDeque<>();
        int iter = 1;

        for(int root : roots)
            if(reached.get(root) == 0)
            {
                vertexDeque.addFirst(root);

                while(!vertexDeque.isEmpty())
                {
                    Integer vertex = vertexDeque.removeFirst();

                    if(reached.get(vertex) == 0)
                    {
                        reached.set(vertex, iter);
                        strategy.preprocess(vertex);

                        for(Integer neighbour : graph.getNeighbours(vertex))
                            if(reached.get(neighbour) == 0)
                            {
                                strategy.forNeighbour(vertex, neighbour);
                                vertexDeque.addFirst(neighbour);
                            }
                            else if(reached.get(neighbour) == iter)
                                strategy.onCycle(vertex, neighbour);

                        strategy.postprocess(vertex);
                        reached.set(root, -iter);
                    }
                }

                ++iter;
            }

        return reached.stream().map(i -> i != 0).collect(Collectors.toList());
    }

    /**
     * Rekurencyjny algorytm DFS od danych wierzchołków
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsr(Graph graph, SearchingStrategy strategy, Integer... roots)
    {
        DfsrState state = new DfsrState(graph.getVerticesNumber());

        for(int root : roots)
            if(state.reached.get(root) == 0)
            {
                dfsrStep(graph, strategy, root, state);
                ++state.iteration;
            }

        return state.reached.stream().map(i -> i != 0).collect(Collectors.toList());
    }

    /**
     * Krok rekurencyjnego DFS
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param vertex aktualny wierzchołek
     * @param state aktualny stan rekurencji
     */
    private static void dfsrStep(Graph graph, SearchingStrategy strategy, Integer vertex,
                                 DfsrState state)
    {
        state.onEntry(vertex);
        strategy.preprocess(vertex);

        for(Integer neighbour : graph.getNeighbours(vertex))
            if(state.reached.get(neighbour) == 0)
            {
                strategy.forNeighbour(vertex, neighbour);
                dfsrStep(graph, strategy, neighbour, state);
            }
            else if(state.reached.get(neighbour) == state.iteration)
                strategy.onCycle(vertex, neighbour);

        strategy.postprocess(vertex);
        state.onExit(vertex);
    }

    private static class DfsrState
    {
        int iteration;
        List<Integer> reached;

        DfsrState(int verticesNumber)
        {
            iteration = 1;
            reached = new ArrayList<>(Collections.nCopies(verticesNumber, 0));
        }

        void onEntry(int vertex)
        {
            reached.set(vertex, iteration);
        }

        void onExit(int vertex)
        {
            reached.set(vertex, -iteration);
        }
    }
}
