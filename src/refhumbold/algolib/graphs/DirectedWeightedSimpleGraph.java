// STRUKTURA SKIEROWANEGO GRAFU WAŻONEGO
package refhumbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import refhumbold.algolib.graphs.exceptions.NoSuchVertexException;
import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.ImmutableTriple;
import refhumbold.algolib.tuples.Pair;

public class DirectedWeightedSimpleGraph
        extends DirectedSimpleGraph
        implements DirectedWeightedGraph
{
    public DirectedWeightedSimpleGraph(int n)
    {
        super(n);
    }

    public DirectedWeightedSimpleGraph(int n,
                                       Iterable<ImmutableTriple<Integer, Integer, Double>> edges)
            throws NoSuchVertexException
    {
        super(n);

        for(ImmutableTriple<Integer, Integer, Double> e : edges)
            addWeightedEdge(e.getFirst(), e.getSecond(), e.getThird());
    }

    @Override
    public Collection<ImmutableTriple<Integer, Integer, Double>> getWeightedEdges()
    {
        List<ImmutableTriple<Integer, Integer, Double>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                edges.add(ImmutableTriple.make(v, e.getFirst(), e.getSecond()));

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

        for(ImmutableTriple<Integer, Integer, Double> e : getWeightedEdges())
            revgraphrepr.get(e.getSecond()).add(ComparablePair.make(e.getFirst(), e.getThird()));

        graphrepr = revgraphrepr;
    }
}
