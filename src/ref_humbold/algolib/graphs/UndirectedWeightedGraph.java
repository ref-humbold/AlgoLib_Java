// STRUKTURA NIESKIEROWANEGO GRAFU WAŻONEGO
package ref_humbold.algolib.graphs;

import java.util.ArrayList;
import java.util.List;

import ref_humbold.algolib.structures.Pair;

public class UndirectedWeightedGraph
    extends UndirectedGraph
    implements WeightedGraph
{
    public UndirectedWeightedGraph(int n)
    {
        super(n);
    }

    public UndirectedWeightedGraph(int n, List<Pair<Pair<Integer, Integer>, Double>> edges)
    {
        super(n);

        for(Pair<Pair<Integer, Integer>, Double> e : edges)
        {
            graphrepr.get(e.getFirst().getFirst()).add(new Pair<>(e.getFirst().getSecond(),
                                                                  e.getSecond()));
            graphrepr.get(e.getFirst().getSecond()).add(new Pair<>(e.getFirst().getSecond(),
                                                                   e.getSecond()));
        }
    }

    /** @see WeightedGraph#getWeightedEdges */
    @Override
    public Iterable<Pair<Pair<Integer, Integer>, Double>> getWeightedEdges()
    {
        List<Pair<Pair<Integer, Integer>, Double>> edges = new ArrayList<Pair<Pair<Integer, Integer>, Double>>();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                if(e.getFirst() > v)
                {
                    Pair<Integer, Integer> edge = new Pair<Integer, Integer>(v, e.getFirst());

                    edges.add(new Pair<Pair<Integer, Integer>, Double>(edge, e.getSecond()));
                }

        return edges;
    }

    /** @see WeightedGraph#addWeightedEdge */
    @Override
    public void addEdge(Integer vertex1, Integer vertex2, Double weight)
    {
        if(vertex1 < 0 || vertex1 > getVerticesNumber() || vertex2 < 0 || vertex2 > getVerticesNumber())
            throw new IllegalArgumentException("No such vertex.");

        graphrepr.get(vertex1).add(new Pair<>(vertex2, weight));
        graphrepr.get(vertex2).add(new Pair<>(vertex1, weight));
    }

    /** @see WeightedGraph#getWeightedNeighbours */
    @Override
    public Iterable<Pair<Integer, Double>> getWeightedNeighbours(Integer vertex)
    {
        return graphrepr.get(vertex);
    }
}
