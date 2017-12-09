// ALGORYTM: SORTOWANIE TOPOLOGICZNE GRAFU SKIEROWANEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import ref_humbold.algolib.tuples.Pair;

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
        List<Integer> order = new ArrayList<>();
        List<Integer> indegs =
            new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(), null));
        PriorityQueue<Integer> vertexQueue = new PriorityQueue<>();

        for(Integer v : digraph.getVertices())
            indegs.set(v, digraph.getIndegree(v));

        for(Integer v : digraph.getVertices())
            if(indegs.get(v).equals(0))
                vertexQueue.add(v);

        while(!vertexQueue.isEmpty())
        {
            Integer v = vertexQueue.poll();

            order.add(v);
            indegs.set(v, null);

            for(Integer nb : digraph.getNeighbours(v))
            {
                indegs.set(nb, indegs.get(nb) - 1);

                if(indegs.get(nb).equals(0))
                    vertexQueue.add(nb);
            }
        }

        if(order.size() != digraph.getVerticesNumber())
            throw new DirectedCyclicGraphException("Given graph has a cycle.");

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
        List<Pair<Integer, Boolean>> indices =
            new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(), null));
        List<Integer> vertices = new ArrayList<>(digraph.getVertices());

        Collections.sort(vertices);
        Collections.reverse(vertices);

        for(Integer v : vertices)
            if(indices.get(v) == null)
                dfs(v, v, digraph, order, indices);

        Collections.reverse(order);

        return order;
    }

    /**
     * Algorytm DFS wyznaczający kolejność wierzchołków.
     * @param vertex aktualny wierzchołek
     * @param index numer iteracji
     * @param digraph graf skierowany
     * @param order aktualny porządek topologiczny
     * @param indices: indeksy iteracji i przetwarzania wierzchołków
     */
    private static void dfs(int vertex, int index, DirectedGraph digraph, List<Integer> order,
                            List<Pair<Integer, Boolean>> indices)
        throws DirectedCyclicGraphException
    {
        indices.set(vertex, Pair.make(index, true));

        for(Integer neighbour : digraph.getNeighbours(vertex))
            if(indices.get(neighbour) == null)
                dfs(neighbour, index, digraph, order, indices);
            else if(indices.get(neighbour).getFirst().equals(index) && indices.get(neighbour)
                                                                              .getSecond())
                throw new DirectedCyclicGraphException("Given graph has a cycle.");

        order.add(vertex);
        indices.set(vertex, Pair.make(index, false));
    }
}
