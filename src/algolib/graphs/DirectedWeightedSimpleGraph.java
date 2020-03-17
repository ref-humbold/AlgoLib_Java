// STRUKTURA SKIEROWANEGO GRAFU WAŻONEGO
package algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algolib.tuples.ComparablePair;
import algolib.tuples.Triple;
import algolib.tuples.Pair;

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
            addWeightedEdge(e.first, e.second, e.third);
    }

    @Override
    public Collection<Triple<Integer, Integer, Double>> getWeightedEdges()
    {
        List<Triple<Integer, Integer, Double>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                edges.add(Triple.make(v, e.first, e.second));

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
            revgraphrepr.get(e.second).add(ComparablePair.make(e.first, e.third));

        graphrepr = revgraphrepr;
    }
}
