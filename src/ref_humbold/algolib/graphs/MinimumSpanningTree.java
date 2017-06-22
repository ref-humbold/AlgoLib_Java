// ALGORYTMY WYZNACZAJĄCE MINIMALNE DRZEWO SPINAJĄCE
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import ref_humbold.algolib.structures.DisjointSets;
import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.structures.Triple;

public class MinimumSpanningTree
{
    /**
     * Algorytm Kruskala wyliczający długość MST.
     * @param wgraph graf ważony
     * @return długość minimalnego drzewa spinającego
     */
    public static double kruskal(WeightedGraph wgraph)
    {
        double sizeMST = 0.0;
        int numComponents = wgraph.getVerticesNumber();
        PriorityQueue<Triple<Double, Integer, Integer>> edgeQueue = new PriorityQueue<>();
        List<Integer> universe = new ArrayList<>();

        for(int i = 0; i <= wgraph.getVerticesNumber(); ++i)
            universe.add(i);

        DisjointSets<Integer> vertexSets = new DisjointSets<>(universe);

        for(int v : wgraph.getVertices())
            for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v))
                edgeQueue.add(Triple.create(-e.getSecond(), e.getFirst(), v));

        while(numComponents > 1 && !edgeQueue.isEmpty())
        {
            Double edgeWeight = -edgeQueue.peek().getFirst();
            Integer vertex1 = edgeQueue.peek().getSecond();
            Integer vertex2 = edgeQueue.peek().getThird();

            edgeQueue.poll();

            if(!vertexSets.isSameSet(vertex1, vertex2))
            {
                sizeMST += edgeWeight;
                --numComponents;
                vertexSets.unionSet(vertex1, vertex2);
            }
        }

        return sizeMST;
    }

    /**
     * Algorytm Prima wyliczający długość MST.
     * @param wgraph graf ważony
     * @param source początkowy wierzchołek
     * @return długość minimalnego drzewa spinającego
     */
    public static double prim(WeightedGraph wgraph, int source)
    {
        double sizeMST = 0.0;
        PriorityQueue<Pair<Double, Integer>> vertexQueue = new PriorityQueue<>();
        List<Boolean> isVisited = new ArrayList<>(
            Collections.nCopies(wgraph.getVerticesNumber(), false));

        vertexQueue.add(Pair.create(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Double edgeWeight = -vertexQueue.peek().getFirst();
            Integer v = vertexQueue.peek().getSecond();

            vertexQueue.poll();

            if(!isVisited.get(v))
            {
                isVisited.set(v, true);
                sizeMST += edgeWeight;

                for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v))
                    if(!isVisited.get(e.getFirst()))
                        vertexQueue.add(new Pair<>(-e.getSecond(), e.getFirst()));
            }
        }

        return sizeMST;
    }
}
