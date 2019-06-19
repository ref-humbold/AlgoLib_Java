// ALGORYTM: SORTOWANIE TOPOLOGICZNE GRAFU SKIEROWANEGO
package algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import algolib.tuples.ImmutablePair;

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
            if(Objects.equals(indegs.get(v), 0))
                vertexQueue.add(v);

        while(!vertexQueue.isEmpty())
        {
            Integer v = vertexQueue.remove();

            order.add(v);
            indegs.set(v, null);

            for(Integer nb : digraph.getNeighbours(v))
            {
                indegs.set(nb, indegs.get(nb) - 1);

                if(Objects.equals(indegs.get(nb), 0))
                    vertexQueue.add(nb);
            }
        }

        if(order.size() != digraph.getVerticesNumber())
            throw new DirectedCyclicGraphException("Given graph contains a cycle.");

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
        List<ImmutablePair<Integer, Boolean>> indices =
                new ArrayList<>(Collections.nCopies(digraph.getVerticesNumber(), null));
        List<Integer> vertices = new ArrayList<>(digraph.getVertices());

        Collections.sort(vertices);
        Collections.reverse(vertices);

        for(Integer v : vertices)
            if(indices.get(v) == null)
                TopologicalSorting.dfs(v, v, digraph, order, indices);

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
                            List<ImmutablePair<Integer, Boolean>> indices)
            throws DirectedCyclicGraphException
    {
        indices.set(vertex, ImmutablePair.make(index, true));

        for(Integer neighbour : digraph.getNeighbours(vertex))
            if(indices.get(neighbour) == null)
                TopologicalSorting.dfs(neighbour, index, digraph, order, indices);
            else if(Objects.equals(indices.get(neighbour).getFirst(), index) && indices.get(
                    neighbour).getSecond())
                throw new DirectedCyclicGraphException("Given graph contains a cycle.");

        order.add(vertex);
        indices.set(vertex, ImmutablePair.make(index, false));
    }
}
