// ALGORYTMY WYLICZANIA NAJKRÓTSZYCH ŚCIEŻEK W GRAFIE WAŻONYM
package ref_humbold.algolib.graphs;

import java.lang.Math;
import java.lang.IllegalStateException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.graphs.WeightedGraph;

public class Paths
{
    /** Oznaczenie nieskończoności. */
    private static double INF = Double.POSITIVE_INFINITY;

    /**
    Algorytm Bellmana-Forda.
    @param wgraph graf ważony
    @param source wierzchołek początkowy
    @return lista odległości wierzchołków
    */
    public static List<Double> bellmanFord(WeightedGraph wgraph, int source)
        throws IllegalStateException
    {
        List<Double> distances =
            new ArrayList<>( Collections.nCopies(wgraph.getVerticesNumber(), INF) );

        for(int u = 0; u < wgraph.getVerticesNumber()-1; ++u)
            for(Integer v : wgraph.getVertices())
                for( Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v) )
                    distances.set( e.first, Math.min(distances.get(e.first), distances.get(v)+e.second) );

        for(Integer v : wgraph.getVertices())
            for( Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v) )
                if( distances.get(v) < INF && distances.get(v)+e.second < distances.get(e.first) )
                    throw new IllegalStateException("Graph contains a negative cycle.");

        return distances;
    }

    /**
    Algorytm Dijkstry.
    @param wgraph graf ważony z wagami nieujemnymi
    @param source wierzchołek początkowy
    @return lista odległości wierzchołków
    */
    public static List<Double> dijkstra(WeightedGraph wgraph, int source)
        throws IllegalStateException
    {
        List<Double> distances =
            new ArrayList<>( Collections.nCopies(wgraph.getVerticesNumber(), INF) );
        List<Boolean> isVisited =
            new ArrayList<>( Collections.nCopies(wgraph.getVerticesNumber(), false) );
        PriorityQueue< Pair<Double, Integer> > vertexQueue = new PriorityQueue<>();

        distances.set(source, 0.0);
        vertexQueue.add( new Pair<Double, Integer>(0.0, source) );

        while(!vertexQueue.isEmpty())
        {
            Integer v = vertexQueue.poll().second;

            if( !isVisited.get(v) )
            {
                isVisited.set(v, true);

                for( Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v) )
                {
                    Integer nb = e.first;
                    Double wg = e.second;

                    if(wg < 0)
                        throw new IllegalStateException("Graph contains a negative weighted edge.");

                    if( distances.get(v)+wg < distances.get(nb) )
                    {
                        distances.set(nb, distances.get(v)+wg);
                        vertexQueue.add( new Pair<Double, Integer>(-distances.get(nb), nb) );
                    }
                }
            }
        }

        return distances;
    }

    /**
    Algorytm Floyda-Warshalla.
    @param wgraph graf ważony
    @return macierz odległości
    */
    public static double[][] floydWarshall(WeightedGraph wgraph)
    {
        double[][] distances = wgraph.getAdjacencyMatrix();

        for(int x = 1; x <= wgraph.getVerticesNumber(); ++x)
            for(int w = 1; w <= wgraph.getVerticesNumber(); ++w)
                for(int u = 1; u <= wgraph.getVerticesNumber(); ++u)
                    distances[w][u] = Math.min(distances[w][u], distances[w][x]+distances[x][u]);

        return distances;
    }
}
