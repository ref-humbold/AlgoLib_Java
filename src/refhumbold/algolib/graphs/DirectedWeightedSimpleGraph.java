// STRUKTURA SKIEROWANEGO GRAFU WAŻONEGO
package refhumbold.algolib.graphs;

import java.util.*;

import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.Pair;
import refhumbold.algolib.tuples.Triple;

public class DirectedWeightedSimpleGraph
    extends DirectedSimpleGraph
    implements DirectedWeightedGraph
{
    public DirectedWeightedSimpleGraph(int n)
    {
        super(n);
    }

    public DirectedWeightedSimpleGraph(int n, Iterable<Triple<Integer, Integer, Double>> edges)
        throws NoSuchVertexException
    {
        super(n);

        for(Triple<Integer, Integer, Double> e : edges)
            this.addWeightedEdge(e.getFirst(), e.getSecond(), e.getThird());
    }

    @Override
    public Collection<Triple<Integer, Integer, Double>> getWeightedEdges()
    {
        List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                edges.add(Triple.make(v, e.getFirst(), e.getSecond()));

        return edges;
    }

    @Override
    public void addWeightedEdge(Integer vertex1, Integer vertex2, Double weight)
        throws NoSuchVertexException
    {
        if(vertex1 < 0 || vertex1 >= getVerticesNumber())
            throw new NoSuchVertexException(vertex1.toString());

        if(vertex2 < 0 || vertex2 >= getVerticesNumber())
            throw new NoSuchVertexException(vertex2.toString());

        graphrepr.get(vertex1).add(ComparablePair.make(vertex2, weight));
    }

    @Override
    public Collection<ComparablePair<Integer, Double>> getWeightedNeighbours(Integer vertex)
    {
        return new ArrayList<>(graphrepr.get(vertex));
    }

    @Override
    public void reverse()
    {
        List<Set<ComparablePair<Integer, Double>>> revgraphrepr = new ArrayList<>();

        for(Integer v : getVertices())
            revgraphrepr.add(new HashSet<>());

        for(Triple<Integer, Integer, Double> e : getWeightedEdges())
            revgraphrepr.get(e.getSecond()).add(ComparablePair.make(e.getFirst(), e.getThird()));

        graphrepr = revgraphrepr;
    }
}
