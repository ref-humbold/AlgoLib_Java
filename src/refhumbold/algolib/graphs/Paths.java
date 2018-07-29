// ALGORYTMY WYLICZANIA NAJKRÓTSZYCH ŚCIEŻEK W GRAFIE WAŻONYM
package refhumbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.Pair;
import refhumbold.algolib.tuples.Triple;

public class Paths
{
    /**
     * Algorytm Bellmana-Forda.
     * @param diwgraph skierowany graf ważony
     * @param source wierzchołek początkowy
     * @return lista odległości wierzchołków
     */
    public static List<Double> bellmanFord(DirectedWeightedGraph diwgraph, int source)
        throws IllegalStateException
    {
        List<Double> distances = new ArrayList<>(
            Collections.nCopies(diwgraph.getVerticesNumber(), Graph.INF));

        distances.set(source, 0.0);

        for(int u = 0; u < diwgraph.getVerticesNumber() - 1; ++u)
            for(Integer v : diwgraph.getVertices())
                for(Pair<Integer, Double> e : diwgraph.getWeightedNeighbours(v))
                    distances.set(e.getFirst(), Math.min(distances.get(e.getFirst()),
                                                         distances.get(v) + e.getSecond()));

        for(Integer v : diwgraph.getVertices())
            for(Pair<Integer, Double> e : diwgraph.getWeightedNeighbours(v))
                if(distances.get(v) < Graph.INF && distances.get(v) + e.getSecond() < distances.get(
                    e.getFirst()))
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
        for(Triple<Integer, Integer, Double> wedge : wgraph.getWeightedEdges())
            if(wedge.getThird() < 0.0)
                throw new IllegalStateException("Graph contains an edge with negative weight.");

        List<Double> distances = new ArrayList<>(
            Collections.nCopies(wgraph.getVerticesNumber(), WeightedGraph.INF));
        List<Boolean> isVisited = new ArrayList<>(
            Collections.nCopies(wgraph.getVerticesNumber(), false));
        PriorityQueue<ComparablePair<Double, Integer>> vertexQueue = new PriorityQueue<>();

        distances.set(source, 0.0);
        vertexQueue.add(ComparablePair.make(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Integer v = vertexQueue.remove().getSecond();

            if(!isVisited.get(v))
            {
                isVisited.set(v, true);

                for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v))
                {
                    Integer nb = e.getFirst();
                    Double wg = e.getSecond();

                    if(distances.get(v) + wg < distances.get(nb))
                    {
                        distances.set(nb, distances.get(v) + wg);
                        vertexQueue.add(ComparablePair.make(distances.get(nb), nb));
                    }
                }
            }
        }

        return distances;
    }

    /**
     * Algorytm Floyda-Warshalla.
     * @param diwgraph skierowany graf ważony
     * @return macierz odległości wierzchołków
     */
    public static double[][] floydWarshall(DirectedWeightedGraph diwgraph)
    {
        double[][] distances = new double[diwgraph.getVerticesNumber()][diwgraph.getVerticesNumber()];

        for(int i = 0; i < distances.length; ++i)
            for(int j = 0; j < distances[i].length; ++j)
                distances[i][j] = i == j ? 0.0 : Graph.INF;

        for(Triple<Integer, Integer, Double> e : diwgraph.getWeightedEdges())
            distances[e.getFirst()][e.getSecond()] = e.getThird();

        for(Integer w : diwgraph.getVertices())
            for(Integer v : diwgraph.getVertices())
                for(Integer u : diwgraph.getVertices())
                    distances[v][u] = Math.min(distances[v][u], distances[v][w] + distances[w][u]);

        return distances;
    }
}