package ref_humbold.algolib.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class BreadthFirstSearch
{
    /**
     * Algorytm BFS.
     * @param graph graf
     * @param root wierzchołek początkowy
     * @return lista odwiedzonych wierzchołków
     */
    public static List<Boolean> bfs(Graph graph, int root)
    {
        List<Boolean> isVisited = new ArrayList<>(Collections.nCopies(graph.getVerticesNumber(),
                                                                      false));
        Deque<Integer> vertexDeque = new ArrayDeque<>();

        vertexDeque.addLast(root);
        isVisited.set(root, true);

        while(!vertexDeque.isEmpty())
        {
            Integer v = vertexDeque.removeFirst();

            for(Integer nb : graph.getNeighbours(v))
                if(!isVisited.get(nb))
                {
                    isVisited.set(nb, true);
                    vertexDeque.addLast(nb);
                }
        }

        return isVisited;
    }
}
