// ALGORYTMY PRZESZUKIWANIA GRAFU
package algolib.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import algolib.graphs.searching.SearchingStrategy;

public class Searching
{
    /**
     * Algorytm BFS od danych wierzchołków.
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

        return reached.stream().map(v -> v != 0).collect(Collectors.toList());
    }

    /**
     * Iteracyjny algorytm DFS od danych wierzchołków.
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
                                vertexDeque.addFirst(neighbour);
                            else if(reached.get(neighbour) == iter)
                                strategy.onCycle(vertex, neighbour);

                        strategy.postprocess(vertex);
                        reached.set(root, -iter);
                    }
                }

                ++iter;
            }

        return reached.stream().map(v -> v != 0).collect(Collectors.toList());
    }

    /**
     * Rekurencyjny algorytm DFS od danych wierzchołków.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsr(Graph graph, SearchingStrategy strategy, Integer... roots)
    {
        DfsrState state = new DfsrState(graph.getVerticesNumber());

        state.iteration = 1;
        state.reached = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), 0));

        for(int root : roots)
            if(state.reached.get(root) == 0)
            {
                state.vertex = root;
                dfsrStep(graph, strategy, state);
                ++state.iteration;
            }

        return state.reached.stream().map(v -> v != 0).collect(Collectors.toList());
    }

    /**
     * Krok rekurencyjnego DFS.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param state aktualny stan rekurencji
     */
    private static void dfsrStep(Graph graph, SearchingStrategy strategy, DfsrState state)
    {
        state.onEntry();
        strategy.preprocess(state.vertex);

        for(Integer neighbour : graph.getNeighbours(state.vertex))
            if(state.reached.get(neighbour) == 0)
                dfsrStep(graph, strategy, new DfsrState(neighbour, state.iteration, state.reached));
            else if(state.reached.get(neighbour) == state.iteration)
                strategy.onCycle(state.vertex, neighbour);

        strategy.postprocess(state.vertex);
        state.onExit();
    }

    private static class DfsrState
    {
        int vertex;
        int iteration;
        List<Integer> reached;

        DfsrState(int vertex, int iteration, List<Integer> reached)
        {
            this.vertex = vertex;
            this.iteration = iteration;
            this.reached = reached;
        }

        DfsrState(int verticesNumber)
        {
            vertex = 0;
            iteration = 1;
            reached = new ArrayList<>(Collections.nCopies(verticesNumber, 0));
        }

        void onEntry()
        {
            reached.set(vertex, iteration);
        }

        void onExit()
        {
            reached.set(vertex, -iteration);
        }
    }
}
