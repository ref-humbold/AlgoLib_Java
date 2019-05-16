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
     * Algorytm BFS.
     * @param graph graf
     * @param root wierzchołek początkowy
     * @param strategy strategia procesowania wierzchołka
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> bfs(Graph graph, int root, SearchingStrategy strategy)
    {
        List<Boolean> isVisited =
                new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));
        Deque<Integer> vertexDeque = new ArrayDeque<>();

        vertexDeque.addLast(root);
        isVisited.set(root, true);

        while(!vertexDeque.isEmpty())
        {
            Integer v = vertexDeque.removeFirst();

            strategy.preprocess(v);

            for(Integer nb : graph.getNeighbours(v))
                if(!isVisited.get(nb))
                {
                    isVisited.set(nb, true);
                    vertexDeque.addLast(nb);
                }

            strategy.postprocess(v);
        }

        return isVisited;
    }

    /**
     * Iteracyjny algorytm DFS.
     * @param graph graf
     * @param root wierzchołek początkowy
     * @param strategy strategia procesowania wierzchołka
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsi(Graph graph, int root, SearchingStrategy strategy)
    {
        List<Boolean> visited =
                new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));
        Deque<Integer> vertexDeque = new ArrayDeque<>();

        vertexDeque.addFirst(root);
        visited.set(root, true);

        while(!vertexDeque.isEmpty())
        {
            Integer v = vertexDeque.removeFirst();

            strategy.preprocess(v);

            if(!visited.get(v))
            {
                visited.set(v, true);

                for(Integer nb : graph.getNeighbours(v))
                    if(!visited.get(nb))
                        vertexDeque.addFirst(nb);
            }

            strategy.postprocess(v);
        }

        return visited;
    }

    /**
     * Rekurencyjny algorytm DFS.
     * @param graph graf
     * @param root wierzchołek początkowy
     * @param strategy strategia procesowania wierzchołka
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> dfsr(Graph graph, int root, SearchingStrategy strategy)
    {
        List<Boolean> visited =
                new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(), false));

        dfsrStep(graph, root, visited, strategy);

        return visited;
    }

    /**
     * Krok rekurencyjnego DFS.
     * @param graph graf
     * @param visited lista odwiedzonych wierzchołków
     * @param strategy strategia procesowania wierzchołka
     * @param vertex aktualny wierzchołek
     */
    private static void dfsrStep(Graph graph, int vertex, List<Boolean> visited,
                                 SearchingStrategy strategy)
    {
        visited.set(vertex, true);
        strategy.preprocess(vertex);

        for(Integer neighbour : graph.getNeighbours(vertex))
            if(!visited.get(neighbour))
                dfsrStep(graph, neighbour, visited, strategy);

        strategy.postprocess(vertex);
    }
}
