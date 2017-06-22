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
     * Oznaczenie nieskończoności.
     */
    private static double INF = Double.POSITIVE_INFINITY;

    /**
     * Algorytm Bellmana-Forda.
     * @param dwgraph graf ważony
     * @param source wierzchołek początkowy
     * @return lista odległości wierzchołków
     */
    public static List<Double> bellmanFord(DirectedWeightedGraph dwgraph, int source)
        throws IllegalStateException
    {
        List<Double> distances = new ArrayList<>(
            Collections.nCopies(dwgraph.getVerticesNumber(), INF));

        for(int u = 0; u < dwgraph.getVerticesNumber() - 1; ++u)
            for(Integer v : dwgraph.getVertices())
                for(Pair<Integer, Double> e : dwgraph.getWeightedNeighbours(v))
                    distances.set(e.getFirst(), Math.min(distances.get(e.getFirst()),
                                                         distances.get(v) + e.getSecond()));

        for(Integer v : dwgraph.getVertices())
            for(Pair<Integer, Double> e : dwgraph.getWeightedNeighbours(v))
                if(distances.get(v) < INF && distances.get(v) + e.getSecond() < distances
                    .get(e.getFirst()))
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
            Collections.nCopies(wgraph.getVerticesNumber(), INF));
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
                        vertexQueue.add(new Pair<>(-distances.get(nb), nb));
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
    public static double[][] floydWarshall(DirectedWeightedGraph diwgraph)
    {
        int vertnum = diwgraph.getVerticesNumber();

        double[][] distances = new double[vertnum][vertnum];

        for(Triple<Integer, Integer, Double> e : diwgraph.getWeightedEdges())
        {
            distances[e.getFirst()][e.getSecond()] = e.getThird();
        }

        for(int x : diwgraph.getVertices())
            for(int v : diwgraph.getVertices())
                for(int u : diwgraph.getVertices())
                    distances[v][u] = Math.min(distances[v][u], distances[v][x] + distances[x][u]);

        return distances;
    }
}
