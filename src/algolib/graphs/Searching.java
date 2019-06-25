// ALGORYTMY PRZESZUKIWANIA GRAFU
package algolib.graphs;

import java.util.*;
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

        return reached.stream().map(v -> v == 0).collect(Collectors.toList());
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

        return reached.stream().map(Objects::isNull).collect(Collectors.toList());
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
        List<Integer> reached = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), 0));
        int iter = 1;

        for(int root : roots)
            if(reached.get(root) == 0)
            {
                dfsrStep(graph, strategy, root, iter, reached);
                ++iter;
            }
        return reached.stream().map(Objects::isNull).collect(Collectors.toList());
    }

    /**
     * Krok rekurencyjnego DFS.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param vertex aktualny wierzchołek
     * @param iter numer iteracji
     * @param reached lista iteracji dla wierzchołków
     */
    private static void dfsrStep(Graph graph, SearchingStrategy strategy, int vertex, int iter,
                                 List<Integer> reached)
    {
        reached.set(vertex, iter);
        strategy.preprocess(vertex);

        for(Integer neighbour : graph.getNeighbours(vertex))
            if(reached.get(neighbour) == 0)
                dfsrStep(graph, strategy, neighbour, iter, reached);
            else if(reached.get(neighbour) == iter)
                strategy.onCycle(vertex, neighbour);

        strategy.postprocess(vertex);
        reached.set(vertex, -iter);
    }
}
