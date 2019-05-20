// ALGORYTMY PRZESZUKIWANIA GRAFU
package refhumbold.algolib.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import refhumbold.algolib.graphs.searching.SearchingStrategy;

public class Searching
{
    /**
     * Algorytm BFS od danych wierzchołków.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> bfs(Graph graph, SearchingStrategy strategy, int... roots)
    {
        List<Boolean> visited =
                new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));
        Deque<Integer> vertexDeque = new ArrayDeque<>();

        for(int root : roots)
            if(!visited.get(root))
            {
                vertexDeque.addLast(root);
                visited.set(root, true);

                while(!vertexDeque.isEmpty())
                {
                    Integer vertex = vertexDeque.removeFirst();

                    strategy.preprocess(vertex);

                    for(Integer neighbour : graph.getNeighbours(vertex))
                    {
                        strategy.checkNeighbour(neighbour);

                        if(!visited.get(neighbour))
                        {
                            visited.set(neighbour, true);
                            vertexDeque.addLast(neighbour);
                        }
                        else
                            strategy.onCycle(vertex, neighbour);
                    }

                    strategy.postprocess(vertex);
                }
            }

        return visited;
    }

    /**
     * Iteracyjny algorytm DFS od danych wierzchołków.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsi(Graph graph, SearchingStrategy strategy, int... roots)
    {
        List<Boolean> visited =
                new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));
        Deque<Integer> vertexDeque = new ArrayDeque<>();

        for(int root : roots)
            if(!visited.get(root))
            {
                vertexDeque.addFirst(root);
                visited.set(root, true);

                while(!vertexDeque.isEmpty())
                {
                    Integer vertex = vertexDeque.removeFirst();

                    if(!visited.get(vertex))
                    {
                        visited.set(vertex, true);
                        strategy.preprocess(vertex);

                        for(Integer neighbour : graph.getNeighbours(vertex))
                        {
                            strategy.checkNeighbour(neighbour);

                            if(!visited.get(neighbour))
                                vertexDeque.addFirst(neighbour);
                            else
                                strategy.onCycle(vertex, neighbour);
                        }

                        strategy.postprocess(vertex);
                    }
                }
            }

        return visited;
    }

    /**
     * Rekurencyjny algorytm DFS od danych wierzchołków.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param roots wierzchołki początkowe
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsr(Graph graph, SearchingStrategy strategy, int... roots)
    {
        List<Boolean> visited =
                new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));

        for(int root : roots)
            if(!visited.get(root))
                dfsrStep(graph, strategy, root, visited);

        return visited;
    }

    /**
     * Krok rekurencyjnego DFS.
     * @param graph graf
     * @param strategy strategia procesowania wierzchołka
     * @param vertex aktualny wierzchołek
     * @param visited lista odwiedzonych wierzchołków
     */
    private static void dfsrStep(Graph graph, SearchingStrategy strategy, int vertex,
                                 List<Boolean> visited)
    {
        visited.set(vertex, true);
        strategy.preprocess(vertex);

        for(Integer neighbour : graph.getNeighbours(vertex))
        {
            strategy.checkNeighbour(neighbour);

            if(!visited.get(neighbour))
                dfsrStep(graph, strategy, neighbour, visited);
            else
                strategy.onCycle(vertex, neighbour);
        }
        strategy.postprocess(vertex);
    }
}
