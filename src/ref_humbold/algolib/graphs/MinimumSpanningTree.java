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
    Algorytm Kruskala wyliczający długość MST.
    @param wgraph graf ważony
    @return długość minimalnego drzewa spinającego
    */
    public static double kruskal(WeightedGraph wgraph)
    {
        double sizeMST = 0.0;
        int numComponents = wgraph.getVerticesNumber();
        PriorityQueue< Pair< Double, Pair<Integer, Integer> > > edgeQueue =
            new PriorityQueue<>();
        List<Integer> universe = new ArrayList<>();

        for(int i = 0; i <= wgraph.getVerticesNumber(); ++i)
            universe.add(i);

        DisjointSets <Integer> vertexSets = new DisjointSets <>(universe);

        for(int v = 1; v <= wgraph.getVerticesNumber(); ++v)
            for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(v))
            {
                Pair< Double, Pair<Integer, Integer> > weightedEdge =
                    new Pair<>(-e.second, new Pair<Integer, Integer>(e.first, v));

                edgeQueue.add(weightedEdge);
            }

        while(numComponents > 1 && !edgeQueue.isEmpty())
        {
            Double edgeWeight = -edgeQueue.peek().first;
            Pair<Integer, Integer> edge = edgeQueue.peek().second;

            edgeQueue.poll();

            if(vertexSets.isSetDifferent(edge.first, edge.second))
            {
                sizeMST += edgeWeight;
                --numComponents;
                vertexSets.unionSet(edge.first, edge.second);
            }
        }

        return sizeMST;
    }

    /**
    Algorytm Prima wyliczający długość MST.
    @param wgraph graf ważony
    @param source początkowy wierzchołek
    @return długość minimalnego drzewa spinającego
    */
    public static double prim(WeightedGraph wgraph, int source)
    {
        double sizeMST = 0.0;
        PriorityQueue< Pair<Double, Integer> > vertexQueue = new PriorityQueue<>();
        List<Boolean> isVisited =
            new ArrayList<>(Collections.nCopies(wgraph.getVerticesNumber(), false));

        vertexQueue.add(new Pair(0.0, source));

        while(!vertexQueue.isEmpty())
        {
            Double edgeWeight = -vertexQueue.peek().first;
            Integer w = vertexQueue.peek().second;

            vertexQueue.poll();

            if(!isVisited.get(w))
            {
                isVisited.set(w, true);
                sizeMST += edgeWeight;

                for(Pair<Integer, Double> e : wgraph.getWeightedNeighbours(w))
                    if(!isVisited.get(e.first))
                        vertexQueue.add(new Pair<Double, Integer>(-e.second, e.first));
            }
        }

        return sizeMST;
    }
}

