// ALGORYTM: SORTOWANIE TOPOLOGICZNE GRAFU SKIEROWANEGO
package algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import refhumbold.algolib.graphs.searching.SearchingStrategy;

public class TopologicalSorting
{
    /**
     * Algorytm sortowania topologicznego przez liczenie poprzedników.
     * @param digraph graf skierowany
     * @return porządek topologiczny wierzchołków
     */
    public static List<Integer> sortTopological1(DirectedGraph digraph)
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
    {
        List<Integer> vertices = new ArrayList<>(digraph.getVertices());
        TopologicalStrategy strategy = new TopologicalStrategy();

        Collections.sort(vertices);
        Collections.reverse(vertices);

        Searching.dfsr(digraph, strategy, vertices.<Integer>toArray(new Integer[0]));

        Collections.reverse(strategy.getOrder());

        return strategy.getOrder();
    }

    private static class TopologicalStrategy
            implements SearchingStrategy
    {
        private List<Integer> order = new ArrayList<>();

        public List<Integer> getOrder()
        {
            return order;
        }

        @Override
        public void preprocess(int vertex)
        {
        }

        @Override
        public void postprocess(int vertex)
        {
            order.add(vertex);
        }

        @Override
        public void onCycle(int vertex, int neighbour)
        {
            throw new DirectedCyclicGraphException("Given graph contains a cycle.");
        }
    }
}
