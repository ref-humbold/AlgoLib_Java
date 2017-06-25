// ALGORYTMY WYZNACZAJĄCE MINIMALNE DRZEWO SPINAJĄCE
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import ref_humbold.algolib.structures.DisjointSets;
import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.structures.Triple;

public class MST
{
    /**
     * Algorytm Kruskala wyliczający długość MST.
     * @param uwgraph graf ważony
     * @return długość minimalnego drzewa spinającego
     */
    public static double kruskal(UndirectedWeightedGraph uwgraph)
    {
        double sizeMST = 0.0;
        int components = uwgraph.getVerticesNumber();
        PriorityQueue<Triple<Double, Integer, Integer>> edgeQueue = new PriorityQueue<>();
        DisjointSets<Integer> vertexSets = new DisjointSets<>(uwgraph.getVertices());

        for(int v : uwgraph.getVertices())
            for(Pair<Integer, Double> e : uwgraph.getWeightedNeighbours(v))
                edgeQueue.add(Triple.make(e.getSecond(), e.getFirst(), v));

        while(components > 1 && !edgeQueue.isEmpty())
        {
            Double edgeWeight = edgeQueue.peek().getFirst();
            Integer vertex1 = edgeQueue.peek().getSecond();
            Integer vertex2 = edgeQueue.peek().getThird();

            edgeQueue.poll();

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
     * Algorytm Prima wyliczający długość MST.
     * @param uwgraph graf ważony
     * @param source początkowy wierzchołek
     * @return długość minimalnego drzewa spinającego
     */
    public static double prim(UndirectedWeightedGraph uwgraph, int source)
    {
        double sizeMST = 0.0;
        PriorityQueue<Pair<Double, Integer>> vertexQueue = new PriorityQueue<>();
        List<Boolean> isVisited = new ArrayList<>(
            Collections.nCopies(uwgraph.getVerticesNumber(), false));

        vertexQueue.add(Pair.make(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Double edgeWeight = vertexQueue.peek().getFirst();
            Integer v = vertexQueue.peek().getSecond();

            vertexQueue.poll();

            if(!isVisited.get(v))
            {
                isVisited.set(v, true);
                sizeMST += edgeWeight;

                for(Pair<Integer, Double> e : uwgraph.getWeightedNeighbours(v))
                    if(!isVisited.get(e.getFirst()))
                        vertexQueue.add(Pair.make(e.getSecond(), e.getFirst()));
            }
        }

        return sizeMST;
    }
}
