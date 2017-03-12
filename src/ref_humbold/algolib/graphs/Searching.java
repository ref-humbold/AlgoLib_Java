// ALGORYTM: BFS, DFS - PRZESZKIWANIE GRAFU
package ref_humbold.algolib.graphs;

import java.util.Collections;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

public class Searching
{
    /**
    Algorytm BFS.
    @param graph graf
    @param root wierzchołek początkowy
    @return lista odwiedzonych wierzchołków
    */
    public static List<Boolean> bfs(Graph graph, int root)
    {
        List<Boolean> isVisited =
            new ArrayList<Boolean>( Collections.nCopies(graph.getVerticesNumber(), false) );
        Deque<Integer> vertexDeque = new ArrayDeque<Integer>();

        vertexDeque.addLast(root);
        isVisited.set(root, true);

        while(!vertexDeque.isEmpty())
        {
            Integer v = vertexDeque.removeFirst();

             for( Integer nb : graph.getNeighbours(v) )
                if( !isVisited.get(nb) )
                {
                    isVisited.set(nb, true);
                    vertexDeque.addLast(nb);
                }
        }

        return isVisited;
    }

    /**
    Iteracyjny algorytm DFS.
    @param graph graf
    @param root wierzchołek początkowy
    @return lista odwiedzonych wierzchołków
    */
    public static List<Boolean> dfsi(Graph graph, int root)
    {
        List<Boolean> isVisited =
            new ArrayList<Boolean>( Collections.nCopies(graph.getVerticesNumber(), false) );
        Deque<Integer> vertexDeque = new ArrayDeque<Integer>();

        vertexDeque.addFirst(root);
        isVisited.set(root, true);

        while(!vertexDeque.isEmpty())
        {
            Integer v = vertexDeque.removeFirst();

            if( !isVisited.get(v) )
            {
                isVisited.set(v, true);

                 for( Integer nb : graph.getNeighbours(v) )
                    if( !isVisited.get(nb) )
                        vertexDeque.addFirst(nb);
            }
        }

        return isVisited;
    }

    /**
    Rekurencyjny algorytm DFS.
    @param graph graf
    @param root wierzchołek początkowy
    @return lista odwiedzonych wierzchołków
    */
    public static List<Boolean> dfsr(Graph graph, int root)
    {
        List<Boolean> isVisited =
            new ArrayList<Boolean>( Collections.nCopies(graph.getVerticesNumber(), false) );

        dfsrStep(graph, root, isVisited);

        return isVisited;
    }

    /**
    Krok rekurencyjnego DFS.
    @param graph graf
    @param isVisited lista odwiedzonych wierzchołków
    @param vertex aktualny wierzchołek
    */
    private static void dfsrStep(Graph graph, int vertex, List<Boolean> isVisited)
    {
        isVisited.set(vertex, true);

         for( Integer neighbour : graph.getNeighbours(vertex) )
            if( !isVisited.get(neighbour) )
                dfsrStep(graph, neighbour, isVisited);
    }
}

