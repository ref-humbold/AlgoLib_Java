// STRUKTURA NIESKIEROWANEGO GRAFU WAŻONEGO
package refhumbold.algolib.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import refhumbold.algolib.tuples.ComparablePair;
import refhumbold.algolib.tuples.ImmutableTriple;
import refhumbold.algolib.tuples.Pair;

public class UndirectedWeightedSimpleGraph
    extends UndirectedSimpleGraph
    implements UndirectedWeightedGraph
{
    public UndirectedWeightedSimpleGraph(int n)
    {
        super(n);
    }

    public UndirectedWeightedSimpleGraph(int n,
                                         Iterable<ImmutableTriple<Integer, Integer, Double>> edges)
        throws NoSuchVertexException
    {
        super(n);

        for(ImmutableTriple<Integer, Integer, Double> e : edges)
            this.addWeightedEdge(e.getFirst(), e.getSecond(), e.getThird());
    }

    @Override
    public Collection<ImmutableTriple<Integer, Integer, Double>> getWeightedEdges()
    {
        List<ImmutableTriple<Integer, Integer, Double>> edges = new ArrayList<>();

        for(Integer v : getVertices())
            for(Pair<Integer, Double> e : getWeightedNeighbours(v))
                if(e.getFirst() >= v)
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
        graphrepr.get(vertex2).add(ComparablePair.make(vertex1, weight));
    }

    @Override
    public Collection<ComparablePair<Integer, Double>> getWeightedNeighbours(Integer vertex)
    {
        return new ArrayList<>(graphrepr.get(vertex));
    }

    @Override
    public DirectedWeightedSimpleGraph asDirected()
    {
        Collection<ImmutableTriple<Integer, Integer, Double>> diwedges = getWeightedEdges();

        for(ImmutableTriple<Integer, Integer, Double> e : getWeightedEdges())
            diwedges.add(ImmutableTriple.make(e.getSecond(), e.getFirst(), e.getThird()));

        return new DirectedWeightedSimpleGraph(getVerticesNumber(), diwedges);
    }
}
