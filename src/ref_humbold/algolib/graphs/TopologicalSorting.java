// ALGORYTM: SORTOWANIE TOPOLOGICZNE GRAFU SKIEROWANEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class TopologicalSorting
{
    /**
     * Algorytm sortowania topologicznego przez liczenie poprzedników.
     * @param digraph graf skierowany
     * @return porządek topologiczny wierzchołków
     */
    public static List<Integer> sortTopological1(DirectedGraph digraph)
        throws DirectedCyclicGraphException
    {
        List<Integer> indegs = new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(), 0));
        List<Integer> order = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();

        for(Integer v : digraph.getVertices())
            indegs.set(v, digraph.getIndegree(v));

        for(Integer v : digraph.getVertices())
            if(indegs.get(v).equals(0))
                deque.addLast(v);

        while(!deque.isEmpty())
        {
            Integer v = deque.removeFirst();

            order.add(v);
            indegs.set(v, null);

            for(Integer nb : digraph.getNeighbours(v))
            {
                indegs.set(nb, indegs.get(nb) - 1);

                if(indegs.get(nb).equals(0))
                    deque.addLast(nb);
            }
        }

        if(order.size() != digraph.getVerticesNumber())
            throw new DirectedCyclicGraphException();

        return order;
    }

    /**
     * Algorytm sortowania topologicznego przez dfs.
     * @param digraph graf skierowany
     * @return porządek topologiczny wierzchołków
     */
    public static List<Integer> sortTopological2(DirectedGraph digraph)
        throws DirectedCyclicGraphException
    {
        List<Integer> order = new ArrayList<>();
        List<Boolean> isVisited = new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(),
                                                                      false));

        for(Integer v : digraph.getVertices())
            if(!isVisited.get(v))
                dfs(digraph, v, order, isVisited);

        Collections.reverse(order);

        if(order.size() != digraph.getVerticesNumber())
            throw new DirectedCyclicGraphException();

        return order;
    }

    /**
     * Algorytm dfs wyznaczający kolejność wierzchołków.
     * @param vertex aktualny wierzchołek
     * @param digraph graf skierowany
     * @param order aktualny porządek topologiczny
     * @param isVisited lista odwiedzonych wierzchołków
     */
    private static void dfs(DirectedGraph digraph, int vertex, List<Integer> order,
                            List<Boolean> isVisited)
    {
        isVisited.set(vertex, true);

        for(Integer neighbour : digraph.getNeighbours(vertex))
            if(!isVisited.get(neighbour))
                dfs(digraph, neighbour, order, isVisited);

        order.add(vertex);
    }
}
