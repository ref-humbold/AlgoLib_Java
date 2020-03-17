// ALGORYTMY WYZNACZAJĄCE MINIMALNE DRZEWO SPINAJĄCE
package algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import algolib.structures.DisjointSets;
import algolib.tuples.ComparablePair;
import algolib.tuples.ComparableTriple;
import algolib.tuples.Pair;

public class MinimalSpanningTree
{
    /**
     * Algorytm Kruskala wyliczający długość MST
     * @param uwgraph graf ważony
     * @return długość minimalnego drzewa spinającego
     */
    public static double kruskal(UndirectedWeightedGraph uwgraph)
    {
        double sizeMST = 0.0;
        int components = uwgraph.getVerticesNumber();
        PriorityQueue<ComparableTriple<Double, Integer, Integer>> edgeQueue = new PriorityQueue<>();
        DisjointSets<Integer> vertexSets = new DisjointSets<>(uwgraph.getVertices());

        for(int v : uwgraph.getVertices())
            for(Pair<Integer, Double> e : uwgraph.getWeightedNeighbours(v))
                edgeQueue.add(ComparableTriple.make(e.second, e.first, v));

        while(components > 1 && !edgeQueue.isEmpty())
        {
            Double edgeWeight = edgeQueue.element().first;
            Integer vertex1 = edgeQueue.element().second;
            Integer vertex2 = edgeQueue.element().third;

            edgeQueue.remove();

            if(!vertexSets.isSameSet(vertex1, vertex2))
            {
                sizeMST += edgeWeight;
                --components;
                vertexSets.unionSet(vertex1, vertex2);
            }
        }

        return sizeMST;
    }

    /**
     * Algorytm Prima wyliczający długość MST
     * @param uwgraph graf ważony
     * @param source początkowy wierzchołek
     * @return długość minimalnego drzewa spinającego
     */
    public static double prim(UndirectedWeightedGraph uwgraph, int source)
    {
        double sizeMST = 0.0;
        PriorityQueue<ComparablePair<Double, Integer>> vertexQueue = new PriorityQueue<>();
        List<Boolean> isVisited =
                new ArrayList<>(Collections.nCopies(uwgraph.getVerticesNumber(), false));

        vertexQueue.add(ComparablePair.make(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Double edgeWeight = vertexQueue.element().first;
            Integer v = vertexQueue.element().second;

            vertexQueue.remove();

            if(!isVisited.get(v))
            {
                isVisited.set(v, true);
                sizeMST += edgeWeight;

                for(Pair<Integer, Double> e : uwgraph.getWeightedNeighbours(v))
                    if(!isVisited.get(e.first))
                        vertexQueue.add(ComparablePair.make(e.second, e.first));
            }
        }

        return sizeMST;
    }
}
