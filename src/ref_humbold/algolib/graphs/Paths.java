// ALGORYTMY WYLICZANIA NAJKRÓTSZYCH ŚCIEŻEK W GRAFIE WAŻONYM
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.structures.Triple;

public class Paths
{
    /**
     * Algorytm Bellmana-Forda.
     * @param diwgraph graf ważony
     * @param source wierzchołek początkowy
     * @return lista odległości wierzchołków
     */
    public static List<Double> bellmanFord(DirectedWeightedSimpleGraph diwgraph, int source)
        throws IllegalStateException
    {
        List<Double> distances = new ArrayList<>(
            Collections.nCopies(diwgraph.getVerticesNumber(), DirectedWeightedSimpleGraph.INF));

        for(int u = 0; u < diwgraph.getVerticesNumber() - 1; ++u)
            for(Integer v : diwgraph.getVertices())
                for(Pair<Integer, Double> e : diwgraph.getWeightedNeighbours(v))
                    distances.set(e.getFirst(), Math.min(distances.get(e.getFirst()),
                                                         distances.get(v) + e.getSecond()));

        for(Integer v : diwgraph.getVertices())
            for(Pair<Integer, Double> e : diwgraph.getWeightedNeighbours(v))
                if(distances.get(v) < DirectedWeightedSimpleGraph.INF
                   && distances.get(v) + e.getSecond() < distances.get(e.getFirst()))
                    throw new IllegalStateException("Graph contains a negative cycle.");

        return distances;
    }

    /**
     * Algorytm Dijkstry.
     * @param wgraph graf ważony z wagami nieujemnymi
     * @param source wierzchołek początkowy
     * @return lista odległości wierzchołków
     */
    public static List<Double> dijkstra(WeightedGraph wgraph, int source)
        throws IllegalStateException
    {
        List<Double> distances = new ArrayList<>(
            Collections.nCopies(wgraph.getVerticesNumber(), WeightedGraph.INF));
        List<Boolean> isVisited = new ArrayList<>(
            Collections.nCopies(wgraph.getVerticesNumber(), false));
        PriorityQueue<Pair<Double, Integer>> vertexQueue = new PriorityQueue<>();

        distances.set(source, 0.0);
        vertexQueue.add(new Pair<>(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Integer v = vertexQueue.poll().getSecond();

            if(!isVisited.get(v))
            {
                isVisited.set(v, true);

                for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v))
                {
                    Integer nb = e.getFirst();
                    Double wg = e.getSecond();

                    if(wg < 0)
                        throw new IllegalStateException("Graph contains a negative weighted edge.");

                    if(distances.get(v) + wg < distances.get(nb))
                    {
                        distances.set(nb, distances.get(v) + wg);
                        vertexQueue.add(Pair.make(-distances.get(nb), nb));
                    }
                }
            }
        }

        return distances;
    }

    /**
     * Algorytm Floyda-Warshalla.
     * @param diwgraph graf ważony
     * @return macierz odległości
     */
    public static double[][] floydWarshall(DirectedWeightedSimpleGraph diwgraph)
    {
        double[][] distances = new double[diwgraph.getVerticesNumber()][diwgraph.getVerticesNumber()];

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                distances[i][j] = i == j ? 0.0 : DirectedWeightedSimpleGraph.INF;

        for(Triple<Integer, Integer, Double> e : diwgraph.getWeightedEdges())
            distances[e.getFirst()][e.getSecond()] = e.getThird();

        for(int x : diwgraph.getVertices())
            for(int v : diwgraph.getVertices())
                for(int u : diwgraph.getVertices())
                    distances[v][u] = Math.min(distances[v][u], distances[v][x] + distances[x][u]);

        return distances;
    }
}
