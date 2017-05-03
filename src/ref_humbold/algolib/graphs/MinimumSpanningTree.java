// ALGORYTMY WYZNACZAJĄCE MINIMALNE DRZEWO SPINAJĄCE
package ref_humbold.algolib.graphs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

import ref_humbold.algolib.structures.Pair;
import ref_humbold.algolib.structures.DisjointSets;

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
        PriorityQueue<Pair<Double, Pair<Integer, Integer>>> edgeQueue = new PriorityQueue<>();
        List<Integer> universe = new ArrayList<>();

        for(int i = 0; i <= wgraph.getVerticesNumber(); ++i)
            universe.add(i);

        DisjointSets<Integer> vertexSets = new DisjointSets<>(universe);

        for(int v : wgraph.getVertices())
            for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v))
            {
                Pair<Double, Pair<Integer, Integer>> weightedEdge = new Pair<>(-e.getSecond(),
                                                                               new Pair<>(e
                                                                                   .getFirst(), v));

                edgeQueue.add(weightedEdge);
            }

        while(numComponents > 1 && !edgeQueue.isEmpty())
        {
            Double edgeWeight = -edgeQueue.peek().getFirst();
            Pair<Integer, Integer> edge = edgeQueue.peek().getSecond();

            edgeQueue.poll();

            if(vertexSets.isSetDifferent(edge.getFirst(), edge.getSecond()))
            {
                sizeMST += edgeWeight;
                --numComponents;
                vertexSets.unionSet(edge.getFirst(), edge.getSecond());
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
        List<Boolean> isVisited = new ArrayList<>(Collections.nCopies(wgraph.getVerticesNumber(),
                                                                      false));

        vertexQueue.add(new Pair<>(0.0, source));

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
                        vertexQueue.add(new Pair<Double, Integer>(-e.getSecond(), e.getFirst()));
            }
        }

        return sizeMST;
    }
}
